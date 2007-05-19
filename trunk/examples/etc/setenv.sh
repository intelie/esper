#!/bin/sh

## run via '. setenv.sh'
##
##

if [ -z "${JAVA_HOME}" ]
then
  echo "JAVA_HOME not set"
  exit 0
fi

if [ ! -x "${JAVA_HOME}/bin/java" ]
then
  echo Cannot find java executable, check JAVA_HOME
  exit 0
fi

LIB=../../esper/lib

CLASSPATH=.
CLASSPATH=$CLASSPATH:../target/classes
CLASSPATH=$CLASSPATH:../../esper-1.8.0.jar
CLASSPATH=$CLASSPATH:$LIB/cglib-full-2.0.2.jar
CLASSPATH=$CLASSPATH:$LIB/commons-logging-1.0.3.jar
CLASSPATH=$CLASSPATH:$LIB/log4j-1.2.8.jar
CLASSPATH=$CLASSPATH:$LIB/antlr-2.7.5.jar

export CLASSPATH="$CLASSPATH"
