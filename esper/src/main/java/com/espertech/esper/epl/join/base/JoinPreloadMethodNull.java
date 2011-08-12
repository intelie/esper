/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.base;

import com.espertech.esper.view.internal.BufferView;
import com.espertech.esper.epl.core.ResultSetProcessor;

/**
 * Implements a method for pre-loading (initializing) join that does not return any events.
 */
public class JoinPreloadMethodNull implements JoinPreloadMethod
{
    /**
     * Ctor.
     */
    public JoinPreloadMethodNull()
    {
    }

    public void preloadFromBuffer(int stream)
    {
    }

    public void preloadAggregation(ResultSetProcessor resultSetProcessor)
    {
    }

    public void setBuffer(BufferView buffer, int i)
    {        
    }

    @Override
    public boolean isPreloading() {
        return false;
    }
}