package net.esper.adapter.subscription;

import net.esper.adapter.OutputAdapter;
import net.esper.adapter.AdapterSPI;
import net.esper.client.EPServiceProvider;
import net.esper.core.EPServiceProviderSPI;
import net.esper.core.EPStatementHandle;
import net.esper.core.EPStatementHandleCallback;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.filter.FilterHandleCallback;
import net.esper.filter.FilterSpecCompiled;
import net.esper.filter.FilterSpecParam;
import net.esper.filter.FilterValueSet;
import net.esper.util.ManagedLockImpl;

import java.util.LinkedList;

/**
 * Subscription is a concept for selecting events for processing out of all events available from an engine instance.
 */
public abstract class BaseSubscription implements Subscription, FilterHandleCallback
{
    protected OutputAdapter adapter;
    protected String eventTypeAlias;
    protected String subscriptionName;

    public abstract void matchFound(EventBean event);

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
        FilterValueSet fvs = new FilterSpecCompiled(eventType, new LinkedList<FilterSpecParam>()).getValueSet(null);

        String name = "subscription:" + subscriptionName;
        EPStatementHandle statementHandle = new EPStatementHandle(new ManagedLockImpl(name), name);
        EPStatementHandleCallback registerHandle = new EPStatementHandleCallback(statementHandle, this);
        spi.getFilterService().add(fvs, registerHandle);
    }
}
