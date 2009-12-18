package com.espertech.esper.core;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPException;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.spec.PatternStreamSpecRaw;

/**
 * Administrative SPI.
 */
public interface EPAdministratorSPI extends EPAdministrator
{
    /**
     * Compile expression.
     * @param expression to compile
     * @return compiled expression
     * @throws EPException if compile failed
     */
    public ExprNode compileExpression(String expression) throws EPException;

    /**
     * Compile pattern.
     * @param expression to compile
     * @return compiled expression
     * @throws EPException if compile failed
     */
    public PatternStreamSpecRaw compilePatternToNode(String expression) throws EPException;
}
