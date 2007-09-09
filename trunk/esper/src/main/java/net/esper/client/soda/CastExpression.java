package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Instance-of expression checks if an expression returns a certain type.
 */
public class CastExpression extends ExpressionBase
{
    private String typeName;

    /**
     * Ctor - for use to create an expression tree, without child expression.
     * @param typeName is the type to cast to: a fully-qualified class name or Java primitive type name or "string"
     */
    public CastExpression(String typeName)
    {
        this.typeName = typeName;
    }

    /**
     * Ctor.
     * @param expressionToCheck provides values to cast
     * @param typeName is the type to cast to: a fully-qualified class names or Java primitive type names or "string"
     */
    public CastExpression(Expression expressionToCheck, String typeName)
    {
        this.getChildren().add(expressionToCheck);
        this.typeName = typeName;
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        writer.write("cast(");
        this.getChildren().get(0).toEQL(writer);
        writer.write(", ");
        writer.write(typeName);
        writer.write(")");
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
}
