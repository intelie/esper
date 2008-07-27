package com.espertech.esper.view.window;

/**
 * Getter that provides an index at runtime.
 */
public class RandomAccessByIndexGetter implements RandomAccessByIndexObserver
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

    public void updated(RandomAccessByIndex randomAccessByIndex)
    {
        this.randomAccessByIndex = randomAccessByIndex;
    }
}
