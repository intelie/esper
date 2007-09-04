package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class SelectClause implements Serializable
{
    private StreamSelector streamSelector;
    private List<SelectClauseElement> selectList;
    private boolean isWildcard;

    public static SelectClause createWildcard()
    {
        return new SelectClause(StreamSelector.RSTREAM_ISTREAM_BOTH, true);
    }

    public static SelectClause create()
    {
        return new SelectClause(StreamSelector.RSTREAM_ISTREAM_BOTH, false);
    }

    public static SelectClause create(String ...propertyNames)
    {
        return new SelectClause(StreamSelector.RSTREAM_ISTREAM_BOTH, propertyNames);
    }

    public static SelectClause createWildcard(StreamSelector streamSelector)
    {
        return new SelectClause(streamSelector, true);
    }

    public static SelectClause create(StreamSelector streamSelector)
    {
        return new SelectClause(streamSelector, false);
    }

    public static SelectClause create(StreamSelector streamSelector, String ...propertyNames)
    {
        return new SelectClause(streamSelector, propertyNames);
    }

    protected SelectClause(StreamSelector streamSelector, boolean isWildcard)
    {
        this.streamSelector = streamSelector;
        this.selectList = new ArrayList<SelectClauseElement>();
        this.isWildcard = isWildcard;
    }

    public SelectClause(StreamSelector streamSelector, String propertyName)
    {
        this(streamSelector, false);
        selectList.add(new SelectClauseElement(new PropertyValueExpression(propertyName)));
    }

    public SelectClause(StreamSelector streamSelector, String ...propertyNames)
    {
        this(streamSelector, false);
        for (String name : propertyNames)
        {
            selectList.add(new SelectClauseElement(new PropertyValueExpression(name)));
        }
    }

    public SelectClause add(String ...propertyNames)
    {
        for (String name : propertyNames)
        {
            selectList.add(new SelectClauseElement(new PropertyValueExpression(name)));
        }
        return this;
    }

    public SelectClause addWithAlias(String propertyName, String alias)
    {
        selectList.add(new SelectClauseElement(new PropertyValueExpression(propertyName), alias));
        return this;
    }

    public SelectClause add(Expression expression)
    {
        selectList.add(new SelectClauseElement(expression));
        return this;
    }

    public SelectClause add(Expression expression, String asName)
    {
        selectList.add(new SelectClauseElement(expression, asName));
        return this;
    }

    public StreamSelector getStreamSelector()
    {
        return streamSelector;
    }

    public List<SelectClauseElement> getSelectList()
    {
        return selectList;
    }

    public boolean isWildcard()
    {
        return isWildcard;
    }

    public void setStreamSelector(StreamSelector streamSelector)
    {
        this.streamSelector = streamSelector;
    }

    public void setSelectList(List<SelectClauseElement> selectList)
    {
        this.selectList = selectList;
    }

    public void setWildcard(boolean wildcard)
    {
        isWildcard = wildcard;
    }    

    public void toEQL(StringWriter writer)
    {
        writer.write("select ");

        if (streamSelector == StreamSelector.ISTREAM_ONLY)
        {
            writer.write("istream ");
        }
        else if (streamSelector == StreamSelector.RSTREAM_ONLY)
        {
            writer.write("rstream ");
        }

        String delimiter = "";
        if (isWildcard)
        {
            writer.write("*");
            delimiter = ", ";
        }

        for (SelectClauseElement element : selectList)
        {
            writer.write(delimiter);
            element.toEQL(writer);
            delimiter = ", ";
        }
        writer.write(' ');
    }
}
