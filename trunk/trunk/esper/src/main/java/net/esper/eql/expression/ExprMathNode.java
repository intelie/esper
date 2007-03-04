package net.esper.eql.expression;

import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import net.esper.type.MathArithTypeEnum;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;

/**
 * Represents a simple Math (+/-/divide/*) in a filter expression tree.
 */
public class ExprMathNode extends ExprNode
{
    private final MathArithTypeEnum mathArithTypeEnum;

    private MathArithTypeEnum.Computer arithTypeEnumComputer;
    private Class resultType;

    /**
     * Ctor.
     * @param mathArithTypeEnum - type of math
     */
    public ExprMathNode(MathArithTypeEnum mathArithTypeEnum)
    {
        this.mathArithTypeEnum = mathArithTypeEnum;
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 2)
        {
            throw new ExprValidationException("Arithmatic node must have 2 child nodes");
        }

        for (ExprNode child : this.getChildNodes())
        {
            Class childType = child.getType();
            if (!JavaClassHelper.isNumeric(childType))
            {
                throw new ExprValidationException("Implicit conversion from datatype '" +
                        childType.getName() +
                        "' to numeric is not allowed");
            }
        }

        // Determine result type, set up compute function
        Class childTypeOne = this.getChildNodes().get(0).getType();
        Class childTypeTwo = this.getChildNodes().get(1).getType();

        if (childTypeOne.equals(childTypeTwo))
        {
            resultType = JavaClassHelper.getBoxedType(childTypeTwo);
        }
        else
        {
            resultType = JavaClassHelper.getArithmaticCoercionType(childTypeOne, childTypeTwo);
        }
        arithTypeEnumComputer = mathArithTypeEnum.getComputer(resultType);
    }

    public Class getType() throws ExprValidationException
    {
        return resultType;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object valueChildOne = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
        Object valueChildTwo = this.getChildNodes().get(1).evaluate(eventsPerStream, isNewData);

        if ((valueChildOne == null) || (valueChildTwo == null))
        {
            return null;
        }

        // arithTypeEnumComputer is initialized by validation
        Object result = arithTypeEnumComputer.compute((Number) valueChildOne, (Number) valueChildTwo);
        return result;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append('(');

        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(mathArithTypeEnum.getExpressionText());
        buffer.append(this.getChildNodes().get(1).toExpressionString());

        buffer.append(')');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprMathNode))
        {
            return false;
        }

        ExprMathNode other = (ExprMathNode) node;

        if (other.mathArithTypeEnum != this.mathArithTypeEnum)
        {
            return false;
        }

        return true;
    }
}
