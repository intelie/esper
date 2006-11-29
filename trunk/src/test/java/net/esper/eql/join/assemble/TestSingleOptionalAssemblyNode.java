package net.esper.eql.join.assemble;

import net.esper.support.eql.join.SupportJoinProcNode;
import net.esper.support.eql.join.SupportJoinResultNodeFactory;
import net.esper.eql.join.rep.Node;
import net.esper.event.EventBean;

import java.util.List;

import junit.framework.TestCase;

public class TestSingleOptionalAssemblyNode extends TestCase
{
    private SupportJoinProcNode parentNode;
    private BranchOptionalAssemblyNode optAssemblyNode;
    private List<Node>[] resultMultipleEvents;
    private List<Node>[] resultSingleEvent;

    public void setUp()
    {
        optAssemblyNode = new BranchOptionalAssemblyNode(1, 4);
        parentNode = new SupportJoinProcNode(-1, 4);
        parentNode.addChild(optAssemblyNode);

        resultMultipleEvents = SupportJoinResultNodeFactory.makeOneStreamResult(4, 1, 2, 1); // 2 nodes 1 event each for (1)
        resultSingleEvent = SupportJoinResultNodeFactory.makeOneStreamResult(4, 1, 1, 1); // 1 nodes 1 event each for (1)
    }

    public void testProcessMultipleEvents()
    {
        optAssemblyNode.init(resultMultipleEvents);

        // generate an event row originating from a child for 1 of the 2 events in the result
        EventBean[] childRow = new EventBean[4];
        Node nodeOne = resultMultipleEvents[1].get(0);
        EventBean eventOne = nodeOne.getEvents().iterator().next();
        optAssemblyNode.result(childRow, 3, eventOne, nodeOne);

        // test that the node indeed manufactures event rows for any event not received from a child
        parentNode.getRowsList().clear();
        optAssemblyNode.process(resultMultipleEvents);

        // check generated row
        assertEquals(1, parentNode.getRowsList().size());
        EventBean[] row = parentNode.getRowsList().get(0);
        assertEquals(4, row.length);
        Node nodeTwo = resultMultipleEvents[1].get(1);
        assertEquals(nodeTwo.getEvents().iterator().next(), row[1]);
    }

    public void testProcessSingleEvent()
    {
        optAssemblyNode.init(resultSingleEvent);

        // test that the node indeed manufactures event rows for any event not received from a child
        optAssemblyNode.process(resultMultipleEvents);

        // check generated row
        assertEquals(1, parentNode.getRowsList().size());
        EventBean[] row = parentNode.getRowsList().get(0);
        assertEquals(4, row.length);
        Node node = resultSingleEvent[1].get(0);
        assertEquals(node.getEvents().iterator().next(), row[1]);
    }

    public void testChildResult()
    {
        optAssemblyNode.init(resultMultipleEvents);
        testChildResult(optAssemblyNode, parentNode);
    }

    protected static void testChildResult(BaseAssemblyNode nodeUnderTest, SupportJoinProcNode mockParentNode)
    {
        EventBean[] childRow = new EventBean[4];
        childRow[3] = SupportJoinResultNodeFactory.makeEvent();

        EventBean myEvent = SupportJoinResultNodeFactory.makeEvent();
        Node myNode = SupportJoinResultNodeFactory.makeNode(3, 1);

        // indicate child result
        nodeUnderTest.result(childRow, 3, myEvent, myNode);

        // assert parent node got the row
        assertEquals(1, mockParentNode.getRowsList().size());
        EventBean[] resultRow = mockParentNode.getRowsList().get(0);

        // assert the node has added his event to the row
        assertEquals(myEvent, resultRow[1]);
    }
}
