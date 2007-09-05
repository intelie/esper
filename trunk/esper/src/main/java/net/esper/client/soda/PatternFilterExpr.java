package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Filter for use in pattern expressions. 
 */
public class PatternFilterExpr extends PatternExprBase
{
    private String tagName;
    private Filter filter;

    /**
     * Ctor.
     * @param filter specifies to events to filter out
     */
    public PatternFilterExpr(Filter filter)
    {
        this(filter, null);
    }

    /**
     * Ctor.
     * @param filter specifies to events to filter out
     * @param tagName specifies the name of the tag to assigned to matching events
     */
    public PatternFilterExpr(Filter filter, String tagName)
    {
        this.tagName = tagName;
        this.filter = filter;
    }

    /**
     * Returns the tag name.
     * @return tag name.
     */
    public String getTagName()
    {
        return tagName;
    }

    /**
     * Sets the tag name.
     * @param tagName tag name to set
     */
    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    /**
     * Returns the filter specification.
     * @return filter
     */
    public Filter getFilter()
    {
        return filter;
    }

    /**
     * Sets the filter specification.
     * @param filter to use
     */
    public void setFilter(Filter filter)
    {
        this.filter = filter;
    }

    public void toEQL(StringWriter writer)
    {
        if (tagName != null)
        {
            writer.write(tagName);
            writer.write('=');
        }
        filter.toEQL(writer);
    }
}
