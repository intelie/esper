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
	    private readonly IDictionary<String, IDictionary<String, Pair<Class, TypeEnum>>> nameToFactoryMap;

	    /// <summary>Ctor.</summary>
	    /// <param name="configEntries">is the pattern plug-in objects configured</param>
	    /// <throws>ConfigurationException if the configs are invalid</throws>
	    public PatternObjectResolutionServiceImpl(List<ConfigurationPlugInPatternObject> configEntries)
	    {
	        nameToFactoryMap = new EHashDictionary<String, IDictionary<String, Pair<Class, TypeEnum>>>();

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

	            Class clazz;
	            try
	            {
	                clazz = Class.ForName(entry.FactoryClassName);
	            }
	            catch (ClassNotFoundException ex)
	            {
	                throw new ConfigurationException("Pattern object factory class " + entry.FactoryClassName + " could not be loaded");
	            }

	            IDictionary<String, Pair<Class, TypeEnum>> namespaceMap = nameToFactoryMap.Get(entry.Namespace);
	            if (namespaceMap == null)
	            {
	                namespaceMap = new EHashDictionary<String, Pair<Class, TypeEnum>>();
	                nameToFactoryMap.Put(entry.Namespace, namespaceMap);
	            }
	            TypeEnum typeEnum;
	            if (entry.PatternObjectType == ConfigurationPlugInPatternObject.PatternObjectType.GUARD)
	            {
	                typeEnum =  TypeEnum.GUARD;
	            }
	            else if (entry.PatternObjectType == ConfigurationPlugInPatternObject.PatternObjectType.OBSERVER)
	            {
	                typeEnum =  TypeEnum.OBSERVER;
	            }
	            else
	            {
	                throw new IllegalArgumentException("Pattern object type '" + entry.PatternObjectType + "' not known");
	            }
	            namespaceMap.Put(entry.Name, new Pair<Class, TypeEnum>(clazz, typeEnum));
	        }
	    }

	    public ObserverFactory Create(PatternObserverSpec spec)
	    {
	        Object result = CreateFactory(spec, TypeEnum.OBSERVER);
	        ObserverFactory factory;
	        try
	        {
	            factory = (ObserverFactory) result;

	            if (log.IsDebugEnabled())
	            {
	                log.Debug(".create Successfully instantiated observer");
	            }
	        }
	        catch (ClassCastException e)
	        {
	            String message = "Error casting observer factory instance to " + typeof(ObserverFactory).FullName + " interface for observer '" + spec.ObjectName + "'";
	            throw new PatternObjectException(message, e);
	        }
	        return factory;
	    }

	    public GuardFactory Create(PatternGuardSpec spec)
	    {
	        Object result = CreateFactory(spec, TypeEnum.GUARD);
	        GuardFactory factory;
	        try
	        {
	            factory = (GuardFactory) result;

	            if (log.IsDebugEnabled())
	            {
	                log.Debug(".create Successfully instantiated guard");
	            }
	        }
	        catch (ClassCastException e)
	        {
	            String message = "Error casting guard factory instance to " + typeof(GuardFactory).FullName + " interface for guard '" + spec.ObjectName + "'";
	            throw new PatternObjectException(message, e);
	        }
	        return factory;
	    }

	    private Object CreateFactory(ObjectSpec spec, TypeEnum type)
	    {
	        if (log.IsDebugEnabled())
	        {
	            log.Debug(".create Creating observer factory, spec=" + spec.ToString());
	        }

	        Class factoryClass = null;

	        // Pre-configured objects override build-in
	        IDictionary<String, Pair<Class, TypeEnum>> namespaceMap = nameToFactoryMap.Get(spec.ObjectNamespace);
	        if (namespaceMap != null)
	        {
	            Pair<Class, TypeEnum> pair = namespaceMap.Get(spec.ObjectName);
	            if (pair != null)
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
	                        String message = "Invalid use for pattern observer named '" + spec.ObjectName + "'";
	                        throw new PatternObjectException(message);
	                    }

	                    String message = "Pattern guard name '" + spec.ObjectName + "' is not a known pattern object name";
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
	                        String message = "Invalid use for pattern guard named '" + spec.ObjectName + "' outside of where-clause";
	                        throw new PatternObjectException(message);
	                    }

	                    String message = "Pattern observer name '" + spec.ObjectName + "' is not a known pattern object name";
	                    throw new PatternObjectException(message);
	                }

	                factoryClass = observerEnum.Clazz;
	            }
	            else
	            {
	                throw new IllegalStateException("Pattern object type '" + type + "' not known");
	            }
	        }

	        Object result = null;
	        try
	        {
	            result = factoryClass.NewInstance();
	        }
	        catch (IllegalAccessException e)
	        {
	            String message = "Error invoking pattern object factory constructor for object '" + spec.ObjectName;
	            message += "', no invocation access for Class.newInstance";
	            throw new PatternObjectException(message, e);
	        }
	        catch (InstantiationException e)
	        {
	            String message = "Error invoking pattern object factory constructor for object '" + spec.ObjectName;
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
}
