package com.espertech.esper.eql.join.assemble;

import com.espertech.esper.support.eql.join.SupportJoinProcNode;
import com.espertech.esper.support.eql.join.SupportJoinResultNodeFactory;
import com.espertech.esper.eql.join.rep.Node;

import java.util.List;

import junit.framework.TestCase;

public class TestLeafAssemblyNode extends TestCase
{
    private SupportJoinProcNode parentNode;
    private LeafAssemblyNode leafNode;

    public void setUp()
    {
        leafNode = new LeafAssemblyNode(1, 4);
        parentNode = new SupportJoinProcNode(-1, 4);
        parentNode.addChild(leafNode);
    }

    public void testProcess()
    {
        List<Node>[] result = SupportJoinResultNodeFactory.makeOneStreamResult(4, 1, 2, 2);

        leafNode.process(result);

        assertEquals(4, parentNode.getRowsList().size());
        assertEquals(result[1].get(0).getEvents().iterator().next(), parentNode.getRowsList().get(0)[1]);   // compare event
    }

    public void testChildResult()
    {
        try
        {
            leafNode.result(null, 0, null, null);
            fail();
        }
        catch (UnsupportedOperationException ex)
        {
            // expected
        }
    }
}
