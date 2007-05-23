package net.esper.eql.core;

import net.esper.event.EventBean;
import net.esper.collection.MultiKeyUntyped;

import java.util.List;

public class ResultSetProcessorResult
{
    private EventBean[] newOut;
    private EventBean[] oldOut;

    private MultiKeyUntyped[] newOrderKey;
    private MultiKeyUntyped[] oldOrderKey;

    public ResultSetProcessorResult()
    {
    }

    public ResultSetProcessorResult(EventBean[] newOut, EventBean[] oldOut)
    {
        this.newOut = newOut;
        this.oldOut = oldOut;
    }

    public EventBean[] getNewOut()
    {
        return newOut;
    }

    public EventBean[] getOldOut()
    {
        return oldOut;
    }

    public void setNewOut(EventBean[] newOut)
    {
        this.newOut = newOut;
    }

    public void setOldOut(EventBean[] oldOut)
    {
        this.oldOut = oldOut;
    }

    public void setNewOrderKey(MultiKeyUntyped[] newOrderKey)
    {
        this.newOrderKey = newOrderKey;
    }

    public void setOldOrderKey(MultiKeyUntyped[] oldOrderKey)
    {
        this.oldOrderKey = oldOrderKey;
    }

    public MultiKeyUntyped[] getNewOrderKey()
    {
        return newOrderKey;
    }

    public MultiKeyUntyped[] getOldOrderKey()
    {
        return oldOrderKey;
    }
}
