package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

/**
 * Interface representing a pattern expression.
 * <p>
 * Pattern expressions are organized into a tree-like structure with nodes representing sub-expressions (composite).
 * <p>
 * Certain types of nodes have certain requirements towards the number or types of nodes that
 * are expected as pattern sub-expressions to an pattern expression.
 */
public interface PatternExpr extends Serializable
{
    /**
     * Returns the list of pattern sub-expressions (child expressions) to the current pattern expression node.
     * @return pattern child expressions or empty list if there are no child expressions
     */
    public List<PatternExpr> getChildren();

    /**
     * Renders the pattern expression and all it's child expressions, in full tree depth, as a string in
     * language syntax.
     * @param writer is the output to use
     */    
    public void toEQL(StringWriter writer);
}
