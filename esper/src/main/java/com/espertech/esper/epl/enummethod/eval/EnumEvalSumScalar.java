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

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;

import java.util.Collection;

public class EnumEvalSumScalar extends EnumEvalBase implements EnumEval {

    private final AggregationMethod aggregationMethod;

    public EnumEvalSumScalar(int streamCountIncoming, AggregationMethod aggregationMethod) {
        super(streamCountIncoming);
        this.aggregationMethod = aggregationMethod;
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {

        aggregationMethod.clear();
        
        for (Object next : target) {

            aggregationMethod.enter(next);
        }

        return aggregationMethod.getValue();
    }
}
