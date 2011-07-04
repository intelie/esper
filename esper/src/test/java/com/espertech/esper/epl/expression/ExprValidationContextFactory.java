package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.StreamTypeService;

public class ExprValidationContextFactory {
    public static ExprValidationContext makeEmpty() {
        return new ExprValidationContext(null, null, null, null, null, null, null, null, null, null);
    }

    public static ExprValidationContext make(StreamTypeService streamTypeService) {
        return new ExprValidationContext(streamTypeService, null, null, null, null, null, null, null, null, null);
    }
}
