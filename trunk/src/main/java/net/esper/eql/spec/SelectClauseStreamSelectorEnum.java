package net.esper.eql.spec;

/**
 * Enumeration for representing select-clause selection of the remove stream or the insert stream, or both.
 */
public enum SelectClauseStreamSelectorEnum
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
    RSTREAM_ISTREAM_BOTH;
}
