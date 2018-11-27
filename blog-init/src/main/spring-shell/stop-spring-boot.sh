#!/bin/bash

export FDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export APP_ROOT=${FDIR}
source ${APP_ROOT}/app-env.${APP_NAME}.sh

if [ -z "${APP_NAME}" ]; then
    echo Please specify the APP_NAME
    exit 1
fi

if [ -z "${APP_FILENAME}" ]; then
    echo Please specify the APP_FILENAME
    exit 1
fi

export PIDFILE=${RUN_PATH}/${APP_NAME}.pid

if [ -e ${PIDFILE} ]; then
    PID=`cat ${PIDFILE}`
    PSINFO=`ps -f -p ${PID} | grep ${APP_FILENAME}`
    if [ ! -z "${PSINFO}" ]; then
        echo Stopping command is sent to ${PID}, wait several seconds for it to end.
        kill -s TERM ${PID}
    else
        echo ${APP_NAME} is not running PID=${PID}
        exit 1
    fi
else
    echo Cannot find the pid file ${PIDFILE}
    echo Is the application started?
fi
