/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.bean;

public class SupportBeanCtorTwo {

    private final SupportBean_ST0 st0;
    private final SupportBean_ST1 st1;

    public SupportBeanCtorTwo(SupportBean_ST0 st0, SupportBean_ST1 st1) {
        this.st0 = st0;
        this.st1 = st1;
    }

    public SupportBean_ST0 getSt0() {
        return st0;
    }

    public SupportBean_ST1 getSt1() {
        return st1;
    }
}
