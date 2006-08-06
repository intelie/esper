package net.esper.eql.expression;

import net.esper.event.EventBean;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Aug 3, 2006
 * Time: 8:23:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExprElseNode extends ExprNode
{
    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" else ");
        buffer.append(getChildNodes().get(0).toExpressionString());
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node_)
    {
        if (!(node_ instanceof ExprElseNode))
        {
            return false;
        }
        ExprNode valExpr = getChildNodes().get(0);
        ExprElseNode other = (ExprElseNode) node_;
        ExprNode otherValExpr = other.getChildNodes().get(0);

        if (!(valExpr.equalsNode(otherValExpr)))
        {
            return false;
        }

        return true;
    }

    public void validate(StreamTypeService streamTypeService_) throws ExprValidationException
    {
        // Must have 1 child node
        if (getChildNodes().size() != 1)
        {
            throw new ExprValidationException("else node must have exactly 1 child node");
        }
    }

    public Class getType()
    {
        try {
            return getChildNodes().get(0).getType();
        } catch (ExprValidationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object evaluate(EventBean[] eventsPerStream_)
    {
        Object result = getChildNodes().get(0).evaluate(eventsPerStream_);
        return result;
    }

}
