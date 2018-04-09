package com.hhoj.judger.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

/**
 * 文件处理类
 * @author zhu
 *
 */
public final class FileUtil {
	public static final String separator="/";
	private FileUtil(){
		
	}
	
    /**
     * 创建新文件并将指定字符串写入文件内
     * @param filePath
     * @param data
     */
	public static boolean createFile(String filePath,String data){
		if(StringUtil.isEmpty(filePath)){
			return false;
		}
		PrintWriter pw=null;
		try {
			File file=new File(filePath);
			pw=new PrintWriter(file);
			pw.println(data);
			return true;
		} catch (FileNotFoundException e) {
			 e.printStackTrace();
			return false;
		}finally {
			if(pw!=null){
				pw.close();
			}
		}
	}
	/**
	 * 创建文件夹
	 * @param dirPath
	 * @return
	 */
	public static boolean mkdir(String dirPath){
		if(StringUtil.isEmpty(dirPath)){
			return false;
		}
		File file=new File(dirPath);
		if(file.exists()){
			return file.isDirectory();
		}
		return file.mkdirs();
	}
	/**
	 * 读取文件内容并以字符串的形式返回
	 * @param fileName
	 * @return
	 */
	public static String getCharsFromFile(String fileName){
		if(StringUtil.isEmpty(fileName)){
			return "";
		}
		BufferedReader reader=null;
		String result="";
		try {
			File file=new File(fileName);
			InputStreamReader ir=new InputStreamReader(new FileInputStream(file));
			reader=new BufferedReader(ir);
			String s=null;
		
			while((s=reader.readLine())!=null){
				result+=s;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	  /**
	   * 
	   * 比较两个文件内容是否相同
	   */
	  public static boolean compare(String standardOutPath,String userOutPath){
		  if(StringUtil.isEmpty(standardOutPath)||StringUtil.isEmpty(userOutPath)){
			  return false;
		  }
		  String standardOut=getCharsFromFile(standardOutPath);
		  String userOut=getCharsFromFile(userOutPath);
		  return standardOut.equals(userOut);
	  }
	  
	  public static boolean rmdir(String dir){
		  if(StringUtil.isEmpty(dir)){
			  return false;
		  }
		  File file=new File(dir);
		  if(file.exists()){
			  file.delete();
		  }
		  return true;
	  }
}
