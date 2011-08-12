/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.jms;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.adapter.BaseSubscription;
import com.espertech.esper.filter.FilterHandleCallback;

import java.util.Collection;

/**
 * Represents the JMS-aspects of a subscription.
 */
public class JMSSubscription extends BaseSubscription
{
    private JMSMessageMarshaller jmsMessageMarshaller;

    /**
     * Empty Ctor required for use with Spring.
     */
    public JMSSubscription()
    {
    }

    /**
     * Returns the marshaller to use for this subscription.
     * @return marshaller
     */
    public JMSMessageMarshaller getJmsMessageMarshaller()
    {
        return jmsMessageMarshaller;
    }

    /**
     * Sets the marshaller to use for this subscription.
     * @param jmsMessageMarshaller to use
     */
    public void setJmsMessageMarshaller(JMSMessageMarshaller jmsMessageMarshaller)
    {
        this.jmsMessageMarshaller = jmsMessageMarshaller;
    }

    public void matchFound(EventBean event, Collection<FilterHandleCallback> allStmtMatches) {

        if (!(adapter instanceof JMSOutputAdapter))
        {
            return;
        }
        ((JMSOutputAdapter) (adapter)).send(event, jmsMessageMarshaller);
    }

    public boolean isSubSelect()
    {
        return false; 
    }

    public String getStatementId()
    {
        return null;
    }
}
