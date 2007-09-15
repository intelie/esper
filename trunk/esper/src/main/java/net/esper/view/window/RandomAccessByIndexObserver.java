package net.esper.view.window;

/**
 * For indicating that the collection has been updated.
 */
public interface RandomAccessByIndexObserver
{
    /**
     * Callback to indicate an update
     * @param randomAccessByIndex is the collection
     */
    public void updated(RandomAccessByIndex randomAccessByIndex);
}
