package net.esper.adapter.subscription;

import net.esper.adapter.*;

/**
 * Created for ESPER.
 */
public interface Subscription
{
  public void setAlias(String alias);
  public String getAlias();
  public String getEventTypeAlias();
  public void setEventTypeAlias(String eventTypeAlias);
  public OutputAdapter getAdapter();
  public void setAdapter(OutputAdapter adapter);
  public void registerAdapter(OutputAdapter adapter);
}
