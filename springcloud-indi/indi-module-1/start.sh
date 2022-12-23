#!/bin/bash

cd `dirname $0`

sh stop.sh

workspace=$(pwd)

#CLASSPATH="./${app.name}.jar:${workspace}"
#CLASSPATH=".:${workspace}"
#for file in `ls ${workspace}/lib/*.jar`;
#do
#   CLASSPATH="$CLASSPATH":"$file"
#done

# G1
# export MEM=1g  # used for java heap size
# export GC_OPTS="-XX:+UseG1GC -XX:ParallelGCThreads=24 -XX:ConcGCThreads=16 -XX:InitiatingHeapOccupancyPercent=45 -XX:NewRatio=2 -XX:SurvivorRatio=4 -XX:MaxTenuringThreshold=15 -XX:-UseAdaptiveSizePolicy -Xms${MEM} -Xmx${MEM}"
# export GC_LOGS="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${workspace}/logs/${app.name}_%p.hprof -XX:+PrintGCDetails -Xloggc:${workspace}/logs/${app.name}-gc.log -XX:+PrintGCTimeStamps"
# JAVA_OPTS="${GC_OPTS} ${GC_LOGS}"

# export JMX_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=17387 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djava.net.preferIPv4Stack=true -Djava.rmi.server.hostname=0.0.0.0"
# JAVA_OPTS="${JAVA_OPTS} ${JMX_OPTS}"

# export TOMCAT_OPTS="-Djava.security.egd=file:/dev/./urandom"
# JAVA_OPTS="${JAVA_OPTS} ${TOMCAT_OPTS}"

# export COLLECTOR_OPTS="-Dcollector.home=${workspace}"
# JAVA_OPTS="${JAVA_OPTS} ${COLLECTOR_OPTS} -Xms512m -Xmx2g"

# export JMX_REMOTE_OPTS=" -Xdebug -Xrunjdwp:transport=dt_socket,address=8756,server=y,suspend=n "
# JAVA_OPTS="${JAVA_OPTS} ${JMX_REMOTE_OPTS}"

#exec nohup java -XX:OnOutOfMemoryError="\"kill -2 %p\"" $JAVA_OPTS -classpath $CLASSPATH ${app.mainClass} --spring.profiles.active=${app.env} 2>&1 &
nohup java -Xms512m -Xmx1g -jar ${workspace}/lib/${project.artifactId}-${project.version}.jar  > /dev/null 2>&1 &