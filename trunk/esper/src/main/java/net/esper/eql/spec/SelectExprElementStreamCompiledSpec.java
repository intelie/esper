package net.esper.eql.spec;

import net.esper.util.MetaDefItem;

/**
 * Mirror class to {@link SelectExprElementStreamRawSpec} but added the stream number for the alias. 
 */
public class SelectExprElementStreamCompiledSpec implements MetaDefItem
{
    private final String streamAliasName;
    private final String optionalAliasName;
    private final int streamNumber;
    private final boolean isTaggedEvent;

    /**
     * Ctor.
     * @param streamAliasName is the stream alias of the stream to select
     * @param optionalAliasName is the column alias
     * @param streamNumber is the number of the stream
     */
    public SelectExprElementStreamCompiledSpec(String streamAliasName, String optionalAliasName, int streamNumber, boolean isTaggedEvent)
    {
        this.streamAliasName = streamAliasName;
        this.optionalAliasName = optionalAliasName;
        this.streamNumber = streamNumber;
        this.isTaggedEvent = isTaggedEvent;
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
        return streamNumber;
    }

    public boolean isTaggedEvent()
    {
        return isTaggedEvent;
    }
}
