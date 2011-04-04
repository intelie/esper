package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.expression.ExprDotEval;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationContext;
import com.espertech.esper.epl.expression.ExprValidationException;

import java.util.List;

public interface ExprDotEvalEnumMethod extends ExprDotEval {

    public void init(EnumMethodEnum lambda,
                     String lambdaUsedName,
                     ExprDotEvalTypeInfo currentInputType,
                     List<ExprNode> parameters,
                     ExprValidationContext validationContext) throws ExprValidationException;
}
