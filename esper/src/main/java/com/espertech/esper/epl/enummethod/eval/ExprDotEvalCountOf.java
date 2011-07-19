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
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParamLambda;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.expression.ExprDotNodeUtility;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.map.MapEventType;

import java.util.List;

public class ExprDotEvalCountOf extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters) {
        EventType firstParamType;
        if (inputEventType == null) {
            firstParamType = ExprDotNodeUtility.makeTransientMapType(enumMethodUsedName, goesToNames.get(0), collectionComponentType);
        }
        else {
            firstParamType = inputEventType;
        }
        return new EventType[] {firstParamType};
    }

    public EnumEval getEnumEval(EventAdapterService eventAdapterService, StreamTypeService streamTypeService, String statementId, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) {
        super.setTypeInfo(ExprDotEvalTypeInfo.scalarOrUnderlying(Integer.class));
        if (bodiesAndParameters.isEmpty()) {
            return new EnumEvalCountOf(numStreamsIncoming);
        }

        ExprDotEvalParamLambda first = (ExprDotEvalParamLambda) bodiesAndParameters.get(0);
        if (inputEventType != null) {
            return new EnumEvalCountOfSelectorEvents(first.getBodyEvaluator(), first.getStreamCountIncoming());
        }
        else {
            return new EnumEvalCountOfSelectorScalar(first.getBodyEvaluator(), first.getStreamCountIncoming(), (MapEventType) first.getGoesToTypes()[0], first.getGoesToNames().get(0));
        }
    }
}
