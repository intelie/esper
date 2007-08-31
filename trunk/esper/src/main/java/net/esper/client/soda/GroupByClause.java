package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;

public class GroupByClause implements Serializable
{
    private List<Expression> groupByExpressions;

    public static GroupByClause create()
    {
        return new GroupByClause();
    }

    public static GroupByClause create(String ...properties)
    {
        return new GroupByClause(properties);
    }

    public static GroupByClause create(Expression ...expressions)
    {
        return new GroupByClause(expressions);
    }

    public GroupByClause()
    {
        groupByExpressions = new ArrayList<Expression>();
    }

    public GroupByClause(String ...properties)
    {
        this();
        groupByExpressions.addAll(Expressions.toPropertyExpressions(properties));
    }

    public GroupByClause(Expression ...expressions)
    {
        this();
        for (int i = 0; i < expressions.length; i++)
        {
            groupByExpressions.add(expressions[i]);
        }
    }

    public List<Expression> getGroupByExpressions()
    {
        return groupByExpressions;
    }

    public void setGroupByExpressions(List<Expression> groupByExpressions)
    {
        this.groupByExpressions = groupByExpressions;
    }

    public void toEQL(StringWriter writer)
    {
        String delimiter = "";
        for (Expression child : groupByExpressions)
        {
            writer.write(delimiter);
            child.toEQL(writer);
            delimiter = ", ";
        }
    }
}
