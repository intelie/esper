package net.esper.eql.variable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;

import junit.framework.Assert;

public class VariableServiceRunnable implements Runnable
{
    private static final Log log = LogFactory.getLog(VariableServiceRunnable.class);
    private final Random random;
    private final String[] variables;
    private final VariableReader[] readers;
    private final VariableService variableService;
    private final VariableVersionCoord variableVersionCoord;
    private final int numLoops;
    private final int[][] results;
    private final int[] marks;

    public VariableServiceRunnable(String[] variables, VariableService variableService, VariableVersionCoord variableVersionCoord, int numLoops)
    {
        this.random = new Random();
        this.variables = variables;
        this.variableService = variableService;
        this.variableVersionCoord = variableVersionCoord;
        this.numLoops = numLoops;
        
        results = new int[numLoops][variables.length];
        marks = new int[numLoops];

        readers = new VariableReader[variables.length];
        for (int i = 0; i < variables.length; i++)
        {
            readers[i] = variableService.getReader(variables[i]);
        }
    }

    public void run()
    {
        // For each loop
        for (int i = 0; i < numLoops; i++)
        {
            doLoop(i);
        }
    }

    private void doLoop(int loopNumber)
    {
        // Set a mark, there should be no number above that number
        int mark = variableVersionCoord.setVersionGetMark();
        int[] indexes = getIndexesShuffled(variables.length, random, loopNumber);
        marks[loopNumber] = mark;

        // Perform first loops of reading
        int[] readResults = new int[variables.length];
        readAll(indexes, readResults, mark);

        // Write every second of the variables
        for (int i = 0; i < indexes.length; i++)
        {
            int index = indexes[i];
            String variableName = variables[index];

            if (i % 2 == 0)
            {
                try
                {
                    int newMark = variableVersionCoord.incMark();
                    if (log.isDebugEnabled())
                    {
                        log.debug(".run Thread " + Thread.currentThread().getId() + " at mark " + mark + " write variable '" + variableName + "' new value " + newMark);
                    }
                    variableService.write(variableName, newMark);
                }
                catch (VariableNotFoundException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }

        // Read again for the sake of returning results
        results[loopNumber] = new int[variables.length];
        readAll(indexes, results[loopNumber], mark);

        // compare first read with second read
        for (int i = 0; i < variables.length; i++)
        {
            if (results[loopNumber][i] != readResults[i])
            {
                Assert.fail();
            }
        }
    }

    private void readAll(int[] indexes, int[] results, int mark)
    {
        for (int j = 0; j < indexes.length; j++)
        {
            int index = indexes[j];
            String variableName = variables[index];
            Integer value = (Integer) readers[index].getValue();
            results[index] = value;

            if (log.isDebugEnabled())
            {
                log.debug(".run Thread " + Thread.currentThread().getId() + " at mark " + mark + " read variable '" + variableName + " value " + value);
            }            
        }
    }

    public int[][] getResults()
    {
        return results;
    }

    public int[] getMarks()
    {
        return marks;
    }

    // Make a list between 0 and N for each variable
    private static int[] getIndexes(int length, Random random, int loopNum)
    {
        int[] indexRandomized = new int[length];

        for (int i = 0; i < indexRandomized.length; i++)
        {
            indexRandomized[i] = i;
        }

        return indexRandomized;
    }

    // Make a list between 0 and N for each variable
    private static int[] getIndexesShifting(int length, Random random, int loopNum)
    {
        int[] indexRandomized = new int[length];

        int start = loopNum % length;
        int count = 0;
        for (int i = start; i < indexRandomized.length; i++)
        {
            indexRandomized[count++] = i;
        }
        for (int i = 0; i < start; i++)
        {
            indexRandomized[count++] = i;
        }

        return indexRandomized;
    }

    // Make a random list between 0 and N for each variable
    private static int[] getIndexesShuffled(int length, Random random, int loopNum)
    {
        int[] indexRandomized = new int[length];

        for (int i = 0; i < indexRandomized.length; i++)
        {
            indexRandomized[i] = i;
        }

        for (int i = 0; i < length; i++)
        {
            int indexOne = random.nextInt(length);
            int indexTwo = random.nextInt(length);
            int temp = indexRandomized[indexOne];
            indexRandomized[indexOne] = indexRandomized[indexTwo];
            indexRandomized[indexTwo] = temp;
        }

        return indexRandomized;
    }
}
