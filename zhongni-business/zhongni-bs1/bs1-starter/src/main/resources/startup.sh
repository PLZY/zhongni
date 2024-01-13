#!/bin/sh
APP_NAME=server.jar
echo $APP_NAME
nohup java -Xms1024m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:gc.log -Dfile.encoding=utf-8 -jar $APP_NAME &