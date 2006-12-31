package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.AutoImportService;
import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * Represents the in-clause (set check) function in an expression tree.
 */
public class ExprInSetNode extends ExprInNode
{
    /**
     * Ctor.
     * @param isNotIn is true for "not in" and false for "in"
     */
    public ExprInSetNode(boolean isNotIn)
    {
        super(isNotIn);
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService) throws ExprValidationException
    {
        if (this.getChildNodes().size() < 2)
        {
            throw new ExprValidationException("The IN operator used with a set requires at least 2 child expressions");
        }

        List<Class> comparedTypes = new LinkedList<Class>();
        for (int i = 0; i < this.getChildNodes().size(); i++)
        {
            comparedTypes.add(this.getChildNodes().get(i).getType());
        }

        determineCommonDenominatorType(comparedTypes);
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

        return applyIsNotIn(matched);
    }

	public boolean equalsNode(ExprNode node_)
    {
        if (!(node_ instanceof ExprInSetNode))
        {
            return false;
        }

        ExprInNode other = (ExprInNode) node_;
        return other.isNotIn == this.isNotIn;
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();
        String delimiter = "";

        Iterator<ExprNode> it = this.getChildNodes().iterator();
        buffer.append(it.next().toExpressionString());
        if (isNotIn)
        {
            buffer.append(" not in (");
        }
        else
        {
            buffer.append(" in (");
        }

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
