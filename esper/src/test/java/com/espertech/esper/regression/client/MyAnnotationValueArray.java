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

package com.espertech.esper.regression.client;

public @interface MyAnnotationValueArray
{
    public abstract long[] value();
    public abstract int[] intArray();
    public abstract double[] doubleArray();
    public abstract String[] stringArray();
    public abstract String[] stringArrayDef() default {"XYZ"};
}
