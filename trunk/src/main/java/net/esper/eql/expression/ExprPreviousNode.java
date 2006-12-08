package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.ViewFactoryDelegate;
import net.esper.eql.core.ViewFactoryCallback;
import net.esper.event.EventBean;
import net.esper.collection.DataWindowRandomAccess;

/**
 * Represents the 'prev' previous event function in an expression node tree.
 */
public class ExprPreviousNode extends ExprNode implements ViewFactoryCallback
{
    private Class resultType;
    private DataWindowRandomAccess dataWindowRandomAccess;
    private int streamNumber;

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewFactoryDelegate viewFactoryDelegate) throws ExprValidationException
    {
        ExprIdentNode identNode = (ExprIdentNode) this.getChildNodes().get(1);
        streamNumber = identNode.getStreamId();

        if (!viewFactoryDelegate.requestCapability(streamNumber, DataWindowRandomAccess.class, this))
        {
            throw new ExprValidationException("Prior expression node requires a view that provides data window access");
        }

        resultType = this.getChildNodes().get(1).getType();
    }

    public Class getType()
    {
        return resultType;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        // evaluate first child, returns the index (if null, then null)
        // use DataWindowRandomAccess to get(index)
        // TODO
        Object indexResult = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
        Integer index = (Integer) indexResult;

        EventBean substituteEvent = null;
        //if (isNewData)
        {
            if (dataWindowRandomAccess.getNewDataSize() <= index)
            {
                return null;
            }
            substituteEvent = dataWindowRandomAccess.getNewData(index);
        }
        //else
        {
            if (dataWindowRandomAccess.getOldDataSize() <= index)
            {
                return null;
            }
            substituteEvent = dataWindowRandomAccess.getOldData(index);
        }
        
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
        dataWindowRandomAccess = (DataWindowRandomAccess) resource;
    }
}
