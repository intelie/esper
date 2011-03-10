package com.espertech.esper.support.epl;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;

public class SupportExprNodeUtil
{
    public static void validate(ExprNode node) throws ExprValidationException{
        node.validate(null, null, null, null, null, null, null);
    }
}
