package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.eql.join.exec.ExecNode;

import java.util.Set;
import java.util.List;
import java.util.LinkedList;

/**
 * Query strategy for building a join tuple set by using an execution node tree.
 */
public class ExecNodeQueryStrategy implements QueryStrategy
{
    private int forStream;
    private int numStreams;
    private ExecNode execNode;

    /**
     * CTor.
     * @param forStream - stream the strategy is for
     * @param numStreams - number of streams in total
     * @param execNode - execution node for building join tuple set
     */
    public ExecNodeQueryStrategy(int forStream, int numStreams, ExecNode execNode)
    {
        this.forStream = forStream;
        this.numStreams = numStreams;
        this.execNode = execNode;
    }

    public void lookup(EventBean[] lookupEvents, Set<MultiKey<EventBean>> joinSet)
    {
        if (lookupEvents == null)
        {
            return;
        }
        
        for (EventBean event : lookupEvents)
        {
            // Set up prototype row
            EventBean[] prototype = new EventBean[numStreams];
            prototype[forStream] = event;

            // Perform execution
            List<EventBean[]> results = new LinkedList<EventBean[]>();
            execNode.process(event, prototype, results);

            // Convert results into unique set
            for (EventBean[] row : results)
            {
                joinSet.add(new MultiKey<EventBean>(row));
            }
        }
    }

    /**
     * Return stream number this strategy is for.
     * @return stream num
     */
    protected int getForStream()
    {
        return forStream;
    }

    /**
     * Returns the total number of streams.
     * @return number of streams
     */
    protected int getNumStreams()
    {
        return numStreams;
    }

    /**
     * Returns execution node.
     * @return execution node
     */
    protected ExecNode getExecNode()
    {
        return execNode;
    }
}