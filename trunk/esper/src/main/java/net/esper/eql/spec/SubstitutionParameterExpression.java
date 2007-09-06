package net.esper.eql.spec;

import net.esper.client.soda.ExpressionBase;
import net.esper.client.soda.ConstantExpression;

import java.io.StringWriter;

/**
 * Substitution parameter that represents a node in an expression tree for which to supply a parameter value
 * before statement creation time. 
 */
public class SubstitutionParameterExpression extends ExpressionBase
{
    private final int index;
    private Object constant;
    private boolean isSatisfied;

    /**
     * Ctor.
     */
    public SubstitutionParameterExpression(int index)
    {
        this.index = index;
    }

    public void toEQL(StringWriter writer)
    {
        if (!isSatisfied)
        {
            writer.write("?");
        }
        else
        {
            ConstantExpression.renderEQL(writer, constant);
        }
    }

    /**
     * Returns the constant value that the expression represents.
     * @return value of constant
     */
    public Object getConstant()
    {
        return constant;
    }

    public boolean isSatisfied()
    {
        return isSatisfied;
    }

    public int getIndex()
    {
        return index;
    }

    /**
     * Sets the constant value that the expression represents.
     * @param constant is the value, or null to indicate the null value
     */
    public void setConstant(Object constant)
    {
        this.constant = constant;
        isSatisfied = true;
    }
}
