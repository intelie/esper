@echo off

REM Script to run market data feed example
REM

call setenv.bat

set MEMORY_OPTIONS=-Xms256m -Xmx256m -XX:+UseParNewGC

%JAVA_HOME%\bin\java %MEMORY_OPTIONS% -Dlog4j.configuration=log4j.xml net.esper.example.marketdatafeed.FeedSimMain %1 %2