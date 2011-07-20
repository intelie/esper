/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.core;

import com.espertech.esper.client.EPServiceStateListener;

import java.util.Map;
import java.util.Set;

public class ConfiguratorContext {
    private final String engineURI;
    private final Map<String, EPServiceProviderSPI> runtimes;

    public ConfiguratorContext(String engineURI, Map<String, EPServiceProviderSPI> runtimes) {
        this.engineURI = engineURI;
        this.runtimes = runtimes;
    }

    public String getEngineURI() {
        return engineURI;
    }

    public Map<String, EPServiceProviderSPI> getRuntimes() {
        return runtimes;
    }
}
