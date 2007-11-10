package net.esper.eql.join;

/**
 * Method for preloading events for a given stream onto the stream's indexes, from a buffer already associated with a stream.
 */
public interface JoinPreloadMethod
{
    /**
     * Initialize a stream from the stream buffers data.
     * @param stream to initialize and load indexes
     */
    public void preloadFromBuffer(int stream);
}
