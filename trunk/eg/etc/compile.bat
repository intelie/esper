@echo off

call setenv.bat

if not exist "..\target" (
  mkdir ..\target
)
if not exist "..\target\classes" (
  mkdir ..\target\classes
)

set SOURCEPATH=..\src\main\java

%JAVA_HOME%\bin\javac -d ..\target\classes -source 1.5 -sourcepath %SOURCEPATH% %SOURCEPATH%\net\esper\example\transaction\sim\TxnGenMain.java