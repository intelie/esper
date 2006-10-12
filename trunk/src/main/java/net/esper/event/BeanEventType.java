package net.esper.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import net.sf.cglib.reflect.FastClass;
import net.esper.event.property.*;
import net.esper.client.ConfigurationEventTypeLegacy;

/**
 * Implementation of the EventType interface for handling JavaBean-type classes.
 */
public class BeanEventType implements EventType
{
    private final Class clazz;
    private final BeanEventAdapter beanEventAdapter;
    private final ConfigurationEventTypeLegacy optionalLegacyDef;

    private String[] propertyNames;
    private Map<String, Class> simplePropertyTypes;
    private Map<String, EventPropertyGetter> simplePropertyGetters;
    private Map<String, EventPropertyDescriptor> simplePropertyDescriptors;
    private Map<String, EventPropertyDescriptor> mappedPropertyDescriptors;
    private Map<String, EventPropertyDescriptor> indexedPropertyDescriptors;
    private EventType[] superTypes;
    private FastClass fastClass;
    private Set<EventType> deepSuperTypes;

    /**
     * Constructor takes a java bean class as an argument.
     * @param clazz is the class of a java bean or other POJO
     * @param beanEventAdapter is the chache and factory for event bean types and event wrappers
     * @param optionalLegacyDef optional configuration supplying legacy event type information
     */
    public BeanEventType(Class clazz, BeanEventAdapter beanEventAdapter,
                         ConfigurationEventTypeLegacy optionalLegacyDef)
    {
        this.clazz = clazz;
        this.beanEventAdapter = beanEventAdapter;
        this.optionalLegacyDef = optionalLegacyDef;

        initialize();
    }

    public final Class getPropertyType(String propertyName)
    {
        Class propertyType = simplePropertyTypes.get(propertyName);
        if (propertyType != null)
        {
            return propertyType;
        }

        Property prop = PropertyParser.parse(propertyName, beanEventAdapter);
        if (prop instanceof SimpleProperty)
        {
            // there is no such property since it wasn't in simplePropertyTypes
            return null;
        }
        return prop.getPropertyType(this);
    }

    public boolean isProperty(String propertyName)
    {
        if (getPropertyType(propertyName) == null)
        {
            return false;
        }
        return true;
    }

    public final Class getUnderlyingType()
    {
        return clazz;
    }

    public EventPropertyGetter getGetter(String propertyName)
    {
        EventPropertyGetter getter = simplePropertyGetters.get(propertyName);
        if (getter != null)
        {
            return getter;
        }

        Property prop = PropertyParser.parse(propertyName, beanEventAdapter);
        if (prop instanceof SimpleProperty)
        {
            // there is no such property since it wasn't in simplePropertyGetters
            return null;
        }
        return prop.getGetter(this);
    }

    /**
     * Looks up and returns a cached simple property's descriptor.
     * @param propertyName to look up
     * @return property descriptor
     */
    public final EventPropertyDescriptor getSimpleProperty(String propertyName)
    {
        return simplePropertyDescriptors.get(propertyName);
    }

    /**
     * Looks up and returns a cached mapped property's descriptor.
     * @param propertyName to look up
     * @return property descriptor
     */
    public final EventPropertyDescriptor getMappedProperty(String propertyName)
    {
        return mappedPropertyDescriptors.get(propertyName);
    }

    /**
     * Looks up and returns a cached indexed property's descriptor.
     * @param propertyName to look up
     * @return property descriptor
     */
    public final EventPropertyDescriptor getIndexedProperty(String propertyName)
    {
        return indexedPropertyDescriptors.get(propertyName);
    }

    public String[] getPropertyNames()
    {
        return propertyNames;
    }

    public EventType[] getSuperTypes()
    {
        return superTypes;
    }

    public Iterator<EventType> getDeepSuperTypes()
    {
        return deepSuperTypes.iterator();
    }

    /**
     * Returns the fast class reference, if code generation is used for this type, else null.
     * @return fast class, or null if no code generation
     */
    public FastClass getFastClass()
    {
        return fastClass;
    }

    public String toString()
    {
        return "BeanEventType" +
               " clazz=" + clazz.getName();
    }

    private void initialize()
    {
        PropertyListBuilder propertyListBuilder = PropertyListBuilderFactory.createBuilder(optionalLegacyDef);
        List<EventPropertyDescriptor> properties = propertyListBuilder.assessProperties(clazz);

        this.propertyNames = new String[properties.size()];
        this.simplePropertyTypes = new HashMap<String, Class>();
        this.simplePropertyGetters = new HashMap<String, EventPropertyGetter>();
        this.simplePropertyDescriptors = new HashMap<String, EventPropertyDescriptor>();
        this.mappedPropertyDescriptors = new HashMap<String, EventPropertyDescriptor>();
        this.indexedPropertyDescriptors = new HashMap<String, EventPropertyDescriptor>();

        if ((optionalLegacyDef == null) ||
            (optionalLegacyDef.getCodeGeneration() != ConfigurationEventTypeLegacy.CodeGeneration.DISABLED))
        {
            // get CGLib fast class
            fastClass = null;
            try
            {
                fastClass = FastClass.create(clazz);
            }
            catch (Throwable ex)
            {
                log.warn(".initialize Unable to obtain CGLib fast class and/or method implementation for class " +
                        clazz.getName() + ", error msg is " + ex.getMessage());
                fastClass = null;
            }
        }

        int count = 0;
        for (EventPropertyDescriptor desc : properties)
        {
            String propertyName = desc.getPropertyName();
            propertyNames[count++] = desc.getListedName();

            if (desc.getPropertyType().equals(EventPropertyType.SIMPLE))
            {
                EventPropertyGetter getter = null;
                if (desc.getReadMethod() != null)
                {
                    getter = PropertyHelper.getGetter(desc.getReadMethod(), fastClass);
                    simplePropertyTypes.put(propertyName, desc.getReadMethod().getReturnType());
                }
                else
                {
                    getter = new ReflectionPropFieldGetter(desc.getAccessorField());
                    simplePropertyTypes.put(propertyName, desc.getAccessorField().getType());
                }

                simplePropertyGetters.put(propertyName, getter);
                simplePropertyDescriptors.put(propertyName, desc);
            }
            else if (desc.getPropertyType().equals(EventPropertyType.MAPPED))
            {
                mappedPropertyDescriptors.put(propertyName, desc);
            }
            else if (desc.getPropertyType().equals(EventPropertyType.INDEXED))
            {
                indexedPropertyDescriptors.put(propertyName, desc);
            }
        }

        // Determine event type super types
        superTypes = getSuperTypes(clazz, beanEventAdapter);

        // Determine deep supertypes
        // Get Java super types (superclasses and interfaces), deep get of all in the tree
        Set<Class> supers = new HashSet<Class>();
        getSuper(clazz, supers);
        removeJavaLibInterfaces(supers);    // Remove "java." super types

        // Cache the supertypes of this event type for later use
        deepSuperTypes = new HashSet<EventType>();
        for (Class superClass : supers)
        {
            EventType superType = beanEventAdapter.createOrGetBeanType(superClass);
            deepSuperTypes.add(superType);
        }
    }

    private static EventType[] getSuperTypes(Class clazz, BeanEventAdapter beanEventAdapter)
    {
        List<Class> superclasses = new LinkedList<Class>();

        // add superclass
        Class superClass = clazz.getSuperclass();
        if (superClass != null)
        {
            superclasses.add(superClass);
        }

        // add interfaces
        Class interfaces[] = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; i++)
        {
            superclasses.add(interfaces[i]);
        }

        // Build event types, ignoring java language types
        List<EventType> superTypes = new LinkedList<EventType>();
        for (Class superclass : superclasses)
        {
            if (!superclass.getName().startsWith("java"))
            {
                EventType superType = beanEventAdapter.createOrGetBeanType(superclass);
                superTypes.add(superType);
            }
        }

        return superTypes.toArray(new EventType[0]);
    }

    /**
     * Add the given class's implemented interfaces and superclasses to the result set of classes.
     * @param clazz to introspect
     * @param result to add classes to
     */
    protected static void getSuper(Class clazz, Set<Class> result)
    {
        getSuperInterfaces(clazz, result);
        getSuperClasses(clazz, result);
    }

    private static void getSuperInterfaces(Class clazz, Set<Class> result)
    {
        Class interfaces[] = clazz.getInterfaces();

        for (int i = 0; i < interfaces.length; i++)
        {
            result.add(interfaces[i]);
            getSuperInterfaces(interfaces[i], result);
        }
    }

    private static void getSuperClasses(Class clazz, Set<Class> result)
    {
        Class superClass = clazz.getSuperclass();
        if (superClass == null)
        {
            return;
        }

        result.add(superClass);
        getSuper(superClass, result);
    }

    private static void removeJavaLibInterfaces(Set<Class> classes)
    {
        for (Class clazz : classes.toArray(new Class[0]))
        {
            if (clazz.getName().startsWith("java"))
            {
                classes.remove(clazz);
            }
        }
    }

    private static final Log log = LogFactory.getLog(BeanEventType.class);
}
