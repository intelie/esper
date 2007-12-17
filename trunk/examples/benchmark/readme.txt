To run Server and Client, please use the provided runClient and runServer command or shell scripts.

Please make sure the "lib" directory contains the Esper jar file, as well as all dependency jar files before running runClient or runServer.

================

To run Server and Client without Ant you need to write a sh/cmd script like this.
Note that ./etc and ./build must be in the classpath.

sh:

VMOPT=-Xms 1024m -Xmx10240m
OPT=-rate 4x10000 -mode STP
java $VMOPT -classpath etc:build:lib/esper-1.12.0.jar:lib/antlr-2.7.5.jar:(add others) net.esper.example.benchmark.server.Server $OPT

cmd:

set VMOPT=-Xms 1024m -Xmx10240m
set OPT=-rate 4x10000 -mode STP
java %VMOPT% -classpath etc;build:lib\esper-1.12.0.jar;lib\antlr-2.7.5.jar;(add others) net.esper.example.benchmark.server.Server %OPT%

For client the main class is net.esper.example.benchmark.client.Client