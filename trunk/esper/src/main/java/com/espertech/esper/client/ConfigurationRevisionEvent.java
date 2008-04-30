package com.espertech.esper.client;

import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;

public class ConfigurationRevisionEvent implements Serializable
{
    private String aliasFullEvent;
    private Set<String> aliasRevisionEvents;
    private PropertyRevision propertyRevision;
    private String[] keyPropertyNames;

    public ConfigurationRevisionEvent()
    {
        aliasRevisionEvents = new HashSet<String>();
        propertyRevision = PropertyRevision.DECLARED;
    }

    public String getAliasFullEvent()
    {
        return aliasFullEvent;
    }

    public void setAliasFullEvent(String aliasFullEvent)
    {
        this.aliasFullEvent = aliasFullEvent;
    }

    public Set<String> getAliasRevisionEvents()
    {
        return aliasRevisionEvents;
    }

    public void addAliasRevisionEvent(String aliasRevisionEvent)
    {
        aliasRevisionEvents.add(aliasRevisionEvent);
    }

    public PropertyRevision getPropertyRevision()
    {
        return propertyRevision;
    }

    public void setPropertyRevision(PropertyRevision propertyRevision)
    {
        this.propertyRevision = propertyRevision;
    }

    public String[] getKeyPropertyNames()
    {
        return keyPropertyNames;
    }

    public void setKeyPropertyNames(String[] keyPropertyNames)
    {
        this.keyPropertyNames = keyPropertyNames;
    }

    public enum PropertyRevision
    {
        /**
         * All declared properties provide a new revision, regardsless of whether a property actually exists
         * and regardless of whether a property has a non-null value.
         */
        DECLARED,

        /**
         * Only properties that exists and also have a non-null value are a new revision.
         */
        EXISTS_NON_NULL,

        /**
         * Properties that exists regardless of whether the property value is null or not null are a new revision.
         */
        EXISTS
    }
}
