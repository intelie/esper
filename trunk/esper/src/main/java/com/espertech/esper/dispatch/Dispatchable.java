/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.dispatch;

/**
 * Implementations are executed when the DispatchService receives a dispatch invocation.
 */
public interface Dispatchable
{
    /**
     * Execute dispatch. 
     */
    public void execute();
}
