/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is always the root node in the evaluation state tree representing any activated event expression.
 * It hold the handle to a further state node with subnodes making up a whole evaluation state tree.
 */
public final class EvalRootStateNode extends EvalStateNode implements Evaluator, PatternStopCallback, EvalRootState
{
    private EvalNode rootSingleChildNode;
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
        super(null, null);
        this.rootSingleChildNode = rootSingleChildNode;

        topStateNode = rootSingleChildNode.newState(this, beginState, null);
    }

    @Override
    public EvalNode getFactoryNode() {
        return rootSingleChildNode;
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

    public final void quit()
    {
        if (topStateNode != null)
        {
            topStateNode.quit();
        }
        topStateNode = null;
    }

    public final void evaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted)
    {
        if (isQuitted)
        {
            topStateNode = null;
        }

        callback.matchFound(matchEvent.getMatchingEvents());
    }

    public final void evaluateFalse(EvalStateNode fromNode)
    {
    }

    public final Object accept(EvalStateNodeVisitor visitor, Object data)
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

    public boolean isFilterStateNode() {
        return false;
    }

    public boolean isNotOperator() {
        return false;
    }

    public boolean isFilterChildNonQuitting() {
        return false;
    }

    public final String toString()
    {
        return "EvalRootStateNode topStateNode=" + topStateNode;
    }

    private static final Log log = LogFactory.getLog(EvalRootStateNode.class);
}
