package net.esper.adapter;

import net.esper.client.UpdateListener;
import net.esper.client.EPException;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPRuntime;
import net.esper.event.EventBean;
import net.esper.core.EPServiceProviderSPI;
import net.esper.schedule.ScheduleSlot;
import net.esper.schedule.SchedulingService;
import net.esper.filter.FilterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.SortedSet;
import java.util.TreeSet;


public interface OutputAdapter extends Adapter
{
  public void setEPServiceProvider(EPServiceProvider epService);
  public EventBean getLastEvent();
  public int getEventCount();
  public int getAndResetEventCount();
}
