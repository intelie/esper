@echo OFF
setlocal

@rem # uncomment and set your JAVA_HOME
@rem set JAVA_HOME=
set PATH=%JAVA_HOME%\bin;%PATH%

@rem # the classpath
@rem # you need to get an Esper distribution separately from the benchmark kit
set LCP=..\..\esper\target\classes;..\..\esper\lib\commons-logging-1.1.1.jar;..\..\esper\lib\cglib-nodep-2.2.jar;..\..\esper\lib\antlr-runtime-3.2.jar;..\..\esper\lib\log4j-1.2.16.jar
set CP=etc;bin;%LCP%;lib\esper-4.3.0.jar;lib\commons-logging-1.1.1.jar;lib\cglib-nodep-2.2.jar;lib\antlr-runtime-3.2.jar;lib\log4j-1.2.16.jar

@rem # JVM options
set OPT=-Xms1024m -Xmx1024m

@rem # uncomment for simulation without client
@rem set SIM=-rate 2x10000

@rem # we default to synchronous control flow
set QUEUE=-queue -1

@rem #JMX - if available
set CP=%CP%;lib\esperjmx-1.0.0.jar
set OPT=%OPT% -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false 


%JAVA_HOME%\bin\java %OPT% -classpath %CP% -Desper.benchmark.symbol=1000 com.espertech.esper.example.benchmark.server.Server %QUEUE% -stat 10 -mode STP %SIM% > out.log
