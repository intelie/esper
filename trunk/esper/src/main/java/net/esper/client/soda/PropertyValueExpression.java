package net.esper.client.soda;

import java.io.StringWriter;

public class PropertyValueExpression extends ExpressionBase
{
    private String propertyName;

    public PropertyValueExpression(String propertyName)
    {
        this.propertyName = propertyName.trim();
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write(propertyName);
    }
}
