#!/bin/bash
# 程序运行脚本
# 参数解释  cmd：执行命令   infile: 输入文件  outfile:输出文件  resultfile:结果保存文件  sourcefile:源码文件
if [ $# -ne 5 ]
then
echo "usage : $0 command infile outfile resultfile"
exit -1
fi
cmd=$1
infile=$2
outfile=$3
resultfile=$4
sourcefile=$5
#解析路经设置docker数据卷
indir=`echo $infile | sed 's:[^/]*$::g'`
outdir=`echo $outfile | sed 's:[^/]*$::g'`
resultdir=`echo $resultfile | sed 's:[^/]*$::g'`
sourcedir=`echo $sourcefile | sed 's:[^/]*$::g'`
# 执行命令
runcmd="docker run ";
if [ -n "$infile" ]
then
runcmd="$runcmd -v $indir:$indir"
fi
if [ -n "$outfile" ]
then
runcmd="$runcmd -v $outdir:$outdir"
fi
runcmd="$runcmd -v $resultdir:$resultdir -v $sourcedir:$sourcedir"
runcmd="$runcmd judger  /usr/bin/time  -f 'time %e  memary %M'   -o  $resultfile  $cmd"
if [ -n "$infile" ]
then
runcmd="$runcmd <$infile >$outfile"
fi
eval $runcmd
#`docker run -v $indir:$indir -v $outdir:$outdir -v $resultdir:$resultdir -v $sourcedir:$sourcedir \
# judger  /usr/bin/time  -f "time %e memary %M"   -o  $resultfile  $cmd <$infile >$outfile`
echo "result $?" >> $resultfile 
