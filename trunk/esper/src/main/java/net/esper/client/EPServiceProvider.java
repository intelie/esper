/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import javax.naming.Context;


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
     * Provides naming context for public named objects.
     * <p>
     * An extension point designed for use by input and output adapters as well as
     * other extension services.
     * @return naming context providing name-to-object bindings
     */
    public Context getContext();

    /**
     * Frees any resources associated with this runtime instance, and leaves the engine instance
     * ready for further use.
     * <p>Stops and destroys any existing statement resources such as filters, patterns, expressions, views.
     */
    public void initialize();

    /**
     * Returns the provider URI, or null if this is the default provider.
     * @return provider URI
     */
    public String getURI();
}
