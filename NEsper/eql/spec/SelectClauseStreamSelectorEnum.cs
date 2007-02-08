using System;

namespace net.esper.eql.spec
{
    /// <summary>
    /// Enumeration for representing select-clause selection of the remove stream or the insert stream, or both.
    /// </summary>
    
    public enum SelectClauseStreamSelectorEnum
    {
        /// <summary> Indicates selection of the remove stream only.</summary>
        RSTREAM_ONLY,
        /// <summary> Indicates selection of the insert stream only.</summary>
        ISTREAM_ONLY,
        /// <summary> Indicates selection of both the insert and the remove stream.  </summary>
        RSTREAM_ISTREAM_BOTH
    }
}