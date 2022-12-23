#!/bin/bash

cd `dirname $0`

workspace=$(pwd)

pid=$(ps -ef |grep "lib/${project.artifactId}-*.*.*.jar" | grep java | grep -v "grep"| awk '{print $2}')
#pid=$(netstat -nlp | grep ${node.port} | awk '{print $7}' | awk -F "/" '{print $1}')

if [ -n "$pid" ];then
    kill -9 $pid
fi