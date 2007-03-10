package net.esper.core;

import net.esper.client.*;

import java.util.*;

/**
 * Snapshot of Configuration is held for re-initializing engine state
 * from prior configuration values that may have been muted.
 */
public class ConfigurationSnapshot
{
    private Map<String, String> javaClassAliases = new HashMap<String, String>();
    private Map<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = new HashMap<String, ConfigurationEventTypeXMLDOM>();
    private Map<String, ConfigurationEventTypeLegacy> legacyAliases = new HashMap<String, ConfigurationEventTypeLegacy>();
    private String[] autoImports;
    private Map<String, Properties> mapAliases = new HashMap<String, Properties>();
    private Map<String, ConfigurationDBRef> databaseRefs = new HashMap<String, ConfigurationDBRef>();
    private String epServicesContextFactoryClassName;
    private List<ConfigurationPlugInView> plugInViews = new LinkedList<ConfigurationPlugInView>();

    /**
     * Ctor.
     * <p>
     * Copies information out of configuration performing a deep copy
     * to preserve configs.
     * @param configuration is the client configuration holder
     */
    public ConfigurationSnapshot(Configuration configuration)
    {
        javaClassAliases.putAll(configuration.getEventTypeAliases());
        xmlDOMAliases.putAll(configuration.getEventTypesXMLDOM());
        autoImports = configuration.getImports().toArray(new String[0]);
        mapAliases.putAll(configuration.getEventTypesMapEvents());
        legacyAliases.putAll(configuration.getEventTypesLegacy());
        databaseRefs.putAll(configuration.getDatabaseReferences());
        epServicesContextFactoryClassName = configuration.getEPServicesContextFactoryClassName();
        plugInViews.addAll(configuration.getPlugInViews());
    }

    /**
     * Returns event type alias to Java class name mapping.
     * @return map of alias to class name
     */
    public Map<String, String> getJavaClassAliases()
    {
        return javaClassAliases;
    }

    /**
     * Returns map of event alias and XML DOM configs.
     * @return event alias to XML DOM config mapping
     */
    public Map<String, ConfigurationEventTypeXMLDOM> getXmlDOMAliases()
    {
        return xmlDOMAliases;
    }

    /**
     * Returns list of automatic import packages and classes.
     * @return automatic imports, or zero-length array if none
     */
    public String[] getAutoImports()
    {
        return autoImports;
    }

    /**
     * Returns a map of event type alias to Map-event type properties.
     * @return alias to event properties mapping for Map event types
     */
    public Map<String, Properties> getMapAliases()
    {
        return mapAliases;
    }

    /**
     * Returns the map of event type alias to legacy event type configuration.
     * @return map with alias as the key and legacy type config as the value
     */
    public Map<String, ConfigurationEventTypeLegacy> getLegacyAliases()
    {
        return legacyAliases;
    }

    /**
     * Returns a map of database name to database configuration.
     * @return database configs
     */
    public Map<String, ConfigurationDBRef> getDatabaseRefs()
    {
        return databaseRefs;
    }

    /**
     * Returns the services context factory class name
     * @return class name
     */
    public String getEPServicesContextFactoryClassName()
    {
        return epServicesContextFactoryClassName;
    }

    /**
     * Returns a list of configured plug-in views.
     * @return configs for views
     */
    public List<ConfigurationPlugInView> getPlugInViews()
    {
        return plugInViews;
    }
}


