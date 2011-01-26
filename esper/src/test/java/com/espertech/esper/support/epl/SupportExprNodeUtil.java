package com.espertech.esper.support.epl;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;

public class SupportExprNodeUtil
{
    public static void validate(ExprNode node) throws ExprValidationException{
        node.validate(null, null, null, null, null, null);
    }
}
