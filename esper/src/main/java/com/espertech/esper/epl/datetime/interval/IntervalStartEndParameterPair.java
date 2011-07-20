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

public class IntervalStartEndParameterPair {
    private final ExprOptionalConstant start;
    private final ExprOptionalConstant end;

    private IntervalStartEndParameterPair(ExprOptionalConstant start, ExprOptionalConstant end) {
        this.start = start;
        this.end = end;
    }

    public static IntervalStartEndParameterPair fromParamsWithSameEnd(ExprOptionalConstant[] params) {
        ExprOptionalConstant start = params[0];
        ExprOptionalConstant end;
        if (params.length == 1) {
            end = start;
        }
        else {
            end = params[1];
        }
        return new IntervalStartEndParameterPair(start, end);
    }

    public static IntervalStartEndParameterPair fromParamsWithLongMaxEnd(ExprOptionalConstant[] params) {
        ExprOptionalConstant start = params[0];
        ExprOptionalConstant end;
        if (params.length == 1) {
            end = ExprOptionalConstant.make(Long.MAX_VALUE);
        }
        else {
            end = params[1];
        }
        return new IntervalStartEndParameterPair(start, end);
    }

    public ExprOptionalConstant getStart() {
        return start;
    }

    public ExprOptionalConstant getEnd() {
        return end;
    }

    public boolean isConstant() {
        return start.getOptionalConstant() != null && end.getOptionalConstant() != null;
    }

}
