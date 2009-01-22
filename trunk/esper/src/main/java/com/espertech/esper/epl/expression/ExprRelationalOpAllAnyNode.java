package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.type.RelationalOpEnum;
import com.espertech.esper.util.CoercionException;
import com.espertech.esper.util.JavaClassHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents a lesser or greater then (</<=/>/>=) expression in a filter expression tree.
 */
public class ExprRelationalOpAllAnyNode extends ExprNode
{
    private final RelationalOpEnum relationalOpEnum;
    private final boolean isAll;
    private boolean hasCollectionOrArray;

    private RelationalOpEnum.Computer computer;

    /**
     * Ctor.
     * @param relationalOpEnum - type of compare, ie. lt, gt, le, ge
     */
    public ExprRelationalOpAllAnyNode(RelationalOpEnum relationalOpEnum, boolean isAll)
    {
        this.relationalOpEnum = relationalOpEnum;
        this.isAll = isAll;
    }

    public boolean isConstantResult()
    {
        return false;
    }

    /**
     * Returns the type of relational op used.
     * @return enum with relational op type
     */
    public RelationalOpEnum getRelationalOpEnum()
    {
        return relationalOpEnum;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        // Must have 2 child nodes
        if (this.getChildNodes().size() < 1)
        {
            throw new IllegalStateException("Group relational op node must have 1 or more child nodes");
        }

        Class typeOne = JavaClassHelper.getBoxedType(this.getChildNodes().get(0).getType());

        // collections, array or map not supported
        if ((typeOne.isArray()) || (JavaClassHelper.isImplementsInterface(typeOne, Collection.class)) || (JavaClassHelper.isImplementsInterface(typeOne, Map.class)))
        {
            throw new ExprValidationException("Collection or array comparison is not allowed for the IN, ANY, SOME or ALL keywords");
        }

        List<Class> comparedTypes = new ArrayList<Class>();
        comparedTypes.add(typeOne);
        hasCollectionOrArray = false;
        for (int i = 0; i < this.getChildNodes().size() - 1; i++)
        {
            Class propType = this.getChildNodes().get(i + 1).getType();
            if ((propType.isArray() || JavaClassHelper.isImplementsInterface(propType, Collection.class)))
            {
                hasCollectionOrArray = true;
            }
            else
            {
                comparedTypes.add(propType);
            }
        }

        // Determine common denominator type
        Class coercionType;
        try {
            coercionType = JavaClassHelper.getCommonCoercionType(comparedTypes.toArray(new Class[comparedTypes.size()]));
        }
        catch (CoercionException ex)
        {
            throw new ExprValidationException("Implicit conversion not allowed: " + ex.getMessage());
        }

        // Must be either numeric or string
        if (coercionType != String.class)
        {
            if (!JavaClassHelper.isNumeric(coercionType))
            {
                throw new ExprValidationException("Implicit conversion from datatype '" +
                        coercionType.getSimpleName() +
                        "' to numeric is not allowed");
            }
        }

        computer = relationalOpEnum.getComputer(coercionType, coercionType, coercionType);
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        if (this.getChildNodes().size() == 1)
        {
            return false;
        }

        Object valueLeft = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
        int len = this.getChildNodes().size() - 1;

        if (hasCollectionOrArray)
        {
            for (int i = 1; i <= len; i++)
            {
                Object valueRight = this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);

                if ((valueLeft == null) || (valueRight == null))
                {
                    return false;
                }

                if (valueRight instanceof Collection)
                {
                    Collection coll = (Collection) valueRight;
                    for (Object item : coll)
                    {
                        if (!(item instanceof Number))
                        {
                            continue;
                        }
                        if (isAll)
                        {
                            if (!computer.compare(valueLeft, item))
                            {
                                return false;
                            }
                        }
                        else
                        {
                            if (computer.compare(valueLeft, item))
                            {
                                return true;
                            }
                        }
                    }
                }
                else if (valueRight.getClass().isArray())
                {
                    int arrayLength = Array.getLength(valueRight);
                    for (int index = 0; index < arrayLength; index++)
                    {
                        Object item = Array.get(valueRight, index);
                        if (isAll)
                        {
                            if (!computer.compare(valueLeft, item))
                            {
                                return false;
                            }
                        }
                        else
                        {
                            if (computer.compare(valueLeft, item))
                            {
                                return true;
                            }
                        }
                    }
                }
                else if (!(valueRight instanceof Number))
                {
                    continue;
                }
                else
                {
                    if (isAll)
                    {
                        if (!computer.compare(valueLeft, valueRight))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        if (computer.compare(valueLeft, valueRight))
                        {
                            return true;
                        }
                    }
                }
            }

            return isAll;
        }
        else
        {
            for (int i = 1; i <= len; i++)
            {
                Object valueRight = this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);

                if ((valueLeft == null) || (valueRight == null))
                {
                    return false;
                }

                if (isAll)
                {
                    if (!computer.compare(valueLeft, valueRight))
                    {
                        return false;
                    }
                }
                else
                {
                    if (computer.compare(valueLeft, valueRight))
                    {
                        return true;
                    }
                }
            }

            return isAll;
        }
    }

    public String toExpressionString()
    {
        // TODO
        StringBuilder buffer = new StringBuilder();

        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(relationalOpEnum.getExpressionText());
        buffer.append(this.getChildNodes().get(1).toExpressionString());

        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        // TODO
        if (!(node instanceof ExprRelationalOpAllAnyNode))
        {
            return false;
        }

        ExprRelationalOpAllAnyNode other = (ExprRelationalOpAllAnyNode) node;

        if (other.relationalOpEnum != this.relationalOpEnum)
        {
            return false;
        }

        return true;
    }
}
