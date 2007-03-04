package net.esper.adapter;

import net.esper.adapter.subscription.*;

import java.util.*;


public interface OutputAdapter extends Adapter
{
  void setSubscriptionMap(Map<String, Subscription> subscriptionMap);

  Map<String, Subscription> getSubscriptionMap();

  void addSubscription(Subscription subscription, String subscriptionAlias);

  void addSubscription(Subscription subscription);

  Subscription getSubscription(String subscriptionAlias);
}
