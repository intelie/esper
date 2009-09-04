/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.plugin;

import com.espertech.esper.core.EPServiceProviderSPI;

import java.util.Properties;

/**
 * Interface for loaders of input/output adapters.
 */
public interface PluginLoader
{
    /**
     * Initializes the adapter loader.
     * <p>
     * Invoked before the engine instance is fully initialized. Thereby this is not the place to
     * look up an engine instance from {@link com.espertech.esper.client.EPServiceProviderManager}
     * and use it. Use the {@link #postInitialize} method instead.
     * @param name is the loader name
     * @param properties is a set of properties from the configuration
     * @param epService is the SPI of the engine itself for sending events to
     */
    public void init(String name, Properties properties, EPServiceProviderSPI epService);

    /**
     * Called after an engine instances has fully initialized and is already
     * registered with {@link com.espertech.esper.client.EPServiceProviderManager}.
     * @since 3.2.0
     */
    public void postInitialize();

    /**
     * Destroys adapter loader and adapters loaded.
     * <p>
     * Invoked upon {@link com.espertech.esper.client.EPServiceProvider#destroy} before the engine instance is actually destroyed.
     * <p>
     * Implementations may block to ensure dependent threads are stopped or other resources released.
     */
    public void destroy();
}
