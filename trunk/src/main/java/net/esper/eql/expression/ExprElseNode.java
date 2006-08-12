package net.esper.eql.expression;

import net.esper.event.EventBean;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Aug 3, 2006
 * Time: 8:23:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExprElseNode extends ValueExprNode
{
    public ExprElseNode()
    {
        super();
    }

    public ExprElseNode(ExprNode node_)
    {
        super(node_);
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" else ");
        buffer.append(_node.toExpressionString());
        return buffer.toString();
    }
}
