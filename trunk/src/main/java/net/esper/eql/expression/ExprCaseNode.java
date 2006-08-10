package net.esper.eql.expression;

import net.esper.util.JavaClassHelper;
import net.esper.event.EventBean;

import java.util.List;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Jul 29, 2006
 * Time: 1:34:00 PM
 * To change this template use File | Settings | File Templates.
 */


public class ExprCaseNode extends ExprNode
{

    private boolean _inCase2=false;
    private List<ExprNode> _exprNodeList;

    public ExprCaseNode(boolean caseFlag_, List<ExprNode> exprNodeList_)
    {
        _inCase2 = caseFlag_;
        _exprNodeList = exprNodeList_;
    }

    public String toExpressionString()
     {
         StringBuffer buffer = new StringBuffer();
         buffer.append(" case");
         for (ExprNode node : _exprNodeList) {
             buffer.append(node.toExpressionString());
         }
         buffer.append(" end");
         return buffer.toString();
     }

    public boolean equalsNode(ExprNode node_)
    {
        if (!(node_ instanceof ExprCaseNode))
        {
            return false;
        }
        ExprCaseNode otherExprCaseNode = (ExprCaseNode) node_;
        List<ExprNode> otherExprNodeList = otherExprCaseNode._exprNodeList;
        if ((_exprNodeList.size()) !=  otherExprNodeList.size())
        {
            return false;
        }
        ExprNode[] t1 = _exprNodeList.toArray(new ExprNode[0]);
        ExprNode[] t2 = otherExprNodeList.toArray(new ExprNode[0]);
        boolean nodeEquals = true;
        int i =0;
        do
        {
            if (!(t1[i].equalsNode(t2[i])))
            {
                nodeEquals  = false;
            }
        }  while((nodeEquals) && (++i<t1.length));

        if (!nodeEquals)
        {
            return false;
        }

        return true;
    }

   public void validate(StreamTypeService streamTypeService_) throws ExprValidationException
    {
        if (_exprNodeList == null)
        {
            throw new ExprValidationException("The Case node requires at least one child expression");
        }

        if (_exprNodeList.size() == 0)
        {
            throw new ExprValidationException("The Case node requires at least one child expression");
        }

        // Sub-nodes must be at least one when expression
        boolean noWhenExpr = true;
        for (Iterator<ExprNode> it=_exprNodeList.iterator(); it.hasNext();)
        {
            ExprNode node = it.next();
            if (node instanceof ExprWhenNode)
            {
                noWhenExpr = false;
            }
            else if (!(node instanceof ExprElseNode))
            {
                throw new ExprValidationException("The Case node requires a when or else expression");
            }
        }
        if (noWhenExpr)
        {
            throw new ExprValidationException("The Case node requires at least one when expression");
        }
        for (Iterator<ExprNode> it=_exprNodeList.iterator(); it.hasNext();)
        {
            ExprNode node = it.next();
            node.validate(streamTypeService_);
        }
    }

    public Class getType()
    {
        return getType(null);
    }

    public Class getType(EventBean[] eventsPerStream_)
    {
        ExprElseNode elseNode = null;

        for (Iterator<ExprNode> it=_exprNodeList.iterator(); it.hasNext();)
        {
            ExprNode node = it.next();
            if (node instanceof ExprWhenNode)
            {
                if ((node.evaluate(eventsPerStream_)) != null)
                {
                    try {
                        return node.getType();
                    } catch (ExprValidationException e) {
                        e.printStackTrace();  
                    }
                }
            }
            else if(node instanceof ExprElseNode)
            {
                elseNode = (ExprElseNode) node;
            }
        }
        if (elseNode != null)
        {
            return elseNode.getType();
        }
        return null;
    }

    public Object evaluate(EventBean[] eventsPerStream_)
    {
        ExprElseNode elseNode = null;

        for (ExprNode node : _exprNodeList) {
            if (node instanceof ExprWhenNode) {
                Object result = node.evaluate(eventsPerStream_);
                if (result != null) {
                    return result;
                }
            } else if (node instanceof ExprElseNode) {
                elseNode = (ExprElseNode) node;
            }
        }
        if (elseNode != null)
        {
            return elseNode.evaluate(eventsPerStream_);
        }
        return null;
    }

     public List<ExprNode> getExprNodeList()
     {
         return _exprNodeList;
     }

    public ExprNode getExprNodeList(int ndx_)
    {
        if (_exprNodeList == null)
        {
            return null;
        }
        if (ndx_ > (_exprNodeList.size()) )
        {
            return null;
        }
        return (ExprNode) _exprNodeList.get(ndx_);
    }

    public boolean setExprNodeList(int ndx_, ExprNode node_)
    {
        if (_exprNodeList == null)
        {
            return false;
        }
        if (ndx_ > (_exprNodeList.size()) )
        {
            return false;
        }
       _exprNodeList.set(ndx_, node_);
        return true;
    }

    public void clearExprNodeList()
    {
        _exprNodeList.clear();
    }
}

