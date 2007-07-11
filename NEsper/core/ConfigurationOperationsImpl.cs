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

        /// <summary>
        /// Ctor.
        /// </summary>
        /// <param name="eventAdapterService">is the event wrapper and type service</param>
        /// <param name="engineImportService">for imported aggregation functions and static functions</param>
	    public ConfigurationOperationsImpl(EventAdapterService eventAdapterService,
	                                       EngineImportService engineImportService)
	    {
	        this.eventAdapterService = eventAdapterService;
	        this.engineImportService = engineImportService;
	    }

        /// <summary>
        /// Adds a plug-in aggregation function given a function name and an aggregation class name.
        /// <p>
        /// The aggregation class must : the base class {@link net.esper.eql.agg.AggregationSupport}.
        /// </p>
        /// 	<p>
        /// The same function name cannot be added twice.
        /// </p>
        /// </summary>
        /// <param name="functionName">is the new aggregation function name</param>
        /// <param name="aggregationClassName">is the fully-qualified class name of the class implementing the aggregation function</param>
        /// <throws>
        /// ConfigurationException is thrown to indicate a problem adding aggregation function
        /// </throws>
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

        /// <summary>
        /// Adds a package or class to the list of automatically-imported classes and packages.
        /// <p>
        /// To import a single class offering a static method, simply supply the fully-qualified name of the class
        /// and use the syntax <code>classname.Methodname(...)</code>
        /// 	</p>
        /// 	<p>
        /// To import a whole package and use the <code>classname.Methodname(...)</code> syntax, specifiy a package
        /// with wildcard, such as <code>com.mycompany.staticlib.*</code>.
        /// </p>
        /// </summary>
        /// <param name="importName">is a fully-qualified class name or a package name with wildcard</param>
        /// <throws>
        /// ConfigurationException if incorrect package or class names are encountered
        /// </throws>
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

        /// <summary>
        /// Adds the event type alias.
        /// </summary>
        /// <param name="eventTypeAlias">The event type alias.</param>
        /// <param name="eventTypeName">Name of the event type.</param>
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

        /// <summary>
        /// Add an alias for an event type represented by plain-old object events.
        /// <p>
        /// Allows a second alias to be added for the same type.
        /// Does not allow the same alias to be used for different types.
        /// </p>
        /// </summary>
        /// <param name="eventTypeAlias">is the alias for the event type</param>
        /// <param name="eventType">is the event type for which to create the alias</param>
        /// <throws>
        /// ConfigurationException if the alias is already in used for a different type
        /// </throws>
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

        /// <summary>
        /// Add an alias for an event type that represents DataDictionary events.
        /// <p>
        /// Allows a second alias to be added for the same type.
        /// Does not allow the same alias to be used for different types.
        /// </p>
        /// </summary>
        /// <param name="eventTypeAlias">is the alias for the event type</param>
        /// <param name="typeMap">maps the name of each property in the Map event to the type
        /// (fully qualified classname) of its value in Map event instances.</param>
        /// <throws>
        /// ConfigurationException if the alias is already in used for a different type
        /// </throws>
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

        /// <summary>
        /// Add an alias for an event type that represents DataDictionary events, taking a Map of
        /// event property and class name as a parameter.
        /// <p>
        /// This method is provided for convenience and is same in function to method
        /// taking a Properties object that contain fully qualified class name as values.
        /// </p>
        /// 	<p>
        /// Allows a second alias to be added for the same type.
        /// Does not allow the same alias to be used for different types.
        /// </p>
        /// </summary>
        /// <param name="eventTypeAlias">is the alias for the event type</param>
        /// <param name="typeMap">maps the name of each property in the Map event to the type of its value in the Map object</param>
        /// <throws>
        /// ConfigurationException if the alias is already in used for a different type
        /// </throws>
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

        /// <summary>
        /// Add an alias for an event type that represents org.w3c.dom.Node events.
        /// <p>
        /// Allows a second alias to be added for the same type.
        /// Does not allow the same alias to be used for different types.
        /// </p>
        /// </summary>
        /// <param name="eventTypeAlias">is the alias for the event type</param>
        /// <param name="xmlDOMEventTypeDesc">descriptor containing property and mapping information for XML-DOM events</param>
        /// <throws>
        /// ConfigurationException if the alias is already in used for a different type
        /// </throws>
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

        /// <summary>
        /// Creates the property types.
        /// </summary>
        /// <param name="properties">The properties.</param>
        /// <returns></returns>
	    private static EDictionary<String, Type> CreatePropertyTypes(Properties properties)
	    {
	        EDictionary<String, Type> propertyTypes = new HashDictionary<String, Type>();
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
	                type = Type.GetType(boxedTypeName, true);
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
