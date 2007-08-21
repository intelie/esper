package net.esper.client.soda;

public class Expressions
{
    public static Junction or()
    {
        return new Disjunction();
    }

    public static Junction or(Expression ...expressions)
    {
        return new Disjunction();
    }

    public static Junction and()
    {
        return new Conjunction();
    }

    public static Expression ge(String propertyName, Object value)
    {
        return new RelationalOpExpression(null, ">", null);
    }

    public static Expression geProperty(String propertyName, String property)
    {
        return null;
    }

    public static Expression ge(Expression left, Expression right)
    {
        return null;
    }

    public static Expression like(String propertyName, Object value)
    {
        return null;
    }

    public static Expression like(Expression left, Expression right)
    {
        return null;
    }

    public static Expression avg(String propertyName)
    {
        return null;
    }

    public static Expression avg(Expression inner)
    {
        return null;
    }

    public static Expression countStar()
    {
        return null;
    }
}
