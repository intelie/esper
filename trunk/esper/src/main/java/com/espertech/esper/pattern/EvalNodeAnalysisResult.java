/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import java.util.List;
import java.util.ArrayList;

/**
 * Result of analysis of pattern expression node tree.
 */
public class EvalNodeAnalysisResult
{
    private List<EvalFilterNode> filterNodes = new ArrayList<EvalFilterNode>();
    private List<EvalGuardNode> guardNodes = new ArrayList<EvalGuardNode>();
    private List<EvalObserverNode> observerNodes = new ArrayList<EvalObserverNode>();
    private List<EvalMatchUntilNode> repeatNodes = new ArrayList<EvalMatchUntilNode>();

    /**
     * Adds a filter node.
     * @param filterNode filter node to add
     */
    public void add(EvalFilterNode filterNode)
    {
        filterNodes.add(filterNode);
    }
    /**
     * Adds a guard node.
     * @param guardNode node to add
     */
    public void add(EvalGuardNode guardNode)
    {
        guardNodes.add(guardNode);
    }
    /**
     * Adds an observer node.
     * @param observerNode node to add
     */
    public void add(EvalObserverNode observerNode)
    {
        observerNodes.add(observerNode);
    }

    /**
     * Adds an match-until node.
     * @param untilNode node to add
     */
    public void add(EvalMatchUntilNode untilNode)
    {
        repeatNodes.add(untilNode);
    }

    /**
     * Returns filter nodes.
     * @return filter nodes
     */
    public List<EvalFilterNode> getFilterNodes()
    {
        return filterNodes;
    }

    /**
     * Returns guard nodes.
     * @return guard nodes
     */
    public List<EvalGuardNode> getGuardNodes()
    {
        return guardNodes;
    }

    /**
     * Returns observer nodes.
     * @return observer nodes
     */
    public List<EvalObserverNode> getObserverNodes()
    {
        return observerNodes;
    }

    /**
     * Returns the repeat-nodes.
     * @return repeat nodes
     */
    public List<EvalMatchUntilNode> getRepeatNodes()
    {
        return repeatNodes;
    }
}
