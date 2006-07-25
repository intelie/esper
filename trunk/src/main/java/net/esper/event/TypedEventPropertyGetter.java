package net.esper.event;

import net.esper.event.EventPropertyGetter;

/**
 * 
 * @author pablo
 *
 */
public interface TypedEventPropertyGetter extends EventPropertyGetter {

	/**
	 *  
	 * @return class of the objects returned by this getter
	 */
	public Class getResultClass();
}
