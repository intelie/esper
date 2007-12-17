# uncomment and set your JAVA_HOME
#JAVA_HOME=""

# the classpath
# you need to get an Esper distribution separately from the benchmark kit
CP="etc:build:patch:classes:lib/esper-1.12.0.jar:lib/commons-logging-1.0.3.jar:lib/cglib-full-2.0.2.jar:lib/antlr-2.7.5.jar:lib/log4j-1.2.8.jar"

# JVM options
OPT="-Xms128m -Xmx128m"

# rate
RATE="-rate 10000"

# remote host, we default to localhost and default port
HOST="-host 127.0.0.1"

$JAVA_HOME/bin/java $OPT -classpath $CP -Desper.benchmark.symbol=1000 net.esper.example.benchmark.client.Client $RATE $HOST


