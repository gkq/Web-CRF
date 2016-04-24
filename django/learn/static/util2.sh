#!/bin/bash
cd ~/gkq/crf-spark-lab
if [ -e 'test.txt' ]; then
	rm test.txt
fi
echo $* >> test.txt
hadoop fs -rm /user/yarnuser/test.txt > /dev/null
hadoop fs -put test.txt /user/yarnuser
#bin/run-test.sh
if [ -e 'result.txt' ]; then
        rm result.txt
fi

while [ ! -e 'result.txt' ]

do 
	sleep 0.1
done 
RESULTS=$(cat ~/gkq/crf-spark-lab/result.txt)
echo $RESULTS
