package net.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.pattern.guard.Quitable;
import net.esper.pattern.guard.GuardFactory;
import net.esper.pattern.guard.Guard;

/**
 * This class represents the state of a "within" operator in the evaluation state tree.
 * The within operator applies to a subexpression and is thus expected to only
 * have one child node.
 */
public final class EvalGuardStateNode extends EvalStateNode implements Evaluator, Quitable
{
    private EvalStateNode activeChildNode;
    private final Guard guard;

    /**
     * Constructor.
     * @param parentNode is the parent evaluator to call to indicate truth value
     * @param guardFactory is the factory to use for the guard node
     * @param singleWithinChildNode is the single child node of the within node
     * @param beginState contains the events that make up prior matches
     * @param context contains handles to services required
     */
    public EvalGuardStateNode(Evaluator parentNode,
                               GuardFactory guardFactory,
                                 EvalNode singleWithinChildNode,
                                 MatchedEventMap beginState,
                                 PatternContext context)
    {
        super(parentNode);

        if (log.isDebugEnabled())
        {
            log.debug(".constructor");
        }

        guard = guardFactory.makeGuard(context, this);

        this.activeChildNode = singleWithinChildNode.newState(this, beginState, context);
    }

    public final void start()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".start Starting within timer and single child node");
        }

        if (activeChildNode == null)
        {
            throw new IllegalStateException("Invalid state, child state node is inactive");
        }

        // Start the single child state
        activeChildNode.start();

        // Start the guard
        guard.startGuard();
    }

    public final void evaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".evaluateTrue fromNode=" + fromNode.hashCode());
        }

        // If one of the children quits, remove the child
        if (isQuitted)
        {
            activeChildNode = null;

            // Stop guard, since associated subexpression is gone
            guard.stopGuard();
        }

        boolean guardPass = guard.inspect(matchEvent);
        if (guardPass)
        {
            this.getParentEvaluator().evaluateTrue(matchEvent, this, isQuitted);
        }
    }

    public final void evaluateFalse(EvalStateNode fromNode)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".evaluateFalse Removing fromNode=" + fromNode.hashCode());
        }
    }

    protected final void quit()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".guardQuit Stopping all children");
        }

        if (activeChildNode != null)
        {
            activeChildNode.quit();
            guard.stopGuard();
        }

        activeChildNode = null;
    }

    public final Object accept(EvalStateNodeVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    public final Object childrenAccept(EvalStateNodeVisitor visitor, Object data)
    {
        if (activeChildNode != null)
        {
            activeChildNode.accept(visitor, data);
        }
        return data;
    }

    public final String toString()
    {
        return "EvaluationWitinStateNode activeChildNode=" + activeChildNode +
                 " guard=" + guard;
    }

    public void guardQuit()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".guardQuit Guard has quit, stopping child node, activeChildNode=" + activeChildNode);
        }

        // It is possible that the child node has already been quit such as when the parent wait time was shorter.
        // 1. parent node's guard indicates quit to all children
        // 2. this node's guards also indicates quit, however that already occured
        if (activeChildNode != null)
        {
            activeChildNode.quit();
        }
        activeChildNode = null;
    }

    private static final Log log = LogFactory.getLog(EvalGuardStateNode.class);
}
