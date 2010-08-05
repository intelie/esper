/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

import com.espertech.esper.epl.expression.ExprConstantNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.ExecutionPathDebugLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Set;

/**
 * This class represents a match-until observer in the evaluation tree representing any event expressions.
 */
public final class EvalMatchUntilNode extends EvalNode
{
    private ExprNode lowerBounds;
    private ExprNode upperBounds;
    private final EvalMatchUntilSpec spec;
    private MatchedEventConvertor convertor;
    private String[] tagsArrayed;
    private boolean constantBounds;
    private Integer constantUpper;
    private Integer constantLower;
    private static final long serialVersionUID = -959026931248456356L;

    /**
     * Ctor.
     * @param spec specifies an optional range
     */
    public EvalMatchUntilNode(EvalMatchUntilSpec spec, MatchedEventConvertor convertor)
    {
        this.spec = spec;
        this.convertor = convertor;

        constantBounds = true;
        // TODO - test string invalid param
        if (spec.getLowerBounds() instanceof ExprConstantNode) {
            constantLower = (Integer) spec.getLowerBounds().evaluate(null, true, null);
        }
        else {
            constantBounds = false;
        }
        if (spec.getUpperBounds() instanceof ExprConstantNode) {
            constantUpper = (Integer) spec.getUpperBounds().evaluate(null, true, null);
        }
        else {
            constantBounds = false;
        }
    }

    /**
     * Returns the range specification, which is never null however may contain null low and high endpoints.
     * @return range spec
     */
    public EvalMatchUntilSpec getSpec()
    {
        return spec;
    }

    /**
     * Returns an array of tags for events, which is all tags used within the repeat-operator.
     * @return array of tags
     */
    public String[] getTagsArrayed()
    {
        return tagsArrayed;
    }

    /**
     * Sets the convertor for matching events to events-per-stream.
     * @param convertor convertor
     */
    public void setConvertor(MatchedEventConvertor convertor) {
        this.convertor = convertor;
    }

    public ExprNode getLowerBounds() {
        return lowerBounds;
    }

    public ExprNode getUpperBounds() {
        return upperBounds;
    }

    public void setLowerBounds(ExprNode lowerBounds) {
        this.lowerBounds = lowerBounds;
    }

    public void setUpperBounds(ExprNode upperBounds) {
        this.upperBounds = upperBounds;
    }

    /**
     * Sets the tags used within the repeat operator.
     * @param tagsArrayedSet tags used within the repeat operator
     */
    public void setTagsArrayedSet(Set<String> tagsArrayedSet)
    {
        if (tagsArrayedSet != null)
        {
            tagsArrayed = tagsArrayedSet.toArray(new String[tagsArrayedSet.size()]);
        }
        else
        {
            tagsArrayed = new String[0];
        }
    }

    public final EvalStateNode newState(Evaluator parentNode,
                                                 MatchedEventMap beginState,
                                                 PatternContext context,
                                                 Object stateNodeId)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".newState");
        }
        
        if (convertor == null) {
            throw new IllegalStateException("No match-event expression conversion provided");
        }

        if (constantBounds) {
            // if the high and low are bounded to the same value, there should be no until
            if ((constantLower != null) && (constantLower.equals(constantUpper)))
            {
                if (getChildNodes().size() > 2)
                {
                    throw new IllegalStateException("Expected number of child nodes incorrect, expected 1 (no-until) or 2 (with until) child nodes, found "
                            + getChildNodes().size() + " for bound match");
                }
            }
            else
            {
                // expecting a match-expression and an until-expression
                if (getChildNodes().size() != 2)
                {
                    throw new IllegalStateException("Expected number of child nodes incorrect, expected 2 child nodes, found "
                            + getChildNodes().size());
                }
            }
        }

        return context.getPatternStateFactory().makeMatchUntilState(parentNode, this, beginState, stateNodeId, convertor);
    }

    public final String toString()
    {
        return ("EvalMatchUntilNode children=" + this.getChildNodes().size());
    }

    private static final Log log = LogFactory.getLog(EvalMatchUntilNode.class);
}
