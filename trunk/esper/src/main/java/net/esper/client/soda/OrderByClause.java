package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;

public class OrderByClause implements Serializable
{
    private List<OrderByElement> orderByExpressions;

    public static OrderByClause create()
    {
        return new OrderByClause();
    }

    public static OrderByClause create(String ...properties)
    {
        return new OrderByClause(properties);
    }

    public static OrderByClause create(Expression ...expressions)
    {
        return new OrderByClause(expressions);
    }

    public OrderByClause add(String property, boolean isDescending)
    {
        orderByExpressions.add(new OrderByElement(Expressions.getPropExpr(property), false));
        return this;
    }

    public OrderByClause add(Expression expression, boolean isDescending)
    {
        orderByExpressions.add(new OrderByElement(expression, false));
        return this;
    }

    public OrderByClause()
    {
        orderByExpressions = new ArrayList<OrderByElement>();
    }

    public OrderByClause(String ...properties)
    {
        this();
        for (int i = 0; i < properties.length; i++)
        {
            orderByExpressions.add(new OrderByElement(Expressions.getPropExpr(properties[i]), false));
        }
    }

    public OrderByClause(Expression ...expressions)
    {
        this();
        for (int i = 0; i < expressions.length; i++)
        {
            orderByExpressions.add(new OrderByElement(expressions[i], false));
        }
    }

    public List<OrderByElement> getOrderByExpressions()
    {
        return orderByExpressions;
    }

    public void setOrderByExpressions(List<OrderByElement> orderByExpressions)
    {
        this.orderByExpressions = orderByExpressions;
    }

    public void toEQL(StringWriter writer)
    {
        String delimiter = "";
        for (OrderByElement element : orderByExpressions)
        {
            writer.write(delimiter);
            element.toEQL(writer);
            delimiter = ", ";
        }
    }
}
