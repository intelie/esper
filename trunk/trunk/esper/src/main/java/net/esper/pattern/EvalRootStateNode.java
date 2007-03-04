package net.esper.pattern;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * This class is always the root node in the evaluation state tree representing any activated event expression.
 * It hold the handle to a further state node with subnodes making up a whole evaluation state tree.
 */
public final class EvalRootStateNode extends EvalStateNode implements Evaluator, PatternStopCallback
{
    private EvalStateNode topStateNode;
    private PatternMatchCallback callback;

    /**
     * Constructor.
     * @param rootSingleChildNode is the root nodes single child node
     * @param beginState contains the events that make up prior matches
     * @param context contains handles to services required
     */
    public EvalRootStateNode(EvalNode rootSingleChildNode,
                                   MatchedEventMap beginState,
                                   PatternContext context)
    {
        super(null);

        if (log.isDebugEnabled())
        {
            log.debug(".constructor");
        }

        topStateNode = rootSingleChildNode.newState(this, beginState, context);

        if (log.isDebugEnabled())
        {
            log.debug(".constructor Done, dumping full tree");
            EvalStateNodePrinterVisitor visitor = new EvalStateNodePrinterVisitor();
            this.accept(visitor, null);
        }
    }

    /**
     * Hands the callback to use to indicate matching events.
     * @param callback is invoked when the event expressions turns true.
     */
    public final void setCallback(PatternMatchCallback callback)
    {
        this.callback = callback;
    }

    public final void start()
    {
        if (log.isDebugEnabled())
        {
            log.debug(".start Starting single child");
        }

        if (topStateNode == null)
        {
            throw new IllegalStateException("root state node is inactive");
        }

        topStateNode.start();
    }

    public final void stop()
    {
        quit();
    }

    protected final void quit()
    {
        if (topStateNode != null)
        {
            topStateNode.quit();
        }
        topStateNode = null;
    }

    public final void evaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".evaluateTrue isQuitted=" + isQuitted);
        }

        if (isQuitted)
        {
            topStateNode = null;
        }

        callback.matchFound(matchEvent.getMatchingEvents());
    }

    public final void evaluateFalse(EvalStateNode fromNode)
    {
        log.debug(".evaluateFalse");        
    }

    protected final Object accept(EvalStateNodeVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    public final Object childrenAccept(EvalStateNodeVisitor visitor, Object data)
    {
        if (topStateNode != null)
        {
            topStateNode.accept(visitor, data);
        }
        return data;
    }

    public final String toString()
    {
        return "EvalRootStateNode topStateNode=" + topStateNode;
    }

    private static final Log log = LogFactory.getLog(EvalRootStateNode.class);
}
