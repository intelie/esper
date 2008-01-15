package net.esper.eql.spec;

/**
 * For use in select clauses for specifying a selected stream: select a.* from MyEvent as a, MyOther as b
 */
public class SelectClauseStreamRawSpec implements SelectClauseElementRaw
{
    private String streamAliasName;
    private String optionalAsName;

    /**
     * Ctor.
     * @param streamAliasName is the stream alias of the stream to select
     * @param optionalAsName is the column alias
     */
    public SelectClauseStreamRawSpec(String streamAliasName, String optionalAsName)
    {
        this.streamAliasName = streamAliasName;
        this.optionalAsName = optionalAsName;
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
    public String getOptionalAsName()
    {
        return optionalAsName;
    }
}
