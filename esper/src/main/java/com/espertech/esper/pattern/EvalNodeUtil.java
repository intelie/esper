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

import java.util.HashSet;
import java.util.Set;

public class EvalNodeUtil
{
    private static final Log log = LogFactory.getLog(EvalNodeUtil.class);

    /**
     * Searched recursivly for pattern evaluation filter nodes.
     * @param currentNode is the root node
     * @return list of filter nodes
     */
    public static EvalNodeAnalysisResult recursiveAnalyzeChildNodes(EvalNode currentNode)
    {
        EvalNodeAnalysisResult evalNodeAnalysisResult = new EvalNodeAnalysisResult();
        recursiveAnalyzeChildNodes(evalNodeAnalysisResult, currentNode);
        return evalNodeAnalysisResult;
    }

    private static void recursiveAnalyzeChildNodes(EvalNodeAnalysisResult evalNodeAnalysisResult, EvalNode currentNode)
    {
        if ((currentNode instanceof EvalFilterNode) ||
            (currentNode instanceof EvalGuardNode) ||
            (currentNode instanceof EvalObserverNode) ||
            (currentNode instanceof EvalMatchUntilNode) ||
            (currentNode instanceof EvalEveryDistinctNode))
        {
            evalNodeAnalysisResult.addNode(currentNode);
        }

        for (EvalNode node : currentNode.getChildNodes())
        {
            recursiveAnalyzeChildNodes(evalNodeAnalysisResult, node);
        }
    }

    /**
     * Returns all child nodes as a set.
     * @param currentNode parent node
     * @return all child nodes
     */
    public static Set<EvalNode> recursiveGetChildNodes(EvalNode currentNode)
    {
        Set<EvalNode> result = new HashSet<EvalNode>();
        recursiveGetChildNodes(result, currentNode);
        return result;
    }

    private static void recursiveGetChildNodes(Set<EvalNode> set, EvalNode currentNode)
    {
        for (EvalNode node : currentNode.getChildNodes())
        {
            set.add(node);
            recursiveGetChildNodes(set, node);
        }
    }
}
