/**************************************************************************************
 * Copyright (C) 2007 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import java.util.Map;
import java.util.Properties;

/**
 * Provides configuration operations for configuration-time and runtime parameters.
 */
public interface ConfigurationOperations
{
    /**
     * Adds a Java package name of a package that Java event classes reside in.
     * <p>
     * This setting allows an application to place all it's events into one or more Java packages
     * and then declare these packages via this method. The engine
     * attempts to resolve an event type alias to a Java class residing in each declared package.
     * <p>
     * For example, in the statement "select * from MyEvent" the engine attempts to load class "javaPackageName.MyEvent"
     * and if successful, uses that class as the event type.
     * @param javaPackageName is the fully-qualified Java package name of the Java package that event classes reside in
     */
    public void addEventTypeAutoAlias(String javaPackageName);

    /**
     * Adds a plug-in aggregation function given a function name and an aggregation class name.
     * <p>
     * The aggregation class must extends the base class {@link net.esper.eql.agg.AggregationSupport}.
     * <p>
     * The same function name cannot be added twice.
     * @param functionName is the new aggregation function name
     * @param aggregationClassName is the fully-qualified class name of the class implementing the aggregation function
     * @throws ConfigurationException is thrown to indicate a problem adding aggregation function
     */
    public void addPlugInAggregationFunction(String functionName, String aggregationClassName) throws ConfigurationException;

    /**
     * Adds a package or class to the list of automatically-imported classes and packages.
     * <p>
     * To import a single class offering a static method, simply supply the fully-qualified name of the class
     * and use the syntax <code>classname.methodname(...)</code>
     * <p>
     * To import a whole package and use the <code>classname.methodname(...)</code> syntax, specifiy a package
     * with wildcard, such as <code>com.mycompany.staticlib.*</code>.
     * @param importName is a fully-qualified class name or a package name with wildcard
     * @throws ConfigurationException if incorrect package or class names are encountered
     */
    public void addImport(String importName) throws ConfigurationException;

    /**
     * Add an alias for an event type represented by JavaBean object events.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param javaEventClassName fully-qualified class name of the event type
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, String javaEventClassName)
            throws ConfigurationException;

    /**
     * Add an alias for an event type represented by Java-bean plain-old Java object events.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param javaEventClass is the Java event class for which to create the alias
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, Class javaEventClass)
            throws ConfigurationException;

    /**
     * Add an alias for an event type that represents java.util.Map events.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param typeMap maps the name of each property in the Map event to the type
     * (fully qualified classname) of its value in Map event instances.
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, Properties typeMap)
            throws ConfigurationException;

    /**
     * Add an alias for an event type that represents java.util.Map events, taking a Map of
     * event property and class name as a parameter.
     * <p>
     * This method is provided for convenience and is same in function to method
     * taking a Properties object that contain fully qualified class name as values.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param typeMap maps the name of each property in the Map event to the type of its value in the Map object
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, Map<String, Class> typeMap)
            throws ConfigurationException;

    /**
     * Add an alias for an event type that represents org.w3c.dom.Node events.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param xmlDOMEventTypeDesc descriptor containing property and mapping information for XML-DOM events
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc)
            throws ConfigurationException;
}
