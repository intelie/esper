package net.esper.client.soda;

public abstract class Junction extends ExpressionBase
{
    /**
     * Expression to add to the conjunction (AND) or disjunction (OR).
     * @param expression to add
     * @return expression
     */
    public Junction add(Expression expression)
    {
        this.getChildren().add(expression);
        return this;
    }

    public Junction add(String propertyName)
    {
        this.getChildren().add(new PropertyValueExpression(propertyName));
        return this;
    }
}
