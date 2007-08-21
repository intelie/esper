package net.esper.client.soda;

import net.esper.util.MetaDefItem;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class SelectClause implements Serializable
{
    private StreamSelector streamSelector;
    private List<SelectClauseElement> selectList;
    private boolean isWildcard;

    public static SelectClause createWildcard()
    {
        return new SelectClause(true);
    }

    public static SelectClause create()
    {
        return new SelectClause();
    }

    public static SelectClause create(String propertyName)
    {
        return new SelectClause(propertyName);
    }

    public static SelectClause create(String ...propertyNames)
    {
        return new SelectClause(propertyNames);
    }

    protected SelectClause(boolean isWildcard)
    {
        this.streamSelector = StreamSelector.RSTREAM_ISTREAM_BOTH;
        this.selectList = new ArrayList<SelectClauseElement>();
        this.isWildcard = isWildcard;
    }

    public SelectClause(String propertyName)
    {
        this(false);
    }

    public SelectClause(String ...propertyNames)
    {
        this(false);
    }

    public SelectClause add(String propertyName)
    {
        return this;
    }

    public SelectClause add(String propertyName, String asName)
    {
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

    public void add(SelectClause selectClause)
    {
        // todo
    }
}
