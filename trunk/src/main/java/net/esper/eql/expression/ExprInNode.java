package net.esper.eql.expression;

import java.util.List;

import net.esper.util.CoercionException;
import net.esper.util.JavaClassHelper;

public abstract class ExprInNode extends ExprNode
{
	protected final boolean isNotIn;
	protected Class coercionType;
	protected boolean mustCoerce;

    /**
     * Ctor.
     * @param isNotIn is true for "not in" and false for "in"
     */
	public ExprInNode(boolean isNotIn)
	{
		this.isNotIn = isNotIn;
	}

	public Class getType()
	{
	    return Boolean.class;
	}

	protected void determineCommonDenominatorType(List<Class> comparedTypes) throws ExprValidationException
	{
		try {
	        coercionType = JavaClassHelper.getCommonCoercionType(comparedTypes.toArray(new Class[0]));
	
	        // Determine if we need to coerce numbers when one type doesn't match any other type
	        if (JavaClassHelper.isNumeric(coercionType))
	        {
	            mustCoerce = false;
	            for (Class comparedType : comparedTypes)
	            {
	                if (comparedType != coercionType)
	                {
	                    mustCoerce = true;
	                }
	            }
	        }
	    }
	    catch (CoercionException ex)
	    {
	        throw new ExprValidationException("Implicit conversion not allowed: " + ex.getMessage());
	    }
	}

	protected boolean applyIsNotIn(boolean matched)
	{
		if (isNotIn)
	    {
	        return !matched;
	    }
	    return matched;
	}

}