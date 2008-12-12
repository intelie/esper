/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.client.*;
import com.espertech.esper.epl.core.EngineImportException;
import com.espertech.esper.epl.core.EngineImportService;
import com.espertech.esper.epl.core.EngineSettingsService;
import com.espertech.esper.epl.metric.MetricReportingService;
import com.espertech.esper.epl.variable.VariableExistsException;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.epl.variable.VariableTypeException;
import com.espertech.esper.event.EventAdapterException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.vaevent.ValueAddEventProcessor;
import com.espertech.esper.event.vaevent.ValueAddEventService;
import com.espertech.esper.event.vaevent.VariantEventType;
import com.espertech.esper.util.JavaClassHelper;

import java.io.Serializable;
import java.net.URI;
import java.util.*;

/**
 * Provides runtime engine configuration operations.
 */
public class ConfigurationOperationsImpl implements ConfigurationOperations
{
    private final EventAdapterService eventAdapterService;
    private final EngineImportService engineImportService;
    private final VariableService variableService;
    private final EngineSettingsService engineSettingsService;
    private final ValueAddEventService valueAddEventService;
    private final MetricReportingService metricReportingService;
    private final StatementEventTypeRef statementEventTypeRef;

    /**
     * Ctor.
     * @param eventAdapterService is the event wrapper and type service
     * @param engineImportService for imported aggregation functions and static functions
     * @param variableService - provides access to variable values
     * @param engineSettingsService - some engine settings are writable
     * @param valueAddEventService - update event handling
     * @param metricReportingService - for metric reporting
     * @param statementEventTypeRef - statement to event type reference holding 
     */
    public ConfigurationOperationsImpl(EventAdapterService eventAdapterService,
                                       EngineImportService engineImportService,
                                       VariableService variableService,
                                       EngineSettingsService engineSettingsService,
                                       ValueAddEventService valueAddEventService,
                                       MetricReportingService metricReportingService,
                                       StatementEventTypeRef statementEventTypeRef)
    {
        this.eventAdapterService = eventAdapterService;
        this.engineImportService = engineImportService;
        this.variableService = variableService;
        this.engineSettingsService = engineSettingsService;
        this.valueAddEventService = valueAddEventService;
        this.metricReportingService = metricReportingService;
        this.statementEventTypeRef = statementEventTypeRef;
    }

    public void addEventTypeAutoAlias(String javaPackageName)
    {
        eventAdapterService.addAutoAliasPackage(javaPackageName);
    }

    public void addPlugInAggregationFunction(String functionName, String aggregationClassName)
    {
        try
        {
            engineImportService.addAggregation(functionName, aggregationClassName);
        }
        catch (EngineImportException e)
        {
            throw new ConfigurationException(e.getMessage(), e);
        }
    }

    public void addImport(String importName)
    {
        try
        {
            engineImportService.addImport(importName);
        }
        catch (EngineImportException e)
        {
            throw new ConfigurationException(e.getMessage(), e);
        }
    }

    public boolean isEventTypeAliasExists(String eventTypeAlias) {
        return eventAdapterService.getExistsTypeByAlias(eventTypeAlias) != null;
    }

    public void addEventTypeAlias(String eventTypeAlias, String javaEventClassName)
    {
        try
        {
            eventAdapterService.addBeanType(eventTypeAlias, javaEventClassName, false);
        }
        catch (EventAdapterException t)
        {
            throw new ConfigurationException(t.getMessage(), t);
        }
    }

    public void addEventTypeAlias(String eventTypeAlias, Class javaEventClass)
    {
        try
        {
            eventAdapterService.addBeanType(eventTypeAlias, javaEventClass, true);
        }
        catch (EventAdapterException t)
        {
            throw new ConfigurationException(t.getMessage(), t);
        }
    }

    public void addEventTypeAliasSimpleName(Class javaEventClass)
    {
        try
        {
            eventAdapterService.addBeanType(javaEventClass.getSimpleName(), javaEventClass, true);
        }
        catch (EventAdapterException t)
        {
            throw new ConfigurationException(t.getMessage(), t);
        }
    }

    public void addEventTypeAlias(String eventTypeAlias, Properties typeMap)
    {
        Map<String, Object> types = createPropertyTypes(typeMap);
        try
        {
            eventAdapterService.addNestableMapType(eventTypeAlias, types, null, false, false, false);
        }
        catch (EventAdapterException t)
        {
            throw new ConfigurationException(t.getMessage(), t);
        }
    }

    public void addEventTypeAlias(String eventTypeAlias, Map<String, Object> typeMap)
    {
        try
        {
            eventAdapterService.addNestableMapType(eventTypeAlias, typeMap, null, true, false, false);
        }
        catch (EventAdapterException t)
        {
            throw new ConfigurationException(t.getMessage(), t);
        }
    }

    public void addEventTypeAliasNestable(String eventTypeAlias, Map<String, Object> typeMap) throws ConfigurationException
    {
        try
        {
            eventAdapterService.addNestableMapType(eventTypeAlias, typeMap, null, true, false, false);
        }
        catch (EventAdapterException t)
        {
            throw new ConfigurationException(t.getMessage(), t);
        }
    }

    public void addEventTypeAliasNestable(String eventTypeAlias, Map<String, Object> typeMap, String[] superTypes) throws ConfigurationException
    {
        Set<String> superTypeAliases = null;
        if ((superTypes != null) && (superTypes.length > 0))
        {
            superTypeAliases = new HashSet<String>(Arrays.asList(superTypes));
        }

        try
        {
            eventAdapterService.addNestableMapType(eventTypeAlias, typeMap, superTypeAliases, true, false, false);
        }
        catch (EventAdapterException t)
        {
            throw new ConfigurationException(t.getMessage(), t);
        }
    }

    public void addNestableEventTypeAlias(String eventTypeAlias, Map<String, Object> typeMap)
    {
        try
        {
            eventAdapterService.addNestableMapType(eventTypeAlias, typeMap, null, true, false, false);
        }
        catch (EventAdapterException t)
        {
            throw new ConfigurationException(t.getMessage(), t);
        }
    }

    public void addEventTypeAlias(String eventTypeAlias, ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc)
    {
        try
        {
            eventAdapterService.addXMLDOMType(eventTypeAlias, xmlDOMEventTypeDesc);
        }
        catch (EventAdapterException t)
        {
            throw new ConfigurationException(t.getMessage(), t);
        }
    }

    private static Map<String, Object> createPropertyTypes(Properties properties)
    {
        Map<String, Object> propertyTypes = new HashMap<String, Object>();
        for(Map.Entry<Object, Object> entry : properties.entrySet())
        {
            String className = (String) entry.getValue();

            if ("string".equals(className))
            {
                className = String.class.getName();
            }

            // use the boxed type for primitives
            String boxedClassName = JavaClassHelper.getBoxedClassName(className);

            Class clazz;
            try
            {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                clazz = Class.forName(boxedClassName, true, cl);
            }
            catch (ClassNotFoundException ex)
            {
                throw new ConfigurationException("Unable to load class '" + boxedClassName + "', class not found", ex);
            }

            propertyTypes.put((String) entry.getKey(), clazz);
        }
        return propertyTypes;
    }

    public void addVariable(String variableName, Class type, Object initializationValue) throws ConfigurationException
    {
        try
        {
            variableService.createNewVariable(variableName, type, initializationValue, null);
        }
        catch (VariableExistsException e)
        {
            throw new ConfigurationException("Error creating variable: " + e.getMessage(), e);
        }
        catch (VariableTypeException e)
        {
            throw new ConfigurationException("Error creating variable: " + e.getMessage(), e);
        }
    }


    public void addPlugInEventType(String eventTypeAlias, URI[] resolutionURIs, Serializable initializer)
    {
        try
        {
            eventAdapterService.addPlugInEventType(eventTypeAlias, resolutionURIs, initializer);
        }
        catch (EventAdapterException e)
        {
            throw new ConfigurationException("Error adding plug-in event type: " + e.getMessage(), e);
        }
    }

    public void setPlugInEventTypeAliasResolutionURIs(URI[] urisToResolveAlias)
    {
        engineSettingsService.setPlugInEventTypeResolutionURIs(urisToResolveAlias);
    }

    public void addRevisionEventType(String revisionEventTypeAlias, ConfigurationRevisionEventType revisionEventTypeConfig)
    {
        valueAddEventService.addRevisionEventType(revisionEventTypeAlias, revisionEventTypeConfig, eventAdapterService);
    }

    public void addVariantStream(String variantEventTypeAlias, ConfigurationVariantStream variantStreamConfig)
    {
        valueAddEventService.addVariantStream(variantEventTypeAlias, variantStreamConfig, eventAdapterService);
    }

    public void updateMapEventType(String mapEventTypeAlias, Map<String, Object> typeMap) throws ConfigurationException
    {
        try
        {
            eventAdapterService.updateMapEventType(mapEventTypeAlias, typeMap);
        }
        catch (EventAdapterException e)
        {
            throw new ConfigurationException("Error updating Map event type: " + e.getMessage(), e);
        }
    }

    public void setMetricsReportingInterval(String stmtGroupName, long newInterval)
    {
        try
        {
            metricReportingService.setMetricsReportingInterval(stmtGroupName, newInterval);
        }
        catch (RuntimeException e)
        {
            throw new ConfigurationException("Error updating interval for metric reporting: " + e.getMessage(), e);
        }
    }


    public void setMetricsReportingStmtEnabled(String statementName)
    {
        try
        {
            metricReportingService.setMetricsReportingStmtEnabled(statementName);
        }
        catch (RuntimeException e)
        {
            throw new ConfigurationException("Error enabling metric reporting for statement: " + e.getMessage(), e);
        }
    }

    public void setMetricsReportingStmtDisabled(String statementName)
    {
        try
        {
            metricReportingService.setMetricsReportingStmtDisabled(statementName);
        }
        catch (RuntimeException e)
        {
            throw new ConfigurationException("Error enabling metric reporting for statement: " + e.getMessage(), e);
        }
    }

    public void setMetricsReportingEnabled()
    {
        try
        {
            metricReportingService.setMetricsReportingEnabled();
        }
        catch (RuntimeException e)
        {
            throw new ConfigurationException("Error enabling metric reporting: " + e.getMessage(), e);
        }
    }

    public void setMetricsReportingDisabled()
    {
        try
        {
            metricReportingService.setMetricsReportingDisabled();
        }
        catch (RuntimeException e)
        {
            throw new ConfigurationException("Error enabling metric reporting: " + e.getMessage(), e);
        }
    }

    public boolean isVariantStreamExists(String name)
    {
        ValueAddEventProcessor processor = valueAddEventService.getValueAddProcessor(name);
        if (processor == null)
        {
            return false;
        }
        return processor.getValueAddEventType() instanceof VariantEventType;
    }

    public boolean removeEventType(String alias, boolean force) throws ConfigurationException
    {
        if (!force) {
            Set<String> statements = statementEventTypeRef.getStatementNamesForType(alias);
            if ((statements != null) && (!statements.isEmpty())) {
                throw new ConfigurationException("Event type '" + alias + "' is in use by one or more statements");
            }
        }

        EventType type = eventAdapterService.getExistsTypeByAlias(alias);
        if (type == null)
        {
            return false;
        }

        eventAdapterService.removeType(alias);
        statementEventTypeRef.removeReferencesType(alias);
        return true;
    }

    public Set<String> getEventTypeAliasUsedBy(String alias)
    {
        Set<String> statements = statementEventTypeRef.getStatementNamesForType(alias);
        if ((statements == null) || (statements.isEmpty()))
        {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(statements);
    }
}
