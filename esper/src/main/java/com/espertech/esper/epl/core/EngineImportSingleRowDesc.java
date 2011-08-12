/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.core;

import com.espertech.esper.client.ConfigurationMethodRef;
import com.espertech.esper.client.ConfigurationPlugInSingleRowFunction;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.agg.AggregationSupport;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.MethodResolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides information about a single-row function.
 */
public class EngineImportSingleRowDesc
{
    private final String className;
    private final String methodName;
    private final ConfigurationPlugInSingleRowFunction.ValueCache valueCache;

    public EngineImportSingleRowDesc(String className, String methodName, ConfigurationPlugInSingleRowFunction.ValueCache valueCache) {
        this.className = className;
        this.methodName = methodName;
        this.valueCache = valueCache;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public ConfigurationPlugInSingleRowFunction.ValueCache getValueCache() {
        return valueCache;
    }
}
