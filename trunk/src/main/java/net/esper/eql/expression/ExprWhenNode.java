package net.esper.eql.expression;

import net.esper.util.JavaClassHelper;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represents an equals (=) comparator in a filter expressiun tree.
 */
public class ExprWhenNode extends ExprNode
{

    private ExprNode _logExprNode;
    private ExprNode _valExprNode;

    public void validate(StreamTypeService streamTypeService_) throws ExprValidationException
    {
        // Must have 2 child nodes
        if (getChildNodes().size() != 2)
        {
            throw new ExprValidationException("when node does not have exactly 2 child nodes");
        }

        setWhenNodes();
        // The first node is a logical expression and should return a boolean
        if (_logExprNode.getType() != Boolean.class)
        {
            throw new ExprValidationException("datatype '" + _logExprNode.getClass().getName()+
                    "should be a logical expression");
        }
    }

    public Class getType()
    {
        setWhenNodes();
        try {
            if (_valExprNode != null)
            {
                return _valExprNode.getType();
            }
            else
            {
                return null;
            }
        } catch (ExprValidationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object evaluate(EventBean[] eventsPerStream_)
    {
        Object leftResult = null;
        Object rightResult = null;

        setWhenNodes();
        if (_logExprNode != null)
        {
            leftResult  = _logExprNode.evaluate(eventsPerStream_);
        }
        else
        {
            return null;
        }
        if (!(leftResult instanceof Boolean))
        {
            return null;
        }

        if (_valExprNode != null)
        {
            rightResult  = _valExprNode.evaluate(eventsPerStream_);
        }
        else
        {
            return null;
        }

        if (((Boolean) leftResult) == true)
        {
            return rightResult;
        }
        else
        {
            return null;
        }
    }

    public String toExpressionString()
    {
        setWhenNodes();
        StringBuffer buffer = new StringBuffer();
        buffer.append(" when ");
        buffer.append(_logExprNode.toExpressionString());
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

        setWhenNodes();
        ExprWhenNode other = (ExprWhenNode) node_;
        ExprNode otherLogExpr = other.getChildNodes().get(0);
        ExprNode otherValExpr = other.getChildNodes().get(1);

        if ((!(_logExprNode.equalsNode(otherLogExpr))) || (!(_valExprNode.equalsNode(otherValExpr))))
        {
            return false;
        }

        return true;
    }

    private void setWhenNodes()
    {
        if (getChildNodes() != null)
        {
            _logExprNode = getChildNodes().get(0);
            _valExprNode = getChildNodes().get(1);
        }
    }

    public ExprNode getLogExprNode()
    {
        _logExprNode = getChildNodes().get(0);
        return _logExprNode;
    }

    public void setLogExprNode(ExprNode node_)
    {
        getChildNodes().set(0,node_);
        _logExprNode=node_;
    }

    public ExprNode getValExprNode()
    {
        _valExprNode = getChildNodes().get(1);                
        return _valExprNode;
    }

    public void setValExprNode(ExprNode node_)
    {
        getChildNodes().set(1,node_);
        _valExprNode=node_;
    }

    private static final Log log = LogFactory.getLog(ExprWhenNode.class);
}
