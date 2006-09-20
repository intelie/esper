package net.esper.view;

/**
 * The StreamView interface allows a view to interact with a EventStream to share data.
 * A view implementing StreamView is called by the EventStream to determine what usage that view is making of the
 * stream's data structures.
 * This allows the view to forego keeping its own data structures and instead referencing those of the stream.
 */
public interface StreamView extends View
{
    /**
     * Called by the EventStream owning a view to determine the largest event sequence number which that view no longer needs.
     * @return the event number no longer needed
     */
    public long doneWith();
}
