package net.esper.client.soda;

import java.io.StringWriter;


/**
 * Disjunction represents a logical OR allowing multiple sub-expressions to be connected by OR.
 */
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
     * @param first an expression to add to the OR-test
     * @param second an expression to add to the OR-test
     * @param expressions is the expression to put in the OR-relationship.
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
