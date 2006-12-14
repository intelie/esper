package net.esper.eql.expression;

import net.esper.eql.core.ViewResourceCallback;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.collection.RandomAccessByIndex;
import net.esper.view.ViewCapDataWindowAccess;
import net.esper.event.EventBean;

/**
 * Represents the 'prior' prior event function in an expression node tree.
 */
public class ExprPriorNode extends ExprNode implements ViewResourceCallback
{
    private Class resultType;
    private int streamNumber;
    private int constantIndexNumber;
    private RandomAccessByIndex randomAccess;

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        if (!(this.getChildNodes().get(0) instanceof ExprConstantNode))
        {
            throw new ExprValidationException("Prior function requires an integer index parameter");
        }
        ExprConstantNode constantNode = (ExprConstantNode) this.getChildNodes().get(0);
        if (constantNode.getType() != Integer.class)
        {
            throw new ExprValidationException("Prior function requires an integer index parameter");
        }

        Object value = constantNode.evaluate(null, false);
        constantIndexNumber = ((Number) value).intValue();

        // Determine stream number
        ExprIdentNode identNode = (ExprIdentNode) this.getChildNodes().get(1);
        streamNumber = identNode.getStreamId();
        resultType = this.getChildNodes().get(1).getType();

        // Request a callback that provides the required access
        if (!viewResourceDelegate.requestCapability(streamNumber, new ViewCapDataWindowAccess(constantIndexNumber), this))
        {
            throw new ExprValidationException("Previous function requires a single data window view onto the stream");
        }
    }

    public Class getType()
    {
        return resultType;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        // access based on index returned
        EventBean substituteEvent = null;
        if (isNewData)
        {
            substituteEvent = randomAccess.getNewData(constantIndexNumber);
        }
        else
        {
            substituteEvent = randomAccess.getOldData(constantIndexNumber);
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
        buffer.append("prior(");
        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(",");
        buffer.append(this.getChildNodes().get(1).toExpressionString());
        buffer.append(")");
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprPriorNode))
        {
            return false;
        }

        return true;
    }

    public void setViewResource(Object resource)
    {
        if (resource instanceof RandomAccessByIndex)
        {
            randomAccess = (RandomAccessByIndex) resource;
        }
        else
        {
            throw new IllegalArgumentException("View resource " + resource.getClass() + " not recognized by expression node");
        }
    }
}
