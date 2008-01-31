package com.espertech.esper.eql.join.plan;

import junit.framework.TestCase;
import com.espertech.esper.eql.join.plan.NestedIterationNode;
import com.espertech.esper.eql.join.table.UnindexedEventTable;
import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.eql.join.exec.NestedIterationExecNode;
import com.espertech.esper.eql.join.exec.ExecNode;

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
