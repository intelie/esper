package net.esper.pattern;

import net.esper.collection.Pair;
import net.esper.eql.spec.*;
import net.esper.pattern.guard.GuardFactory;
import net.esper.pattern.observer.ObserverFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * Resolves pattern object namespace and name to guard or observer factory class, using configuration. 
 */
public class PatternObjectResolutionServiceImpl implements PatternObjectResolutionService
{
    private static final Log log = LogFactory.getLog(PatternObjectResolutionServiceImpl.class);

    private final PluggableObjectDesc patternObjects;

    /**
     * Ctor.
     * @param patternObjects is the pattern plug-in objects configured
     */
    public PatternObjectResolutionServiceImpl(PluggableObjectDesc patternObjects)
    {
        this.patternObjects = patternObjects;
    }

    public ObserverFactory create(PatternObserverSpec spec) throws PatternObjectException
    {
        Object result = createFactory(spec, PluggableObjectType.PATTERN_OBSERVER);
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
        Object result = createFactory(spec, PluggableObjectType.PATTERN_GUARD);
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

    private Object createFactory(ObjectSpec spec, PluggableObjectType type) throws PatternObjectException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".create Creating factory, spec=" + spec.toString());
        }

        // Find the factory class for this pattern object
        Class factoryClass = null;

        Map<String, Pair<Class, PluggableObjectType>> namespaceMap = patternObjects.getPluggables().get(spec.getObjectNamespace());
        if (namespaceMap != null)
        {
            Pair<Class, PluggableObjectType> pair = namespaceMap.get(spec.getObjectName());
            if (pair != null)
            {
                if (pair.getSecond() == type)
                {
                    factoryClass = pair.getFirst();
                }
                else
                {
                    // invalid type: expecting observer, got guard
                    if (type == PluggableObjectType.PATTERN_GUARD)
                    {
                        throw new PatternObjectException("Pattern observer function '" + spec.getObjectName() + "' cannot be used as a pattern guard");
                    }
                    else
                    {
                        throw new PatternObjectException("Pattern guard function '" + spec.getObjectName() + "' cannot be used as a pattern observer");
                    }
                }
            }
        }

        if (factoryClass == null)
        {
            if (type == PluggableObjectType.PATTERN_GUARD)
            {
                String message = "Pattern guard name '" + spec.getObjectName() + "' is not a known pattern object name";
                throw new PatternObjectException(message);
            }
            else if (type == PluggableObjectType.PATTERN_OBSERVER)
            {
                String message = "Pattern observer name '" + spec.getObjectName() + "' is not a known pattern object name";
                throw new PatternObjectException(message);
            }
            else
            {
                throw new PatternObjectException("Pattern object type '" + type + "' not known");
            }
        }

        Object result;
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
}
