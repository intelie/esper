/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.std;

import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprNodeUtility;
import com.espertech.esper.client.EventType;
import com.espertech.esper.view.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Factory for {@link MergeView} instances.
 */
public class MergeViewFactory implements ViewFactory
{
    private List<ExprNode> viewParameters;

    private ExprNode[] criteriaExpressions;
    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        this.viewParameters = expressionParameters;
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        // Find the group by view matching the merge view
        GroupByViewFactory groupByViewFactory = null;
        ExprNode[] unvalidated = viewParameters.toArray(new ExprNode[viewParameters.size()]);
        for (ViewFactory parentView : parentViewFactories)
        {
            if (!(parentView instanceof GroupByViewFactory))
            {
                continue;
            }
            GroupByViewFactory candidateGroupByView = (GroupByViewFactory) parentView;
            if (ExprNodeUtility.deepEquals(candidateGroupByView.getCriteriaExpressions(), unvalidated))
            {
                groupByViewFactory = candidateGroupByView;
            }
        }

        if (groupByViewFactory == null)
        {
            throw new ViewParameterException("Group by view for this merge view could not be found among parent views");
        }
        criteriaExpressions = groupByViewFactory.getCriteriaExpressions();

        // determine types of fields
        Class[] fieldTypes = new Class[criteriaExpressions.length];
        for (int i = 0; i < fieldTypes.length; i++)
        {
            fieldTypes[i] = criteriaExpressions[i].getExprEvaluator().getType();
        }

        // Determine the final event type that the merge view generates
        // This event type is ultimatly generated by AddPropertyValueView which is added to each view branch for each
        // group key.

        // If the parent event type contains the merge fields, we use the same event type
        boolean parentContainsMergeKeys = true;
        String[] fieldNames = new String[criteriaExpressions.length];
        for (int i = 0; i < criteriaExpressions.length; i++)
        {
            String name = criteriaExpressions[i].toExpressionString();
            fieldNames[i] = name;
            if (!(parentEventType.isProperty(name)))
            {
                parentContainsMergeKeys = false;
            }
        }

        // If the parent view contains the fields to group by, the event type after merging stays the same
        if (parentContainsMergeKeys)
        {
            eventType = parentEventType;
        }
        else
        // If the parent event type does not contain the fields, such as when a statistics views is
        // grouped which simply provides a map of calculated values,
        // then we need to add in the merge field as an event property thus changing event types.
        {
            Map<String, Object> additionalProps = new HashMap<String, Object>();
            for (int i = 0; i < fieldNames.length; i++)
            {
                additionalProps.put(fieldNames[i], fieldTypes[i]);
            }
            eventType = statementContext.getEventAdapterService().createAnonymousWrapperType(parentEventType, additionalProps);
        }
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        return false;
    }

    public void setProvideCapability(ViewCapability viewCapability, ViewResourceCallback resourceCallback)
    {
        throw new UnsupportedOperationException("View capability " + viewCapability.getClass().getSimpleName() + " not supported");
    }

    public View makeView(StatementContext statementContext)
    {
        return new MergeView(statementContext, criteriaExpressions, eventType);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (!(view instanceof MergeView))
        {
            return false;
        }

        MergeView myView = (MergeView) view;
        if (!ExprNodeUtility.deepEquals(myView.getGroupFieldNames(), criteriaExpressions))
        {
            return false;
        }
        return true;
    }
}
