package net.esper.eql.core;

/**
 * Callback for use by expression nodes to receive view resources.
 */
public interface ViewResourceCallback
{
    /**
     * Supplies view resource.
     * @param resource supplied
     */
    public void setViewResource(Object resource);
}
