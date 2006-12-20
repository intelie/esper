package net.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;

/**
 * Superclass of all nodes in an evaluation tree representing an event pattern expression.
 * Follows the Composite pattern. Child nodes do not carry references to parent nodes, the tree
 * is unidirectional.
 */
public abstract class EvalNode
{
    private final LinkedList<EvalNode> childNodes;

    /**
     * Create the evaluation state node containing the truth value state for each operator in an
     * event expression.
     * @param parentNode is the parent evaluator node that this node indicates a change in truth value to
     * @param beginState is the container for events that makes up the start state
     * @param context is the handle to services required for evaluation
     * @return state node containing the truth value state for the operator
     */
    public abstract EvalStateNode newState(Evaluator parentNode,
                                           MatchedEventMap beginState,
                                           PatternContext context);

    /**
     * Constructor creates a list of child nodes.
     */
    EvalNode()
    {
        childNodes = new LinkedList<EvalNode>();
    }

    /**
     * Adds a child node.
     * @param childNode is the child evaluation tree node to add
     */
    public final void addChildNode(EvalNode childNode)
    {
        childNodes.add(childNode);
    }

    /**
     * Returns list of child nodes.
     * @return list of child nodes
     */
    public final LinkedList<EvalNode> getChildNodes()
    {
        return childNodes;
    }

    /**
     * Recursively print out all nodes.
     * @param prefix is printed out for naming the printed info
     */
    public final void dumpDebug(String prefix)
    {
        log.debug(".dumpDebug " + prefix + this.toString());
        for (EvalNode node : childNodes)
        {
            node.dumpDebug(prefix + "  ");
        }
    }

    private static final Log log = LogFactory.getLog(EvalNode.class);
}
