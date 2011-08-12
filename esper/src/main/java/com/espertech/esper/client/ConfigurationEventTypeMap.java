/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;
import java.util.*;

/**
 * Configuration object for Map-based event types.
 */
public class ConfigurationEventTypeMap implements MetaDefItem, Serializable
{
    private static final long serialVersionUID = 6770441816508380856L;

    private Set<String> superTypes;
    private String startTimestampPropertyName;
    private String endTimestampPropertyName;

    /**
     * Ctor.
     */
    public ConfigurationEventTypeMap() {
        superTypes = new LinkedHashSet<String>();
    }

    /**
     * Returns the super types, if any.
     * @return set of super type names
     */
    public Set<String> getSuperTypes() {
        return superTypes;
    }

    /**
     * Sets the super types.
     * @param superTypes set of super type names
     */
    public void setSuperTypes(Set<String> superTypes) {
        this.superTypes = superTypes;
    }

    /**
     * Returns the property name of the property providing the start timestamp value.
     * @return start timestamp property name
     */
    public String getStartTimestampPropertyName() {
        return startTimestampPropertyName;
    }

    /**
     * Sets the property name of the property providing the start timestamp value.
     * @param startTimestampPropertyName start timestamp property name
     */
    public void setStartTimestampPropertyName(String startTimestampPropertyName) {
        this.startTimestampPropertyName = startTimestampPropertyName;
    }

    /**
     * Returns the property name of the property providing the end timestamp value.
     * @return end timestamp property name
     */
    public String getEndTimestampPropertyName() {
        return endTimestampPropertyName;
    }

    /**
     * Sets the property name of the property providing the end timestamp value.
     * @param endTimestampPropertyName start timestamp property name
     */
    public void setEndTimestampPropertyName(String endTimestampPropertyName) {
        this.endTimestampPropertyName = endTimestampPropertyName;
    }
}
