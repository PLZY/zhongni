#!/bin/sh
APP_NAME=server.jar
echo $APP_NAME
tokill=`ps -ef | grep $APP_NAME | awk 'NR>1{print p}{p=$2}'`
kill -9 $tokill
echo "success"