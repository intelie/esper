using System;
using System.Collections.Generic;

using net.esper.adapter.subscription;

namespace net.esper.adapter
{
	/// <summary>
	/// An output adapter transforms engine events and
	/// </summary>
	public interface OutputAdapter : Adapter
	{
	    /// <summary>
	    /// Sets the subscriptions for the output adapter.
	    /// </summary>
	    /// <param name="subscriptionMap">is the active subscriptions.</param>
	    void setSubscriptionMap(IDictionary<String, Subscription> subscriptionMap);

	    /// <summary>
	    /// Returns the subscriptions.
	    /// <returns>map of name and subscription</returns>
	    /// </summary>
	    IDictionary<String, Subscription> getSubscriptionMap();

	    /// <summary>
	    /// Returns a given subscription by it's name, or null if not found
	    /// </summary>
	    /// <param name="subscriptionAlias">is the subscription</param>
	    /// <returns>subcription or null</returns>
	    Subscription getSubscription(String subscriptionAlias);
	}
}