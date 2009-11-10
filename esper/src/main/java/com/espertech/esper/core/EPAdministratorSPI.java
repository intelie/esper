package com.espertech.esper.core;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPException;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.spec.PatternStreamSpecRaw;
import com.espertech.esper.pattern.EvalNode;

public interface EPAdministratorSPI extends EPAdministrator
{
    public ExprNode compileExpression(String expression) throws EPException;
    public PatternStreamSpecRaw compilePatternToNode(String expression) throws EPException;
}
