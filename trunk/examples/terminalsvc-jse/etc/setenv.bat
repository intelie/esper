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

set LIB=..\..\esper\lib

if not exist ..\..\esper-3.0.0.jar goto badenv_esperlib
if not exist %LIB%\cglib-nodep-2.2.jar goto badenv
if not exist %LIB%\commons-logging-1.1.1.jar goto badenv
if not exist %LIB%\log4j-1.2.15.jar goto badenv
if not exist %LIB%\antlr-runtime-3.1.1.jar goto badenv

set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;..\target\classes
set CLASSPATH=%CLASSPATH%;..\..\esper-3.0.0.jar
set CLASSPATH=%CLASSPATH%;%LIB%\cglib-nodep-2.2.jar
set CLASSPATH=%CLASSPATH%;%LIB%\commons-logging-1.1.1.jar
set CLASSPATH=%CLASSPATH%;%LIB%\log4j-1.2.15.jar
set CLASSPATH=%CLASSPATH%;%LIB%\antlr-runtime-3.1.1.jar

goto EOF

:badenv
echo.
echo Error: required libraries not found in %LIB% directory
goto EOF

:badenv_esperlib
echo.
echo Error: esper.jar not found in ..\..\ 

:EOF