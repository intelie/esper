package net.esper.eql.variable;

import junit.framework.TestCase;

public class TestVariableService extends TestCase
{
    private VariableService service;
    
    public void setUp()
    {
        service = new VariableService();
    }

    public void testPerfSetVersion()
    {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++)
        {
            service.setLocalVersion();
        }
        long end = System.currentTimeMillis();
        long delta = (end - start);
        assertTrue("delta=" + delta, delta < 100);
    }

    public void testMultithreadedZero() throws Exception
    {
        // The VariableService.HIGH_WATERMARK_VERSIONS settings affect this test.
        // The threads may get held up and a thread may request a very old version.
        // This is ok but can fail the test as the value is compared against mark.
        tryMT(4, 100, 8);
    }

    public void testMultithreadedOne() throws Exception
    {
        // see above
        tryMT(2, 100, 2);
    }

    // Start N threads
    // each thread performs X loops
    // each loop gets a unique number Y from a shared object and performs setVersion in the synchronized block
    // then the thread performs reads, write and read of shared variables, writing the number Y
    // ==> the reads should not see any higher number (unless watemarks reached)
    // ==> reads should produce the exact same result unless setVersion called
    private void tryMT(int numThreads, int numLoops, int numVariables) throws Exception
    {
        VariableVersionCoord coord = new VariableVersionCoord(service);

        // create variables
        String[] variables = new String[numVariables];
        for (int i = 0; i < numVariables; i++)
        {
            char c = 'A';
            c+=i;
            variables[i] = Character.toString(c);
            service.createNewVariable(variables[i], Integer.class, 0);
        }

        // create runnables
        VariableServiceRunnable[] runnables = new VariableServiceRunnable[numThreads];
        Thread[] threads = new Thread[numThreads];
        long[] threadIds = new long[numThreads];
        for (int i = 0; i < runnables.length; i++)
        {
            runnables[i] = new VariableServiceRunnable(variables, service, coord, numLoops);
            threads[i] = new Thread(runnables[i]);
            threads[i].start();
            threadIds[i] = threads[i].getId();
        }

        // Wait for completion of each thread
        for (Thread thread : threads)
        {
            thread.join();
        }

        //System.out.println(service.toString());
        // Verify results per thread
        for (int i = 0; i < threads.length; i++)
        {
            int[][] result = runnables[i].getResults();
            int[] marks = runnables[i].getMarks();
            verify(result, marks, threadIds[i], numLoops, variables);
        }
    }

    // the reads should not see any higher number then mark, and there should be no zero after 10 rounds
    // other assertions are done by the runnable itself
    private void verify(int[][] result, int[] marks, long threadId, int numLoops, String variables[])
    {
        for (int i = 0; i < numLoops; i++)
        {
            for (int j = 0; j < variables.length; j++)
            {
                if (result[i][j] > marks[i])
                {
                    fail("Failed for variable '" + variables[j] + "' at mark " + marks[i]);
                }
                if (i > 10)
                {
                    assertTrue(result[i][j] > 0);
                }
            }
        }        
    }

    public void testReadWrite() throws Exception
    {
        assertNull(service.getReader("a"));

        service.createNewVariable("a", Long.class, 100L);
        VariableReader reader = service.getReader("a");
        assertEquals(Long.class, reader.getType());
        assertEquals(100L, reader.getValue());

        service.write("a", 101L);
        assertEquals(100L, reader.getValue());
        service.setLocalVersion();
        assertEquals(101L, reader.getValue());        

        service.write("a", 102L);
        assertEquals(101L, reader.getValue());
        service.setLocalVersion();
        assertEquals(102L, reader.getValue());        
    }

    public void testRollover() throws Exception
    {
        service = new VariableService(VariableService.ROLLOVER_READER_BOUNDARY - 100);
        String[] variables = "a,b,c,d".split(",");

        for (int i = 0; i < variables.length; i++)
        {
            service.createNewVariable(variables[i], Long.class, 100L);
        }

        for (int i = 0; i < 1000; i++)
        {
            for (int j = 0; j < variables.length; j++)
            {
                service.write(variables[j], 100L + i);
            }
            readCompare(variables, 100L + i);
        }
    }

    private void readCompare(String[] variables, Object value)
    {
        service.setLocalVersion();
        for (int i = 0; i < variables.length; i++)
        {
            assertEquals(value, service.getReader(variables[i]).getValue());
        }
    }

    public void testInvalid() throws Exception
    {
        service.createNewVariable("a", Long.class, null);
        try
        {
            service.createNewVariable("a", Long.class, null);
            fail();
        }
        catch (VariableExistsException e)
        {
            assertEquals("Variable by name 'a' has already been created", e.getMessage());
        }

        try
        {
            service.write("dummy", null);
            fail();
        }
        catch (VariableNotFoundException ex)
        {
            assertEquals("Variable by name 'dummy' could not be found", ex.getMessage());
        }
    }
}
