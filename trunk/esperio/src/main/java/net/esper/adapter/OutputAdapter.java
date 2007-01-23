package net.esper.adapter;

import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.event.EventTypeListener;


public interface OutputAdapter extends Adapter
{
  public void setEPServiceProvider(EPServiceProvider epService);
  public String getEventTypeAlias();
  public void setEventTypeAlias(String eventTypeAlias);
  public EventTypeListener getEventTypeListener();
}
