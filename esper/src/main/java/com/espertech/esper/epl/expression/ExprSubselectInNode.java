/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.spec.StatementSpecRaw;
import com.espertech.esper.event.EventAdapterService;

import java.util.Collection;
import java.util.Map;

/**
 * Represents a subselect in an expression tree.
 */
public class ExprSubselectInNode extends ExprSubselectNode
{
    private boolean isNotIn;
    private transient SubselectEvalStrategy subselectEvalStrategy;
    private static final long serialVersionUID = -7233906204211162498L;

    /**
     * Ctor.
     * @param statementSpec is the lookup statement spec from the parser, unvalidated
     */
    public ExprSubselectInNode(StatementSpecRaw statementSpec)
    {
        super(statementSpec);
    }

    public Class getType()
    {
        return Boolean.class;
    }

    /**
     * Indicate that this is a not-in lookup.
     * @param notIn is true for not-in, or false for regular 'in'
     */
    public void setNotIn(boolean notIn)
    {
        isNotIn = notIn;
    }

    /**
     * Returns true for not-in, or false for in.
     * @return true for not-in
     */
    public boolean isNotIn()
    {
        return isNotIn;
    }

    public void validate(ExprValidationContext validationContext) throws ExprValidationException
    {
        subselectEvalStrategy = SubselectEvalStrategyFactory.createStrategy(this, isNotIn, false, false, null);
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, Collection<EventBean> matchingEvents, ExprEvaluatorContext exprEvaluatorContext)
    {
        return subselectEvalStrategy.evaluate(eventsPerStream, isNewData, matchingEvents, exprEvaluatorContext);
    }

    public Map<String, Object> getEventType() {
        return null;
    }

    public Collection<EventBean> evaluateGetCollEvents(EventBean[] eventsPerStream, boolean isNewData, Collection<EventBean> matchingEvents, ExprEvaluatorContext context) {
        return null;
    }

    public EventType getEventTypeCollection(EventAdapterService eventAdapterService) {
        return null;
    }

    public Class getComponentTypeCollection() throws ExprValidationException {
        return null;
    }

    public Collection evaluateGetCollScalar(EventBean[] eventsPerStream, boolean isNewData, Collection<EventBean> matchingEvents, ExprEvaluatorContext exprEvaluatorContext) {
        return null;
    }

    public boolean isAllowMultiColumnSelect() {
        return false;
    }

    public EventType getEventTypeSingle(EventAdapterService eventAdapterService, String statementId) throws ExprValidationException {
        return null;
    }

    public EventBean evaluateGetEventBean(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        return null;
    }
}
