package com.espertech.esper.event.xml;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.PropertyAccessException;

import org.w3c.dom.Node;

/**
 * EventBean wrapper for XML documents. 
 * Currently only instances of org.w3c.dom.Node can be used
 * 
 * @author pablo
 *
 */
public class XMLEventBean implements EventBean {

	private EventType eventType;
	private Node event;

    /**
     * Ctor.
     * @param event is the node with event property information
     * @param type is the event type for this event wrapper
     */
    public XMLEventBean(Node event, EventType type) {
		this.event = event;
		eventType = type;
	}

	public EventType getEventType() {
		return eventType;
	}

	public Object get(String property) throws PropertyAccessException {
		EventPropertyGetter getter = eventType.getGetter(property);
		if (getter == null)
			 throw new PropertyAccessException("Property named '" + property + "' is not a valid property name for this type");
		return getter.get(this);
	}

	public Object getUnderlying() {
		return event;
	}

}
