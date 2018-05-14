if [ $# -lt 1 ]
then
 echo "usage: $0 filename1 filename2...filenameN"
 exit -1 
fi
for filename in $@
do 
cat $filename | sed 's/%/%25/g' | sed 's/&/%26/g' | sed 's/.*/\0%0A/g'|sed 's/+/%2B/g' > ${filename}.temp
done
