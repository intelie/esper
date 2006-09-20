#!/bin/sh

# Script to run market data feed example
#

. setenv.sh

MEMORY_OPTIONS="-Xms256m -Xmx256m -XX:+UseParNewGC"

$JAVA_HOME/bin/java $MEMORY_OPTIONS -Dlog4j.configuration=log4j.xml -cp ${CLASSPATH} net.esper.example.marketdatafeed.FeedSimMain $1 $2
