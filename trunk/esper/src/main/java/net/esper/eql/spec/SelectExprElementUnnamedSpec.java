package net.esper.eql.spec;

import net.esper.eql.expression.ExprNode;
import net.esper.util.MetaDefItem;

/**
 * Represents a single item in a SELECT-clause, potentially unnamed
 * as no "as" tag may have been supplied in the syntax.
 * <p>
 * Compare to {@link SelectExprElementNamedSpec} which carries a determined name.
 */
public class SelectExprElementUnnamedSpec implements MetaDefItem
{
    private transient ExprNode selectExpression;
    private String optionalAsName;

    /**
     * Ctor.
     * @param selectExpression - the expression node to evaluate for matching events
     * @param optionalAsName - the name of the item, null if not name supplied
     */
    public SelectExprElementUnnamedSpec(ExprNode selectExpression, String optionalAsName)
    {
        this.selectExpression = selectExpression;
        this.optionalAsName = optionalAsName;
    }

    /**
     * Returns the expression node representing the item in the select clause.
     * @return expression node for item
     */
    public ExprNode getSelectExpression()
    {
        return selectExpression;
    }

    /**
     * Returns the name of the item in the select clause.
     * @return name of item
     */
    public String getOptionalAsName()
    {
        return optionalAsName;
    }
}
