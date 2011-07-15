/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.window;

import com.espertech.esper.client.EventType;
import com.espertech.esper.core.ExpressionResultCacheService;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.named.RemoveStreamViewCapability;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.view.*;

import java.util.*;

/**
 * Factory for {@link com.espertech.esper.view.window.ExpressionWindowView}.
 */
public class ExpressionWindowViewFactory implements DataWindowViewFactory
{
    /**
     * The access into the data window.
     */
    protected RandomAccessByIndexGetter randomAccessGetterImpl;

    /**
     * Flag to indicate that the view must handle the removed events from a parent view.
     */
    protected boolean isRemoveStreamHandling;

    private EventType eventType;
    protected ExprNode expiryExpression;
    protected MapEventBean builtinMapBean;
    protected Set<String> variableNames;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        if (expressionParameters.size() != 1) {
            String errorMessage = "Expression window view requires a single expression as a parameter";
            throw new ViewParameterException(errorMessage);
        }
        expiryExpression = expressionParameters.get(0);
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        this.eventType = parentEventType;

        // define built-in fields
        Map<String, Object> builtinTypeDef = new LinkedHashMap<String, Object>();
        builtinTypeDef.put(ExpressionWindowView.CURRENT_COUNT, Integer.class);
        builtinTypeDef.put(ExpressionWindowView.OLDEST_TIMESTAMP, Long.class);
        builtinTypeDef.put(ExpressionWindowView.NEWEST_TIMESTAMP, Long.class);
        builtinTypeDef.put(ExpressionWindowView.EXPIRED_COUNT, Integer.class);
        builtinTypeDef.put(ExpressionWindowView.VIEW_REFERENCE, Object.class);
        EventType builtinMapType = statementContext.getEventAdapterService().createAnonymousMapType(statementContext.getStatementId() + "_exprview", builtinTypeDef);
        builtinMapBean = new MapEventBean(new HashMap<String, Object>(), builtinMapType);
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {eventType, builtinMapType}, new String[2], new boolean[2], statementContext.getEngineURI(), false);

        // validate expression
        expiryExpression = ViewFactorySupport.validateExpr(statementContext, expiryExpression, streamTypeService, 0);

        ExprNodeSummaryVisitor summaryVisitor = new ExprNodeSummaryVisitor();
        expiryExpression.accept(summaryVisitor);
        if (summaryVisitor.isHasAggregation() || summaryVisitor.isHasSubselect() || summaryVisitor.isHasStreamSelect() || summaryVisitor.isHasPreviousPrior()) {
            throw new ViewParameterException("Invalid expiry expression: Aggregation, sub-select, previous or prior functions are not supported in this context");
        }

        Class returnType = expiryExpression.getExprEvaluator().getType();
        if (JavaClassHelper.getBoxedType(returnType) != Boolean.class) {
            throw new ViewParameterException("Invalid return value for expiry expression, expected a boolean return value but received " + JavaClassHelper.getParameterAsString(returnType));
        }

        // determine variables used, if any
        ExprNodeVariableVisitor visitor = new ExprNodeVariableVisitor();
        expiryExpression.accept(visitor);
        variableNames = visitor.getVariableNames();
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof ViewCapDataWindowAccess)
        {
            return true;
        }
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        if (!canProvideCapability(viewCapability))
        {
            throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
        }
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            isRemoveStreamHandling = true;
            return;
        }
        if (randomAccessGetterImpl == null)
        {
            randomAccessGetterImpl = new RandomAccessByIndexGetter();
        }
        resourceCallback.setViewResource(randomAccessGetterImpl);
    }

    public View makeView(final StatementContext statementContext)
    {
        IStreamRandomAccess randomAccess = null;

        if (randomAccessGetterImpl != null)
        {
            randomAccess = new IStreamRandomAccess(randomAccessGetterImpl);
            randomAccessGetterImpl.updated(randomAccess);
        }

        ExprEvaluatorContext evaluatorContext = new ExprEvaluatorContext()
        {
            public TimeProvider getTimeProvider()
            {
                return statementContext.getTimeProvider();
            }

            public ExpressionResultCacheService getExpressionResultCacheService() {
                return null;
            }
        };
        return new ExpressionWindowView(this, randomAccess, expiryExpression.getExprEvaluator(), evaluatorContext, builtinMapBean, variableNames, statementContext);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        return false;
    }
}
