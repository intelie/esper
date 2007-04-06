package net.esper.pattern;

import net.esper.client.ConfigurationException;
import net.esper.client.ConfigurationPlugInPatternObject;
import net.esper.collection.Pair;
import net.esper.eql.spec.ObjectSpec;
import net.esper.eql.spec.PatternGuardSpec;
import net.esper.eql.spec.PatternObserverSpec;
import net.esper.pattern.guard.GuardEnum;
import net.esper.pattern.guard.GuardFactory;
import net.esper.pattern.observer.ObserverEnum;
import net.esper.pattern.observer.ObserverFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Resolves pattern object namespace and name to guard or observer factory class, using configuration. 
 */
public class PatternObjectResolutionServiceImpl implements PatternObjectResolutionService
{
    private static final Log log = LogFactory.getLog(PatternObjectResolutionServiceImpl.class);

    // Map of namespace, name, factory class and boolean (true=guard, false=observer)
    private final Map<String, Map<String, Pair<Class, TypeEnum>>> nameToFactoryMap;

    /**
     * Ctor.
     * @param configEntries is the pattern plug-in objects configured
     * @throws ConfigurationException if the configs are invalid
     */
    public PatternObjectResolutionServiceImpl(List<ConfigurationPlugInPatternObject> configEntries) throws ConfigurationException
    {
        nameToFactoryMap = new HashMap<String, Map<String, Pair<Class, TypeEnum>>>();

        if (configEntries == null)
        {
            return;
        }

        for (ConfigurationPlugInPatternObject entry : configEntries)
        {
            if (entry.getFactoryClassName() == null)
            {
                throw new ConfigurationException("Factory class name has not been supplied for object '" + entry.getName() + "'");
            }
            if (entry.getNamespace() == null)
            {
                throw new ConfigurationException("Namespace name has not been supplied for object '" + entry.getName() + "'");
            }
            if (entry.getName() == null)
            {
                throw new ConfigurationException("Name has not been supplied for object in namespace '" + entry.getNamespace() + "'");
            }
            if (entry.getPatternObjectType() == null)
            {
                throw new ConfigurationException("Pattern object type has not been supplied for object '" + entry.getName() + "'");
            }

            Class clazz;
            try
            {
                clazz = Class.forName(entry.getFactoryClassName());
            }
            catch (ClassNotFoundException ex)
            {
                throw new ConfigurationException("Pattern object factory class " + entry.getFactoryClassName() + " could not be loaded");
            }

            Map<String, Pair<Class, TypeEnum>> namespaceMap = nameToFactoryMap.get(entry.getNamespace());
            if (namespaceMap == null)
            {
                namespaceMap = new HashMap<String, Pair<Class, TypeEnum>>();
                nameToFactoryMap.put(entry.getNamespace(), namespaceMap);
            }
            TypeEnum typeEnum;
            if (entry.getPatternObjectType() == ConfigurationPlugInPatternObject.PatternObjectType.GUARD)
            {
                typeEnum =  TypeEnum.GUARD;
            }
            else if (entry.getPatternObjectType() == ConfigurationPlugInPatternObject.PatternObjectType.OBSERVER)
            {
                typeEnum =  TypeEnum.OBSERVER;
            }
            else
            {
                throw new IllegalArgumentException("Pattern object type '" + entry.getPatternObjectType() + "' not known");
            }
            namespaceMap.put(entry.getName(), new Pair<Class, TypeEnum>(clazz, typeEnum));
        }
    }

    public ObserverFactory create(PatternObserverSpec spec) throws PatternObjectException
    {
        Object result = createFactory(spec, TypeEnum.OBSERVER);
        ObserverFactory factory;
        try
        {
            factory = (ObserverFactory) result;

            if (log.isDebugEnabled())
            {
                log.debug(".create Successfully instantiated observer");
            }
        }
        catch (ClassCastException e)
        {
            String message = "Error casting observer factory instance to " + ObserverFactory.class.getName() + " interface for observer '" + spec.getObjectName() + "'";
            throw new PatternObjectException(message, e);
        }
        return factory;
    }

    public GuardFactory create(PatternGuardSpec spec) throws PatternObjectException
    {
        Object result = createFactory(spec, TypeEnum.GUARD);
        GuardFactory factory;
        try
        {
            factory = (GuardFactory) result;

            if (log.isDebugEnabled())
            {
                log.debug(".create Successfully instantiated guard");
            }
        }
        catch (ClassCastException e)
        {
            String message = "Error casting guard factory instance to " + GuardFactory.class.getName() + " interface for guard '" + spec.getObjectName() + "'";
            throw new PatternObjectException(message, e);
        }
        return factory;
    }

    private Object createFactory(ObjectSpec spec, TypeEnum type) throws PatternObjectException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".create Creating observer factory, spec=" + spec.toString());
        }

        Class factoryClass = null;

        // Pre-configured objects override build-in
        Map<String, Pair<Class, TypeEnum>> namespaceMap = nameToFactoryMap.get(spec.getObjectNamespace());
        if (namespaceMap != null)
        {
            Pair<Class, TypeEnum> pair = namespaceMap.get(spec.getObjectName());
            if (pair != null)
            {
                if (pair.getSecond() == type)
                {
                    factoryClass = pair.getFirst();
                }
                else
                {
                    // invalid type: expecting observer, got guard
                    if (type == TypeEnum.GUARD)
                    {
                        throw new PatternObjectException("Pattern observer function '" + spec.getObjectName() + "' cannot be used as a guard");
                    }
                    else
                    {
                        throw new PatternObjectException("Pattern guard function '" + spec.getObjectName() + "' cannot be used as an observer");
                    }
                }
            }
        }

        // if not found in the plugins, try o resolve as a builtin
        if (factoryClass == null)
        {
            if (type == TypeEnum.GUARD)
            {
                GuardEnum guardEnum = GuardEnum.forName(spec.getObjectNamespace(), spec.getObjectName());

                if (guardEnum == null)
                {
                    if (ObserverEnum.forName(spec.getObjectNamespace(), spec.getObjectName()) != null)
                    {
                        String message = "Invalid use for pattern observer named '" + spec.getObjectName() + "'";
                        throw new PatternObjectException(message);
                    }

                    String message = "Pattern guard name '" + spec.getObjectName() + "' is not a known pattern object name";
                    throw new PatternObjectException(message);
                }

                factoryClass = guardEnum.getClazz();
            }
            else if (type == TypeEnum.OBSERVER)
            {
                ObserverEnum observerEnum = ObserverEnum.forName(spec.getObjectNamespace(), spec.getObjectName());

                if (observerEnum == null)
                {
                    if (GuardEnum.forName(spec.getObjectNamespace(), spec.getObjectName()) != null)
                    {
                        String message = "Invalid use for pattern guard named '" + spec.getObjectName() + "' outside of where-clause";
                        throw new PatternObjectException(message);
                    }

                    String message = "Pattern observer name '" + spec.getObjectName() + "' is not a known pattern object name";
                    throw new PatternObjectException(message);
                }

                factoryClass = observerEnum.getClazz();
            }
            else
            {
                throw new IllegalStateException("Pattern object type '" + type + "' not known");
            }
        }

        Object result = null;
        try
        {
            result = factoryClass.newInstance();
        }
        catch (IllegalAccessException e)
        {
            String message = "Error invoking pattern object factory constructor for object '" + spec.getObjectName();
            message += "', no invocation access for Class.newInstance";
            throw new PatternObjectException(message, e);
        }
        catch (InstantiationException e)
        {
            String message = "Error invoking pattern object factory constructor for object '" + spec.getObjectName();
            message += "' using Class.newInstance";
            throw new PatternObjectException(message, e);
        }

        return result;
    }

    private enum TypeEnum
    {
        GUARD,
        OBSERVER
    }
}
