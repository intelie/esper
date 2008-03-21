package com.espertech.esperio.subscription;

import com.espertech.esperio.OutputAdapter;
import com.espertech.esperio.AdapterSPI;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.core.EPStatementHandle;
import com.espertech.esper.core.EPStatementHandleCallback;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.filter.FilterHandleCallback;
import com.espertech.esper.filter.FilterSpecCompiled;
import com.espertech.esper.filter.FilterSpecParam;
import com.espertech.esper.filter.FilterValueSet;
import com.espertech.esper.util.ManagedLockImpl;

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
    protected String eventTypeAlias;

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

    public String getEventTypeAlias()
    {
        return eventTypeAlias;
    }

    /**
     * Set the event type name we are looking for.
     * @param eventTypeAlias is a type name
     */
    public void setEventTypeAlias(String eventTypeAlias)
    {
        this.eventTypeAlias = eventTypeAlias;
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
        EventType eventType = spi.getEventAdapterService().getExistsTypeByAlias(eventTypeAlias);
        FilterValueSet fvs = new FilterSpecCompiled(eventType, null, new LinkedList<FilterSpecParam>()).getValueSet(null);

        String name = "subscription:" + subscriptionName;
        EPStatementHandle statementHandle = new EPStatementHandle(name, new ManagedLockImpl(name), name, false);
        EPStatementHandleCallback registerHandle = new EPStatementHandleCallback(statementHandle, this);
        spi.getFilterService().add(fvs, registerHandle);
    }
}
