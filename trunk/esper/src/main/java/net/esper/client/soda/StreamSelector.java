package net.esper.client.soda;

/**
 * Enumeration for representing selection of the remove stream or the insert stream, or both.
 */
public enum StreamSelector
{
    /**
     * Indicates selection of the remove stream only.
     */
    RSTREAM_ONLY,

    /**
     * Indicates selection of the insert stream only.
     */
    ISTREAM_ONLY,

    /**
     * Indicates selection of both the insert and the remove stream.
     */
    RSTREAM_ISTREAM_BOTH
}
