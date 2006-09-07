package net.esper.eql.expression;

import net.esper.util.JavaClassHelper;
import net.esper.event.EventBean;
import net.esper.collection.Pair;

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

    private boolean _inCase2;
    private List<Pair<ExprNode,ExprNode>> _exprNodeList;

    public ExprCaseNode(boolean caseFlag_, List<Pair<ExprNode,ExprNode>> exprNodeList_)
    {
        _inCase2 = caseFlag_;
        _exprNodeList = exprNodeList_;
    }


    public String toExpressionString()
     {
         StringBuffer buffer = new StringBuffer();
         buffer.append(" case ");
         if (_inCase2)
         {
            buffer.append(this.getChildNodes().getFirst().toExpressionString());
         }
         for (Pair<ExprNode,ExprNode> p : _exprNodeList)
         {
             if (p.getFirst() == null)
             {
                buffer.append(" else ");
             }
             else
             {
                buffer.append(" when ");
                buffer.append(p.getFirst().toExpressionString());
                buffer.append(" then ");
             }
             buffer.append(p.getSecond().toExpressionString());
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
        if ((!getChildNodes().isEmpty()) && (getChildNodes().getFirst() != null))
        {
            ExprNode valueExpr = getChildNodes().getFirst();
            if (!otherExprCaseNode.getChildNodes().isEmpty())
            {
                ExprNode otherValueExpr =  otherExprCaseNode.getChildNodes().getFirst();
                if (!valueExpr.equalsNode(otherValueExpr))
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        List<Pair<ExprNode,ExprNode>> otherExprNodeList = otherExprCaseNode._exprNodeList;
        if ((_exprNodeList.size()) !=  otherExprNodeList.size())
        {
            return false;
        }
        Pair<ExprNode,ExprNode>[] t1 = _exprNodeList.toArray(new Pair[0]);
        Pair<ExprNode,ExprNode>[] t2 = otherExprNodeList.toArray(new Pair[0]);
        int i =0;
         while(i<t1.length)
        {
            Pair<ExprNode,ExprNode> p1 = t1[i];
            Pair<ExprNode,ExprNode> p2 = t2[i];
            if ((p1.getFirst()) == null)
            {
                if (p2.getFirst() !=null)
                {
                    // First case expression and it is the else part
                    // The second case expression should have the else expression at same place.
                    return false;
                }
                // Compare the else expressions ofr both case expressions.
                if ((p1.getSecond() == null) || (p2.getSecond() == null))
                {
                    return false;
                }
                if ( !(p1.getSecond().equalsNode(p2.getSecond())))
                {
                    return false;
                }
            }
            else
            {
                // The condition part of the first case expression is not null.
                // We assume that the same is true for the second case expression.
                // Let's compare them including the action parts.
                if ( !(p1.getFirst().equalsNode(p2.getFirst())))
                {
                    return false;
                }
                // This should not happen, the validate method should take care of this checking.
                // Still we make sure this is still verified.
                if ((p1.getSecond() == null) || (p2.getSecond() == null))
                {
                    return false;
                }
                if ( !(p1.getSecond().equalsNode(p2.getSecond())))
                {
                    return false;
                }
            }
            i++;
        }
        return true;
    }

   public void validate(StreamTypeService streamTypeService_) throws ExprValidationException
    {
        if ((_exprNodeList == null) || (_exprNodeList.size() == 0))
        {
            throw new ExprValidationException("The Case node requires at least one child expression");
        }

        // If the case expression has an evaluation expression
        // like in: case expr when expr then expr else expr
        // validate it.
        if ((!getChildNodes().isEmpty()) && (getChildNodes().getFirst() != null))
        {
            getChildNodes().getFirst().validate(streamTypeService_);
        }
        // Sub-nodes must be at least one when expression
        // A when node as its condition part not null
        boolean noWhenExpr = true;
        int oneElse = 0;
        for (Pair<ExprNode, ExprNode> p : _exprNodeList)
        {
            if (p.getFirst() != null) {
                p.getFirst().validate(streamTypeService_);
                noWhenExpr = false;
                if (p.getSecond() == null) {
                    throw new ExprValidationException("The Case expression is not syntaxically correct. The when statement is uncomplete");
                }
            } else {
                // No more than one else expression per case statement.
                if (p.getSecond() == null) {
                    throw new ExprValidationException("The Case expression is not syntaxically correct");
                }
                if (++oneElse > 1) {
                    throw new ExprValidationException("The Case expression contains more than one else statement");
                }
            }
        }

        if (noWhenExpr)
        {
            throw new ExprValidationException("The Case node requires at least one when expression");
        }
    }

    public Class getType()
    {
        return getType(null);
    }

    // The type of the case Node is determined by the first when node for which the condition
    // node is either true or equal to the value expression of the case node when present.
    // The type of the case ndoe is then the type of the action part of the when node.
    // When no when node is found to satisfy the condition of the case node, the type is
    // the one of the else expression or null when no else expression is found.

    public Class getType(EventBean[] eventsPerStream_)
    {

        ExprNode elseNode = null;

        for (Pair<ExprNode, ExprNode> p : _exprNodeList)
        {
            ExprNode actionNode = p.getSecond();
            if (p.getFirst() != null)
            {
                ExprNode conditionNode = p.getFirst();
                if (!_inCase2)
                {
                    // This is a "when condition then action" statement
                    // The first when node for which the condition true,
                    // the evaluation is its action expression.
                    try {

                        if ((conditionNode.getType()) == Boolean.class)
                        {
                            if ((Boolean) conditionNode.evaluate(eventsPerStream_))
                            {
                                return actionNode.getType();
                            }
                        }

                    } catch (ExprValidationException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                else
                {
                    // This is a "case value when condition then action" statement
                    // This time the first when node for which its condition matches in value
                    // the expression of the case node, is the node that is evaluated.
                    // The evaluation of the action part of this node is the evaluation of the case node.
                    ExprNode caseValExpr = getChildNodes().getFirst();
                    if (compareEQLNodes(caseValExpr, conditionNode, eventsPerStream_))
                    {
                        try {
                            return actionNode.getType();
                        } catch (ExprValidationException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            }
            // if condition part is null, it is a else node
            else
            {
                elseNode =  actionNode;
            }
        }
        if (elseNode != null)
        {
            try {
                return elseNode.getType();
            } catch (ExprValidationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return null;
    }

    public Object evaluate(EventBean[] eventsPerStream_)
    {
        ExprNode elseNode = null;

        for (Pair<ExprNode, ExprNode> p : _exprNodeList)
        {
            ExprNode actionNode = p.getSecond();
            if (p.getFirst() != null)
            {
                ExprNode conditionNode = p.getFirst();
                if (!_inCase2)
                {
                    // This is a "when condition then action" statement
                    // The first when node for which the condition true,
                    // the evaluation is its action expression.
                    try {

                        if ((conditionNode.getType()) == Boolean.class)
                        {
                            if ((Boolean) conditionNode.evaluate(eventsPerStream_))
                            {
                                return actionNode.evaluate(eventsPerStream_);
                            }
                        }

                    } catch (ExprValidationException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                else
                {
                    // This is a "case value when condition then action" statement
                    // This time the first when node for which its condition matches in value
                    // the expression of the case node, is the node that is evaluated.
                    // The evaluation of the action part of this node is the evaluation of the case node.
                    ExprNode caseValExpr = getChildNodes().getFirst();
                    if (compareEQLNodes(caseValExpr, conditionNode, eventsPerStream_))
                    {
                        return actionNode.evaluate(eventsPerStream_);
                    }
                }
            }
            // if condition part is null, it is a else node
            else
            {
                elseNode =  actionNode;
            }
        }
        if (elseNode != null)
        {
            return elseNode.evaluate(eventsPerStream_);
        }
        return null;
    }

    private boolean compareEQLNodes(Object nodeOne_, Object nodeTwo_, EventBean[] eventsPerStream_)
    {
        if ((nodeOne_ instanceof ExprNode) && (nodeTwo_ instanceof ExprNode))
        {
            if (eventsPerStream_ == null)
            {
              try
              {
                  if ((((ExprNode)nodeOne_).getType()) != (((ExprNode)nodeTwo_).getType()))
                  {
                    return false;
                  }
              }
              catch (ExprValidationException e) {
                  e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
              }
            }
            else
            {
                Object result = ((ExprNode)nodeOne_).evaluate(eventsPerStream_);
                Object otherResult = ((ExprNode)nodeTwo_).evaluate(eventsPerStream_);
                if (valueComparaison(result, otherResult))
                {
                    return true;
                }
                return (result.equals(otherResult));
            }
        }
        return false;
    }

    private static boolean valueComparaison(Object objectOne, Object objectTwo)
    {
        if ((objectOne == null) || (objectTwo == null))
        {
            return false;
        }

        Class typeOne = objectOne.getClass();
        Class typeTwo = objectTwo.getClass();

        if ((typeOne == String.class) && (typeTwo == String.class))
        {
            return ((((String)objectOne).intern()) == (((String)objectTwo).intern()));
        }

        Class boxedOne = JavaClassHelper.getBoxedType(typeOne);
        Class boxedTwo = JavaClassHelper.getBoxedType(typeTwo);

        if (  ((typeOne == boolean.class) || ((typeOne == Boolean.class))) &&
              ((typeTwo == boolean.class) || ((typeTwo == Boolean.class))) )
        {
            return (objectOne == objectTwo);
        }

        if (!JavaClassHelper.isNumeric(boxedOne) || !JavaClassHelper.isNumeric(boxedTwo))
        {
            return false;
        }
        if ((boxedOne == Double.class) || (boxedTwo == Double.class))
        {
            return ((((Number) objectOne).doubleValue()) == (((Number) objectTwo).doubleValue()));
        }
        if ((boxedOne == Float.class) || (boxedTwo == Float.class))
        {
            return ((((Number) objectOne).floatValue()) == (((Number) objectTwo).floatValue()));
        }
        if ((boxedOne == Long.class) || (boxedTwo == Long.class))
        {
            return ((((Number) objectOne).longValue()) == (((Number) objectTwo).longValue()));
        }
        return ((((Number) objectOne).intValue()) == (((Number) objectTwo).intValue()));
    }

     public List<Pair<ExprNode,ExprNode>> getExprNodeList()
     {
         return _exprNodeList;
     }

    public Pair<ExprNode,ExprNode> getExprNodeList(int ndx_)
    {
        if (_exprNodeList == null)
        {
            return null;
        }
        if (ndx_ > (_exprNodeList.size()) )
        {
            return null;
        }
        return _exprNodeList.get(ndx_);
    }

    public boolean setExprNodeList(int ndx_, Pair<ExprNode,ExprNode> pair_)
    {
        if (_exprNodeList == null)
        {
            return false;
        }
        if (ndx_ > (_exprNodeList.size()) )
        {
            return false;
        }
       _exprNodeList.set(ndx_, pair_);
        return true;
    }

    public void clearExprNodeList()
    {
        _exprNodeList.clear();
    }
}

