package com.espertech.esper.core;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPException;
import com.espertech.esper.epl.expression.ExprNode;

public interface EPAdministratorSPI extends EPAdministrator
{
    public ExprNode compileExpression(String expression) throws EPException;
}
