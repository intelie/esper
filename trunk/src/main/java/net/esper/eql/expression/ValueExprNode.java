package net.esper.eql.expression;

import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Aug 12, 2006
 * Time: 9:19:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class ValueExprNode extends ExprNode
{
    private static Class[] aClasses = new Class[]
    {
        // Constant
        ExprConstantNode.class,
        // ArithExpr
        ExprMathNode.class,
        ExprBitWiseNode.class,
        // eventPropertyExpr
        ExprIdentNode.class,
        // evalExprChoice
        ExprOrNode.class,
        ExprAndNode.class,
        ExprEqualsNode.class,
        ExprRelationalOpNode.class,
        // builtIn functions
        ExprMinMaxAggrNode.class,
        ExprMinMaxRowNode.class,
        ExprAvedevNode.class,
        ExprAvgNode.class,
        ExprCountNode.class,
        ExprSumNode.class,
        ExprMedianNode.class,
        ExprStddevNode.class,
     };

    ExprNode _node;

    public ValueExprNode()
    {
    }

    public ValueExprNode(ExprNode node_)
    {
        _node = node_;
    }

    public void validate(StreamTypeService streamTypeService_) throws ExprValidationException
    {
        //Only one child node is allowed.
        if (getChildNodes().size() != 1)
        {
            throw new ExprValidationException("value expression node must have exactly 1 child node");
        }

        _node = this.getChildNodes().getFirst();
        boolean isValueExp = false;
        for (int i=0; i<ValueExprNode.aClasses.length; i++)
        {
            if (_node.getClass() == ValueExprNode.aClasses[i])
            {
                isValueExp = true;
            }
        }

        if(!isValueExp)
        {
            throw new ExprValidationException("datatype '" + _node.getClass().getName()+
                    " is not a valid value Expression");
        }
        _node.validate(streamTypeService_);
    }

    public Class getType()
    {
        try {
            return _node.getType();
        }
        catch (ExprValidationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public Object evaluate(EventBean[] eventsPerStream_)
    {
        if (_node instanceof ExprNode)
        {
            return (((ExprNode)_node).evaluate(eventsPerStream_));
        }
        if (_node instanceof ExprAggregateNode)
        {
             Aggregator agg = ((ExprAggregateNode)_node).getAggregationFunction();
            return agg.getValue();
        }
        return null;
    }

    public String toExpressionString()
    {
        return _node.toExpressionString();
    }

    public boolean equalsNode(ExprNode node_)
    {
        if (!(node_ instanceof ValueExprNode))
        {
            return false;
        }
        ExprNode othervalNode = ((ValueExprNode)node_)._node;
        /* if ((_node.getType()) != (othervalNode.getType()))
        {
            return false;
        }*/
        if ((_node instanceof ExprNode) && (!_node.equalsNode(othervalNode)))
        {
            return false;
        }
        if ((_node instanceof ExprAggregateNode) && (!(((ExprAggregateNode)_node).equalsNodeAggregate((ExprAggregateNode)othervalNode))))
        {
            return false;
        }
        return true;
    }

    public boolean equalsNode(ExprNode node_, EventBean[] eventsPerStream_)
    {
        // Equals only if equal values...
        if (!(node_ instanceof ValueExprNode))
        {
            return false;
        }
        ExprNode othervalNode = ((ValueExprNode)node_)._node;
        if (_node instanceof ExprNode)
        {
            if (eventsPerStream_ == null)
            {
              try
              {
                  if ((_node.getType()) != (othervalNode.getType()))
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
                Object result = ((ExprNode)_node).evaluate(eventsPerStream_);
                Object otherResult = ((ExprNode)node_).evaluate(eventsPerStream_);
                if (ValueExprNode.valueComparaison(result, otherResult))
                {
                    return true;
                }
                return (result.equals(otherResult));
            }
        }
        if (_node instanceof ExprAggregateNode)
        {
            Aggregator agg = ((ExprAggregateNode)_node).getAggregationFunction();
            Aggregator otherAgg = ((ExprAggregateNode)node_).getAggregationFunction();
            return ((agg.getValue()).equals(otherAgg.getValue()));
        }
        return true;
    }

    public ExprNode getExprNode()
    {
        return _node;
    }

    public void setExprNode(ExprNode node_)
    {
        _node = node_;
    }

    public static boolean valueComparaison(Object objectOne, Object objectTwo)
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
            return (((Boolean)objectOne) == ((Boolean)objectTwo));
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

    private static final Log log = LogFactory.getLog(ValueExprNode.class);
}
