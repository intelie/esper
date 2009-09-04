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

LIB=../../../lib

CLASSPATH=.
CLASSPATH=$CLASSPATH:../terminalsvc-sender/target/example-terminalsvc-sender-1.0.jar
CLASSPATH=$CLASSPATH:../terminalsvc-common/target/example-terminalsvc-common-1.0.jar
CLASSPATH=$CLASSPATH:../terminalsvc-receiver/target/example-terminalsvc-receiver-1.0.jar
CLASSPATH=$CLASSPATH:../../../esper-3.2.0.jar
CLASSPATH=$CLASSPATH:../lib/concurrent.jar
CLASSPATH=$CLASSPATH:../lib/jboss-common-client.jar
CLASSPATH=$CLASSPATH:../lib/jbossmq-client.jar
CLASSPATH=$CLASSPATH:../lib/jnp-client.jar
CLASSPATH=$CLASSPATH:../lib/jboss-j2ee.jar
CLASSPATH=$CLASSPATH:$LIB/cglib-nodep-2.2.jar
CLASSPATH=$CLASSPATH:$LIB/commons-logging-1.1.1.jar
CLASSPATH=$CLASSPATH:$LIB/log4j-1.2.15.jar
CLASSPATH=$CLASSPATH:$LIB/antlr-runtime-3.1.1.jar

export CLASSPATH="$CLASSPATH"
