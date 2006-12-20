package net.esper.eql.join.assemble;

import net.esper.eql.join.rep.Node;
import net.esper.event.EventBean;
import net.esper.util.IndentWriter;

import java.util.List;
import java.util.Set;

/**
 * Assembly node for an event stream that is a leaf with a no child nodes below it.
 */
public class LeafAssemblyNode extends BaseAssemblyNode
{
    /**
     * Ctor.
     * @param streamNum - is the stream number
     * @param numStreams - is the number of streams
     */
    public LeafAssemblyNode(int streamNum, int numStreams)
    {
        super(streamNum, numStreams);
    }

    public void init(List<Node>[] result)
    {
        return;
    }

    public void process(List<Node>[] result)
    {
        List<Node> nodes = result[streamNum];
        if (nodes == null)
        {
            return;
        }

        for (Node node : nodes)
        {
            Set<EventBean> events = node.getEvents();
            for (EventBean event : events)
            {
                processEvent(event, node);
            }
        }
    }

    private void processEvent(EventBean event, Node currentNode)
    {
        EventBean[] row = new EventBean[numStreams];
        row[streamNum] = event;
        parentNode.result(row, streamNum, currentNode.getParentEvent(), currentNode.getParent());
    }

    public void result(EventBean[] row, int streamNum, EventBean myEvent, Node myNode)
    {
        throw new UnsupportedOperationException("Leaf node cannot process child results");
    }

    public void print(IndentWriter indentWriter)
    {
        indentWriter.println("LeafAssemblyNode streamNum=" + streamNum);
    }
}
