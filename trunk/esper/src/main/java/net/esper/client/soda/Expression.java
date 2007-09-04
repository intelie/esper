package net.esper.client.soda;

import java.util.List;
import java.util.Iterator;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * Interface representing an expression.
 * <p>
 * Expressions are organized into a tree-like structure with nodes representing sub-expressions (composite pattern).
 * <p>
 * Certain types of nodes have certain requirements towards the number or types of nodes that
 * are expected as sub-expressions to an expression.
 */
public interface Expression extends Serializable
{
    /**
     * Returns the list of sub-expressions (child expressions) to the current expression node.
     * @return child expressions or empty list if there are no child expressions
     */
    public List<Expression> getChildren();

    /**
     * Renders the expressions and all it's child expression, in full tree depth, as a string in
     * language syntax.
     * @param writer is the output to use
     */
    public void toEQL(StringWriter writer);
}
