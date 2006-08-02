package net.esper.event;

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
