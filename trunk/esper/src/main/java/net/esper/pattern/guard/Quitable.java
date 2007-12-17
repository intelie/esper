/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.pattern.guard;

/**
 * Receiver for quit events for use by guards.
 */
public interface Quitable
{
    /**
     * Indicate guard quitted.
     */
    public void guardQuit();
}
