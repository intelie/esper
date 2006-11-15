package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.AutoImportService;
import net.esper.util.JavaClassHelper;
import net.esper.event.EventBean;

import java.util.Iterator;

/**
 * Represents the between-clause function in an expression tree.
 */
public class ExprBetweenNode extends ExprNode
{
    private final boolean isNotBetween;

    private boolean isAlwaysFalse;
    private ExprBetweenComp computer;

    /**
     * Ctor.
     * @param isNotBetween is true to indicate this is a "not between", or false for a "between"
     */
    public ExprBetweenNode(boolean isNotBetween)
    {
        this.isNotBetween = isNotBetween;
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 3)
        {
            throw new ExprValidationException("The Between operator requires exactly 3 child expressions");
        }

        // Must be either numeric or string
        Class typeOne = JavaClassHelper.getBoxedType(this.getChildNodes().get(0).getType());
        Class typeTwo = JavaClassHelper.getBoxedType(this.getChildNodes().get(1).getType());
        Class typeThree = JavaClassHelper.getBoxedType(this.getChildNodes().get(2).getType());

        if (typeOne == null)
        {
            throw new ExprValidationException("Null value not allowed in between-clause");
        }

        Class compareType = null;
        if ((typeTwo == null) || (typeThree == null))
        {
            isAlwaysFalse = true;
        }
        else {
            if ((typeOne != String.class) || (typeTwo != String.class) || (typeThree != String.class))
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
                if (!JavaClassHelper.isNumeric(typeThree))
                {
                    throw new ExprValidationException("Implicit conversion from datatype '" +
                            typeThree.getName() +
                            "' to numeric is not allowed");
                }
            }

            Class intermedType = JavaClassHelper.getCompareToCoercionType(typeOne, typeTwo);
            compareType = JavaClassHelper.getCompareToCoercionType(intermedType, typeThree);
            computer = makeComputer(compareType);
        }
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream)
    {
        if (isAlwaysFalse)
        {
            return false;
        }

        // Evaluate first child which is the base value to compare to
        Iterator<ExprNode> it = this.getChildNodes().iterator();
        Object value = it.next().evaluate(eventsPerStream);
        if (value == null)
        {
            return false;
        }
        Object lower = it.next().evaluate(eventsPerStream);
        Object higher = it.next().evaluate(eventsPerStream);

        boolean result = computer.isBetween(value, lower, higher);
        if (isNotBetween)
        {
            return !result;
        }
        return result;
    }

    public boolean equalsNode(ExprNode node_)
    {
        if (!(node_ instanceof ExprBetweenNode))
        {
            return false;
        }

        ExprBetweenNode other = (ExprBetweenNode) node_;
        return other.isNotBetween == this.isNotBetween;
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();

        Iterator<ExprNode> it = this.getChildNodes().iterator();
        buffer.append(it.next().toExpressionString());
        if (isNotBetween)
        {
            buffer.append(" not between ");
        }
        else
        {
            buffer.append(" between ");
        }

        buffer.append(it.next().toExpressionString());
        buffer.append(" and ");
        buffer.append(it.next().toExpressionString());

        return buffer.toString();
    }

    private ExprBetweenComp makeComputer(Class compareType)
    {
        ExprBetweenComp computer = null;

        if (compareType == String.class)
        {
            computer = new ExprBetweenCompString();
        }
        else if (compareType == Long.class)
        {
            computer = new ExprBetweenCompLong();
        }
        else
        {
            computer = new ExprBetweenCompDouble();
        }
        return computer;
    }

    private interface ExprBetweenComp
    {
        public boolean isBetween(Object value, Object lower, Object upper);
    }

    private class ExprBetweenCompString implements ExprBetweenComp
    {
        public boolean isBetween(Object value, Object lower, Object upper)
        {
            if ((value == null) || (lower == null) || ((upper == null)))
            {
                return false;
            }
            String valueStr = (String) value;
            String lowerStr = (String) lower;
            String upperStr = (String) upper;

            if (upperStr.compareTo(lowerStr) < 0)
            {
                if (valueStr.compareTo(lowerStr) > 0)
                {
                    return false;
                }
                if (valueStr.compareTo(upperStr) < 0)
                {
                    return false;
                }
            }
            else
            {
                if (valueStr.compareTo(lowerStr) < 0)
                {
                    return false;
                }
                if (valueStr.compareTo(upperStr) > 0)
                {
                    return false;
                }
            }
            return true;
        }
    }

    private class ExprBetweenCompDouble implements ExprBetweenComp
    {
        public boolean isBetween(Object value, Object lower, Object upper)
        {
            if ((value == null) || (lower == null) || ((upper == null)))
            {
                return false;
            }
            double valueD = ((Number) value).doubleValue();
            double lowerD = ((Number) lower).doubleValue();
            double upperD = ((Number) upper).doubleValue();

            if (lowerD > upperD)
            {
                if (valueD <= lowerD && valueD >= upperD)
                {
                    return true;
                }
                return false;
            }
            if (valueD >= lowerD && valueD <= upperD)
            {
                return true;
            }
            return false;
        }
    }

    private class ExprBetweenCompLong implements ExprBetweenComp
    {
        public boolean isBetween(Object value, Object lower, Object upper)
        {
            if ((value == null) || (lower == null) || ((upper == null)))
            {
                return false;
            }
            long valueD = ((Number) value).longValue();
            long lowerD = ((Number) lower).longValue();
            long upperD = ((Number) upper).longValue();

            if (lowerD > upperD)
            {
                if (valueD <= lowerD && valueD >= upperD)
                {
                    return true;
                }
                return false;
            }
            if (valueD >= lowerD && valueD <= upperD)
            {
                return true;
            }
            return false;
        }
    }
}
