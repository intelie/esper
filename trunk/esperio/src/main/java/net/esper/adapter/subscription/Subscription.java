package net.esper.adapter.subscription;

import net.esper.adapter.*;

/**
 * Subscriptions are associated with an output adapter and dictate which events are sent to a given adapter.
 */
public interface Subscription
{
    /**
     * Returns the subscription name.
     * @return subscription name
     */
    public String getSubscriptionName();

    /**
     * Sets the subscription name.
     * @param name is the subscription name
     */
    public void setSubscriptionName(String name);

    /**
     * Returns the type name of the event type we are looking for.
     * @return event type alias
     */
    public String getEventTypeAlias();

    /**
     * Returns the output adapter this subscription is associated with.
     * @return output adapter
     */
    public OutputAdapter getAdapter();

    /**
     * Sets the output adapter this subscription is associated with.
     * @param adapter to set
     */
    public void registerAdapter(OutputAdapter adapter);
}
