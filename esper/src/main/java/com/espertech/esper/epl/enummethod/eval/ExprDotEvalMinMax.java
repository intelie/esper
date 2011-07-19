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

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.*;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.util.JavaClassHelper;

import java.util.List;

public class ExprDotEvalMinMax extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {inputEventType};
    }

    public EnumEval getEnumEval(EventAdapterService eventAdapterService, StreamTypeService streamTypeService, String statementId, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) {

        if (bodiesAndParameters.isEmpty()) {
            Class returnType = JavaClassHelper.getBoxedType(collectionComponentType);
            super.setTypeInfo(ExprDotEvalTypeInfo.scalarOrUnderlying(returnType));
            if (this.getEnumMethodEnum() == EnumMethodEnum.MAX) {
                return new EnumEvalMaxScalar(numStreamsIncoming);
            }
            return new EnumEvalMinScalar(numStreamsIncoming);
        }

        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        Class returnType = JavaClassHelper.getBoxedType(first.getBodyEvaluator().getType());
        super.setTypeInfo(ExprDotEvalTypeInfo.scalarOrUnderlying(returnType));

        if (this.getEnumMethodEnum() == EnumMethodEnum.MAX) {
            return new EnumEvalMax(first.getBodyEvaluator(), first.getStreamCountIncoming());
        }
        return new EnumEvalMin(first.getBodyEvaluator(), first.getStreamCountIncoming());
    }
}
