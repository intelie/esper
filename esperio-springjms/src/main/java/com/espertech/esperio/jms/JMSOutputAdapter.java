/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.jms;

import com.espertech.esper.client.*;
import com.espertech.esper.core.*;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.adapter.*;
import org.apache.commons.logging.*;

import javax.jms.*;
import java.util.*;

/**
 * Implements a JMS output adapter.
 */
public abstract class JMSOutputAdapter implements OutputAdapter, AdapterSPI
{
    private EPServiceProviderSPI spi;
    private long startTime;
    private final AdapterStateManager stateManager = new AdapterStateManager();
    private Map<String, Subscription> subscriptionMap;

    /**
     * Abstract send methods for marshalling and sending an event of to JMS care.
     * @param eventBean is the event
     * @param jmsAdapterMarshaller is the marshaller
     * @throws EPException when the send failed
     */
    public abstract void send(final EventBean eventBean, JMSMessageMarshaller jmsAdapterMarshaller) throws EPException;

    /**
     * Marshaller to use.
     */
    protected JMSMessageMarshaller jmsMessageMarshaller;

    /**
     * JMS Destination.
     */
    protected Destination destination;

    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * Returns the JMS message marshaller.
     * @return marshaller
     */
    public JMSMessageMarshaller getJmsMessageMarshaller()
    {
        return jmsMessageMarshaller;
    }

    /**
     * Sets the JMS message marshaller.
     * @param jmsMessageMarshaller is the marshaller
     */
    public void setJmsMessageMarshaller(JMSMessageMarshaller jmsMessageMarshaller)
    {
        this.jmsMessageMarshaller = jmsMessageMarshaller;
    }

    /**
     * Sets the JMS destination.
     * @param destination is the queue or topic
     */
    public void setDestination(Destination destination)
    {
        this.destination = destination;
    }

    public Map<String, Subscription> getSubscriptionMap()
    {
        return subscriptionMap;
    }

    public void setSubscriptionMap(Map<String, Subscription> subscriptionMap)
    {
        this.subscriptionMap = subscriptionMap;
        // In case an name has not been set for each subscription
        Iterator<Map.Entry<String, Subscription>> it =
                subscriptionMap.entrySet().iterator();
        for (String name : subscriptionMap.keySet())
        {
            Subscription subscription = subscriptionMap.get(name);
            subscription.setSubscriptionName(name);
        }
    }

    public Subscription getSubscription(String subscriptionName)
    {
        if (subscriptionName == null)
        {
            return null;
        }
        return subscriptionMap.get(subscriptionName);
    }

    public EPServiceProvider getEPServiceProvider()
    {
        return spi;
    }

    public void setEPServiceProvider(EPServiceProvider epService)
    {
        if (epService == null)
        {
            throw new IllegalArgumentException("Null service provider");
        }
        if (!(epService instanceof EPServiceProviderSPI))
        {
            throw new IllegalArgumentException("Cannot downcast service provider to SPI");
        }
        spi = (EPServiceProviderSPI) epService;
    }

    public void start() throws EPException
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".start");
        }
        if (spi.getEPRuntime() == null)
        {
            throw new EPException("Attempting to start an Adapter that hasn't had the epService provided");
        }

        startTime = System.currentTimeMillis();
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".start startTime==" + startTime);
        }

        stateManager.start();
        Iterator<Map.Entry<String, Subscription>> it = subscriptionMap.entrySet().iterator();
        while (it.hasNext())
        {
            it.next().getValue().registerAdapter(this);
        }
    }

    public void pause() throws EPException
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".pause");
        }
        stateManager.pause();
    }

    public void resume() throws EPException
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".resume");
        }
        stateManager.resume();
    }

    public void stop() throws EPException
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".stop");
        }
        stateManager.stop();
    }

    public void destroy() throws EPException
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".destroy");
        }
        stateManager.destroy();
    }

    public AdapterState getState()
    {
        return stateManager.getState();
    }
}
