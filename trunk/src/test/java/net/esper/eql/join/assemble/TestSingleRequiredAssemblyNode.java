package net.esper.eql.join.assemble;

import net.esper.support.eql.join.SupportJoinProcNode;
import net.esper.support.eql.join.SupportJoinResultNodeFactory;
import net.esper.eql.join.rep.Node;
import net.esper.event.EventBean;

import java.util.List;

import junit.framework.TestCase;

public class TestSingleRequiredAssemblyNode extends TestCase
{
    private SupportJoinProcNode parentNode;
    private BranchRequiredAssemblyNode reqNode;

    public void setUp()
    {
        reqNode = new BranchRequiredAssemblyNode(1, 3);
        parentNode = new SupportJoinProcNode(-1, 3);
        parentNode.addChild(reqNode);
    }

    public void testProcess()
    {
        // the node does nothing when asked to process as it doesn't originate events
    }

    public void testChildResult()
    {
        TestSingleOptionalAssemblyNode.testChildResult(reqNode, parentNode);
    }
}
