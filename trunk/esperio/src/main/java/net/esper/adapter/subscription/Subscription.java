package net.esper.adapter.subscription;

import net.esper.adapter.*;

public interface Subscription
{
    public String getSubscriptionName();
    public void setSubscriptionName(String name);
    public String getEventTypeAlias();
    public OutputAdapter getAdapter();
    public void registerAdapter(OutputAdapter adapter);
}
