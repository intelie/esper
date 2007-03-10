package net.esper.view.window;

import net.esper.view.ext.IStreamSortedRandomAccess;

/**
 * Getter that provides an index at runtime.
 */
public class RandomAccessByIndexGetter implements IStreamRandomAccess.IStreamRandomAccessUpdateObserver, IStreamSortedRandomAccess.IStreamRandomAccessUpdateObserver
{
    private RandomAccessByIndex randomAccessByIndex;

    /**
     * Returns the index for access.
     * @return index
     */
    public RandomAccessByIndex getAccessor()
    {
        return randomAccessByIndex;
    }

    public void updated(IStreamRandomAccess iStreamRandomAccess)
    {
        this.randomAccessByIndex = iStreamRandomAccess;
    }

    public void updated(IStreamSortedRandomAccess iStreamSortedRandomAccess)
    {
        this.randomAccessByIndex = iStreamSortedRandomAccess;
    }
}
