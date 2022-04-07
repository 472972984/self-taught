#!/bin/bash

cd `dirname $0`

sh stop.sh

workspace=$(pwd)

CLASSPATH=".:${workspace}"
for file in `ls ${workspace}/lib/*.jar`;
do
   CLASSPATH="$CLASSPATH":"$file"
done

export COLLECTOR_OPTS="-Dcollector.home=${workspace}"
JAVA_OPTS="${JAVA_OPTS} ${COLLECTOR_OPTS} -Xms512m -Xmx2g"

nohup java -jar lib/${project.artifactId}-${project.version}.jar --spring.profiles.active=${app.env} > /dev/null 2>&1 &