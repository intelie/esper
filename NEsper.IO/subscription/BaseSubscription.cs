using System;
using System.Collections.Generic;

using net.esper.adapter;
using net.esper.client;
using net.esper.compat;
using net.esper.core;
using net.esper.events;
using net.esper.filter;
using net.esper.util;

namespace net.esper.adapter.subscription
{
    /// <summary>
    /// Subscription is a concept for selecting events for processing out of all events 
    /// available from an engine instance.
    /// </summary>

    public abstract class BaseSubscription : Subscription, FilterHandleCallback
    {
        /// <summary>
        /// The output adapter to which the subscription applies.
        /// </summary>
        protected OutputAdapter adapter;

        /// <summary>
        /// The event type of the events we are subscribing for.
        /// </summary>

        protected String eventTypeAlias;

        /// <summary>
        /// The name of the subscription.
        /// </summary>

        protected String subscriptionName;

        public abstract void matchFound(EventBean _event);

        /// <summary>Ctor, assigns default name.</summary>

        public BaseSubscription()
        {
            subscriptionName = "default";
        }

        /// <summary>
        /// Gets or sets the subscription name.
        /// </summary>
        /// <value></value>
        /// <returns>subscription name</returns>
        public String SubscriptionName
        {
            get { return subscriptionName; }
            set { this.subscriptionName = value; }
        }

        /// <summary>
        /// Gets the type name of the event type we are looking for.
        /// </summary>
        /// <value></value>
        /// <returns>event type alias</returns>
        public String EventTypeAlias
        {
            get { return eventTypeAlias; }
            set { this.eventTypeAlias = value ; }
        }

        /// <summary>
        /// Gets or sets the output adapter this subscription is associated with.
        /// </summary>
        /// <value></value>
        /// <returns>output adapter</returns>
        public OutputAdapter Adapter
        {
            get { return adapter; }
            set
            {
                this.adapter = value;
                EPServiceProvider epService = ((AdapterSPI)adapter).EPServiceProvider;
                if (!(epService is EPServiceProviderSPI))
                {
                    throw new ArgumentException("Invalid type of EPServiceProvider");
                }
                EPServiceProviderSPI spi = (EPServiceProviderSPI)epService;
                EventType eventType = spi.EventAdapterService.GetEventTypeByAlias(eventTypeAlias);
                FilterValueSet fvs = new FilterSpecCompiled(eventType, new List<FilterSpecParam>()).GetValueSet(null);

                String name = "subscription:" + subscriptionName;
                EPStatementHandle statementHandle = new EPStatementHandle(name, new ManagedLockImpl(name), name);
                EPStatementHandleCallback registerHandle = new EPStatementHandleCallback(statementHandle, this);
                spi.FilterService.Add(fvs, registerHandle);
            }
        }

        #region FilterHandleCallback Members

        public abstract void MatchFound(EventBean _event);

        #endregion
    }
}
