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

package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.event.map.MapEventBean;
import com.espertech.esper.event.map.MapEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;

public abstract class EnumEvalBaseScalar extends EnumEvalBase implements EnumEval {

    protected final String evalPropertyName;
    protected final MapEventBean evalEvent;

    public EnumEvalBaseScalar(ExprEvaluator innerExpression, int streamCountIncoming, MapEventType type, String propertyName) {
        super(innerExpression, streamCountIncoming);
        this.evalPropertyName = propertyName;
        this.evalEvent = new MapEventBean(new HashMap<String, Object>(), type);
    }
}
