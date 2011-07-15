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
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.named.RemoveStreamViewCapability;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.view.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    protected ExprNode validatedExpr;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<ExprNode> expressionParameters) throws ViewParameterException
    {
        if (expressionParameters.size() != 1) {
            String errorMessage = "Expression window view requires a single expression as a parameter";
            throw new ViewParameterException(errorMessage);
        }

        Map<String, Object> builtinTypeDef = new LinkedHashMap<String, Object>();
        builtinTypeDef.put("event_count", Integer.class);
        EventType builtinMapType = viewFactoryContext.getEventAdapterService().createAnonymousMapType(viewFactoryContext.getStatementId() + "_exprview", builtinTypeDef);

        StreamTypeService streamTypeService = new StreamTypeServiceImpl(new EventType[] {eventType, builtinMapType}, new String[2], new boolean[2], viewFactoryContext.getStatementContext().getEngineURI(), false);

        validatedExpr = ViewFactorySupport.validateExpr(viewFactoryContext.getStatementContext(), expressionParameters.get(0), streamTypeService, 0);
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewParameterException
    {
        this.eventType = parentEventType;
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

    public View makeView(StatementContext statementContext)
    {
        IStreamRandomAccess randomAccess = null;

        if (randomAccessGetterImpl != null)
        {
            randomAccess = new IStreamRandomAccess(randomAccessGetterImpl);
            randomAccessGetterImpl.updated(randomAccess);
        }

        if (isRemoveStreamHandling)
        {
            // TODO return new LengthWindowViewRStream(this, size);
        }
        else
        {
            return new ExpressionWindowView(this, randomAccess);
        }
        return null; // TODO
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public boolean canReuse(View view)
    {
        //return myView.isEmpty(); // TODO
        return false;
    }
}
