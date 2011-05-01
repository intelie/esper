package com.espertech.esper.epl.enummethod.eval;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Iterator;

public class EnumEvalSequenceEqual extends EnumEvalBase implements EnumEval {

    private static final Log log = LogFactory.getLog(EnumEvalSequenceEqual.class);

    public EnumEvalSequenceEqual(ExprEvaluator innerExpression, int streamCountIncoming) {
        super(innerExpression, streamCountIncoming);
    }

    public Object evaluateEnumMethod(Collection target, boolean isNewData, ExprEvaluatorContext context) {
        Object otherObj = this.getInnerExpression().evaluate(eventsLambda, isNewData, context);

        if (otherObj == null) {
            return false;
        }
        if (!(otherObj instanceof Collection)) {
            log.warn("Enumeration method 'sequenceEqual' expected a Collection-type return value from its parameter but received '" + otherObj.getClass() + "'");
            return false;
        }

        Collection other = (Collection) otherObj;
        if (target.size() != other.size()) {
            return false;
        }

        if (target.isEmpty()) {
            return true;
        }

        Iterator oneit = target.iterator();
        Iterator twoit = other.iterator();
        for (int i = 0; i < target.size(); i++) {
            Object first = oneit.next();
            Object second = twoit.next();

            if (first == null) {
                if (second != null) {
                    return false;
                }
                continue;
            }
            if (second == null) {
                return false;
            }

            if (!first.equals(second)) {
                return false;
            }
        }

        return true;
    }
}
