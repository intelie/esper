package net.esper.client.soda;

/**
 * Base junction for conjunction (and) and disjunction (or).
 */
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

    /**
     * Property to add to the conjunction (AND) or disjunction (OR).
     * @param propertyName is the name of the property
     * @return expression
     */
    public Junction add(String propertyName)
    {
        this.getChildren().add(new PropertyValueExpression(propertyName));
        return this;
    }
}
