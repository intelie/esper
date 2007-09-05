package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Expression returning a property value.
 */
public class PropertyValueExpression extends ExpressionBase
{
    private String propertyName;

    /**
     * Ctor.
     * @param propertyName is the name of the property
     */
    public PropertyValueExpression(String propertyName)
    {
        this.propertyName = propertyName.trim();
    }

    /**
     * Returns the property name.
     * @return name of the property
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    /**
     * Sets the property name.
     * @param propertyName name of the property
     */
    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write(propertyName);
    }
}
