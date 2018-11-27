#!/bin/bash

#common settings
export LOG_PATH=/opt/symbio/logs/blog
export RUN_PATH=/opt/symbio/run

export JAVA_OPTIONS="-Xms2048m -Xmx2560m -Xmn800m -XX:CompressedClassSpaceSize=512m -Xloggc:/opt/symbio/logs/blog/blog-rest-app/GC.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/opt/symbio/logs/blog/blog-rest-app/"

