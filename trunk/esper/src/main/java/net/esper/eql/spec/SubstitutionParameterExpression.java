package net.esper.eql.spec;

import net.esper.client.soda.ExpressionBase;

import java.io.StringWriter;

/**
 * Substitution parameter that represents a node in an expression tree for which to supply a parameter value
 * before statement creation time. 
 */
public class SubstitutionParameterExpression extends ExpressionBase
{
    private Object constant;
    private boolean isSatisfied;

    /**
     * Ctor.
     */
    public SubstitutionParameterExpression()
    {
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("?");
    }

    /**
     * Returns the constant value that the expression represents.
     * @return value of constant
     */
    public Object getConstant()
    {
        return constant;
    }

    /**
     * Sets the constant value that the expression represents.
     * @param constant is the value, or null to indicate the null value
     */
    public void setConstant(Object constant)
    {
        this.constant = constant;
    }
}
