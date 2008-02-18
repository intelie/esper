# uncomment and set your JAVA_HOME
#JAVA_HOME=""

# the classpath
# you need to get an Esper distribution separately from the benchmark kit
CP="etc:build:patch:classes:lib/esper-2.0.0.jar:lib/commons-logging-1.1.1.jar:lib/cglib-nodep-2.1_3.jar:lib/antlr-runtime-3.0.1.jar:lib/log4j-1.2.14.jar"

# JVM options
OPT="-Xms128m -Xmx128m"

# rate
RATE="-rate 10000"

# remote host, we default to localhost and default port
HOST="-host 127.0.0.1"

$JAVA_HOME/bin/java $OPT -classpath $CP -Desper.benchmark.symbol=1000 com.espertech.esper.example.benchmark.client.Client $RATE $HOST


