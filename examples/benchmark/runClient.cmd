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
set OPT=-Xms128m -Xmx128m

@rem # rate
set RATE=-rate 10000

@rem # remote host, we default to localhost and default port
set HOST=-host 127.0.0.1

%JAVA_HOME%\bin\java %OPT% -classpath %CP% -Desper.benchmark.symbol=1000 com.espertech.esper.example.benchmark.client.Client %RATE% %HOST% 
