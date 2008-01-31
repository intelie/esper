package com.espertech.esper.eql.spec;

/**
 * Mirror class to {@link SelectClauseStreamRawSpec} but added the stream number for the alias.
 */
public class SelectClauseStreamCompiledSpec implements SelectClauseElementCompiled
{
    private final String streamAliasName;
    private final String optionalAliasName;
    private int streamNumber = -1;
    private boolean isTaggedEvent = false;

    /**
     * Ctor.
     * @param streamAliasName is the stream alias of the stream to select
     * @param optionalAliasName is the column alias
     */
    public SelectClauseStreamCompiledSpec(String streamAliasName, String optionalAliasName)
    {
        this.streamAliasName = streamAliasName;
        this.optionalAliasName = optionalAliasName;
    }

    /**
     * Returns the stream alias (e.g. select streamAlias from MyEvent as streamAlias).
     * @return alias
     */
    public String getStreamAliasName()
    {
        return streamAliasName;
    }

    /**
     * Returns the column alias (e.g. select streamAlias as mycol from MyEvent as streamAlias).
     * @return alias
     */
    public String getOptionalAliasName()
    {
        return optionalAliasName;
    }

    /**
     * Returns the stream number of the stream for the stream alias.
     * @return stream number
     */
    public int getStreamNumber()
    {
        if (streamNumber == -1)
        {
            throw new IllegalStateException("Not initialized for stream number and tagged event");
        }
        return streamNumber;
    }

    /**
     * Returns true to indicate that we are meaning to select a tagged event in a pattern, or false if
     * selecting an event from a stream.
     * @return true for tagged event in pattern, false for stream
     */
    public boolean isTaggedEvent()
    {
        if (streamNumber == -1)
        {
            throw new IllegalStateException("Not initialized for stream number and tagged event");
        }
        return isTaggedEvent;
    }

    /**
     * Sets the stream number of the selected stream within the context of the from-clause.
     * @param streamNumber to set
     */
    public void setStreamNumber(int streamNumber) {
        this.streamNumber = streamNumber;
    }

    /**
     * Sets a flag indicating whether the stream wildcard is for a tagged event in a pattern.
     * @param taggedEvent in pattern
     */
    public void setTaggedEvent(boolean taggedEvent) {
        isTaggedEvent = taggedEvent;
    }
}
