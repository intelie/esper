package net.esper.client.soda;

import java.io.StringWriter;

public class PatternFilterExpr extends PatternExprBase
{
    private String tagName;
    private Filter filter;

    public PatternFilterExpr(Filter filter)
    {
        this(filter, null);
    }

    public PatternFilterExpr(Filter filter, String tagName)
    {
        this.tagName = tagName;
        this.filter = filter;
    }

    public String getTagName()
    {
        return tagName;
    }

    public void setTagName(String tagName)
    {
        this.tagName = tagName;
    }

    public Filter getFilter()
    {
        return filter;
    }

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
