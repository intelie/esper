/**************************************************************************************
 * Copyright (C) 2007 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import java.util.Properties;
import java.io.Serializable;

/**
 * Holds configuration for an input/output adapter loader.
 */
public class ConfigurationAdapterLoader implements Serializable
{
    private String loaderName;
    private String className;
    private Properties configProperties;

    /**
     * Ctor.
     */
    public ConfigurationAdapterLoader()
    {
    }

    /**
     * Returns the loader class name.
     * @return class name of loader
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * Sets the loader classname.
     * @param className of loader
     */
    public void setClassName(String className)
    {
        this.className = className;
    }

    /**
     * Returns loader configuration properties.
     * @return config entries
     */
    public Properties getConfigProperties()
    {
        return configProperties;
    }

    /**
     * Sets the loader configuration.
     * @param configProperties is the configuration of the loader
     */
    public void setConfigProperties(Properties configProperties)
    {
        this.configProperties = configProperties;
    }

    /**
     * Returns the loader name.
     * @return loader name
     */
    public String getLoaderName()
    {
        return loaderName;
    }

    /**
     * Sets the loader name.
     * @param loaderName is the loader name
     */
    public void setLoaderName(String loaderName)
    {
        this.loaderName = loaderName;
    }
}
