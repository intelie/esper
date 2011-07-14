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
    private Set<String> superTypes;
    private String startTimestampPropertyName;
    private String endTimestampPropertyName;

    public ConfigurationEventTypeMap() {
        superTypes = new LinkedHashSet<String>();
    }

    public Set<String> getSuperTypes() {
        return superTypes;
    }

    public void setSuperTypes(Set<String> superTypes) {
        this.superTypes = superTypes;
    }

    public String getStartTimestampPropertyName() {
        return startTimestampPropertyName;
    }

    public void setStartTimestampPropertyName(String startTimestampPropertyName) {
        this.startTimestampPropertyName = startTimestampPropertyName;
    }

    public String getEndTimestampPropertyName() {
        return endTimestampPropertyName;
    }

    public void setEndTimestampPropertyName(String endTimestampPropertyName) {
        this.endTimestampPropertyName = endTimestampPropertyName;
    }
}
