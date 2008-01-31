package com.espertech.esper.adapter;

import com.espertech.esper.adapter.subscription.*;

import java.util.*;

/**
 * An output adapter transforms engine events and
 */
public interface OutputAdapter extends Adapter
{
    /**
     * Sets the subscriptions for the output adapter.
     * @param subscriptionMap is the active subscriptions.
     */
    public void setSubscriptionMap(Map<String, Subscription> subscriptionMap);

    /**
     * Returns the subscriptions.
     * @return map of name and subscription
     */
    public Map<String, Subscription> getSubscriptionMap();

    /**
     * Returns a given subscription by it's name, or null if not found
     * @param subscriptionAlias is the subscription
     * @return subcription or null
     */
    public Subscription getSubscription(String subscriptionAlias);
}
