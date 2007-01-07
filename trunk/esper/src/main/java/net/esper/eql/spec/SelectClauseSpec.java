package net.esper.eql.spec;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the parsed select expressions in a select-clause in an EQL statement.
 */
public class SelectClauseSpec
{
	private List<SelectExprElementUnnamedSpec> selectList;
	private boolean isUsingWildcard;

    /**
     * Ctor.
     */
    public SelectClauseSpec()
	{
		selectList = new ArrayList<SelectExprElementUnnamedSpec>();
	}

    /**
     * Ctor.
     * @param selectList for a populates list of select expressions
     */
    public SelectClauseSpec(List<SelectExprElementUnnamedSpec> selectList)
	{
		this.selectList = selectList;
	}

    /**
     * To indicate that a wildcard is one of the values in the list of expressions.
     * @param isUsingWildcard true if a wildcard is encountered
     */
    public void setIsUsingWildcard(boolean isUsingWildcard)
	{
		this.isUsingWildcard = isUsingWildcard;
	}

    /**
     * Adds an select expression within the select clause.
     * @param element is the expression to add 
     */
    public void add(SelectExprElementUnnamedSpec element)
	{
		selectList.add(element);
	}

    /**
     * Returns the list of select expressions.
     * @return list of expressions
     */
    public List<SelectExprElementUnnamedSpec> getSelectList()
	{
		return selectList;
	}

    /**
     * Returns true if a wildcard was found in the select clause, or false if not
     * @return true for wildcard found
     */
    public boolean isUsingWildcard()
	{
		return isUsingWildcard;
	}
}
