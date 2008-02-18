package com.espertech.esper.event;

/**
 * Interface for property getters also returning type information for the property.
 * @author pablo
 */
public interface TypedEventPropertyGetter extends EventPropertyGetter {

	/**
	 * Returns type of event property.
	 * @return class of the objects returned by this getter
	 */
	public Class getResultClass();
}
