#!/bin/bash

export FDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export APP_ROOT=${FDIR}
source ${APP_ROOT}/app-env.${APP_NAME}.sh

export PIDFILE=${RUN_PATH}/${APP_NAME}.pid
export SPRING_PID_FILE=${PIDFILE}

if [ -z "${APP_NAME}" ]; then
    echo Please specify the APP_NAME
    exit 1
fi

if [ -z "${APP_FILENAME}" ]; then
    echo Please specify the APP_FILENAME
    exit 1
fi

if [ -e ${PIDFILE} ]; then
    #possibly, previous instance had been killed
    #so need to confirm if another instance is already running
    PID=`cat ${PIDFILE}`
    PSINFO=`ps -f -p ${PID} | grep ${APP_NAME}`
    if [ ! -z "${PSINFO}" ]; then
        echo ${APP_NAME} is already running PID=${PID}
        exit 1
    fi
fi

if [ ! -e ${APP_ROOT}/${APP_NAME}/${APP_FILENAME} ]; then
    echo The application does not exist : ${APP_ROOT}/${APP_NAME}/${APP_FILENAME}
    exit 1
fi

nohup java ${JAVA_OPTIONS} -jar ${APP_ROOT}/${APP_NAME}/${APP_FILENAME} $* 1>> /dev/null 2>&1 &

if [ ! $? -eq 0 ]; then
    echo Not able to start ${APP_FILENAME}
    kill $!
    exit 1
else
    echo Log is written to ${LOG_PATH}/${LOG_FILENAME}
    echo Pid $! is written to ${SPRING_PID_FILE}
fi
