package net.esper.eql.expression;

import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import net.esper.type.ArithTypeEnum;

/**
 * Represents a simple Math (+/-/divide/*) in a filter expression tree.
 */
public class ExprMathNode extends ExprNode
{
    private final ArithTypeEnum arithTypeEnum;

    private ArithTypeEnum.Computer arithTypeEnumComputer;
    private Class resultType;

    /**
     * Ctor.
     * @param arithTypeEnum - type of math
     */
    public ExprMathNode(ArithTypeEnum arithTypeEnum)
    {
        this.arithTypeEnum = arithTypeEnum;
    }

    public void validate(StreamTypeService streamTypeService) throws ExprValidationException
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
        arithTypeEnumComputer = arithTypeEnum.getComputer(resultType);
    }

    public Class getType() throws ExprValidationException
    {
        return resultType;
    }

    public Object evaluate(EventBean[] eventsPerStream)
    {
        Object valueChildOne = this.getChildNodes().get(0).evaluate(eventsPerStream);
        Object valueChildTwo = this.getChildNodes().get(1).evaluate(eventsPerStream);

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
        StringBuffer buffer = new StringBuffer();
        buffer.append("(");

        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(arithTypeEnum.getExpressionText());
        buffer.append(this.getChildNodes().get(1).toExpressionString());

        buffer.append(")");
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprMathNode))
        {
            return false;
        }

        ExprMathNode other = (ExprMathNode) node;

        if (other.arithTypeEnum != this.arithTypeEnum)
        {
            return false;
        }

        return true;
    }
}
