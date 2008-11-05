/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.ext;

import com.espertech.esper.core.StatementContext;
import com.espertech.esper.epl.core.ViewResourceCallback;
import com.espertech.esper.epl.named.RemoveStreamViewCapability;
import com.espertech.esper.event.EventType;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.view.*;
import com.espertech.esper.view.window.RandomAccessByIndexGetter;

import java.util.Arrays;
import java.util.List;

/**
 * Factory for sort window views.
 */
public class SortWindowViewFactory implements DataWindowViewFactory
{
    /**
     * The sort-by field names.
     */
    protected String[] sortFieldNames;

    /**
     * The flags defining the ascending or descending sort order.
     */
    protected boolean[] isDescendingValues;

    /**
     * The sort window size.
     */
    protected int sortWindowSize;

    /**
     * The access into the collection for use with 'previous'.
     */
    protected RandomAccessByIndexGetter randomAccessGetterImpl;

    private EventType eventType;

    public void setViewParameters(ViewFactoryContext viewFactoryContext, List<Object> viewParameters) throws ViewParameterException
    {
        String errorMessage = "Sort window view requires a field name, a boolean sort order and a numeric size parameter or parameter list";
        if (viewParameters.size() == 3)
        {
            if ((viewParameters.get(0) instanceof String) &&
                (viewParameters.get(1) instanceof Boolean) &&
                (viewParameters.get(2) instanceof Number))
            {
                sortFieldNames = new String[] {(String)viewParameters.get(0)};
                isDescendingValues = new boolean[] {(Boolean) viewParameters.get(1)};
                Number sizeParam = (Number) viewParameters.get(2);
                if (JavaClassHelper.isFloatingPointNumber(sizeParam))
                {
                    throw new ViewParameterException(errorMessage);
                }
                sortWindowSize = sizeParam.intValue();
            }
            else
            {
                throw new ViewParameterException(errorMessage);
            }
        }
        else if (viewParameters.size() == 2)
        {
            if ( (!(viewParameters.get(0) instanceof Object[]) ||
                 (!(viewParameters.get(1) instanceof Integer))) )
            {
                throw new ViewParameterException(errorMessage);
            }

            Object[] ascFieldsArr = (Object[]) viewParameters.get(0);
            try
            {
                setNamesAndIsDescendingValues(ascFieldsArr);
            }
            catch (RuntimeException ex)
            {
                throw new ViewParameterException(errorMessage + ",reason:" + ex.getMessage());
            }

            Number sizeParam = (Number) viewParameters.get(1);
            if (JavaClassHelper.isFloatingPointNumber(sizeParam))
            {
                throw new ViewParameterException(errorMessage);
            }
            sortWindowSize = sizeParam.intValue();
        }
        else
        {
            throw new ViewParameterException(errorMessage);
        }

        if (sortWindowSize < 1)
        {
            throw new ViewParameterException("Illegal argument for sort window size of sort window");
        }
    }

    public void attach(EventType parentEventType, StatementContext statementContext, ViewFactory optionalParentFactory, List<ViewFactory> parentViewFactories) throws ViewAttachException
    {
        // Attaches to parent views where the sort fields exist and implement Comparable
        String result = null;
        for(String name : sortFieldNames)
        {
            result = PropertyCheckHelper.exists(parentEventType, name);

            if(result != null)
            {
                throw new ViewAttachException(result);
            }
        }

        eventType = parentEventType;
    }

    public boolean canProvideCapability(ViewCapability viewCapability)
    {
        if (viewCapability instanceof RemoveStreamViewCapability)
        {
            return true;
        }
        if (viewCapability instanceof ViewCapDataWindowAccess)
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
        IStreamSortedRandomAccess sortedRandomAccess = null;

        if (randomAccessGetterImpl != null)
        {
            sortedRandomAccess = new IStreamSortedRandomAccess(randomAccessGetterImpl);
            randomAccessGetterImpl.updated(sortedRandomAccess);
        }

        boolean useCollatorSort = false;
        if (statementContext.getConfigSnapshot() != null)
        {
            useCollatorSort = statementContext.getConfigSnapshot().getEngineDefaults().getLanguage().isSortUsingCollator();
        }

        return new SortWindowView(this, sortFieldNames, isDescendingValues, sortWindowSize, sortedRandomAccess, useCollatorSort);
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

        if (!(view instanceof SortWindowView))
        {
            return false;
        }

        SortWindowView other = (SortWindowView) view;
        if ((other.getSortWindowSize() != sortWindowSize) ||
            (!compare(other.getIsDescendingValues(), isDescendingValues)) ||
            (!Arrays.deepEquals(other.getSortFieldNames(), sortFieldNames)) )
        {
            return false;
        }

        return other.isEmpty();
    }

    @SuppressWarnings({"MultiplyOrDivideByPowerOfTwo"})
    private void setNamesAndIsDescendingValues(Object[] propertiesAndDirections)
    {
        if(propertiesAndDirections.length % 2 != 0)
        {
            throw new IllegalArgumentException("Each property to sort by must have an isDescending boolean qualifier");
        }

        int length = propertiesAndDirections.length / 2;
        sortFieldNames = new String[length];
        isDescendingValues  = new boolean[length];

        for(int i = 0; i < length; i++)
        {
            sortFieldNames[i] = (String)propertiesAndDirections[2*i];
            isDescendingValues[i] = (Boolean)propertiesAndDirections[2*i + 1];
        }
    }

    private boolean compare(boolean[] one, boolean[] two)
    {
        if (one.length != two.length)
        {
            return false;
        }

        for (int i = 0; i < one.length; i++)
        {
            if (one[i] != two[i])
            {
                return false;
            }
        }

        return true;
    }
}
