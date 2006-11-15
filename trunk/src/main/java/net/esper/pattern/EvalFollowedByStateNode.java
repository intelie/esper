package net.esper.pattern;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class represents the state of a followed-by operator in the evaluation state tree.
 */
public final class EvalFollowedByStateNode extends EvalStateNode implements Evaluator
{
    private final LinkedList<EvalNode> childNodes;
    private final HashMap<EvalStateNode, Integer> nodes;
    private final PatternContext context;

    /**
     * Constructor.
     * @param parentNode is the parent evaluator to call to indicate truth value
     * @param childNodes are the child nodes of the followed by node
     * @param beginState contains the events that make up prior matches
     * @param context contains handles to services required
     */
    public EvalFollowedByStateNode(Evaluator parentNode,
                                         LinkedList<EvalNode> childNodes,
                                         MatchedEventMap beginState,
                                         PatternContext context)
    {
        super(parentNode);

        if (log.isDebugEnabled())
        {
            log.debug(".constructor");
        }

        this.nodes = new HashMap<EvalStateNode, Integer>();
        this.childNodes = childNodes;
        this.context = context;

        EvalNode child = childNodes.get(0);
        EvalStateNode childState = child.newState(this, beginState, context);
        nodes.put(childState, 0);
    }

    public final void start()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".start Starting followed-by expression for the first child");
        }

        if (nodes.size() == 0)
        {
            throw new IllegalStateException("Followed by state node is inactive");
        }

        for (EvalStateNode child : nodes.keySet())
        {
            child.start();
        }
    }

    public final void evaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted)
    {
        Integer index = nodes.get(fromNode);

        if (log.isDebugEnabled())
        {
            log.debug(".evaluateTrue index=" + index + "  fromNode=" + fromNode.hashCode() + "  isQuitted=" + isQuitted);
        }

        if (isQuitted)
        {
            nodes.remove(fromNode);
        }

        // If the match came from the very last filter, need to escalate
        int numChildNodes = childNodes.size();
        if (index == (numChildNodes - 1))
        {
            boolean isFollowedByQuitted = false;
            if (nodes.size() == 0)
            {
                isFollowedByQuitted = true;
            }

            this.getParentEvaluator().evaluateTrue(matchEvent, this, isFollowedByQuitted);
        }
        // Else start a new listener for the next-in-line filter
        else
        {
            EvalNode child = childNodes.get(index + 1);
            EvalStateNode childState = child.newState(this, matchEvent, context);
            nodes.put(childState, index + 1);
            childState.start();
        }
    }

    public final void evaluateFalse(EvalStateNode fromNode)
    {
        log.debug(".evaluateFalse Child node has indicated permanently false");
        fromNode.quit();
        nodes.remove(fromNode);
    }

    public final void quit()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".guardQuit Stopping followed-by all children");
        }

        for (EvalStateNode child : nodes.keySet())
        {
            child.quit();
        }
    }

    public final Object accept(EvalStateNodeVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    public final Object childrenAccept(EvalStateNodeVisitor visitor, Object data)
    {
        for (EvalStateNode node : nodes.keySet())
        {
            node.accept(visitor, data);
        }
        return data;
    }

    public final String toString()
    {
        return "EvalFollowedByStateNode nodes=" + nodes.size();
    }

    private static final Log log = LogFactory.getLog(EvalFollowedByStateNode.class);
}
