@echo off

if "%JAVA_HOME%" == "" (
  echo.
  echo JAVA_HOME not set
  goto EOF
)

if not exist "%JAVA_HOME%\bin\java.exe" (
  echo.
  echo Cannot find java executable, check JAVA_HOME
  goto EOF
)

set LIB=..\..\..\esper\lib
set EXLIB=..\..\..\examples\lib
set IOLIB=..\..\..\esperio\lib

set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;..\target\classes
set CLASSPATH=%CLASSPATH%;..\..\..\esper-4.3.0.jar
set CLASSPATH=%CLASSPATH%;%LIB%\cglib-nodep-2.2.jar
set CLASSPATH=%CLASSPATH%;%LIB%\commons-logging-1.1.1.jar
set CLASSPATH=%CLASSPATH%;%LIB%\log4j-1.2.16.jar
set CLASSPATH=%CLASSPATH%;%LIB%\antlr-runtime-3.2.jar
set CLASSPATH=%CLASSPATH%;%EXLIB%\jms.jar
set CLASSPATH=%CLASSPATH%;%IOLIB%\apache-activemq-4.3.0-incubator.jar

goto EOF

:badenv
echo.
echo Error: required libraries not found in %LIB% directory
goto EOF

:badenv_esperlib
echo.
echo Error: esper.jar not found in ..\..\ 

:EOF
