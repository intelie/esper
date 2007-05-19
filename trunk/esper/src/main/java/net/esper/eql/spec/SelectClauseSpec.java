/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.spec;

import net.esper.util.MetaDefItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the parsed select expressions in a select-clause in an EQL statement.
 */
public class SelectClauseSpec implements MetaDefItem
{
	private List<SelectExprElementRawSpec> selectList;
	private boolean isUsingWildcard;

    /**
     * Ctor.
     */
    public SelectClauseSpec()
	{
		selectList = new ArrayList<SelectExprElementRawSpec>();
	}

    /**
     * Ctor.
     * @param selectList for a populates list of select expressions
     */
    public SelectClauseSpec(List<SelectExprElementRawSpec> selectList)
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
    public void add(SelectExprElementRawSpec element)
	{
		selectList.add(element);
	}

    /**
     * Returns the list of select expressions.
     * @return list of expressions
     */
    public List<SelectExprElementRawSpec> getSelectList()
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
