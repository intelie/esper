package net.esper.eql.spec;

import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a single item in a SELECT-clause.
 */
public class SelectExprElementSpec
{
    private ExprNode selectExpression;
    private String asName;

    /**
     * Ctor.
     * @param selectExpression - the expression node to evaluate for matching events
     * @param optionalAsName - the name of the item, null if not name supplied
     */
    public SelectExprElementSpec(ExprNode selectExpression, String optionalAsName)
    {
        this.selectExpression = selectExpression;

        if (optionalAsName == null)
        {
            asName = selectExpression.toExpressionString();
        }
        else
        {
            asName = optionalAsName; 
        }
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
    public String getAsName()
    {
        return asName;
    }

    /**
     * Verify that each given name occurs exactly one.
     * @param selectionList is the list of select items to verify names
     * @throws net.esper.eql.expression.ExprValidationException thrown if a name occured more then once
     */
    public static void verifyNameUniqueness(List<SelectExprElementSpec> selectionList) throws ExprValidationException
    {
        Set<String> names = new HashSet<String>();
        for (SelectExprElementSpec element : selectionList)
        {
            if (names.contains(element.getAsName()))
            {
                throw new ExprValidationException("Property alias name '" + element.getAsName() + "' appears more then once in select clause");
            }
            names.add(element.getAsName());
        }
    }
}
