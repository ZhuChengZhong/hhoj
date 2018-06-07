declare -A files
cat data/input.txt | tail -n +2|awk '{files[$1]=$2}END{for (pid in files) print pid files[pid]}'
echo ${files[3]}
