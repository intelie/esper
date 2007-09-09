package net.esper.event.property;

import net.esper.event.EventPropertyGetter;
import net.esper.event.EventBean;
import net.esper.event.PropertyAccessException;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.util.concurrent.CopyOnWriteArrayList;
import java.lang.reflect.Method;

/**
 * Base class for getters for a dynamic property (syntax field.inner?), caches methods to use for classes.
 */
public abstract class DynamicPropertyGetterBase implements EventPropertyGetter
{
    private final CopyOnWriteArrayList<DynamicPropertyDescriptor> cache;

    /**
     * To be implemented to return the method required, or null to indicate an appropriate method could not be found.
     * @param clazz to search for a matching method
     * @return method if found, or null if no matching method exists
     */
    protected abstract Method determineMethod(Class clazz);

    /**
     * Call the getter to obtains the return result object, or null if no such method exists.
     * @param descriptor provides method information for the class
     * @param underlying is the underlying object to ask for the property value
     * @return underlying
     */
    protected abstract Object call(DynamicPropertyDescriptor descriptor, Object underlying);

    /**
     * Ctor.
     */
    public DynamicPropertyGetterBase()
    {
        cache = new CopyOnWriteArrayList<DynamicPropertyDescriptor>();
    }

    public final Object get(EventBean obj) throws PropertyAccessException
    {
        DynamicPropertyDescriptor desc = getPopulateCache(obj);
        if (desc.getMethod() == null)
        {
            return null;
        }        
        return call(desc, obj.getUnderlying());
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        DynamicPropertyDescriptor desc = getPopulateCache(eventBean);
        if (desc.getMethod() == null)
        {
            return false;
        }
        return true;
    }

    private DynamicPropertyDescriptor getPopulateCache(EventBean obj)
    {
        // Check if the method is already there
        Class target = obj.getUnderlying().getClass();
        for (DynamicPropertyDescriptor desc : cache)
        {
            if (desc.getClazz() == target)
            {
                return desc;
            }
        }

        // need to add it
        synchronized(this)
        {
            for (DynamicPropertyDescriptor desc : cache)
            {
                if (desc.getClazz() == target)
                {
                    return desc;
                }
            }

            // Lookup method to use
            Method method = determineMethod(target);

            // Cache descriptor and create fast method
            DynamicPropertyDescriptor propertyDescriptor;
            if (method == null)
            {
                propertyDescriptor = new DynamicPropertyDescriptor(target, null, false);
            }
            else
            {
                FastClass fastClass = FastClass.create(target);
                FastMethod fastMethod = fastClass.getMethod(method);
                propertyDescriptor = new DynamicPropertyDescriptor(target, fastMethod, fastMethod.getParameterTypes().length > 0);
            }
            cache.add(propertyDescriptor);
            return propertyDescriptor;
        }
    }
}
