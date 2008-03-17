package com.espertech.esper.client.soda;

import java.io.StringWriter;

/**
 * For use in a select clause, this element in a select clause defines that for a given stream we want to
 * select the underlying type. Most often used in joins to select wildcard from one of the joined streams.
 * <p>
 * For example:
 * <pre>select streamOne.* from StreamOne as streamOne, StreamTwo as streamTwo</pre>
 * <p>
 * There may also be an alias such that the event ends up in  
 * <p>
 * For example:
 * <pre>select streamOne.* from StreamOne as streamOne, StreamTwo as streamTwo</pre>
 */
public class SelectClauseStreamWildcard implements SelectClauseElement
{
    private String streamAliasName;
    private String optionalColumnAlias;

    /**
     * Ctor.
     * @param streamAliasName is the alias name assigned to a stream
     * @param optionalColumnAlias is the name to assign to the column carrying the streams generated events, or
     * null if the event should not appear in a column
     */
    public SelectClauseStreamWildcard(String streamAliasName, String optionalColumnAlias)
    {
        this.streamAliasName = streamAliasName;
        this.optionalColumnAlias = optionalColumnAlias;
    }

    /**
     * Returns the stream alias name (e.g. select streamAliasName.* as colAlias from MyStream as streamAliasName)
     * @return alias
     */
    public String getStreamAliasName()
    {
        return streamAliasName;
    }

    /**
     * Returns the optional column alias name (e.g. select streamAliasName.* as colAlias from MyStream as streamAliasName)
     * @return alias of column, or null if none defined
     */
    public String getOptionalColumnAlias()
    {
        return optionalColumnAlias;
    }

    /**
     * Renders the element in textual representation.
     * @param writer to output to
     */
    public void toEPLElement(StringWriter writer)
    {
        writer.write(streamAliasName);
        writer.write(".*");
        if (optionalColumnAlias != null)
        {
            writer.write(" as ");
            writer.write(optionalColumnAlias);
        }
    }
}
