package net.esper.client.soda;

public class Expressions
{
    public static Junction or()
    {
        return new Disjunction(new Expression[]{});
    }

    public static Junction or(Expression ...expressions)
    {
        return new Disjunction(expressions);
    }

    public static Junction and()
    {
        return new Conjunction(new Expression[]{});
    }

    public static Junction and(Expression ...expressions)
    {
        return new Conjunction(expressions);
    }

    public static Expression ge(String propertyName, Object value)
    {
        return new RelationalOpExpression(new PropertyValueExpression(propertyName)
                , ">", new ConstantExpression(value));
    }

    public static Expression geProperty(String propertyName, String propertyRight)
    {
        return new RelationalOpExpression(new PropertyValueExpression(propertyName)
                , ">", new PropertyValueExpression(propertyRight));
    }

    public static Expression ge(Expression left, Expression right)
    {
        return new RelationalOpExpression(left, ">", right);
    }

    public static Expression like(String propertyName, Object value)
    {
        return new LikeExpression(new PropertyValueExpression(propertyName), new ConstantExpression(value));
    }

    public static Expression like(Expression left, Expression right)
    {
        return new LikeExpression(left, right);
    }

    public static Expression avg(String propertyName)
    {
        return new AvgProjectionExpression(new PropertyValueExpression(propertyName));
    }

    public static Expression avg(Expression inner)
    {
        return new AvgProjectionExpression(inner);
    }

    public static Expression countStar()
    {
        return new CountStarProjectionExpression();
    }
}
