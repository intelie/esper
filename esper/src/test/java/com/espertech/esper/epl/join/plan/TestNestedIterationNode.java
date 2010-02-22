package com.espertech.esper.epl.join.plan;

import junit.framework.TestCase;
import com.espertech.esper.epl.join.plan.NestedIterationNode;

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
