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
using net.esper.eql.core;
using net.esper.events;
using net.esper.util;

using Properties = net.esper.compat.EDataDictionary;

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

	    public void AddEventTypeAlias(String eventTypeAlias, String javaEventClassName)
	    {
	        try
	        {
	            eventAdapterService.AddBeanType(eventTypeAlias, javaEventClassName);
	        }
	        catch (EventAdapterException t)
	        {
	            throw new ConfigurationException(t.Message, t);
	        }
	    }

	    public void AddEventTypeAlias(String eventTypeAlias, Type javaEventClass)
	    {
	        try
	        {
	            eventAdapterService.AddBeanType(eventTypeAlias, javaEventClass);
	        }
	        catch (EventAdapterException t)
	        {
	            throw new ConfigurationException(t.Message, t);
	        }
	    }

	    public void AddEventTypeAlias(String eventTypeAlias, Properties typeMap)
	    {
	        IDictionary<String, Class> types = CreatePropertyTypes(typeMap);
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
	            eventAdapterService.AddMapType(eventTypeAlias, typeMap);
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

	    private static IDictionary<String, Class> CreatePropertyTypes(Properties properties)
	    {
	        IDictionary<String, Type> propertyTypes = new EHashDictionary<String, Type>();
	        foreach(Object property in properties.KeySet())
	        {
	            String className = (String) properties.Get(property);

	            if ("string".Equals(className))
	            {
	                className = typeof(String).FullName;
	            }

	            // use the boxed type for primitives
	            String boxedClassName = TypeHelper.GetBoxedClassName(className);

	            Type type = null;
	            try
	            {
	                type = Class.ForName(boxedClassName);
	            }
	            catch (ClassNotFoundException ex)
	            {
	                throw new ConfigurationException("Unable to load class '" + boxedClassName + "', class not found", ex);
	            }

	            propertyTypes.Put((String) property, type);
	        }
	        return propertyTypes;
	    }
	}
} // End of namespace
