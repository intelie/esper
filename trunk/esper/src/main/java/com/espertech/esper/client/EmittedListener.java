/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

/**
 * Listener interface for events emitted from an {@link EPRuntime}.
 */
public interface EmittedListener
{
    /**
     * Called to indicate an event emitted.
     * @param event is the event emitted
     */
    public void emitted(Object event);
}