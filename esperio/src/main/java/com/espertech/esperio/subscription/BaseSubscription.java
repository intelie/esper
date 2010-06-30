/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.subscription;

import com.espertech.esperio.OutputAdapter;
import com.espertech.esperio.AdapterSPI;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.core.EPStatementHandleCallback;
import com.espertech.esper.core.StatementFilterVersion;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.filter.FilterHandleCallback;
import com.espertech.esper.filter.FilterSpecCompiled;
import com.espertech.esper.filter.FilterSpecParam;
import com.espertech.esper.filter.FilterValueSet;
import com.espertech.esper.util.ManagedLockImpl;
import com.espertech.esper.epl.metric.StatementMetricHandle;

import java.util.LinkedList;

/**
 * Subscription is a concept for selecting events for processing out of all events available from an engine instance.
 */
public abstract class BaseSubscription implements Subscription, FilterHandleCallback
{
    /**
     * The output adapter to which the subscription applies.
     */
    protected OutputAdapter adapter;

    /**
     * The event type of the events we are subscribing for.
     */
    protected String eventTypeName;

    /**
     * The name of the subscription.
     */
    protected String subscriptionName;

    public abstract void matchFound(EventBean event);

    /**
     * Ctor, assigns default name.
     */
    public BaseSubscription()
    {
        subscriptionName = "default";
    }

    public void setSubscriptionName(String subscriptionName)
    {
        this.subscriptionName = subscriptionName;
    }

    public String getSubscriptionName()
    {
        return subscriptionName;
    }

    public String getEventTypeName()
    {
        return eventTypeName;
    }

    /**
     * Set the event type name we are looking for.
     * @param eventTypeName is a type name
     */
    public void seteventTypeName(String eventTypeName)
    {
        this.eventTypeName = eventTypeName;
    }

    public OutputAdapter getAdapter()
    {
        return adapter;
    }

    public void registerAdapter(OutputAdapter adapter)
    {
        this.adapter = adapter;
        EPServiceProvider epService = ((AdapterSPI) adapter).getEPServiceProvider();
        if (!(epService instanceof EPServiceProviderSPI))
        {
            throw new IllegalArgumentException("Invalid type of EPServiceProvider");
        }
        EPServiceProviderSPI spi = (EPServiceProviderSPI) epService;
        EventType eventType = spi.getEventAdapterService().getExistsTypeByName(eventTypeName);
        FilterValueSet fvs = new FilterSpecCompiled(eventType, null, new LinkedList<FilterSpecParam>(), null).getValueSet(null);

        String name = "subscription:" + subscriptionName;
        StatementMetricHandle metricsHandle = spi.getMetricReportingService().getStatementHandle(name, name);
        EPStatementHandle statementHandle = new EPStatementHandle(name, new ManagedLockImpl(name), name, false, metricsHandle, 0, false, new StatementFilterVersion());
        EPStatementHandleCallback registerHandle = new EPStatementHandleCallback(statementHandle, this);
        spi.getFilterService().add(fvs, registerHandle);
    }
}