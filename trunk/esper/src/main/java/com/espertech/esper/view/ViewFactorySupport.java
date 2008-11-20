/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view;

import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.event.EventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Abstract base class for view factories that do not make re-useable views and that do
 * not share view resources with expression nodes.
 */
public abstract class ViewFactorySupport implements ViewFactory
{
    private static Log log = LogFactory.getLog(ViewFactorySupport.class);

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public boolean canReuse(View view)
    {
        return false;
    }

    public static List<Object> validateAndEvaluate(String viewName, ViewFactoryContext viewFactoryContext, List<ExprNode> viewParameters)
            throws ViewParameterException
    {
        return validateAndEvaluate(viewName, viewFactoryContext.getStatementContext(), viewParameters);
    }

    public static Object validateAndEvaluate(String viewName, StatementContext statementContext, ExprNode expression)
            throws ViewParameterException
    {
        return validateAndEvaluateExpr(statementContext, expression, new StreamTypeServiceImpl(statementContext.getEngineURI()), 0);
    }

    public static List<Object> validateAndEvaluate(String viewName, StatementContext statementContext, List<ExprNode> viewParameters)
            throws ViewParameterException
    {
        List<Object> results = new ArrayList<Object>();
        int expressionNumber = 0;
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(statementContext.getEngineURI());
        for (ExprNode expr : viewParameters)
        {
            Object result = validateAndEvaluateExpr(statementContext, expr, streamTypeService, expressionNumber);
            results.add(result);
            expressionNumber++;
        }
        return results;
    }

    public static ExprNode[] validate(String viewName, EventType eventType, StatementContext statementContext, ExprNode[] viewParameters, boolean allowConstantResult)
            throws ViewParameterException
    {
        return validate(viewName, eventType, statementContext, Arrays.asList(viewParameters), allowConstantResult);
    }

    public static ExprNode[] validate(String viewName, EventType eventType, StatementContext statementContext, List<ExprNode> viewParameters, boolean allowConstantResult)
            throws ViewParameterException
    {
        List<ExprNode> results = new ArrayList<ExprNode>();
        int expressionNumber = 0;
        StreamTypeService streamTypeService = new StreamTypeServiceImpl(eventType, null, statementContext.getEngineURI(), eventType.getName());
        for (ExprNode expr : viewParameters)
        {
            ExprNode validated = validateExpr(statementContext, expr, streamTypeService, expressionNumber);
            results.add(validated);
            
            if ((!allowConstantResult) && (validated.isConstantResult()))
            {
                String message = "Invalid view parameter expression " + expressionNumber + ", the expression returns a constant result value, are you sure?";
                log.error(message);
                throw new ViewParameterException(message);
            }

            expressionNumber++;
        }
        return results.toArray(new ExprNode[results.size()]);
    }

    public static void validateReturnsNonConstant(String viewName, ExprNode expression, int index) throws ViewParameterException
    {
        if (expression.isConstantResult())
        {
            String message = "Invalid view parameter expression " + index + ", the expression returns a constant result value, are you sure?";
            log.error(message);
            throw new ViewParameterException(message);
        }
    }

    public static Object evaluateNoProperties(String viewName, ExprNode validatedNode, int expressionNumber) throws ViewParameterException
    {
        ExprNodeSummaryVisitor visitor = new ExprNodeSummaryVisitor();
        validatedNode.accept(visitor);
        if (!visitor.isPlain())
        {
            String message = "Invalid view parameter expression " + expressionNumber + ", " + visitor.getMessage() + " are not allowed within the expression";
            log.error(message);
            throw new ViewParameterException(message);
        }

        return validatedNode.evaluate(null, false);
    }

    private static Object validateAndEvaluateExpr(StatementContext statementContext, ExprNode expression, StreamTypeService streamTypeService, int expressionNumber)
            throws ViewParameterException
    {
        ExprNode validated = validateExpr(statementContext, expression, streamTypeService, expressionNumber);

        try
        {
            return validated.evaluate(null, true);
        }
        catch (RuntimeException ex)
        {
            String message = "Failed to evaluate parameter expression " + expressionNumber;
            if (ex.getMessage() != null)
            {
                message += ": " + ex.getMessage();
            }
            log.error(message, ex);
            throw new ViewParameterException(message);
        }
    }

    private static ExprNode validateExpr(StatementContext statementContext, ExprNode expression, StreamTypeService streamTypeService, int expressionNumber)
            throws ViewParameterException
    {
        ExprNode validated;
        try
        {
            validated = expression.getValidatedSubtree(streamTypeService, statementContext.getMethodResolutionService(),
                    null, statementContext.getSchedulingService(), statementContext.getVariableService());
        }
        catch (ExprValidationException ex)
        {
            String message = "Invalid parameter expression " + expressionNumber;
            if (ex.getMessage() != null)
            {
                message += ": " + ex.getMessage();
            }
            log.error(message, ex);
            throw new ViewParameterException(message);
        }
        return validated;
    }
}
