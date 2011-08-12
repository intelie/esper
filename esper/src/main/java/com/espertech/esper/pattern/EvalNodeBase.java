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

import java.util.ArrayList;
import java.util.List;

/**
 * Superclass of all nodes in an evaluation tree representing an event pattern expression.
 * Follows the Composite pattern. Child nodes do not carry references to parent nodes, the tree
 * is unidirectional.
 */
public abstract class EvalNodeBase implements EvalNode
{
    private static final Log log = LogFactory.getLog(EvalNodeBase.class);
    private static final long serialVersionUID = 0L;

    private final List<EvalNode> childNodes;
    private EvalNodeNumber nodeNumber;
    private transient PatternContext context;

    /**
     * Create the evaluation state node containing the truth value state for each operator in an
     * event expression.
     *
     * @param parentNode is the parent evaluator node that this node indicates a change in truth value to
     * @param beginState is the container for events that makes up the start state
     * @param stateNodeId is the new state object's identifier
     * @return state node containing the truth value state for the operator
     */
    public abstract EvalStateNode newState(Evaluator parentNode,
                                           MatchedEventMap beginState,
                                           EvalStateNodeNumber stateNodeId);

    /**
     * Constructor creates a list of child nodes.
     */
    EvalNodeBase()
    {
        childNodes = new ArrayList<EvalNode>();
    }

    public final PatternContext getContext() {
        return context;
    }

    public final void setContext(PatternContext context) {
        this.context = context;
    }

    /**
     * Returns the evaluation node's relative node number in the evaluation node tree.
     * @return node number
     */
    public EvalNodeNumber getNodeNumber()
    {
        return nodeNumber;
    }

    /**
     * Sets the evaluation node's relative node number.
     * @param nodeNumber is the node number to set
     */
    public void setNodeNumber(EvalNodeNumber nodeNumber)
    {
        this.nodeNumber = nodeNumber;
    }

    /**
     * Adds a child node.
     * @param childNode is the child evaluation tree node to add
     */
    public void addChildNode(EvalNode childNode)
    {
        childNodes.add(childNode);
    }

    public void addChildNodes(List<EvalNode> childNodesToAdd) {
        childNodes.addAll(childNodesToAdd);
    }

    /**
     * Returns list of child nodes.
     * @return list of child nodes
     */
    public List<EvalNode> getChildNodes()
    {
        return childNodes;
    }
}
