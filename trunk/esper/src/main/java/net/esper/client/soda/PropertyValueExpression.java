package net.esper.client.soda;

public class PropertyValueExpression extends ExpressionBase
{
    private String propertyName;

    public PropertyValueExpression(String propertyName)
    {
        this.propertyName = propertyName;
    }
}
