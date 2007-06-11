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
using net.esper.compat;
using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

namespace net.esper.core
{
	/// <summary>Provides runtime engine configuration operations.</summary>
	public class ConfigurationOperationsImpl : ConfigurationOperations
	{
	    private EventAdapterService eventAdapterService;
	    private EngineImportService engineImportService;

	    /// <summary>Ctor.</summary>
	    /// <param name="eventAdapterService">is the event wrapper and type service</param>
	    /// <param name="engineImportService">
	    /// for imported aggregation functions and static functions
	    /// </param>
	    public ConfigurationOperationsImpl(EventAdapterService eventAdapterService,
	                                       EngineImportService engineImportService)
	    {
	        this.eventAdapterService = eventAdapterService;
	        this.engineImportService = engineImportService;
	    }

	    public void AddPlugInAggregationFunction(String functionName, String aggregationClassName)
	    {
	        try
	        {
	            engineImportService.AddAggregation(functionName, aggregationClassName);
	        }
	        catch (EngineImportException e)
	        {
	            throw new ConfigurationException(e.Message, e);
	        }
	    }

	    public void AddImport(String importName)
	    {
	        try
	        {
	            engineImportService.AddImport(importName);
	        }
	        catch (EngineImportException e)
	        {
	            throw new ConfigurationException(e.Message, e);
	        }
	    }

	    public void AddEventTypeAlias(String eventTypeAlias, String eventTypeName)
	    {
	        try
	        {
	            eventAdapterService.AddBeanType(eventTypeAlias, eventTypeName);
	        }
	        catch (EventAdapterException t)
	        {
	            throw new ConfigurationException(t.Message, t);
	        }
	    }

	    public void AddEventTypeAlias(String eventTypeAlias, Type eventType)
	    {
	        try
	        {
	            eventAdapterService.AddBeanType(eventTypeAlias, eventType);
	        }
	        catch (EventAdapterException t)
	        {
	            throw new ConfigurationException(t.Message, t);
	        }
	    }

	    public void AddEventTypeAlias(String eventTypeAlias, Properties typeMap)
	    {
	        EDictionary<String, Type> types = CreatePropertyTypes(typeMap);
	        try
	        {
	            eventAdapterService.AddMapType(eventTypeAlias, types);
	        }
	        catch (EventAdapterException t)
	        {
	            throw new ConfigurationException(t.Message, t);
	        }
	    }

	    public void AddEventTypeAlias(String eventTypeAlias, IDictionary<String, Type> typeMap)
	    {
	        try
	        {
                eventAdapterService.AddMapType(eventTypeAlias, new EBaseDictionary<String, Type>(typeMap));
	        }
	        catch (EventAdapterException t)
	        {
	            throw new ConfigurationException(t.Message, t);
	        }
	    }

	    public void AddEventTypeAlias(String eventTypeAlias, ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc)
	    {
	        try
	        {
	            eventAdapterService.AddXMLDOMType(eventTypeAlias, xmlDOMEventTypeDesc);
	        }
	        catch (EventAdapterException t)
	        {
	            throw new ConfigurationException(t.Message, t);
	        }
	    }

	    private static EDictionary<String, Type> CreatePropertyTypes(Properties properties)
	    {
	        EDictionary<String, Type> propertyTypes = new EHashDictionary<String, Type>();
	        foreach(string property in properties.Keys)
	        {
	            string typename = properties.Fetch(property);

	            if (typename == "string")
	            {
	                typename = typeof(string).FullName;
	            }

	            // use the boxed type for primitives
	            string boxedTypeName = TypeHelper.GetBoxedTypeName(typename);

	            Type type = null;
	            try
	            {
	                type = Type.GetType(boxedTypeName);
	            }
	            catch (TypeLoadException ex)
	            {
	                throw new ConfigurationException("Unable to load class '" + boxedTypeName + "', class not found", ex);
	            }

	            propertyTypes[property] = type;
	        }
	        return propertyTypes;
	    }
	}
} // End of namespace
