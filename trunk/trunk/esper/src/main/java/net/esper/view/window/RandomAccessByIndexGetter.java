package net.esper.view.window;

import net.esper.view.ext.IStreamSortedRandomAccess;

public class RandomAccessByIndexGetter implements IStreamRandomAccess.UpdateObserver, IStreamSortedRandomAccess.UpdateObserver
{
    private RandomAccessByIndex randomAccessByIndex;

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
