#!/bin/sh

# A note to cygwin users: please replace "-cp ${CLASSPATH}" with "-cp `cygpath -wp $CLASSPATH`"
#

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

${JAVA_HOME}/bin/javac -cp ${CLASSPATH} -d ../target/classes -source 1.5 -sourcepath ${SOURCEPATH} ${SOURCEPATH}/com/espertech/esper/example/transaction/sim/TxnGenMain.java ${SOURCEPATH}/com/espertech/esper/example/marketdatafeed/FeedSimMain.java ${SOURCEPATH}/com/espertech/esper/example/autoid/AutoIdSimMain.java ${SOURCEPATH}/com/espertech/esper/example/stockticker/monitor/StockTickerMonitor.java ${SOURCEPATH}/com/espertech/esper/example/rfid/RFIDMouseDragExample.java ${SOURCEPATH}/com/espertech/esper/example/rfid/LRMovingSimMain.java ${SOURCEPATH}/com/espertech/esper/example/servershell/ServerShellMain.java ${SOURCEPATH}/com/espertech/esper/example/servershellclient/ServerShellClientMain.java
