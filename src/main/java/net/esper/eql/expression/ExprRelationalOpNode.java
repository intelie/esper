package net.esper.eql.expression;

import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import net.esper.type.RelationalOpEnum;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;

/**
 * Represents a lesser or greater then (</<=/>/>=) expression in a filter expression tree.
 */
public class ExprRelationalOpNode extends ExprNode
{
    private final RelationalOpEnum relationalOpEnum;
    private RelationalOpEnum.Computer computer;

    /**
     * Ctor.
     * @param relationalOpEnum - type of compare, ie. lt, gt, le, ge
     */
    public ExprRelationalOpNode(RelationalOpEnum relationalOpEnum)
    {
        this.relationalOpEnum = relationalOpEnum;
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService) throws ExprValidationException
    {
        // Must have 2 child nodes
        if (this.getChildNodes().size() != 2)
        {
            throw new IllegalStateException("Relational op node does not have exactly 2 child nodes");
        }

        // Must be either numeric or string
        Class typeOne = JavaClassHelper.getBoxedType(this.getChildNodes().get(0).getType());
        Class typeTwo = JavaClassHelper.getBoxedType(this.getChildNodes().get(1).getType());

        if ((typeOne != String.class) || (typeTwo != String.class))
        {
            if (!JavaClassHelper.isNumeric(typeOne))
            {
                throw new ExprValidationException("Implicit conversion from datatype '" +
                        typeOne.getName() +
                        "' to numeric is not allowed");
            }
            if (!JavaClassHelper.isNumeric(typeTwo))
            {
                throw new ExprValidationException("Implicit conversion from datatype '" +
                        typeTwo.getName() +
                        "' to numeric is not allowed");
            }
        }

        Class compareType = JavaClassHelper.getCompareToCoercionType(typeOne, typeTwo);
        computer = relationalOpEnum.getComputer(compareType);
    }

    public Class getType() throws ExprValidationException
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream)
    {
        Object valueLeft = this.getChildNodes().get(0).evaluate(eventsPerStream);
        Object valueRight = this.getChildNodes().get(1).evaluate(eventsPerStream);

        if ((valueLeft == null) || (valueRight == null))
        {
            return false;
        }

        return computer.compare(valueLeft, valueRight);
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(relationalOpEnum.getExpressionText());
        buffer.append(this.getChildNodes().get(1).toExpressionString());

        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprRelationalOpNode))
        {
            return false;
        }

        ExprRelationalOpNode other = (ExprRelationalOpNode) node;

        if (other.relationalOpEnum != this.relationalOpEnum)
        {
            return false;
        }

        return true;
    }
}
