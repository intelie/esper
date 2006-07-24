package net.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.LinkedList;

/**
 * This class contains the state of an 'every' operator in the evaluation state tree.
 * EVERY nodes work as a factory for new state subnodes. When a child node of an EVERY
 * node calls the evaluateTrue method on the EVERY node, the EVERY node will call newState on its child
 * node BEFORE it calls evaluateTrue on its parent node. It keeps a reference to the new child in
 * its list. (BEFORE because the root node could call guardQuit on child nodes for stopping all
 * listeners).
 */
final class EvalEveryStateSpawnEvaluator implements Evaluator
{
    private boolean isEvaluatedTrue;

    public final boolean isEvaluatedTrue()
    {
        return isEvaluatedTrue;
    }

    public final void evaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted)
    {
        log.warn("Event/request processing: Uncontrolled pattern matching of \"every\" operator - infinite loop when using EVERY operator on expression(s) containing a not operator");
        isEvaluatedTrue = true;
    }

    public final void evaluateFalse(EvalStateNode fromNode)
    {
        log.warn("Event/request processing: Uncontrolled pattern matching of \"every\" operator - infinite loop when using EVERY operator on expression(s) containing a not operator");
        isEvaluatedTrue = true;
    }

    private static final Log log = LogFactory.getLog(EvalEveryStateSpawnEvaluator.class);
};

/**
 * Contains the state collected by an "every" operator. The state includes handles to any sub-listeners
 * started by the operator.
 */
public final class EvalEveryStateNode extends EvalStateNode implements Evaluator
{
    private final EvalNode everyChildNode;
    private final List<EvalStateNode> spawnedNodes;
    private final MatchedEventMap beginState;
    private final PatternContext context;

    /**
     * Constructor.
     * @param parentNode is the parent evaluator to call to indicate truth value
     * @param everyChildNode is single child node within the all node
     * @param beginState contains the events that make up prior matches
     * @param context contains handles to services required 
     */
    public EvalEveryStateNode(Evaluator parentNode,
                            EvalNode everyChildNode,
                                  MatchedEventMap beginState,
                                  PatternContext context)
    {
        super(parentNode);

        if (log.isDebugEnabled())
        {
            log.debug(".constructor");
        }

        this.everyChildNode = everyChildNode;
        this.spawnedNodes = new LinkedList<EvalStateNode>();
        this.beginState = beginState.shallowCopy();
        this.context = context;

        EvalStateNode child = everyChildNode.newState(this, beginState, context);
        spawnedNodes.add(child);
    }

    public final void start()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".start Starting single child");
        }

        if (spawnedNodes.size() != 1)
        {
            throw new IllegalStateException("EVERY state node is expected to have single child state node");
        }

        // During the start of the child we need to use the temporary evaluator to catch any event created during a start.
        // Events created during the start would likely come from the "not" operator.
        // Quit the new child again if
        EvalEveryStateSpawnEvaluator spawnEvaluator = new EvalEveryStateSpawnEvaluator();
        EvalStateNode child = spawnedNodes.get(0);
        child.setParentEvaluator(spawnEvaluator);
        child.start();

        // If the spawned expression turned true already, just guardQuit it
        if (spawnEvaluator.isEvaluatedTrue())
        {
            child.quit();
        }
        else
        {
            child.setParentEvaluator(this);
        }
    }

    public final void evaluateFalse(EvalStateNode fromNode)
    {
        log.debug(".evaluateFalse");
    }

    public final void evaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".evaluateTrue fromNode=" + fromNode  + "  isQuitted=" + isQuitted);
        }

        if (isQuitted)
        {
            spawnedNodes.remove(fromNode);
        }

        // See explanation in EvalFilterStateNode for the type check
        if (fromNode instanceof EvalFilterStateNode)
        {
            // We do not need to newState new listeners here, since the filter state node below this node did not guardQuit
        }
        else
        {
            // Spawn all nodes below this EVERY node
            // During the start of a child we need to use the temporary evaluator to catch any event created during a start
            // Such events can be raised when the "not" operator is used.
            EvalNode child = everyChildNode;
            EvalEveryStateSpawnEvaluator spawnEvaluator = new EvalEveryStateSpawnEvaluator();
            EvalStateNode spawned = child.newState(spawnEvaluator, beginState, context);
            spawned.start();

            // If the whole spawned expression already turned true, guardQuit it again
            if (spawnEvaluator.isEvaluatedTrue())
            {
                spawned.quit();
            }
            else
            {
                spawnedNodes.add(spawned);
                spawned.setParentEvaluator(this);
            }
        }

        // All nodes indicate to their parents that their child node did not guardQuit, therefore a false for isQuitted
        this.getParentEvaluator().evaluateTrue(matchEvent, this, false);
    }

    public final void quit()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".guardQuit Quitting EVERY-node all children");
        }

        // Stop all child nodes
        for (EvalStateNode child : spawnedNodes)
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
        for (EvalStateNode spawnedNode : spawnedNodes)
        {
            spawnedNode.accept(visitor, data);
        }

        return data;
    }

    public final String toString()
    {
        return "EvalEveryStateNode spawnedChildren=" + spawnedNodes.size();
    }

    private static final Log log = LogFactory.getLog(EvalEveryStateNode.class);
}
