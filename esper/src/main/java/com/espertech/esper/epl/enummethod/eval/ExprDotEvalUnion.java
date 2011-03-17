package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalEnumMethodBase;
import com.espertech.esper.epl.enummethod.dot.ExprDotEvalParam;
import com.espertech.esper.epl.expression.ExprDotNode;
import com.espertech.esper.epl.expression.ExprEvaluatorEnumeration;
import com.espertech.esper.epl.expression.ExprValidationException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ExprDotEvalUnion extends ExprDotEvalEnumMethodBase {

    public EventType[] getAddStreamTypes(List<String> goesToNames, EventType inputEventType, List<ExprDotEvalParam> bodiesAndParameters) {
        return new EventType[] {};
    }

    public EnumEval getEnumEval(StreamTypeService streamTypeService, String enumMethodUsedName, List<ExprDotEvalParam> bodiesAndParameters, EventType inputEventType, int numStreamsIncoming) throws ExprValidationException {
        ExprDotEvalParam first = bodiesAndParameters.get(0);

        Pair<ExprEvaluatorEnumeration, EventType> enumSrc = ExprDotNode.getEnumerationSource(first.getBody(), streamTypeService);

        if (enumSrc.getFirst() == null) {
            String message = "Enumeration method '" + enumMethodUsedName + "' requires an expression yielding an event-collection as input paramater";
            throw new ExprValidationException(message);
        }

        EventType setType = enumSrc.getFirst().getEventTypeIterator();
        if (setType != inputEventType) {
            boolean isSubtype = false;
            if (setType.getSuperTypes() != null) {
                for (Iterator<EventType> it = setType.getDeepSuperTypes(); it.hasNext();) {
                    if (it.next() == inputEventType) {
                        isSubtype = true;
                    }
                }
            }

            if (!isSubtype) {
                String message = "Enumeration method '" + enumMethodUsedName + "' expects event type '" + inputEventType.getName() + "' but receives event type '" + enumSrc.getFirst().getEventTypeIterator().getName() + "'";
                throw new ExprValidationException(message);
            }
        }

        return new EnumEvalUnion(numStreamsIncoming, enumSrc.getFirst());
    }

    public Class getResultType() {
        return Collection.class;
    }

    public EventType getResultEventType() {
        return null;
    }
}
