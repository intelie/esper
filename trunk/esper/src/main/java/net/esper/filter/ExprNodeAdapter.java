package net.esper.filter;

import net.esper.event.EventBean;
import net.esper.eql.expression.ExprNode;

public class ExprNodeAdapter
{
    private final ExprNode exprNode;
    private EventBean[] prototype;

    public ExprNodeAdapter(ExprNode exprNode)
    {
        this.exprNode = exprNode;
    }

    public void setPrototype(EventBean[] prototype)
    {
        this.prototype = prototype;
    }

    public boolean evaluate(EventBean event)
    {
        EventBean[] eventsPerStream;

        if (prototype == null)
        {
            eventsPerStream = new EventBean[1];
        }
        else
        {
            eventsPerStream = new EventBean[prototype.length];
            for (int i = 1; i < prototype.length; i++)
            {
                eventsPerStream[i] = prototype[i];
            }
        }
        
        eventsPerStream[0] = event;
        return (Boolean) exprNode.evaluate(eventsPerStream, true);
    }
}
