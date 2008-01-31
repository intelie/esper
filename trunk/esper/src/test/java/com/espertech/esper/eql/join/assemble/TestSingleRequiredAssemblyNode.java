package com.espertech.esper.eql.join.assemble;

import com.espertech.esper.support.eql.join.SupportJoinProcNode;
import com.espertech.esper.support.eql.join.SupportJoinResultNodeFactory;
import com.espertech.esper.eql.join.rep.Node;
import com.espertech.esper.event.EventBean;

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
