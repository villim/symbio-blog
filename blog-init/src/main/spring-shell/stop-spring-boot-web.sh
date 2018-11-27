#!/bin/bash

export FDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export APP_ROOT=${FDIR}
export CURL=/usr/bin/curl
export USER=admin

if [ -e ${APP_ROOT}/${APP_NAME}/application.properties ]; then
    export APP_PROPERTIES=${APP_ROOT}/${APP_NAME}/application.properties
    
elif [ -e ./application.properties ]; then
    export APP_PROPERTIES=./application.properties
else
    echo Please run this command in the application''s directory where application.properties exists or define the APP_NAME env
    exit 1
fi

export ADDRESS=`grep management.address= ${APP_PROPERTIES} | cut -d'=' -f2`
export PORT=`grep management.port= ${APP_PROPERTIES} | cut -d'=' -f2`
export CONTEXT_PATH=`grep management.context-path= ${APP_PROPERTIES} | cut -d'=' -f2`

if [ -z "${ADDRESS}" ]; then
    echo management.address is missing
    exit 1
fi

if [ -z "${PORT}" ]; then
    echo management.port is missing
    exit 1
fi

if [ -z "${CONTEXT_PATH}" ]; then
    echo management.context-path is missing
    exit 1
fi

# First, Try to trigger a controlled shutdown 
${CURL} --user ${USER} --request POST http://${ADDRESS}:${PORT}/${CONTEXT_PATH}/shutdown
   
# If controlled shutdonw encounter problem, Kill it manually
attempts=0
while pkill -0 -f ${FULL_JAR_PATH} > /dev/null 2>&1
do
    attempts=$[$attempts + 1]
    if [ $attempts -gt 5 ]
    then
        # We have waited too long. Kill it.
        pkill -f ${FULL_JAR_PATH} > /dev/null 2>&1
    fi
    sleep 1s
done

