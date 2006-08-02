package net.esper.event;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

/**
 * EventType than can be supplied with a preconfigured list of properties getters.
 * 
 * @author pablo
 */
public abstract class BaseConfigurableEventType implements EventType {
	
    private Class underlyngType;
	private Map<String,TypedEventPropertyGetter> explicitProperties;

    protected BaseConfigurableEventType(Class underlyngType)
    {
        this.underlyngType = underlyngType;
    }

    public void setExplicitProperties(Map<String, TypedEventPropertyGetter> explicitProperties)
    {
        this.explicitProperties = explicitProperties;
    }

    public Class getPropertyType(String property) {
		TypedEventPropertyGetter getter = explicitProperties.get(property);
		if (getter != null)
			return getter.getResultClass();
		return doResolvePropertyType(property);
	}

	
	public Class getUnderlyingType() {
		return underlyngType;
	}

	public EventPropertyGetter getGetter(String property) {
		EventPropertyGetter getter = explicitProperties.get(property);
		if (getter != null)
			return getter;
		return doResolvePropertyGetter(property);
	}

	
	public String[] getPropertyNames() {
		Collection<String> propNames = new LinkedList<String>(explicitProperties.keySet());
		Collections.addAll(propNames,doListPropertyNames());
		return propNames.toArray(new String[propNames.size()]);
	}
	
	public boolean isProperty(String property) {
		return (getGetter(property) != null);
	}
	
	/**
	 * subclasses must implement this 
	 * @return list of properties
	 */
	protected abstract String[] doListPropertyNames();
	
	/**
	 * subclasses must implement this 
	 * @return getter
	 */
	protected abstract EventPropertyGetter doResolvePropertyGetter(String property);
	
	/**
	 * subclasses must implement this 
	 * @return property type
	 */
	protected abstract Class doResolvePropertyType(String property);
}
