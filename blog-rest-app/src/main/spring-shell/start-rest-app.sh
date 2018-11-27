#!/bin/bash

export FDIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export APP_ROOT=${FDIR}/../

#specific settings
export APP_NAME=blog-rest-app
export APP_FILENAME=${APP_NAME}.jar

${APP_ROOT}/start-spring-boot.sh
