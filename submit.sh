#!/bin/bash
if [ $# -ne 3 ]
then
 echo "Usage : $0 datafile sendcount jsessionid"
 exit -1
fi
datafile=$1
sendcount=$2
jsessionid=$3
# 将文件中的pid 与 输入文件转化为关联数组 files[pid]=filename
declare -A files=`cat $datafile | tail -n +2|awk 'BEGIN{array="("} {array=array" ["$1"]="$2}
  END{
    array=array")";
    print array;
  }'`
for pid in ${!files[@]}
do
url="http://localhost:8080/hhoj/submit/problem/${pid}/add";
curl -d "@${files[$pid]}" $url --cookie "JSESSIONID=${jsessionid}"
done
