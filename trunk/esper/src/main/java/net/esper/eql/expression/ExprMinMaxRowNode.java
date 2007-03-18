/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.expression;

import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import net.esper.type.MinMaxTypeEnum;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;

/**
 * Represents the MAX(a,b) and MIN(a,b) functions is an expression tree.
 */
public class ExprMinMaxRowNode extends ExprNode
{
    private MinMaxTypeEnum minMaxTypeEnum;
    private Class resultType;

    /**
     * Ctor.
     * @param minMaxTypeEnum - type of compare
     */
    public ExprMinMaxRowNode(MinMaxTypeEnum minMaxTypeEnum)
    {
        this.minMaxTypeEnum = minMaxTypeEnum;
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        if (this.getChildNodes().size() < 2)
        {
            throw new ExprValidationException("MinMax node must have at least 2 child nodes");
        }

        for (ExprNode child : this.getChildNodes())
        {
            Class childType = child.getType();
            if (!JavaClassHelper.isNumeric(childType))
            {
                throw new ExprValidationException("Implicit conversion from datatype '" +
                        childType.getSimpleName() +
                        "' to numeric is not allowed");
            }
        }

        // Determine result type, set up compute function
        Class childTypeOne = this.getChildNodes().get(0).getType();
        Class childTypeTwo = this.getChildNodes().get(1).getType();
        resultType = JavaClassHelper.getArithmaticCoercionType(childTypeOne, childTypeTwo);

        for (int i = 2; i < this.getChildNodes().size(); i++)
        {
            resultType = JavaClassHelper.getArithmaticCoercionType(resultType, this.getChildNodes().get(i).getType());
        }
    }

    public Class getType() throws ExprValidationException
    {
        return resultType;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        Number valueChildOne = (Number) this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
        Number valueChildTwo = (Number) this.getChildNodes().get(1).evaluate(eventsPerStream, isNewData);

        if ((valueChildOne == null) || (valueChildTwo == null))
        {
            return null;
        }

        Number result = null;
        if (minMaxTypeEnum == MinMaxTypeEnum.MAX)
        {
            if (valueChildOne.doubleValue() > valueChildTwo.doubleValue())
            {
                result = valueChildOne;
            }
            else
            {
                result = valueChildTwo;
            }

            for (int i = 2; i < this.getChildNodes().size(); i++)
            {
                Number valueChild = (Number) this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);
                if (valueChild == null)
                {
                    return null;
                }
                if (valueChild.doubleValue() > result.doubleValue())
                {
                    result = valueChild;
                }
            }
        }
        else
        {
            if (valueChildOne.doubleValue() > valueChildTwo.doubleValue())
            {
                result = valueChildTwo;
            }
            else
            {
                result = valueChildOne;
            }
            for (int i = 2; i < this.getChildNodes().size(); i++)
            {
                Number valueChild = (Number) this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);
                if (valueChild == null)
                {
                    return null;
                }
                if (valueChild.doubleValue() < result.doubleValue())
                {
                    result = valueChild;
                }
            }
        }

        return JavaClassHelper.coerceBoxed(result, resultType);
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append(minMaxTypeEnum.getExpressionText());
        buffer.append('(');

        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(',');
        buffer.append(this.getChildNodes().get(1).toExpressionString());

        for (int i = 2; i < this.getChildNodes().size(); i++)
        {
            buffer.append(',');
            buffer.append(this.getChildNodes().get(i).toExpressionString());
        }

        buffer.append(')');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprMinMaxRowNode))
        {
            return false;
        }

        ExprMinMaxRowNode other = (ExprMinMaxRowNode) node;

        if (other.minMaxTypeEnum != this.minMaxTypeEnum)
        {
            return false;
        }

        return true;
    }
}
