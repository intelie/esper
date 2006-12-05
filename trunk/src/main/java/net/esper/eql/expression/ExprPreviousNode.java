package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.AutoImportService;
import net.esper.event.EventBean;
import net.esper.collection.DataWindowRandomAccess;

/**
 * Represents Previous-function.
 */
public class ExprPreviousNode extends ExprNode
{
    private Class resultType;
    private DataWindowRandomAccess dataWindowRandomAccess;
    private int streamNumber;

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService) throws ExprValidationException
    {
        // get the stream number of the ExprIdentNode which must be the second child node
        // get a ViewResourceService.getDataWindowRandomAccess(stream)
        // first child must return int value
        // TODO

        resultType = this.getChildNodes().get(1).getType();

        ExprIdentNode identNode = (ExprIdentNode) this.getChildNodes().get(1);
        streamNumber = identNode.getStreamId();
    }

    public Class getType()
    {
        return resultType;
    }

    public Object evaluate(EventBean[] eventsPerStream)
    {
        // evaluate first child, returns the index (if null, then null)
        // use DataWindowRandomAccess to get(index)
        // TODO
        Object indexResult = this.getChildNodes().get(0).evaluate(eventsPerStream);
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
        Object evalResult = this.getChildNodes().get(1).evaluate(eventsPerStream);
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
}
