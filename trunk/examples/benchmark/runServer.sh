# uncomment and set your JAVA_HOME
#JAVA_HOME=""

# the classpath
# you need to get an Esper distribution separately from the benchmark kit
CP="etc:build:patch:classes:lib/esper-1.12.0.jar:lib/commons-logging-1.0.3.jar:lib/cglib-full-2.0.2.jar:lib/antlr-2.7.5.jar:lib/log4j-1.2.8.jar"

# JVM options
OPT="-Xms1024m -Xmx1024m"

# uncomment for simulation without client
#SIM="-rate 2x10000"

# we default to synchronous control flow
QUEUE="-queue -1"

$JAVA_HOME/bin/java $OPT -classpath $CP -Desper.benchmark.symbol=1000 net.esper.example.benchmark.server.Server $QUEUE -stat 10 -mode STP $SIM 2>&1 > out.log
