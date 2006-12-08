package net.esper.eql.join.exec;

import net.esper.eql.join.exec.TableLookupStrategy;
import net.esper.eql.join.exec.LookupInstructionExec;
import net.esper.support.eql.join.SupportTableLookupStrategy;
import net.esper.support.eql.join.SupportRepositoryImpl;
import net.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

public class TestLookupInstructionExec extends TestCase
{
    private LookupInstructionExec exec;
    private SupportRepositoryImpl rep;
    private TableLookupStrategy[] lookupStrategies;

    public void setUp()
    {
        lookupStrategies = new TableLookupStrategy[4];
        for (int i = 0; i < lookupStrategies.length; i++)
        {
            lookupStrategies[i] = new SupportTableLookupStrategy(1);
        }

        exec = new LookupInstructionExec(0, "test",
                new int[] {1, 2, 3, 4}, lookupStrategies, new boolean[] {false, true, true, false, false});

        rep = new SupportRepositoryImpl();
    }

    public void testProcessAllResults()
    {
        boolean result = exec.process(rep);

        assertTrue(result);
        assertEquals(4, rep.getLookupResultsList().size());
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {1, 2, 3, 4}, rep.getResultStreamList().toArray());
    }

    public void testProcessNoRequiredResults()
    {
        lookupStrategies[1] = new SupportTableLookupStrategy(0);

        boolean result = exec.process(rep);

        assertFalse(result);
        assertEquals(0, rep.getLookupResultsList().size());
    }

    public void testProcessPartialOptionalResults()
    {
        lookupStrategies[3] = new SupportTableLookupStrategy(0);

        boolean result = exec.process(rep);

        assertTrue(result);
        assertEquals(3, rep.getLookupResultsList().size());
    }
}