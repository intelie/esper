package net.esper.view.window;

import net.esper.event.EventBean;

/**
 * Random access interface to insert stream and remove stream data based on an index.
 */
public interface RandomAccessByIndex
{
    /**
     * Returns an new data event given an index.
     * @param index to return new data for
     * @return new data event
     */
    public EventBean getNewData(int index);

    /**
     * Returns an old data event given an index.
     * @param index to return old data for
     * @return old data event
     */
    public EventBean getOldData(int index);
}