/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.pattern;


import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.util.AuditPath;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringWriter;
import java.util.Map;

/**
 * This class represents the state of a followed-by operator in the evaluation state tree.
 */
public final class EvalAuditStateNode extends EvalStateNode implements Evaluator
{
    private static final Log auditLog = LogFactory.getLog(AuditPath.AUDIT_LOG);

    private final EvalAuditNode evalAuditNode;
    private EvalStateNode childState;

    /**
     * Constructor.
     * @param parentNode is the parent evaluator to call to indicate truth value
     * @param beginState contains the events that make up prior matches
     * @param evalAuditNode is the factory node associated to the state
     */
    public EvalAuditStateNode(Evaluator parentNode,
                              EvalAuditNode evalAuditNode,
                              MatchedEventMap beginState)
    {
        super(parentNode, null);

        this.evalAuditNode = evalAuditNode;

        EvalNode child = evalAuditNode.getChildNodes().get(0);
        EvalStateNode childState = child.newState(this, beginState, null);
        this.childState = childState;
    }

    public EvalNode getFactoryNode() {
        return evalAuditNode;
    }

    public final void start()
    {
        childState.start();
        evalAuditNode.increaseRefCount(this);
    }

    public final void evaluateTrue(MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted)
    {
        if (evalAuditNode.isAuditPattern() && auditLog.isInfoEnabled()) {
            auditLog.info(toStringEvaluateTrue(this, evalAuditNode.getPatternExpr(), evalAuditNode.getContext().getStatementName(), matchEvent, fromNode, isQuitted));
        }

        if (isQuitted)
        {
            childState = null;
            evalAuditNode.decreaseRefCount(this);
        }

        this.getParentEvaluator().evaluateTrue(matchEvent, this, isQuitted);
    }

    public final void evaluateFalse(EvalStateNode fromNode)
    {
        if (evalAuditNode.isAuditPattern() && auditLog.isInfoEnabled()) {
            auditLog.info(toStringEvaluateFalse(this, evalAuditNode.getPatternExpr(), evalAuditNode.getContext().getStatementName(), fromNode));
        }

        this.getParentEvaluator().evaluateFalse(this);
    }

    public final void quit()
    {
        if (childState != null) {
            childState.quit();
        }
        childState = null;
        evalAuditNode.decreaseRefCount(this);
    }

    public final Object accept(EvalStateNodeVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    public final Object childrenAccept(EvalStateNodeVisitor visitor, Object data)
    {
        if (childState != null) {
            childState.accept(visitor, data);
        }
        return data;
    }

    public final String toString()
    {
        return "EvalAuditStateNode";
    }

    public boolean isNotOperator() {
        EvalNode evalNode = evalAuditNode.getChildNodes().get(0);
        return evalNode instanceof EvalNotNode;
    }

    public boolean isFilterChildNonQuitting() {
        return evalAuditNode.isFilterChildNonQuitting();
    }

    public boolean isFilterStateNode() {
        return evalAuditNode.getChildNodes().get(0) instanceof EvalFilterNode;
    }

    private static String toStringEvaluateTrue(EvalAuditStateNode current, String patternExpression, String statementName, MatchedEventMap matchEvent, EvalStateNode fromNode, boolean isQuitted) {

        StringWriter writer = new StringWriter();

        writer.write("Statement ");
        writer.write(statementName);
        writer.write(" pattern ");
        writePatternExpr(current, patternExpression, writer);
        writer.write(" evaluate-true {");

        writer.write(" from: ");
        JavaClassHelper.writeInstance(writer, fromNode, false);

        writer.write(" map: {");
        String delimiter = "";
        for (Object entryObj : matchEvent.getMatchingEvents().entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) entryObj;
            writer.write(delimiter);
            writer.write(entry.getKey());
            writer.write("=");
            if (entry.getValue() instanceof EventBean) {
                writer.write(((EventBean) entry.getValue()).getUnderlying().toString());
            }
            else if (entry.getValue() instanceof EventBean[]) {
                writer.write(EventBeanUtility.summarize((EventBean[]) entry.getValue()));
            }
            delimiter = ", ";
        }

        writer.write("} quitted: ");
        writer.write(Boolean.toString(isQuitted));

        writer.write("}");
        return writer.toString();
    }

    private String toStringEvaluateFalse(EvalAuditStateNode current, String patternExpression, String statementName, EvalStateNode fromNode) {

        StringWriter writer = new StringWriter();
        writer.write("Statement ");
        writer.write(statementName);
        writer.write(" pattern ");
        writePatternExpr(current, patternExpression, writer);
        writer.write(" evaluate-false {");

        writer.write(" from ");
        JavaClassHelper.writeInstance(writer, fromNode, false);

        writer.write("}");
        return writer.toString();
    }

    protected static void writePatternExpr(EvalAuditStateNode current, String patternExpression, StringWriter writer) {
        if (patternExpression != null) {
            writer.write('(');
            writer.write(patternExpression);
            writer.write(')');
        }
        else {
            JavaClassHelper.writeInstance(writer, "subexr", current);
        }
    }

    private static final Log log = LogFactory.getLog(EvalAuditStateNode.class);
}
