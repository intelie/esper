package net.esper.client.soda;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Expressions implements Serializable
{
    public static Junction or()
    {
        return new Disjunction();
    }

    public static Junction or(Expression ...expressions)
    {
        return new Disjunction(expressions);
    }

    public static Junction and()
    {
        return new Conjunction();
    }

    public static Junction and(Expression ...expressions)
    {
        return new Conjunction(expressions);
    }

    public static Expression ge(String propertyName, Object value)
    {
        return new RelationalOpExpression(getPropExpr(propertyName)
                , ">=", new ConstantExpression(value));
    }

    public static Expression ge(Expression left, Expression right)
    {
        return new RelationalOpExpression(left, ">=", right);
    }

    public static Expression geProperty(String propertyName, String propertyRight)
    {
        return new RelationalOpExpression(getPropExpr(propertyName)
                , ">=", new PropertyValueExpression(propertyRight));
    }

    public static Expression gt(String propertyName, Object value)
    {
        return new RelationalOpExpression(getPropExpr(propertyName)
                , ">", new ConstantExpression(value));
    }

    public static Expression gt(Expression left, Expression right)
    {
        return new RelationalOpExpression(left, ">", right);
    }

    public static Expression gtProperty(String propertyName, String propertyRight)
    {
        return new RelationalOpExpression(getPropExpr(propertyName)
                , ">", new PropertyValueExpression(propertyRight));
    }

    public static Expression le(String propertyName, Object value)
    {
        return new RelationalOpExpression(getPropExpr(propertyName)
                , "<=", new ConstantExpression(value));
    }

    public static Expression lt(Expression left, Expression right)
    {
        return new RelationalOpExpression(left, "<", right);
    }

    public static Expression leProperty(String propertyName, String propertyRight)
    {
        return new RelationalOpExpression(getPropExpr(propertyName)
                , "<=", new PropertyValueExpression(propertyRight));
    }

    public static Expression le(Expression left, Expression right)
    {
        return new RelationalOpExpression(left, "<=", right);
    }

    public static Expression lt(String propertyName, Object value)
    {
        return new RelationalOpExpression(getPropExpr(propertyName)
                , "<", new ConstantExpression(value));
    }

    public static Expression ltProperty(String propertyName, String propertyRight)
    {
        return new RelationalOpExpression(getPropExpr(propertyName)
                , "<", new PropertyValueExpression(propertyRight));
    }

    public static Expression eq(String propertyName, Object value)
    {
        return new RelationalOpExpression(getPropExpr(propertyName)
                , "=", new ConstantExpression(value));
    }

    public static Expression eqProperty(String propertyName, String propertyRight)
    {
        return new RelationalOpExpression(getPropExpr(propertyName)
                , "=", new PropertyValueExpression(propertyRight));
    }

    public static Expression property(String propertyName)
    {
        return getPropExpr(propertyName);
    }

    public static Expression like(String propertyName, Object value)
    {
        return new LikeExpression(getPropExpr(propertyName), new ConstantExpression(value));
    }

    public static Expression like(Expression left, Expression right)
    {
        return new LikeExpression(left, right);
    }

    public static Expression avg(String propertyName)
    {
        return new AvgProjectionExpression(getPropExpr(propertyName));
    }

    public static Expression avg(Expression inner)
    {
        return new AvgProjectionExpression(inner);
    }

    public static Expression sum(String propertyName)
    {
        return new SumProjectionExpression(getPropExpr(propertyName));
    }

    public static Expression sum(Expression inner)
    {
        return new SumProjectionExpression(inner);
    }

    public static Expression minus(Expression left, Expression right)
    {
        return new ArithmaticExpression(left, "-", right);
    }

    public static Expression minus(String propertyLeft, String propertyRight)
    {
        return new ArithmaticExpression(new PropertyValueExpression(propertyLeft), "-", new PropertyValueExpression(propertyRight));
    }

    public static Expression times(Expression left, Expression right)
    {
        return new ArithmaticExpression(left, "*", right);
    }

    public static Expression times(String propertyLeft, String propertyRight)
    {
        return new ArithmaticExpression(new PropertyValueExpression(propertyLeft), "*", new PropertyValueExpression(propertyRight));
    }

    public static Expression countStar()
    {
        return new CountStarProjectionExpression();
    }

    public static Expression count(String propertyName)
    {
        return new CountProjectionExpression(getPropExpr(propertyName), false);
    }

    public static Expression count(Expression expression)
    {
        return new CountProjectionExpression(expression, false);
    }

    public static Expression countDistinct(String propertyName)
    {
        return new CountProjectionExpression(getPropExpr(propertyName), true);
    }

    public static Expression countDistinct(Expression expression)
    {
        return new CountProjectionExpression(expression, true);
    }

    public static Expression concat(String property, String ...properties)
    {
        ConcatExpression concat = new ConcatExpression();
        concat.getChildren().add(new PropertyValueExpression(property));
        concat.getChildren().addAll(toPropertyExpressions(properties));
        return concat;
    }

    public static Expression subquery(EPStatementObjectModel model)
    {
        return new SubqueryExpression(model);
    }

    public static Expression subqueryIn(String property, EPStatementObjectModel model)
    {
        return new SubqueryInExpression(getPropExpr(property), model);
    }

    public static Expression subqueryExists(EPStatementObjectModel model)
    {
        return new SubqueryExistsExpression(model);
    }

    public static Expression subqueryIn(Expression expression, EPStatementObjectModel model)
    {
        return new SubqueryInExpression(expression, model);
    }

    protected static List<PropertyValueExpression> toPropertyExpressions(String ...properties)
    {
        List<PropertyValueExpression> expr = new ArrayList<PropertyValueExpression>();
        for (int i = 0; i < properties.length; i++)
        {
            expr.add(getPropExpr(properties[i]));
        }
        return expr;
    }

    protected static PropertyValueExpression getPropExpr(String property)
    {
        return new PropertyValueExpression(property);
    }
}
