/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.representation.axiom;

import org.apache.axiom.om.OMNode;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.client.EventPropertyGetter;

/**
 * EventBean wrapper for XML documents. Currently only instances of OMNode can
 * be used
 *
 * @author Paul Fremantle
 *
 */
public class AxiomEventBean implements EventBean
{
	private EventType eventType;
	private OMNode event;

	/**
	 * Ctor.
	 *
	 * @param event
	 *            is the node with event property information
	 * @param type
	 *            is the event type for this event wrapper
	 */
	public AxiomEventBean(OMNode event, EventType type) {
		this.event = event;
		eventType = type;
	}

	public EventType getEventType() {
		return eventType;
	}

	public Object get(String property) throws PropertyAccessException
    {
		EventPropertyGetter getter = eventType.getGetter(property);
		if (getter == null) {
			throw new PropertyAccessException("Property named '" + property
					+ "' is not a valid property name for this type");
		}
		return getter.get(this);
	}

	public Object getUnderlying() {
		return event;
	}

    public Object getFragment(String propertyExpression) throws PropertyAccessException
    {
        return null;  
    }
}
