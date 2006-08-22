package net.esper.eql.join.assemble;

import net.esper.eql.join.rep.Node;
import net.esper.event.EventBean;
import net.esper.util.IndentWriter;

import java.util.List;

/**
 * Assembly node for an event stream that is a branch with a single required child node below it.
 */
public class BranchRequiredAssemblyNode extends BaseAssemblyNode
{
    /**
     * Ctor.
     * @param streamNum - is the stream number
     * @param numStreams - is the number of streams
     */
    public BranchRequiredAssemblyNode(int streamNum, int numStreams)
    {
        super(streamNum, numStreams);
    }

    public void init(List<Node>[] result)
    {
        // need not be concerned with results, all is passed from the child node
    }

    public void process(List<Node>[] result)
    {
        // no action here, since we have a required child row
        // The single required child generates all events that may exist
    }

    public void result(EventBean[] row, int fromStreamNum, EventBean myEvent, Node myNode)
    {
        row[streamNum] = myEvent;
        Node parentResultNode = myNode.getParent();
        parentNode.result(row, streamNum, myNode.getParentEvent(), parentResultNode);
    }

    public void print(IndentWriter indentWriter)
    {
        indentWriter.println("BranchRequiredAssemblyNode streamNum=" + streamNum);
    }
}
