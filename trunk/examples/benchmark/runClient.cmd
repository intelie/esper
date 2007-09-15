@echo OFF
setlocal

@rem # uncomment and set your JAVA_HOME
@rem set JAVA_HOME=
set PATH=%JAVA_HOME%\bin;%PATH%

@rem # the classpath
@rem # you need to get an Esper distribution separately from the benchmark kit
set CP=etc;build;lib\esper-1.11.0.jar;lib\commons-logging-1.0.3.jar;lib\cglib-full-2.0.2.jar;lib\antlr-2.7.5.jar;lib\log4j-1.2.8.jar

@rem # JVM options
set OPT=-Xms128m -Xmx128m

@rem # rate
set RATE=-rate 10000

@rem # remote host, we default to localhost and default port
set HOST=-host 127.0.0.1

%JAVA_HOME%\bin\java %OPT% -classpath %CP% -Desper.benchmark.symbol=1000 net.esper.example.benchmark.client.Client %RATE% %HOST% 