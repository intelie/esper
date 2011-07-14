/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.datetime.eval;

import com.espertech.esper.epl.datetime.eval.DatetimeMethodEnum;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.type.RelationalOpEnum;

public class ExprDotNodeFilterAnalyzerDTIntervalDesc
{
    private final DatetimeMethodEnum methodEnum;
    private final int streamIdLeft;
    private final String propertyLeft;
    private final ExprNode propertyLeftExpr;
    private final int streamIdRight;
    private final String propertyRight;
    private final ExprNode propertyRightExpr;
    private final RelationalOpEnum relationalOpEnum;

    public ExprDotNodeFilterAnalyzerDTIntervalDesc(DatetimeMethodEnum methodEnum, int streamIdLeft, String propertyLeft, ExprNode propertyLeftExpr, int streamIdRight, String propertyRight, ExprNode propertyRightExpr, RelationalOpEnum relationalOpEnum) {
        this.methodEnum = methodEnum;
        this.streamIdLeft = streamIdLeft;
        this.propertyLeft = propertyLeft;
        this.propertyLeftExpr = propertyLeftExpr;
        this.streamIdRight = streamIdRight;
        this.propertyRight = propertyRight;
        this.propertyRightExpr = propertyRightExpr;
        this.relationalOpEnum = relationalOpEnum;
    }

    public DatetimeMethodEnum getMethodEnum() {
        return methodEnum;
    }

    public int getStreamIdLeft() {
        return streamIdLeft;
    }

    public String getPropertyLeft() {
        return propertyLeft;
    }

    public ExprNode getPropertyLeftExpr() {
        return propertyLeftExpr;
    }

    public int getStreamIdRight() {
        return streamIdRight;
    }

    public String getPropertyRight() {
        return propertyRight;
    }

    public ExprNode getPropertyRightExpr() {
        return propertyRightExpr;
    }

    public RelationalOpEnum getRelationalOpEnum() {
        return relationalOpEnum;
    }
}

