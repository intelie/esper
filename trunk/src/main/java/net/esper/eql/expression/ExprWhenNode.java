package net.esper.eql.expression;

import net.esper.util.JavaClassHelper;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.LinkedList;

/**
 * Represents an equals (=) comparator in a filter expressiun tree.
 */
public class ExprWhenNode extends ExprNode
{
    private ValueExprNode _evalExprNode;
    private ValueExprNode _valExprNode;

    public ExprWhenNode()
    {
    }

    public ExprWhenNode(ExprNode evalExprNode_, ExprNode valExprNode_)
    {
        _evalExprNode =  new ValueExprNode(evalExprNode_);
        _valExprNode =  new ValueExprNode(valExprNode_);
    }

    public void validate(StreamTypeService streamTypeService_) throws ExprValidationException
    {
        // Must have 2 child nodes
        if (getChildNodes().size() != 2)
        {
            throw new ExprValidationException("when node does not have exactly 2 child nodes");
        }

        setWhenNodes();
        _evalExprNode.validate(streamTypeService_);
        _valExprNode.validate(streamTypeService_);
    }

    public Class getType()
    {
        if (_valExprNode != null)
        {
            return _valExprNode.getType();
        }
        else
        {
            return null;
        }
    }

    public Object evaluate(EventBean[] eventsPerStream_)
    {
        if (_valExprNode != null)
        {
            return _valExprNode.evaluate(eventsPerStream_);
        }
        return null;
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" when ");
        buffer.append(_evalExprNode.toExpressionString());
        buffer.append(" then ");
        buffer.append(_valExprNode.toExpressionString());
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node_)
    {
        if (!(node_ instanceof ExprWhenNode))
        {
            return false;
        }

        ExprWhenNode other = (ExprWhenNode) node_;
        if ((!(_evalExprNode.equalsNode(other._evalExprNode))) || (!(_valExprNode.equalsNode(other._valExprNode))))
        {
            return false;
        }

        return true;
    }

    private void setWhenNodes()
    {
        if (getChildNodes() != null)
        {
            _evalExprNode = new ValueExprNode(getChildNodes().get(0));
            _evalExprNode.addChildNode(getChildNodes().get(0));
            _valExprNode = new ValueExprNode(getChildNodes().get(1));
            _valExprNode.addChildNode(getChildNodes().get(1));
        }
    }

    public ValueExprNode getEvalExprNode()
    {
        return _evalExprNode;
    }

    public void setEvalExprNode(ExprNode node_)
    {
        _evalExprNode.setExprNode(node_);
    }

    public ValueExprNode getValExprNode()
    {
        return _valExprNode;
    }

    public void setValExprNode(ExprNode node_)
    {
        _valExprNode.setExprNode(node_);
    }

}
