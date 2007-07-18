/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.view;

import net.esper.collection.TransformEventIterator;
import net.esper.eql.core.ResultSetProcessor;
import net.esper.eql.core.OrderByProcessor;
import net.esper.eql.join.JoinSetIndicator;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.view.ViewSupport;

import java.util.Iterator;

/**
 * Base output processing view that has the responsibility to serve up event type and
 * statement iterator.
 * <p>
 * Implementation classes may enforce an output rate stabilizing or limiting policy.
 */
public abstract class OutputProcessView extends ViewSupport implements JoinSetIndicator
{
    /**
     * Processes the parent views result set generating events for pushing out to child view.
     */
    protected final ResultSetProcessor resultSetProcessor;
    private boolean isJoin;

    /**
     * Ctor.
     * @param resultSetProcessor processes the results posted by parent view or joins
     * @param isJoin is true for join statements
     */
    protected OutputProcessView(ResultSetProcessor resultSetProcessor, boolean isJoin)
    {
        this.resultSetProcessor = resultSetProcessor;
        this.isJoin = isJoin;
    }

    public EventType getEventType()
    {
    	if(resultSetProcessor != null)
    	{
            EventType eventType = resultSetProcessor.getResultEventType();
            if (eventType != null)
            {
                return eventType;
            }
            return parent.getEventType();
    	}
    	else
    	{
    		return parent.getEventType();
    	}
    }

    public Iterator<EventBean> iterator()
    {
        if (isJoin)
        {
            throw new UnsupportedOperationException("Joins statements do not allow iteration");
        }
        if(resultSetProcessor != null)
    	{
            return resultSetProcessor.getIterator(parent); 
    	}
    	else
    	{
    		return parent.iterator();
    	}
    }    
}
