# uncomment and set your JAVA_HOME
#JAVA_HOME=""

# the classpath
# you need to get an Esper distribution separately from the benchmark kit
CP="etc:build:patch:classes:lib/esper-2.0.0.jar:lib/commons-logging-1.1.1.jar:lib/cglib-nodep-2.1_3.jar:lib/antlr-runtime-3.0.1.jar:lib/log4j-1.2.14.jar"

# JVM options
OPT="-Xms1024m -Xmx1024m"

# uncomment for simulation without client
#SIM="-rate 2x10000"

# we default to synchronous control flow
QUEUE="-queue -1"

$JAVA_HOME/bin/java $OPT -classpath $CP -Desper.benchmark.symbol=1000 com.espertech.esper.example.benchmark.server.Server $QUEUE -stat 10 -mode STP $SIM 2>&1 > out.log
