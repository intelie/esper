@echo off

call setenv.bat

if not exist "..\target" (
  mkdir ..\target
)
if not exist "..\target\classes" (
  mkdir ..\target\classes
)

set SOURCEPATH=..\src\main\java

"%JAVA_HOME%"\bin\javac -d ..\target\classes -source 1.5 -sourcepath %SOURCEPATH% %SOURCEPATH%\com\espertech\esper\example\transaction\sim\TxnGenMain.java %SOURCEPATH%\com\espertech\esper\example\marketdatafeed\FeedSimMain.java %SOURCEPATH%\com\espertech\esper\example\autoid\AutoIdSimMain.java %SOURCEPATH%\com\espertech\esper\example\stockticker\monitor\StockTickerMonitor.java %SOURCEPATH%\com\espertech\esper\example\rfid\RFIDMouseDragExample.java %SOURCEPATH%\com\espertech\esper\example\rfid\LRMovingSimMain.java %SOURCEPATH%\com\espertech\esper\example\servershell\ServerShellMain.java %SOURCEPATH%\com\espertech\esper\example\servershellclient\ServerShellClientMain.java
