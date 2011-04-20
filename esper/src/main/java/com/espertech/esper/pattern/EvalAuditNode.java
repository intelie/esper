/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;

/**
 * This class represents an 'or' operator in the evaluation tree representing any event expressions.
 */
public class EvalAuditNode extends EvalNodeBase
{
    private static final long serialVersionUID = 2024418131446981960L;

    private final boolean auditPattern;
    private final boolean auditPatternInstance;
    private final String patternExpr;
    private final transient EvalAuditInstanceCount instanceCount;
    private final boolean filterChildNonQuitting;

    private transient PatternContext context;

    public EvalAuditNode(boolean auditPattern, boolean auditPatternInstance, String patternExpr, EvalAuditInstanceCount instanceCount, boolean filterChildNonQuitting) {
        this.auditPattern = auditPattern;
        this.auditPatternInstance = auditPatternInstance;
        this.patternExpr = patternExpr;
        this.instanceCount = instanceCount;
        this.filterChildNonQuitting = filterChildNonQuitting;
    }

    public EvalStateNode newState(Evaluator parentNode,
                                        MatchedEventMap beginState,
                                        PatternContext context, EvalStateNodeNumber stateNodeId)
    {
        if (this.context == null) {
            this.context = context;
        }
        return new EvalAuditStateNode(parentNode, this, beginState);
    }

    public boolean isAuditPattern() {
        return auditPattern;
    }

    public String getPatternExpr() {
        return patternExpr;
    }

    public EvalAuditInstanceCount getInstanceCount() {
        return instanceCount;
    }

    public PatternContext getContext() {
        return context;
    }

    public final String toString()
    {
        return ("EvalAuditStateNode children=" + this.getChildNodes().size());
    }

    public void decreaseRefCount(EvalAuditStateNode current) {
        if (!auditPatternInstance) {
            return;
        }
        instanceCount.decreaseRefCount(this.getChildNodes().get(0), current, patternExpr, context.getStatementName());
    }

    public void increaseRefCount(EvalAuditStateNode current) {
        if (!auditPatternInstance) {
            return;
        }
        instanceCount.increaseRefCount(this.getChildNodes().get(0), current, patternExpr, context.getStatementName());
    }

    public boolean isFilterChildNonQuitting() {
        return filterChildNonQuitting;
    }
}
