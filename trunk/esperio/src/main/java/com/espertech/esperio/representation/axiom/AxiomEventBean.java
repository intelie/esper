package com.espertech.esperio.representation.axiom;

import org.apache.axiom.om.OMNode;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;

/**
 * EventBean wrapper for XML documents. Currently only instances of OMNode can
 * be used
 *
 * @author Paul Fremantle
 *
 */
public class AxiomEventBean implements EventBean {

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

	public Object get(String property) throws PropertyAccessException {
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
}
