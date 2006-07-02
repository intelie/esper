package net.esper.pattern;


import java.util.List;
import java.util.LinkedList;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * This class represents the state of a "or" operator in the evaluation state tree.
 */
public final class EvalOrStateNode extends EvalStateNode implements Evaluator
{
    private final LinkedList<EvalNode> orNodeChildNodes;
    private final List<EvalStateNode> childNodes;

    /**
     * Constructor.
     * @param parentNode is the parent evaluator to call to indicate truth value
     * @param orNodeChildNodes are the child nodes of the or-node
     * @param beginState contains the events that make up prior matches
     * @param context contains handles to services required
     */
    public EvalOrStateNode(Evaluator parentNode,
                                 LinkedList<EvalNode> orNodeChildNodes,
                                 MatchedEventMap beginState,
                                 PatternContext context)
    {
        super(parentNode);

        if (log.isDebugEnabled())
        {
            log.debug(".constructor");
        }

        this.orNodeChildNodes = orNodeChildNodes;
        this.childNodes = new LinkedList<EvalStateNode>();

        // In an "or" expression we need to create states for all child expressions/listeners,
        // since all are going to be started
        for (EvalNode node : orNodeChildNodes)
        {
            EvalStateNode childState = node.newState(this, beginState, context);
            childNodes.add(childState);
        }
    }

    public final void start()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".start Starting or-expression all children, size=" + orNodeChildNodes.size());
        }

        if (childNodes.size() != orNodeChildNodes.size())
        {
            throw new IllegalStateException("OR state node does not have the required child state nodes");
        }

        // In an "or" expression we start all child listeners
        for (EvalStateNode child : childNodes)
        {
            child.start();
        }
    }

    public final void evaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".evaluateTrue fromNode=" + fromNode.hashCode());
        }

        // If one of the children quits, the whole or expression turns true and all subexpressions must guardQuit
        if (isQuitted)
        {
            childNodes.remove(fromNode);
            quit();     // Quit the remaining listeners
        }

        this.getParentEvaluator().evaluateTrue(matchEvent, this, isQuitted);
    }

    public final void evaluateFalse(EvalStateNode fromNode)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".evaluateFalse fromNode=" + fromNode.hashCode());
        }
    }

    protected final void quit()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".guardQuit Stopping all children");
        }

        for (EvalStateNode child : childNodes)
        {
            child.quit();
        }
        childNodes.clear();
    }

    public final Object accept(EvalStateNodeVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    public final Object childrenAccept(EvalStateNodeVisitor visitor, Object data)
    {
        for (EvalStateNode node : childNodes)
        {
            node.accept(visitor, data);
        }
        return data;
    }

    public final String toString()
    {
        return "EvalOrStateNode nodes=" + childNodes.size();
    }

    private static final Log log = LogFactory.getLog(EvalOrStateNode.class);
}
