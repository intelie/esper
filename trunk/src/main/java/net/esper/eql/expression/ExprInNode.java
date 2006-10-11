package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.AutoImportService;
import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import net.esper.util.CoercionException;
import net.esper.collection.UniformPair;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * Represents the in-clause (set check) function in an expression tree.
 */
public class ExprInNode extends ExprNode
{
    private Class coercionType;
    private boolean mustCoerce;

    public ExprInNode()
    {
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService) throws ExprValidationException
    {
        if (this.getChildNodes().size() < 2)
        {
            throw new ExprValidationException("The IN operator requires at least 2 child expressions");
        }

        List<Class> comparedTypes = new LinkedList<Class>();
        for (int i = 0; i < this.getChildNodes().size(); i++)
        {
            comparedTypes.add(this.getChildNodes().get(i).getType());
        }

        // Determine common denominator type
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

    public Class getType()
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream)
    {
        // Evaluate first child which is the base value to compare to
        Iterator<ExprNode> it = this.getChildNodes().iterator();
        Object inPropResult = it.next().evaluate(eventsPerStream);

        boolean matched = false;
        do
        {
            ExprNode inSetValueExpr = it.next();
            Object subExprResult = inSetValueExpr.evaluate(eventsPerStream);

            if (compare(inPropResult, subExprResult)) {
                matched = true;
                break;
            }
        }
        while (it.hasNext());

        return matched;
    }

    public boolean equalsNode(ExprNode node_)
    {
        if (!(node_ instanceof ExprInNode))
        {
            return false;
        }

        return true;
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();
        String delimiter = "";

        Iterator<ExprNode> it = this.getChildNodes().iterator();
        buffer.append(it.next().toExpressionString());
        buffer.append(" in (");

        do
        {
            ExprNode inSetValueExpr = it.next();
            buffer.append(delimiter);
            buffer.append(inSetValueExpr.toExpressionString());
            delimiter = ",";
        }
        while (it.hasNext());

        buffer.append(")");
        return buffer.toString();
    }

    private boolean compare(Object leftResult, Object rightResult)
    {
        if (leftResult == null)
        {
            return (rightResult == null);
        }
        if (rightResult == null)
        {
            return (leftResult == null);
        }

        if (!mustCoerce)
        {
            return leftResult.equals(rightResult);
        }
        else
        {
            Number left = JavaClassHelper.coerceNumber((Number) leftResult, coercionType);
            Number right = JavaClassHelper.coerceNumber((Number) rightResult, coercionType);
            return left.equals(right);
        }
    }
}
