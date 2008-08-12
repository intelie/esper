@echo OFF
setlocal

@rem # uncomment and set your JAVA_HOME
@rem set JAVA_HOME=
set PATH=%JAVA_HOME%\bin;%PATH%

@rem # the classpath
@rem # you need to get an Esper distribution separately from the benchmark kit
set CP=etc;build;lib\esper-2.2.0.jar;lib\commons-logging-1.1.1.jar;lib\cglib-nodep-2.1_3.jar;lib\antlr-runtime-3.0.1.jar;lib\log4j-1.2.14.jar

@rem # JVM options
set OPT=-Xms1024m -Xmx1024m

@rem # uncomment for simulation without client
@rem set SIM=-rate 2x10000

@rem # we default to synchronous control flow
set QUEUE=-queue -1

%JAVA_HOME%\bin\java %OPT% -classpath %CP% -Desper.benchmark.symbol=1000 com.espertech.esper.example.benchmark.server.Server %QUEUE% -stat 10 -mode STP %SIM% > out.log