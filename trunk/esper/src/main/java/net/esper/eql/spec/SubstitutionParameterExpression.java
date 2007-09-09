package net.esper.eql.spec;

import net.esper.client.soda.ExpressionBase;
import net.esper.client.soda.ConstantExpression;
import net.esper.core.EPStatementObjectModelHelper;

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
     * @param index is the index of the substitution parameter
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
            EPStatementObjectModelHelper.renderEQL(writer, constant);
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

    /**
     * Returns true if the parameter is satisfied, or false if not.
     * @return true if the actual value is supplied, false if not
     */
    public boolean isSatisfied()
    {
        return isSatisfied;
    }

    /**
     * Returns the index of the parameter.
     * @return parameter index.
     */
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