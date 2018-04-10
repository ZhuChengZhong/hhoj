#include "hhoj_hashmap.h"
#include "hhoj_library.h"
#include "com_hhoj_judge_core_Runner.h"

#include <algorithm>
#include <cmath>
#include <cstdint>
#include <cstring>
#include <future>
#include <iostream>
#include <iterator>
#include <limits>
#include <string>
#include <sstream>
#include <vector>

#include <fcntl.h>
#include <signal.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>

#define  STDIN  0
#define  STDOUT 1
#define  STDERR 2

/**
 * A pointer to an array of character pointers to the environment strings.
 */
extern char** environ;

/**
 * Function Prototypes.
 */
bool createProcess(pid_t&, sigset_t&);
void setupIoRedirection(const std::string&, const std::string&);
void setupRunUser();
int runProcess(pid_t, sigset_t, const std::string&, int, int, int&, int&);
char** getCommandArgs(const std::string& commandLine);
bool isCurrentUsedMemoryIgnored(int, int);
int getMaxUsedMemory(pid_t, int);
int getCurrentUsedMemory(pid_t);
long long getMillisecondsNow();
int killProcess(pid_t&);

/**
 * JNI�������.
 * ��ȡ�������н��.
 * @param  jniEnv          - JNI ���л�������
 * @param  selfReference   - �Ե���Java�Ķ��������
 * @param  jCommandLine    - ��ִ�е�������
 * @param  jUsername       - Unix�û���
 * @param  jPassword       - Unix����
 * @param  jInputFilePath  - ִ�г���ʱ�������ļ�·��(��ΪNULL)
 * @param  jOutputFilePath - ִ�г���������ļ�·��(��ΪNULL)
 * @param  timeLimit       - ����ִ��ʱ������(ms, 0Ϊ������)
 * @param  memoryLimit     - ����ִ���ڴ�����(KB, 0Ϊ������)
 * @return һ���������н����Map<String, Object>����
 */
JNIEXPORT jobject JNICALL Java_com_hhoj_judger_core_Runner_runCommand(
    JNIEnv* jniEnv, jobject selfReference, jstring jCommandLine, jstring jUsername,
    jstring jPassword, jstring jInputFilePath, jstring jOutputFilePath, jint timeLimit, 
    jint memoryLimit) {
    std::string commandLine         = getStringValue(jniEnv, jCommandLine);
    std::string inputFilePath       = getStringValue(jniEnv, jInputFilePath);
    std::string outputFilePath      = getStringValue(jniEnv, jOutputFilePath);

    std::cout << "Command Line: " << commandLine << std::endl;

    JHashMap    result;
    jint        usedTime            = 0;
    jint        usedMemory          = 0;
    jint        exitCode            = 127;

    pid_t       pid                 = -1;
    sigset_t    sigset;
    if ( !createProcess(pid, sigset) ) {
        throwCStringException(jniEnv, "Failed to fork a process.");
    }
    // Setup I/O Redirection for Child Process
    if ( pid == 0 ) {
        //setupRunUser();
        setupIoRedirection(inputFilePath, outputFilePath);
    }
    exitCode = runProcess(pid, sigset, commandLine, timeLimit, memoryLimit, usedTime, usedMemory);

    std::cout << "[JNI DEBUG] usedTime: " << usedTime << " ms" << std::endl;
    std::cout << "[JNI DEBUG] usedMemory: " << usedMemory  << " KB" << std::endl;
    std::cout << "[JNI DEBUG] exitCode: " << exitCode << std::endl;
    
    result.put("usedTime", usedTime);
    result.put("usedMemory", usedMemory);
    result.put("exitCode", exitCode);

    return result.toJObject(jniEnv);
}

/**
 * ��������.
 * @param  pid    - ����ID
 * @param  sigset - ���̵ı��
 * @return ���д���״̬(-1��ʾδ�ɹ�����, 0��ʾ�ӽ���)
 */
bool createProcess(pid_t& pid, sigset_t& sigset) {
    sigset_t originalSigset;
    sigemptyset (&sigset);
    sigaddset (&sigset, SIGCHLD);
    if ( sigprocmask(SIG_BLOCK, &sigset, &originalSigset) < 0 ) {
        return false;
    }

    pid         = fork();
    if ( pid == -1 ) {
        return false;
    }
    return true;
}

/**
 * ���ó���I/O�ض���.
 * @param  inputFilePath  - ִ�г���ʱ�������ļ�·��(��ΪNULL)
 * @param  outputFilePath - ִ�г���������ļ�·��(��ΪNULL)
 */
void setupIoRedirection(
    const std::string& inputFilePath, const std::string& outputFilePath) {
    if ( inputFilePath != "" ) {
        int inputFileDescriptor = open(inputFilePath.c_str(), O_RDONLY);
        dup2(inputFileDescriptor, STDIN);
        close(inputFileDescriptor);
    }
    if ( outputFilePath != "" ) {
        int outputFileDescriptor = open(outputFilePath.c_str(), O_CREAT | O_WRONLY);
        chmod(outputFilePath.c_str(), S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
        dup2(outputFileDescriptor, STDOUT);
        dup2(outputFileDescriptor, STDERR);
        close(outputFileDescriptor);
    }
}

void setupRunUser() {
	std::cout <<  "set run user." << std::endl;
    while ( setgid(1000) != 0 ) {
        std::cout <<  "[WARN] setgid(1000) failed." << std::endl;
        sleep(1); 
    }
    while ( setuid(1000) != 0 ) {
        std::cout <<  "[WARN] setuid(1000) failed." << std::endl;
        sleep(1); 
    }
    while ( setresuid(1000, 1000, 1000) != 0 ) {
        std::cout <<  "[WARN] setresuid(1000, 1000, 1000) failed." << std::endl;
        sleep(1);
    }
}

/**
 * ���н���.
 * @param  pid         - �ӽ���ID
 * @param  sigset      - ���̵ı��
 * @param  commandLine - ������
 * @param  timeLimit   - ����ʱʱ������(ms)
 * @param  memoryLimit - ����ʱ�ռ�����(KB)
 * @param  UsedTime    - ����ʱʱ��ռ��(ms)
 * @param  UsedMemory  - ����ʱ�ռ�ռ��(ms)
 * @return �����˳�״̬
 */
int runProcess(pid_t pid, sigset_t sigset, const std::string& commandLine, int timeLimit, 
    int memoryLimit, int& UsedTime, int& UsedMemory) {
    char**            argv       = getCommandArgs(commandLine);
    long long         startTime  = 0;
    long long         endTime    = 0;
    int               exitCode   = 0;
    std::future<int>  feature;

    // Setup Monitor in Parent Process
    if ( pid > 0 ) {
        // Memory Monitor
        feature     = std::async(std::launch::async, getMaxUsedMemory, pid, memoryLimit);
        
        // Time Monitor
        struct timespec timeout;
        timeout.tv_sec  = timeLimit / 1000;
        timeout.tv_nsec = timeLimit % 1000 * 1000000;
        
        startTime       = getMillisecondsNow();
        do {
            if ( sigtimedwait(&sigset, NULL, &timeout) < 0 ) {
                if ( errno == EINTR ) {
                    /* Interrupted by a signal other than SIGCHLD. */
                    continue;
                } else if (errno == EAGAIN) {
                    killProcess(pid);
                } else {
                    return 127;
                }
            }
            break;
        } while ( true );
    }
    // Run Child Process
    if ( pid == 0 ) {
        _exit(execvp(argv[0], argv));
    }

    // Collect information in Parent Process
    waitpid(pid, &exitCode, 0);
    endTime     = getMillisecondsNow();
    UsedTime    = endTime - startTime;
    UsedMemory  = feature.get();

    return exitCode;
}

/**
 * ��ȡ�����в����б�.
 * @param  commandLine - ������
 * @return �����в����б�
 */
char** getCommandArgs(const std::string& commandLine) {
    std::istringstream iss(commandLine);
    std::vector<std::string> args = {
        std::istream_iterator<std::string>{iss},
        std::istream_iterator<std::string>{}
    };

    size_t numberOfArguments = args.size();
    char** argv = new char*[numberOfArguments + 1]();

    for ( size_t i = 0; i < numberOfArguments; ++ i ) {
        char* arg = new char[ args[i].size() + 1 ];
        strcpy(arg, args[i].c_str());
        argv[i] = arg;
    }
    argv[numberOfArguments] = nullptr;

    return argv;
}

/**
 * �Ƿ���Ե�ǰ��õ��ڴ�ռ��ֵ.
 * ������ʵ�����й�����, ������ܻ��ȡ��JVM�����е��ڴ�ռ��.
 * �����������, ����Ӧ�������ֵ.
 * @param  currentUsedMemory - ��ǰ��ȡ�����ڴ�ռ��
 * @param  memoryLimit        - ����ʱ�ռ�����(KB)
 * @return �Ƿ���Ե�ǰ��ȡ�����ڴ�ռ��
 */
bool isCurrentUsedMemoryIgnored(int currentUsedMemory, int memoryLimit) {
    int jvmUsedMemory = getCurrentUsedMemory(getpid());

    if ( currentUsedMemory >= jvmUsedMemory / 2 &&
         currentUsedMemory <= jvmUsedMemory * 2 ) {
        return true;
    }
    return false;
}

/**
 * ��ȡ����ʱ�ڴ�ռ�����ֵ
 * @param  pid         - ����ID
 * @param  memoryLimit - ����ʱ�ռ�����(KB)
 * @return ����ʱ�ڴ�ռ�����ֵ
 */
int getMaxUsedMemory(pid_t pid, int memoryLimit) {
    int  maxUsedMemory     = 0,
         currentUsedMemory = 0;
    do {
        currentUsedMemory = getCurrentUsedMemory(pid);
        std::cout << "currentUsedMemory: [PID #" << pid << "]" << currentUsedMemory << std::endl;

        if ( currentUsedMemory > maxUsedMemory && 
            !isCurrentUsedMemoryIgnored(currentUsedMemory, memoryLimit) ) {
            maxUsedMemory = currentUsedMemory;
        }
        if ( memoryLimit != 0 && maxUsedMemory > memoryLimit ) {
            killProcess(pid);
        }
        usleep(5000);
    } while ( currentUsedMemory != 0 );

    return maxUsedMemory;
}

/**
 * ��ȡ�ڴ�ռ�����.
 * @param  pid - ����ID
 * @return ��ǰ�����ڴ�ʹ����(KB)
 */
int getCurrentUsedMemory(pid_t pid) {
    int    currentUsedMemory   = 0;
    long   residentSetSize      = 0L;
    FILE*  fp                   = NULL;
    
    std::stringstream stringStream;
    stringStream << "/proc/" << pid << "/statm";
    const char* filePath = stringStream.str().c_str();

    if ( (fp = fopen( filePath, "r" )) != NULL ) {
        if ( fscanf(fp, "%*s%ld", &residentSetSize) == 1 ) {
            currentUsedMemory = (int)residentSetSize * (int)sysconf(_SC_PAGESIZE) >> 10;
            if ( currentUsedMemory < 0 ) {
                currentUsedMemory = std::numeric_limits<int32_t>::max() >> 10;
            }
        }
        fclose(fp);
    }
    return currentUsedMemory;
}

/**
 * ��ȡ��ǰϵͳʱ��.
 * ����ͳ�Ƴ�������ʱ��.
 * @return ��ǰϵͳʱ��(�Ժ���Ϊ��λ)
 */
long long getMillisecondsNow() {
    long            milliseconds;
    time_t          seconds;
    struct timespec spec;

    clock_gettime(CLOCK_REALTIME, &spec);
    seconds                 = spec.tv_sec;
    milliseconds            = round(spec.tv_nsec / 1.0e6);
    long long currentTime   = seconds * 1000 + milliseconds;

    return currentTime;
}

/**
 * ǿ�����ٽ���(��������ֵʱ).
 * @param  pid         - ����ID
 * @return 0, ��ʾ���̱��ɹ�����.
 */
int killProcess(pid_t& pid) {
    return kill(pid, SIGKILL);
}
