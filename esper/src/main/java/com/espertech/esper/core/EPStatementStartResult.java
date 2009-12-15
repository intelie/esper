/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.view.Viewable;

public class EPStatementStartResult
{
    private final Viewable viewable;
    private final EPStatementStopMethod stopMethod;
    private final EPStatementDestroyMethod destroyMethod;

    public EPStatementStartResult(Viewable viewable, EPStatementStopMethod stopMethod) {
        this.viewable = viewable;
        this.stopMethod = stopMethod;
        this.destroyMethod = null;
    }

    public EPStatementStartResult(Viewable viewable, EPStatementStopMethod stopMethod, EPStatementDestroyMethod destroyMethod) {
        this.viewable = viewable;
        this.stopMethod = stopMethod;
        this.destroyMethod = destroyMethod;
    }

    public Viewable getViewable() {
        return viewable;
    }

    public EPStatementStopMethod getStopMethod() {
        return stopMethod;
    }

    public EPStatementDestroyMethod getDestroyMethod() {
        return destroyMethod;
    }
}