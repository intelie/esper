package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalTypeInfo;
import com.espertech.esper.epl.expression.ExprDotNode;
import com.espertech.esper.epl.expression.ExprEvaluatorEnumeration;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.event.EventTypeUtility;

import java.util.Iterator;
import java.util.List;

public class ExprDotEvalUnion extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(String enumMethodUsedName, List<String> goesToNames, EventType inputEventType, Class collectionComponentType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {};
    }

    public EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, Class collectionComponentType, int numStreamsIncoming) throws ExprValidationException {
        ExprDotEvalParam first = bodiesAndParameters.get(0);

        Pair<ExprEvaluatorEnumeration, ExprDotEvalTypeInfo> enumSrc = ExprDotNode.getEnumerationSource(first.getBody(), streamTypeService, true);
        if (inputEventType != null) {
            super.setTypeInfo(ExprDotEvalTypeInfo.eventColl(inputEventType));
        }
        else {
            super.setTypeInfo(ExprDotEvalTypeInfo.componentColl(collectionComponentType));
        }

        if (enumSrc.getFirst() == null) {
            String message = "Enumeration method '" + enumMethodUsedName + "' requires an expression yielding an event-collection as input paramater";
            throw new ExprValidationException(message);
        }

        EventType setType = enumSrc.getFirst().getEventTypeCollection();
        if (setType != inputEventType) {
            boolean isSubtype = EventTypeUtility.isTypeOrSubTypeOf(setType, inputEventType);
            if (!isSubtype) {
                String message = "Enumeration method '" + enumMethodUsedName + "' expects event type '" + inputEventType.getName() + "' but receives event type '" + enumSrc.getFirst().getEventTypeCollection().getName() + "'";
                throw new ExprValidationException(message);
            }
        }

        return new EnumEvalUnion(numStreamsIncoming, enumSrc.getFirst(), inputEventType == null);
    }
}
