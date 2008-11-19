/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.window;

import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.named.RemoveStreamViewCapability;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.type.TimePeriodParameter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.view.*;
import com.espertech.esper.core.StatementContext;

import java.util.List;

/**
 * Factory for {@link ExternallyTimedWindowView}.
 */
public class ExternallyTimedWindowViewFactory implements DataWindowViewFactory
{
    private EventType eventType;

    /**
     * The timestamp property name.
     */
    protected String timestampFieldName;

    /**
     * The number of msec to expire.
     */
    protected long millisecondsBeforeExpiry;

    /**
     * Flag to indicate that the view must handle the removed events from a parent view.
     */
    protected boolean isRemoveStreamHandling;

    /**
     * The getter for direct access into the window.
     */
    protected RandomAccessByIndexGetter randomAccessGetterImpl;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        List<Object> viewParameters = ViewFactorySupport.validateAndEvaluate("Externally-timed window view", viewFactoryContext, expressionParameters);
        String errorMessage = "Externally-timed window view requires a timestamp field name and a numeric or time period parameter";
        if (viewParameters.size() != 2)
        {
            throw new ViewParameterException(errorMessage);
        }

        if (!(viewParameters.get(0) instanceof String))
        {
            throw new ViewParameterException(errorMessage);
        }
        timestampFieldName = (String) viewParameters.get(0);

        Object parameter = viewParameters.get(1);
        if (parameter instanceof TimePeriodParameter)
        {
            TimePeriodParameter param = (TimePeriodParameter) parameter;
            millisecondsBeforeExpiry = Math.round(1000d * param.getNumSeconds());
        }
        else if (!(parameter instanceof Number))
        {
            throw new ViewParameterException(errorMessage);
        }
        else
        {
            Number param = (Number) parameter;
            if (JavaClassHelper.isFloatingPointNumber(param))
            {
                millisecondsBeforeExpiry = Math.round(1000d * param.doubleValue());
            }
            else
            {
                millisecondsBeforeExpiry = 1000 * param.longValue();
            }
        }
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        String message = PropertyCheckHelper.checkLong(parentEventType, timestampFieldName);
        if (message != null)
        {
            throw new ViewParameterException(message);
        }
        this.eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof ViewCapDataWindowAccess)
        {
            return true;
        }
        else if (viewCapability instanceof RemoveStreamViewCapability)
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

    public View makeView(StatementContext statementContext)
    {
        IStreamRandomAccess randomAccess = null;

        if (randomAccessGetterImpl != null)
        {
            randomAccess = new IStreamRandomAccess(randomAccessGetterImpl);
            randomAccessGetterImpl.updated(randomAccess);
        }

        return new ExternallyTimedWindowView(this, timestampFieldName, millisecondsBeforeExpiry, randomAccess, isRemoveStreamHandling);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        if (randomAccessGetterImpl != null)
        {
            return false;
        }

        if (!(view instanceof ExternallyTimedWindowView))
        {
            return false;
        }

        ExternallyTimedWindowView myView = (ExternallyTimedWindowView) view;
        if ((myView.getMillisecondsBeforeExpiry() != millisecondsBeforeExpiry) ||
            (!myView.getTimestampFieldName().equals(timestampFieldName)))
        {
            return false;
        }
        return myView.isEmpty();
    }
}
