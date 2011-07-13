/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.MetaDefItem;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Configuration object for Map-based event types.
 */
public class ConfigurationEventTypeMap implements MetaDefItem, Serializable
{
    private Set<String> superTypes;
    private String timestampPropertyName;
    private String durationPropertyName;

    public ConfigurationEventTypeMap() {
        superTypes = new LinkedHashSet<String>();
    }

    public Set<String> getSuperTypes() {
        return superTypes;
    }

    public void setSuperTypes(Set<String> superTypes) {
        this.superTypes = superTypes;
    }

    public String getTimestampPropertyName() {
        return timestampPropertyName;
    }

    public void setTimestampPropertyName(String timestampPropertyName) {
        this.timestampPropertyName = timestampPropertyName;
    }

    public String getDurationPropertyName() {
        return durationPropertyName;
    }

    public void setDurationPropertyName(String durationPropertyName) {
        this.durationPropertyName = durationPropertyName;
    }
}
