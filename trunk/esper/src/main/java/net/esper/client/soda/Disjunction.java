package net.esper.client.soda;

import java.io.StringWriter;

public class Disjunction extends Junction
{
    /**
     * Ctor - for use to create an expression tree, without child expression.
     * <p>
     * Use add methods to add child expressions to acts upon.
     */
    public Disjunction()
    {
    }

    /**
     * Ctor.
     * @param expressions is the expression to put in the AND-relationship.
     */
    public Disjunction(Expression first, Expression second, Expression... expressions)
    {
        addChild(first);
        addChild(second);
        for (int i = 0; i < expressions.length; i++)
        {
            addChild(expressions[i]);
        }        
    }

    public void toEQL(StringWriter writer)
    {
        String delimiter = "";
        for (Expression child : this.getChildren())
        {
            writer.write(delimiter);
            writer.write('(');
            child.toEQL(writer);
            writer.write(')');
            delimiter = " or ";
        }
    }
}
