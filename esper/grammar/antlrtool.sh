#!/bin/sh

# Script to the ANTLR tool parser compiler
#
$JAVA_HOME/bin/java -classpath ../lib/antlr-3.2.jar org.antlr.Tool -fo ../src/main/java/com/espertech/esper/epl/generated EsperEPL2Grammar.g EsperEPL2Ast.g -Xmaxinlinedfastates 2000
