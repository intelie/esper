package net.esper.adapter.subscription;

import net.esper.adapter.*;
import net.esper.adapter.jms.*;
import net.esper.event.*;
import net.esper.filter.*;
import net.esper.core.*;
import net.esper.client.*;

import java.util.*;

/**
 * Created for ESPER.
 */
public abstract class BaseSubscription implements Subscription, FilterCallback
{
  protected String alias;
  protected OutputAdapter adapter;
  protected String eventTypeAlias;

  public BaseSubscription()
  {
  }

  public BaseSubscription(String alias, OutputAdapter adapter, String eventTypeAlias)
  {
    this(adapter, eventTypeAlias);
    this.alias = alias;
  }

  public BaseSubscription(OutputAdapter adapter, String eventTypeAlias)
  {
    this.adapter = adapter;
    this.eventTypeAlias = eventTypeAlias;
  }

  public String getAlias()
  {
    return alias;
  }

  public void setAlias(String alias)
  {
    this.alias = alias;
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

  public void setAdapter(OutputAdapter adapter)
  {
    this.adapter = adapter;
  }

  public void registerAdapter(OutputAdapter adapter)
  {
    if (adapter == null)
    {
      return;
    }
    setAdapter(adapter);
    EPServiceProvider epService = this.adapter.getEPServiceProvider();
    if (!(epService instanceof EPServiceProviderSPI))
    {
      throw new IllegalArgumentException("Invalid type of EPServiceProvider");
    }
    EPServiceProviderSPI spi = (EPServiceProviderSPI)epService;
    EventType eventType =
      spi.getEventAdapterService().getEventType(eventTypeAlias);
    FilterValueSet fvs =
      new FilterSpec(eventType, new LinkedList<FilterSpecParam>()).getValueSet(
        null);
    spi.getFilterService().add(fvs, this);
  }

  public abstract void matchFound(EventBean event);

}
