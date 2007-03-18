/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern;

/**
 * Superclass of all state nodes in an evaluation node tree representing an event expressions.
 * Follows the Composite pattern. Subclasses are expected to keep their own collection containing child nodes
 * as needed.
 */
public abstract class EvalStateNode
{
    private Evaluator parentEvaluator;

    /**
     * Starts the event expression or an instance of it.
     * Child classes are expected to initialize and start any event listeners
     * or schedule any time-based callbacks as needed.
     */
    protected abstract void start();

    /**
     * Stops the event expression or an instance of it. Child classes are expected to free resources
     * and stop any event listeners or remove any time-based callbacks.
     */
    protected abstract void quit();

    /**
     * Accept a visitor. Child classes are expected to invoke the visit method on the visitor instance
     * passed in.
     * @param visitor on which the visit method is invoked by each node
     * @param data any additional data the visitor may need is passed in this parameter
     * @return any additional data the visitor may need or null
     */
    protected abstract Object accept(EvalStateNodeVisitor visitor, Object data);

    /**
     * Pass the visitor to all child nodes.
     * @param visitor is the instance to be passed to all child nodes
     * @param data any additional data the visitor may need is passed in this parameter
     * @return any additional data the visitor may need or null
     */
    public abstract Object childrenAccept(EvalStateNodeVisitor visitor, Object data);

    /**
     * Constructor.
     * @param parentNode is the evaluator for this node on which to indicate a change in truth value
     */
    EvalStateNode(Evaluator parentNode)
    {
        this.parentEvaluator = parentNode;
    }

    /**
     * Returns the parent evaluator.
     * @return parent evaluator instance
     */
    final Evaluator getParentEvaluator()
    {
        return parentEvaluator;
    }

    /**
     * Sets the parent evaluator.
     * @param parentEvaluator for this node
     */
    final void setParentEvaluator(Evaluator parentEvaluator)
    {
        this.parentEvaluator = parentEvaluator;
    }
}
