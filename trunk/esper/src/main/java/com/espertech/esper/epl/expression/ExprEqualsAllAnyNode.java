package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.util.CoercionException;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.SimpleNumberCoercerFactory;
import com.espertech.esper.util.SimpleNumberCoercer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents an equals-for-group (= ANY/ALL/SOME (expression list)) comparator in a expression tree.
 */
public class ExprEqualsAllAnyNode extends ExprNode
{
    private final boolean isNot;
    private final boolean isAll;

    private boolean mustCoerce;
    private SimpleNumberCoercer coercer;
    private boolean hasCollectionOrArray;

    // TODO: render correctly and equals
    
    /**
     * Ctor.
     * @param isNotEquals - true if this is a (!=) not equals rather then equals, false if its a '=' equals
     */
    public ExprEqualsAllAnyNode(boolean isNotEquals, boolean isAll)
    {
        this.isNot = isNotEquals;
        this.isAll = isAll;
    }

    /**
     * Returns true if this is a NOT EQUALS node, false if this is a EQUALS node.
     * @return true for !=, false for =
     */
    public boolean isNot()
    {
        return isNot;
    }

    public boolean isAll()
    {
        return isAll;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        // Must have 2 child nodes
        if (this.getChildNodes().size() < 1)
        {
            throw new IllegalStateException("Equals group node does not have 1 or more child nodes");
        }

        // Must be the same boxed type returned by expressions under this
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

        // Check if we need to coerce
        mustCoerce = false;
        if (JavaClassHelper.isNumeric(coercionType))
        {
            for (Class compareType : comparedTypes)
            {
                if (coercionType != JavaClassHelper.getBoxedType(compareType))
                {
                    mustCoerce = true;
                }
            }
            coercer = SimpleNumberCoercerFactory.getCoercer(null, JavaClassHelper.getBoxedType(coercionType));
        }

    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        if (hasCollectionOrArray)
        {
            if (isAll)
            {
                return compareAllColl(eventsPerStream, isNewData);
            }
            else
            {
                return compareAnyColl(eventsPerStream, isNewData);
            }
        }
        else
        {
            if (isAll)
            {
                return compareAll(eventsPerStream, isNewData);
            }
            else
            {
                return compareAny(eventsPerStream, isNewData);
            }
        }
    }

    private Object compareAll(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object leftResult = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
        if (mustCoerce)
        {
            leftResult = coercer.coerceBoxed()
        }


        if (this.getChildNodes().size() == 1)
        {
            return true;
        }

        if (isNot)
        {
            int len = this.getChildNodes().size() - 1;
            for (int i = 1; i <= len; i++)
            {
                Object rightResult = this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        continue;
                    }
                    return false;
                }
                if (rightResult == null)
                {
                    continue;
                }

                if (!mustCoerce)
                {
                    if (leftResult.equals(rightResult))
                    {
                        return false;
                    }
                }
                else
                {
                    Number left = coercer.JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                    Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                    if (left.equals(right))
                    {
                        return false;
                    }
                }
            }

            return true;
        }
        else
        {
            int len = this.getChildNodes().size() - 1;
            for (int i = 1; i <= len; i++)
            {
                Object rightResult = this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        continue;
                    }
                    return false;
                }
                if (rightResult == null)
                {
                    continue;
                }

                if (!mustCoerce)
                {
                    if (!leftResult.equals(rightResult))
                    {
                        return false;
                    }
                }
                else
                {
                    Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                    Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                    if (!left.equals(right))
                    {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    private Object compareAllColl(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object leftResult = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);

        if (this.getChildNodes().size() == 1)
        {
            return true;
        }

        if (isNot)
        {
            int len = this.getChildNodes().size() - 1;
            for (int i = 1; i <= len; i++)
            {
                Object rightResult = this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        continue;
                    }
                    return false;
                }
                if (rightResult == null)
                {
                    continue;
                }

                if (rightResult instanceof Collection)
                {
                    Collection coll = (Collection) rightResult;
                    for (Object item : coll)
                    {
                        if (leftResult.equals(item))
                        {
                            return false;
                        }
                    }
                }
                else if (rightResult.getClass().isArray())
                {
                    int arrayLength = Array.getLength(rightResult);
                    for (int index = 0; index < arrayLength; index++)
                    {
                        Object item = Array.get(rightResult, index);
                        if (leftResult.equals(item))
                        {
                            return false;
                        }
                    }
                }
                else
                {
                    if (!mustCoerce)
                    {
                        if (leftResult.equals(rightResult))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                        Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                        if (left.equals(right))
                        {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
        else
        {
            int len = this.getChildNodes().size() - 1;
            for (int i = 1; i <= len; i++)
            {
                Object rightResult = this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        continue;
                    }
                    return false;
                }
                if (rightResult == null)
                {
                    continue;
                }

                if (rightResult instanceof Collection)
                {
                    Collection coll = (Collection) rightResult;
                    for (Object item : coll)
                    {
                        if (!leftResult.equals(item))
                        {
                            return false;
                        }
                    }
                }
                else if (rightResult.getClass().isArray())
                {
                    int arrayLength = Array.getLength(rightResult);
                    for (int index = 0; index < arrayLength; index++)
                    {
                        Object item = Array.get(rightResult, index);
                        if (!leftResult.equals(item))
                        {
                            return false;
                        }
                    }
                }
                else
                {
                    if (!mustCoerce)
                    {
                        if (!leftResult.equals(rightResult))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                        Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                        if (!left.equals(right))
                        {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private Object compareAny(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object leftResult = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);

        if (this.getChildNodes().size() == 1)
        {
            if (leftResult == null)
            {
                return !isNot;
            }
            return isNot;
        }

        // Return true on the first not-equal.
        if (isNot)
        {
            int len = this.getChildNodes().size() - 1;
            for (int i = 1; i <= len; i++)
            {
                Object rightResult = this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        return false;
                    }
                    continue;
                }
                if (rightResult == null)
                {
                    continue;
                }

                if (!mustCoerce)
                {
                    if (!leftResult.equals(rightResult))
                    {
                        return true;
                    }
                }
                else
                {
                    Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                    Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                    if (!left.equals(right))
                    {
                        return true;
                    }
                }
            }

            return false;
        }
        // Return true on the first equal.
        else
        {
            int len = this.getChildNodes().size() - 1;
            for (int i = 1; i <= len; i++)
            {
                Object rightResult = this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        return true;
                    }
                    continue;
                }
                if (rightResult == null)
                {
                    continue;
                }

                if (!mustCoerce)
                {
                    if (leftResult.equals(rightResult))
                    {
                        return true;
                    }
                }
                else
                {
                    Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                    Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                    if (left.equals(right))
                    {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    private Object compareAnyColl(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object leftResult = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);

        if (this.getChildNodes().size() == 1)
        {
            if (leftResult == null)
            {
                return !isNot;
            }
            return isNot;
        }

        // Return true on the first not-equal.
        if (isNot)
        {
            int len = this.getChildNodes().size() - 1;
            for (int i = 1; i <= len; i++)
            {
                Object rightResult = this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        return false;
                    }
                    continue;
                }
                if (rightResult == null)
                {
                    continue;
                }

                if (rightResult instanceof Collection)
                {
                    Collection coll = (Collection) rightResult;
                    for (Object item : coll)
                    {
                        if (!leftResult.equals(item))
                        {
                            return true;
                        }
                    }
                }
                else if (rightResult.getClass().isArray())
                {
                    int arrayLength = Array.getLength(rightResult);
                    for (int index = 0; index < arrayLength; index++)
                    {
                        Object item = Array.get(rightResult, index);
                        if (!leftResult.equals(item))
                        {
                            return true;
                        }
                    }
                }
                else
                {
                    if (!mustCoerce)
                    {
                        if (!leftResult.equals(rightResult))
                        {
                            return true;
                        }
                    }
                    else
                    {
                        Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                        Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                        if (!left.equals(right))
                        {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
        // Return true on the first equal.
        else
        {
            int len = this.getChildNodes().size() - 1;
            for (int i = 1; i <= len; i++)
            {
                Object rightResult = this.getChildNodes().get(i).evaluate(eventsPerStream, isNewData);

                if (leftResult == null)
                {
                    if (rightResult == null)
                    {
                        return true;
                    }
                    continue;
                }
                if (rightResult == null)
                {
                    continue;
                }

                if (rightResult instanceof Collection)
                {
                    Collection coll = (Collection) rightResult;
                    for (Object item : coll)
                    {
                        if (leftResult.equals(item))
                        {
                            return true;
                        }
                    }
                }
                else if (rightResult.getClass().isArray())
                {
                    int arrayLength = Array.getLength(rightResult);
                    for (int index = 0; index < arrayLength; index++)
                    {
                        Object item = Array.get(rightResult, index);
                        if (leftResult.equals(item))
                        {
                            return true;
                        }
                    }
                }
                else
                {
                    if (!mustCoerce)
                    {
                        if (leftResult.equals(rightResult))
                        {
                            return true;
                        }
                    }
                    else
                    {
                        Number left = JavaClassHelper.coerceBoxed((Number) leftResult, coercionType);
                        Number right = JavaClassHelper.coerceBoxed((Number) rightResult, coercionType);
                        if (left.equals(right))
                        {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();

        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(" = ");
        buffer.append(this.getChildNodes().get(1).toExpressionString());

        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprEqualsAllAnyNode))
        {
            return false;
        }

        ExprEqualsAllAnyNode other = (ExprEqualsAllAnyNode) node;
        return other.isNot == this.isNot;
    }
}
