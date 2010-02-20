package com.espertech.esper.epl.spec;

import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.MetaDefItem;

import java.io.Serializable;

/**
 * Specification for a "define" construct within a match_recognize.
 */
public class MatchRecognizeDefineItem implements MetaDefItem, Serializable
{
    private String identifier;
    private ExprNode expression;
    private static final long serialVersionUID = -7736241770279336651L;

    /**
     * Ctor.
     * @param identifier variable name
     * @param expression expression
     */
    public MatchRecognizeDefineItem(String identifier, ExprNode expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    /**
     * Returns the variable name.
     * @return name
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the expression.
     * @return expression
     */
    public ExprNode getExpression() {
        return expression;
    }

    /**
     * Sets the validated expression
     * @param validated to set
     */
    public void setExpression(ExprNode validated)
    {
        this.expression = validated;
    }
}
