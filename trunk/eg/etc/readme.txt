==============
Esper Examples
==============

In order to compile and run the samples please follow the below instructions:

1. Make sure Java 1.5 or greater is installed and the JAVA_HOME environment variable is set

2. Open a console window and change directory to esper\eg\etc

3. Run "setenv.bat" to verify your environment settings

4. Run "compile.bat" to compile the examples

5. Now you are ready to run the examples. Some examples require mandatory parameters.

The examples can produce lots of pretty detailed output. The logging level can be configured by changing the 
log4j.xml configuration file to DEBUG for more output or to INFO for less detailed output.


Samples description
--------------------

The 3-Transaction Challenge

	Run "run_txnsim.bat" for this example.
	The mandatory arguments are: bucket_size num_transactions
	
	Some suggested parameters:
		run_txnsim tiniest 100		// Bucket size of just 20 with only 100 transactions 
		run_txnsim small 100000		// Bucket size of 4999 with 100k transactions 
		run_txnsim large 1000000	// Bucket size of 49999 with 1M transactions
	

(more examples added here in the future...)
