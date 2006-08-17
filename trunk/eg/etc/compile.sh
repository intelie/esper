#!/bin/sh

. setenv.sh

if [ ! -d "../target" ]
then
    mkdir ../target
fi
if [ ! -d "../target/classes" ]
then
    mkdir ../target/classes
fi

SOURCEPATH=../src/main/java

${JAVA_HOME}/bin/javac -cp ${CLASSPATH} -d ../target/classes -source 1.5 -sourcepath ${SOURCEPATH} ${SOURCEPATH}/net/esper/example/transaction/sim/TxnGenMain.java ${SOURCEPATH}/net/esper/example/marketdatafeed/FeedSimMain.java ${SOURCEPATH}/net/esper/example/autoid/AutoIdSimMain.java ${SOURCEPATH}/net/esper/example/stockticker/monitor/StockTickerMonitor.java
