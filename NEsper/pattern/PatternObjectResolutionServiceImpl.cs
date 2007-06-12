///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.collection;
using net.esper.compat;
using net.esper.eql.spec;
using net.esper.pattern.guard;
using net.esper.pattern.observer;
using org.apache.commons.logging;

namespace net.esper.pattern
{
	/// <summary>
	/// Resolves pattern object namespace and name to guard or observer factory class, using configuration.
	/// </summary>
	public class PatternObjectResolutionServiceImpl : PatternObjectResolutionService
	{
	    private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    // Map of namespace, name, factory class and bool (true=guard, false=observer)
	    private readonly EDictionary<String, IDictionary<String, Pair<Type, TypeEnum>>> nameToFactoryMap;

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="configEntries">is the pattern plug-in objects configured</param>
        /// <throws>ConfigurationException if the configs are invalid</throws>
	    public PatternObjectResolutionServiceImpl(IList<ConfigurationPlugInPatternObject> configEntries)
	    {
	        nameToFactoryMap = new EHashDictionary<String, IDictionary<String, Pair<Type, TypeEnum>>>();

	        if (configEntries == null)
	        {
	            return;
	        }

	        foreach (ConfigurationPlugInPatternObject entry in configEntries)
	        {
	            if (entry.FactoryClassName == null)
	            {
	                throw new ConfigurationException("Factory class name has not been supplied for object '" + entry.Name + "'");
	            }
	            if (entry.Namespace == null)
	            {
	                throw new ConfigurationException("Namespace name has not been supplied for object '" + entry.Name + "'");
	            }
	            if (entry.Name == null)
	            {
	                throw new ConfigurationException("Name has not been supplied for object in namespace '" + entry.Namespace + "'");
	            }
	            if (entry.PatternObjectType == null)
	            {
	                throw new ConfigurationException("Pattern object type has not been supplied for object '" + entry.Name + "'");
	            }

	            Type type;
	            try
	            {
	                type = Type.GetType(entry.FactoryClassName);
	            }
	            catch (TypeLoadException)
	            {
	                throw new ConfigurationException("Pattern object factory class " + entry.FactoryClassName + " could not be loaded");
	            }

	            IDictionary<String, Pair<Type, TypeEnum>> namespaceMap = nameToFactoryMap.Fetch(entry.Namespace);
	            if (namespaceMap == null)
	            {
	                namespaceMap = new EHashDictionary<String, Pair<Type, TypeEnum>>();
	                nameToFactoryMap[entry.Namespace] = namespaceMap;
	            }
	            TypeEnum typeEnum;
	            if (entry.PatternObjectType == ConfigurationPlugInPatternObject.PatternObjectTypeEnum.GUARD)
	            {
	                typeEnum =  TypeEnum.GUARD;
	            }
	            else if (entry.PatternObjectType == ConfigurationPlugInPatternObject.PatternObjectTypeEnum.OBSERVER)
	            {
	                typeEnum =  TypeEnum.OBSERVER;
	            }
	            else
	            {
	                throw new ArgumentException("Pattern object type '" + entry.PatternObjectType + "' not known");
	            }
	            namespaceMap[entry.Name] = new Pair<Type, TypeEnum>(type, typeEnum);
	        }
	    }

        /// <summary>
        /// Creates an observer factory considering configured plugged-in resources.
        /// </summary>
        /// <param name="spec">is the namespace, name and parameters for the observer</param>
        /// <returns>observer factory</returns>
        /// <throws>PatternObjectException if the observer cannot be resolved</throws>
	    public ObserverFactory Create(PatternObserverSpec spec)
	    {
	        Object result = CreateFactory(spec, TypeEnum.OBSERVER);
	        ObserverFactory factory = result as ObserverFactory;
	        if ( factory == null )
	        {
	            String message = "Error casting observer factory instance to " + typeof(ObserverFactory).FullName + " interface for observer '" + spec.ObjectName + "'";
	            throw new PatternObjectException(message);
	        }

            if (log.IsDebugEnabled)
            {
                log.Debug(".create Successfully instantiated observer");
            }

            return factory;
	    }

        /// <summary>
        /// Creates a guard factory considering configured plugged-in resources.
        /// </summary>
        /// <param name="spec">is the namespace, name and parameters for the guard</param>
        /// <returns>guard factory</returns>
        /// <throws>PatternObjectException if the guard cannot be resolved</throws>
	    public GuardFactory Create(PatternGuardSpec spec)
	    {
	        Object result = CreateFactory(spec, TypeEnum.GUARD);
	        GuardFactory factory = result as GuardFactory;
            if ( factory == null )
            {
	            String message = "Error casting guard factory instance to " + typeof(GuardFactory).FullName + " interface for guard '" + spec.ObjectName + "'";
	            throw new PatternObjectException(message);
            }

            if (log.IsDebugEnabled)
            {
                log.Debug(".create Successfully instantiated guard");
            }

            return factory;
	    }

        /// <summary>
        /// Creates the factory.
        /// </summary>
        /// <param name="spec">The spec.</param>
        /// <param name="type">The type.</param>
        /// <returns></returns>
	    private Object CreateFactory(ObjectSpec spec, TypeEnum type)
	    {
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(string.Format(".create Creating observer factory, spec={0}", spec));
	        }

	        Type factoryClass = null;

	        // Pre-configured objects override build-in
	        IDictionary<String, Pair<Type, TypeEnum>> namespaceMap = nameToFactoryMap.Fetch(spec.ObjectNamespace);
	        if (namespaceMap != null)
	        {
	            Pair<Type, TypeEnum> pair ;
                if (namespaceMap.TryGetValue(spec.ObjectName, out pair))
	            {
	                if (pair.Second == type)
	                {
	                    factoryClass = pair.First;
	                }
	                else
	                {
	                    // invalid type: expecting observer, got guard
	                    if (type == TypeEnum.GUARD)
	                    {
	                        throw new PatternObjectException("Pattern observer function '" + spec.ObjectName + "' cannot be used as a guard");
	                    }
	                    else
	                    {
	                        throw new PatternObjectException("Pattern guard function '" + spec.ObjectName + "' cannot be used as an observer");
	                    }
	                }
	            }
	        }

	        String message;
	        
	        // if not found in the plugins, try o resolve as a builtin
	        if (factoryClass == null)
	        {
	            if (type == TypeEnum.GUARD)
	            {
	                GuardEnum guardEnum = GuardEnum.ForName(spec.ObjectNamespace, spec.ObjectName);

	                if (guardEnum == null)
	                {
	                    if (ObserverEnum.ForName(spec.ObjectNamespace, spec.ObjectName) != null)
	                    {
	                        message = "Invalid use for pattern observer named '" + spec.ObjectName + "'";
	                        throw new PatternObjectException(message);
	                    }

	                    message = "Pattern guard name '" + spec.ObjectName + "' is not a known pattern object name";
	                    throw new PatternObjectException(message);
	                }

	                factoryClass = guardEnum.Clazz;
	            }
	            else if (type == TypeEnum.OBSERVER)
	            {
	                ObserverEnum observerEnum = ObserverEnum.ForName(spec.ObjectNamespace, spec.ObjectName);

	                if (observerEnum == null)
	                {
	                    if (GuardEnum.ForName(spec.ObjectNamespace, spec.ObjectName) != null)
	                    {
	                        message = "Invalid use for pattern guard named '" + spec.ObjectName + "' outside of where-clause";
	                        throw new PatternObjectException(message);
	                    }

	                    message = "Pattern observer name '" + spec.ObjectName + "' is not a known pattern object name";
	                    throw new PatternObjectException(message);
	                }

	                factoryClass = observerEnum.Type;
	            }
	            else
	            {
	                throw new IllegalStateException("Pattern object type '" + type + "' not known");
	            }
	        }

	        Object result;
	        try
	        {
	        	result = Activator.CreateInstance(factoryClass);
	        }
	        catch (MethodAccessException e)
	        {
	            message = "Error invoking pattern object factory constructor for object '" + spec.ObjectName;
	            message += "', no invocation access for Activator.CreateInstance";
	            throw new PatternObjectException(message, e);
	        }
	        catch (Exception e)
	        {
	            message = "Error invoking pattern object factory constructor for object '" + spec.ObjectName;
                message += "' using Activator.CreateInstance";
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
}
