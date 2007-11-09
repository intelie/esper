package net.esper.eql.named;

import net.esper.event.EventBean;

public class NamedWindowDeltaData
{
    private final EventBean[] newData;
    private final EventBean[] oldData;

    public NamedWindowDeltaData(EventBean[] newData, EventBean[] oldData)
    {
        this.newData = newData;
        this.oldData = oldData;
    }

    public NamedWindowDeltaData(NamedWindowDeltaData deltaOne, NamedWindowDeltaData deltaTwo)
    {
        this.newData = aggregate(deltaOne.getNewData(), deltaTwo.getNewData());
        this.oldData = aggregate(deltaOne.getOldData(), deltaTwo.getOldData());
    }

    public EventBean[] getNewData()
    {
        return newData;
    }

    public EventBean[] getOldData()
    {
        return oldData;
    }

    private static EventBean[] aggregate(EventBean[] arrOne, EventBean[] arrTwo)
    {
        if (arrOne == null)
        {
            return arrTwo;
        }
        if (arrTwo == null)
        {
            return arrOne;
        }
        EventBean[] arr = new EventBean[arrOne.length + arrTwo.length];
        System.arraycopy(arrOne, 0, arr, 0, arrOne.length);
        System.arraycopy(arrTwo, 0, arr, arrOne.length, arrTwo.length);
        return arr;
    }
}
