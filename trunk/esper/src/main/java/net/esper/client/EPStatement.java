/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

/**
 * Interface to add and remove update listeners receiving events for an EP statement.
 */
public interface EPStatement extends EPListenable, EPIterable
{
    /**
     * Start the statement.
     */
    public void start();

    /**
     * Stop the statement.
     */
    public void stop();

    /**
     * Returns the underlying expression text or XML.
     * @return expression text
     */
    public String getText();
}

