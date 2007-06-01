///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using Properties = net.esper.compat.EDataDictionary;

namespace net.esper.client
{
    /// <summary>
    /// Provides configuration operations for configuration-time and runtime parameters.
    /// </summary>
    interface ConfigurationOperations
    {
        /// <summary>
        /// Adds a plug-in aggregation function given a function name and an aggregation class name.
        /// <p>
        /// The aggregation class must : the base class {@link net.esper.eql.agg.AggregationSupport}.
        /// <p>
        /// The same function name cannot be added twice.
        /// </summary>
        /// <param name="functionName">is the new aggregation function name</param>
        /// <param name="aggregationClassName">
        /// is the fully-qualified class name of the class implementing the aggregation function
        /// </param>
        /// <throws>
        /// ConfigurationException is thrown to indicate a problem adding aggregation function
        /// </throws>
        void AddPlugInAggregationFunction(String functionName, String aggregationClassName);

        /// <summary>
        /// Adds a package or class to the list of automatically-imported classes and packages.
        /// <p>
        /// To import a single class offering a static method, simply supply the fully-qualified name of the class
        /// and use the syntax <code>classname.Methodname(...)</code>
        /// <p>
        /// To import a whole package and use the <code>classname.Methodname(...)</code> syntax, specifiy a package
        /// with wildcard, such as <code>com.mycompany.staticlib.*</code>.
        /// </summary>
        /// <param name="importName">
        /// is a fully-qualified class name or a package name with wildcard
        /// </param>
        /// <throws>
        /// ConfigurationException if incorrect package or class names are encountered
        /// </throws>
        void AddImport(String importName);

        /// <summary>
        /// Add an alias for an event type represented by JavaBean object events.
        /// <p>
        /// Allows a second alias to be added for the same type.
        /// Does not allow the same alias to be used for different types.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias for the event type</param>
        /// <param name="javaEventClassName">fully-qualified class name of the event type</param>
        /// <throws>
        /// ConfigurationException if the alias is already in used for a different type
        /// </throws>
        void AddEventTypeAlias(String eventTypeAlias, String javaEventClassName);

        /// <summary>
        /// Add an alias for an event type represented by Java-bean plain-old Java object events.
        /// <p>
        /// Allows a second alias to be added for the same type.
        /// Does not allow the same alias to be used for different types.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias for the event type</param>
        /// <param name="javaEventClass">
        /// is the Java event class for which to create the alias
        /// </param>
        /// <throws>
        /// ConfigurationException if the alias is already in used for a different type
        /// </throws>
        void AddEventTypeAlias(String eventTypeAlias, Type javaEventClass);

        /// <summary>
        /// Add an alias for an event type that represents java.util.Map events.
        /// <p>
        /// Allows a second alias to be added for the same type.
        /// Does not allow the same alias to be used for different types.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias for the event type</param>
        /// <param name="typeMap">
        /// maps the name of each property in the Map event to the type
        /// (fully qualified classname) of its value in Map event instances.
        /// </param>
        /// <throws>
        /// ConfigurationException if the alias is already in used for a different type
        /// </throws>
        void AddEventTypeAlias(String eventTypeAlias, Properties typeMap);

        /// <summary>
        /// Add an alias for an event type that represents java.util.Map events, taking a Map of
        /// event property and class name as a parameter.
        /// <p>
        /// This method is provided for convenience and is same in function to method
        /// taking a Properties object that contain fully qualified class name as values.
        /// <p>
        /// Allows a second alias to be added for the same type.
        /// Does not allow the same alias to be used for different types.
        /// </summary>
        /// <param name="eventTypeAlias">is the alias for the event type</param>
        /// <param name="typeMap">
        /// maps the name of each property in the Map event to the type of its value in the Map object
        /// </param>
        /// <throws>
        /// ConfigurationException if the alias is already in used for a different type
        /// </throws>
        void AddEventTypeAlias(String eventTypeAlias, IDictionary<String, Type> typeMap);

        /// <summary>
        /// Add an alias for an event type that represents org.w3c.dom.Node events.
        /// <p>
        /// Allows a second alias to be added for the same type.
        /// Does not allow the same alias to be used for different types.
		/// </p>
        /// </summary>
        /// <param name="eventTypeAlias">is the alias for the event type</param>
        /// <param name="xmlDOMEventTypeDesc">
        /// descriptor containing property and mapping information for XML-DOM events
        /// </param>
        /// <throws>
        /// ConfigurationException if the alias is already in used for a different type
        /// </throws>
        void AddEventTypeAlias(String eventTypeAlias, ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc);
    }
}
