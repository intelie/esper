package net.esper.adapter;

import net.esper.adapter.subscription.*;
import net.esper.client.*;

import java.util.*;


public interface OutputAdapter extends Adapter
{
  public void setEPServiceProvider(EPServiceProvider epService);

  public EPServiceProvider getEPServiceProvider();

  public void setSubscriptionMap(Map<String, Subscription> subscriptionMap);

  public Map<String, Subscription> getSubscriptionMap();

  public void addSubscription(Subscription subscription,
    String subscriptionAlias);

  public void addSubscription(Subscription subscription);

  public Subscription getSubscription(String subscriptionAlias);

}
