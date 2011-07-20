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

public class SupportBeanCtorOne {

    private final String string;
    private final Integer intBoxed;
    private final int intPrimitive;
    private final boolean boolPrimitive;

    public SupportBeanCtorOne(String string, Integer intBoxed, int intPrimitive, boolean boolPrimitive) {
        this.string = string;
        this.intBoxed = intBoxed;
        this.intPrimitive = intPrimitive;
        this.boolPrimitive = boolPrimitive;
    }

    public SupportBeanCtorOne(String string, Integer intBoxed, int intPrimitive) {
        this.string = string;
        this.intBoxed = intBoxed;
        this.intPrimitive = intPrimitive;
        this.boolPrimitive = false;
    }

    public SupportBeanCtorOne(String string, Integer intBoxed) {
        this.string = string;
        this.intBoxed = intBoxed;
        this.intPrimitive = 99;
        this.boolPrimitive = false;
    }

    public SupportBeanCtorOne(String string) {
        throw new RuntimeException("This is a test exception");
    }

    public String getString() {
        return string;
    }

    public Integer getIntBoxed() {
        return intBoxed;
    }

    public int getIntPrimitive() {
        return intPrimitive;
    }

    public boolean isBoolPrimitive() {
        return boolPrimitive;
    }
}
