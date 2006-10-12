package net.esper.eql.join.plan;

import junit.framework.TestCase;
import net.esper.eql.join.plan.NestedIterationNode;
import net.esper.eql.join.table.UnindexedEventTable;
import net.esper.eql.join.table.EventTable;
import net.esper.eql.join.exec.NestedIterationExecNode;
import net.esper.eql.join.exec.ExecNode;

public class TestNestedIterationNode extends TestCase
{
    public void testMakeExec()
    {
        try
        {
            new NestedIterationNode(new int[] {});
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }
}
