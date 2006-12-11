package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.ViewFactoryDelegate;
import net.esper.eql.core.ViewFactoryCallback;
import net.esper.event.EventBean;
import net.esper.collection.RandomAccess;
import net.esper.view.ViewCapabilityRandomAccess;
import net.esper.util.JavaClassHelper;

/**
 * Represents the 'prev' previous event function in an expression node tree.
 */
public class ExprPreviousNode extends ExprNode implements ViewFactoryCallback
{
    private Class resultType;
    private RandomAccess randomAccess;
    private int streamNumber;
    private Integer constantIndexNumber;
    private boolean isConstantIndex;

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewFactoryDelegate viewFactoryDelegate) throws ExprValidationException
    {
        // Determine if the index is a constant value or an expression to evaluate
        if (this.getChildNodes().get(0) instanceof ExprConstantNode)
        {
            ExprConstantNode constantNode = (ExprConstantNode) this.getChildNodes().get(0);
            Object value = constantNode.evaluate(null, false);
            if (!(value instanceof Number))
            {
                throw new ExprValidationException("Previous function requires an integer index parameter or expression");
            }

            Number valueNumber = (Number) value;
            if ( (JavaClassHelper.isFloatingPointNumber(valueNumber)) ||
                 (valueNumber instanceof Long))
            {
                throw new ExprValidationException("Previous function requires an integer index parameter or expression");
            }

            constantIndexNumber = valueNumber.intValue();
            isConstantIndex = true;
        }

        // Determine stream number
        ExprIdentNode identNode = (ExprIdentNode) this.getChildNodes().get(1);
        streamNumber = identNode.getStreamId();
        resultType = this.getChildNodes().get(1).getType();

        // Request a callback that provides the required access
        if (!viewFactoryDelegate.requestCapability(streamNumber, new ViewCapabilityRandomAccess(constantIndexNumber), this))
        {
            throw new ExprValidationException("Previous function requires a view that provides a data window");
        }
    }

    public Class getType()
    {
        return resultType;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        Integer index = null;

        // Use constant if supplied
        if (isConstantIndex)
        {
            index = constantIndexNumber;
        }
        else
        {
            // evaluate first child, which returns the index
            Object indexResult = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
            if (indexResult == null)
            {
                return null;
            }
            index = (Integer) indexResult;
        }

        // access based on index returned
        EventBean substituteEvent = null;
        if (isNewData)
        {
            substituteEvent = randomAccess.getNewData(index);
        }
        else
        {
            substituteEvent = randomAccess.getOldData(index);
        }
        if (substituteEvent == null)
        {
            return null;
        }

        // Substitute original event with prior event, evaluate inner expression
        EventBean originalEvent = eventsPerStream[streamNumber];
        eventsPerStream[streamNumber] = substituteEvent;
        Object evalResult = this.getChildNodes().get(1).evaluate(eventsPerStream, isNewData);
        eventsPerStream[streamNumber] = originalEvent;

        return evalResult;
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("previous (");
        buffer.append(this.getChildNodes().get(0));
        buffer.append(",");
        buffer.append(this.getChildNodes().get(1));
        buffer.append(")");
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprPreviousNode))
        {
            return false;
        }

        return true;
    }

    public void setViewResource(Object resource)
    {
        randomAccess = (RandomAccess) resource;
    }
}
