/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.net.URI;
import java.io.Serializable;

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
     * @param packageName is the fully-qualified Java package name of the Java package that event classes reside in
     */
    public void addEventTypeAutoAlias(String packageName);

    /**
     * Adds a plug-in aggregation function given a function name and an aggregation class name.
     * <p>
     * The aggregation class must extends the base class {@link com.espertech.esper.epl.agg.AggregationSupport}.
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
     * Checks if an eventTypeAlias has already been registered for that alias name.
     * @since 2.1
     * @param eventTypeAlias the alias name
     * @return true if already registered
     */
    public boolean isEventTypeAliasExists(String eventTypeAlias);

    /**
     * Add an alias for an event type represented by JavaBean object events.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param eventClassName fully-qualified class name of the event type
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, String eventClassName)
            throws ConfigurationException;

    /**
     * Add an alias for an event type represented by Java-bean plain-old Java object events.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventTypeAlias is the alias for the event type
     * @param eventClass is the Java event class for which to create the alias
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAlias(String eventTypeAlias, Class eventClass)
            throws ConfigurationException;

    /**
     * Add an alias for an event type represented by Java-bean plain-old Java object events,
     * using the simple name of the Java class as the alias.
     * <p>
     * For example, if your class is "com.mycompany.MyEvent", then this method
     * adds the alias "MyEvent" for the class.
     * <p>
     * Allows a second alias to be added for the same type.
     * Does not allow the same alias to be used for different types.
     * @param eventClass is the Java event class for which to create the alias from the class simple name
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAliasSimpleName(Class eventClass);

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
     * Add an alias for an event type that represents java.util.Map events,
     * and for which each property may itself be a Map of further properties,
     * with unlimited nesting levels.
     * <p>
     * Each entry in the type mapping must contain the String property name
     * and either a Class or further Map<String, Object> value.
     * @param eventTypeAlias is the alias for the event type
     * @param typeMap maps the name of each property in the Map event to the type
     * (fully qualified classname) of its value in Map event instances.
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAliasNestable(String eventTypeAlias, Map<String, Object> typeMap)
            throws ConfigurationException;

    /**
     * Add an alias for an event type that represents java.util.Map events,
     * and for which each property may itself be a Map of further properties,
     * with unlimited nesting levels, and specify an optional list of super types
     * to the new Map event type.
     * <p>
     * Each entry in the type mapping must contain the String property name
     * and either a Class or further Map<String, Object> value.
     * @param eventTypeAlias is the alias for the event type
     * @param typeMap maps the name of each property in the Map event to the type
     * (fully qualified classname) of its value in Map event instances.
     * @param superTypes is an array of event type alias of further Map types that this
     * 
     * @throws ConfigurationException if the alias is already in used for a different type
     */
    public void addEventTypeAliasNestable(String eventTypeAlias, Map<String, Object> typeMap, String[] superTypes)
            throws ConfigurationException;

    /**
     * Add an alias for an event type that represents nestable strong-typed java.util.Map events, taking a Map of
     * event property and class name as a parameter.
     * <p>
     * This method takes a Map of String property names and Object property type. Each Object property
     * type can either be a java.lang.Class to denote a built-in type or POJO application object,
     * or can itself also be a Map<String, Object> to describe a property that itself is a
     * map of further properties. 
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
    public void addNestableEventTypeAlias(String eventTypeAlias, Map<String, Object> typeMap)
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

    /**
     * Add a variable.
     * @param variableName name of the variable to add
     * @param type the type of the variable must be a primitive or boxed Java-builtin scalar type.
     * @param initializationValue is the first assigned value
     * @throws ConfigurationException if the type and initialization value don't match or the variable name
     * is already in use
     */
    public void addVariable(String variableName, Class type, Object initializationValue) throws ConfigurationException;

    /**
     * Adds an alias for an event type that one of the plug-in event representations resolves to an event type.
     * <p>
     * The order of the URIs matters as event representations are asked in turn, to accept the event type.
     * <p>
     * URIs can be child URIs of plug-in event representations and can add additional parameters or fragments
     * for use by the event representation.
     * @param eventTypeAlias is the alias name of the event type
     * @param resolutionURIs is URIs that are matched to registered event representations
     * @param initializer is an optional value for parameterizing or configuring the event type
     */
    public void addPlugInEventType(String eventTypeAlias, URI[] resolutionURIs, Serializable initializer);

    /**
     * Sets the URIs that point to plug-in event representations that are given a chance to dynamically resolve an event
     * type alias to an event type, when a new (unseen) event type alias occurs in a new EPL statement.
     * <p>
     * The order of the URIs matters as event representations are asked in turn, to accept the alias.
     * <p>
     * URIs can be child URIs of plug-in event representations and can add additional parameters or fragments
     * for use by the event representation.
     * @param urisToResolveAlias URIs for resolving the alias
     */
    public void setPlugInEventTypeAliasResolutionURIs(URI[] urisToResolveAlias);

    /**
     * Adds an revision event type. The alias name of the event type may be used with named windows
     * to indicate that updates or new versions of events are processed.
     * @param revisionEventTypeAlias the alias name of the revision event type
     * @param revisionEventTypeConfig the configuration 
     */
    public void addRevisionEventType(String revisionEventTypeAlias, ConfigurationRevisionEventType revisionEventTypeConfig);

    /**
     * Adds a new variant stream. Variant streams allow events of disparate types to be treated the same.
     * @param variantStreamName is the name of the variant stream
     * @param variantStreamConfig the configuration such as variant type aliases and any-type setting
     */
    public void addVariantStream(String variantStreamName, ConfigurationVariantStream variantStreamConfig);

    /**
     * Updates an existing Map event type with additional properties.
     * <p>
     * Does not update existing properties of the updated Map event type.
     * <p>
     * Adds additional nested properties to nesting levels, if any.
     * <p>
     * Each entry in the type mapping must contain the String property name of the additional property
     * and either a Class or further Map<String, Object> value for nested properties.
     * <p>
     * Map event types can only be updated at runtime, at configuration time updates are not allowed.
     * <p>
     * The type Map may list previously declared properties or can also contain only the new properties to be added. 
     * @param mapEventTypeAlias the name of the map event type to update
     * @param typeMap a Map of string property name and type
     * @throws ConfigurationException if the event type alias could not be found or is not a Map
     */
    public void updateMapEventType(String mapEventTypeAlias, Map<String, Object> typeMap) throws ConfigurationException;

    /**
     * Returns true if a variant stream by the name has been declared, or false if not.
     * @param name of variant stream
     * @return indicator whether the variant stream by that name exists 
     */
    public boolean isVariantStreamExists(String name);

    /**
     * Sets a new interval for metrics reporting for a pre-configured statement group, or changes
     * the default statement reporting interval if supplying a null value for the statement group name.
     * @param stmtGroupName name of statement group, provide a null value for the default statement interval (default group) 
     * @param newIntervalMSec millisecond interval, use zero or negative value to disable
     * @throws ConfigurationException if the statement group cannot be found
     */
    public void setMetricsReportingInterval(String stmtGroupName, long newIntervalMSec) throws ConfigurationException;

    /**
     * Enable metrics reporting for the given statement.
     * <p>
     * This operation can only be performed at runtime and is not available at engine initialization time.
     * <p>
     * Statement metric reporting follows the configured default or statement group interval.
     * <p>
     * Only if metrics reporting (on the engine level) has been enabled at initialization time
     * can statement-level metrics reporting be enabled through this method.
     * @param statementName for which to enable metrics reporting
     * @throws ConfigurationException if the statement cannot be found
     */
    public void setMetricsReportingStmtEnabled(String statementName) throws ConfigurationException;

    /**
     * Disable metrics reporting for a given statement.
     * @param statementName for which to disable metrics reporting
     * @throws ConfigurationException if the statement cannot be found
     */
    public void setMetricsReportingStmtDisabled(String statementName) throws ConfigurationException;

    /**
     * Enable engine-level metrics reporting.
     * <p>
     * Use this operation to control, at runtime, metrics reporting globally.
     * <p>
     * Only if metrics reporting (on the engine level) has been enabled at initialization time
     * can metrics reporting be re-enabled at runtime through this method.
     * @throws ConfigurationException if use at runtime and metrics reporting had not been enabled at initialization time
     */
    public void setMetricsReportingEnabled() throws ConfigurationException;

    /**
     * Disable engine-level metrics reporting.
     * <p>
     * Use this operation to control, at runtime, metrics reporting globally. Setting metrics reporting
     * to disabled removes all performance cost for metrics reporting.
     * @throws ConfigurationException if use at runtime and metrics reporting had not been enabled at initialization time
     */
    public void setMetricsReportingDisabled() throws ConfigurationException;

    /**
     * Remove an event type by its alias name, returning an indicator whether the event type was found and removed.
     * <p>
     * This method deletes the event type by it's alias name from the memory of the engine,
     * thereby allowing that the name to be reused for a new event type and disallowing new statements
     * that attempt to use the deleted name.
     * <p>
     * If there are one or more statements in started or stopped state that reference the event type,
     * this operation throws ConfigurationException unless the force flag is passed.
     * <p>
     * If using the force flag to remove the type while statements use the type, the exact
     * behavior of the engine depends on the event representation of the deleted event type and is thus
     * not well defined. It is recommended to destroy statements that use the type before removing the type.
     * Use #getEventTypeAliasUsedBy to obtain a list of statements that use a type.
     * <p>
     * The method can be used for event types implicitly created for insert-into streams and for named windows. 
     * The method does not remove variant streams and does not remove revision event types.
     * @param alias the name of the event type to remove
     * @param force false to include a check that the type is no longer in use, true to force the remove
     * even though there can be one or more statements relying on that type
     * @return indicator whether the event type was found and removed
     * @throws ConfigurationException thrown to indicate that the remove operation failed
     */
    public boolean removeEventType(String alias, boolean force) throws ConfigurationException;

    /**
     * Return the set of statement names of statements that are in started or stopped state and
     * that reference the given event type alias.
     * <p>
     * A reference counts as any mention of the event type in a from-clause, a pattern, a insert-into or
     * as part of on-trigger.
     * @param eventTypeAlias name of the event type
     * @return statement names referencing that type
     */
    public Set<String> getEventTypeAliasUsedBy(String eventTypeAlias);
}
