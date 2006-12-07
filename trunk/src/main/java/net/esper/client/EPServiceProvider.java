/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;


/**
 * This class provides access to the EPRuntime and EPAdministrator implementations.
 */
public interface EPServiceProvider
{
    /**
     * Returns a class instance of EPRuntime.
     * @return an instance of EPRuntime
     */
    public EPRuntime getEPRuntime();

    /**
     * Returns a class instance of EPAdministrator.
     * @return an instance of EPAdministrator
     */
    public EPAdministrator getEPAdministrator();

    /**
     * Frees any resources associated with this runtime instance.
     * Stops and destroys any event filters, patterns, expressions, views.
     */
    public void initialize();
}
