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

package com.espertech.esper.epl.datetime.interval;

public abstract class IntervalComputerConstantBase {
    protected final long start;
    protected final long end;

    public IntervalComputerConstantBase(IntervalStartEndParameterPair pair, boolean allowSwitch) {
        long startVal = pair.getStart().getOptionalConstant();
        long endVal = pair.getEnd().getOptionalConstant();

        if (startVal > endVal && allowSwitch) {
            start = endVal;
            end = startVal;
        }
        else {
            start = startVal;
            end = endVal;
        }
    }
}
