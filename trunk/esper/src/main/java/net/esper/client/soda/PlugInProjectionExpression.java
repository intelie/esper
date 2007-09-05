package net.esper.client.soda;

import net.esper.type.BitWiseOpEnum;

import java.io.StringWriter;

/**
 * Represents a plug-in aggregation function.
 */
public class PlugInProjectionExpression extends ExpressionBase
{
    private String functionName;
    private boolean isDistinct;

    /**
     * Ctor.
     * @param functionName the name of the function
     * @param isDistinct true for distinct
     */
    public PlugInProjectionExpression(String functionName, boolean isDistinct)
    {
        this.functionName = functionName;
        this.isDistinct = isDistinct;
    }

    /**
     * Ctor.
     * @param functionName the name of the function
     * @param isDistinct true for distinct
     * @param expression provides aggregated values
     */
    public PlugInProjectionExpression(String functionName, boolean isDistinct, Expression expression)
    {
        this.functionName = functionName;
        this.isDistinct = isDistinct;
        this.getChildren().add(expression);
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        writer.write(functionName);
        writer.write('(');
        if (isDistinct)
        {
            writer.write("distinct ");
        }
        if (this.getChildren().size() > 0)
        {
            this.getChildren().get(0).toEQL(writer);
        }
        writer.write(")");
    }

    /**
     * Returns the function name.
     * @return name of function
     */
    public String getFunctionName()
    {
        return functionName;
    }

    /**
     * Sets the function name.
     * @param functionName name of function
     */
    public void setFunctionName(String functionName)
    {
        this.functionName = functionName;
    }

    /**
     * Returns true for distinct.
     * @return boolean indicating distinct or not
     */
    public boolean isDistinct()
    {
        return isDistinct;
    }

    /**
     * Set to true for distinct.
     * @param distinct indicating distinct or not
     */
    public void setDistinct(boolean distinct)
    {
        isDistinct = distinct;
    }
}
