/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.io.Serializable;

/**
 * Encapsulates the parsed select expressions in a select-clause in an EPL statement.
 */
public class SelectClauseSpecRaw implements MetaDefItem, Serializable
{
    private boolean isDistinct;
	private List<SelectClauseElementRaw> selectClauseElements;
    private static final long serialVersionUID = -6530225321409268186L;

    /**
     * Ctor.
     */
    public SelectClauseSpecRaw()
	{
		selectClauseElements = new ArrayList<SelectClauseElementRaw>();
        isDistinct = false;
    }

    /**
     * Adds an select expression within the select clause.
     * @param element is the expression to add
     */
    public void add(SelectClauseElementRaw element)
	{
		selectClauseElements.add(element);
	}

    /**
     * Adds select expressions within the select clause.
     * @param elements is the expressions to add
     */
    public void addAll(Collection<SelectClauseElementRaw> elements)
	{
		selectClauseElements.addAll(elements);
	}

    /**
     * Returns the list of select expressions.
     * @return list of expressions
     */
    public List<SelectClauseElementRaw> getSelectExprList()
	{
		return selectClauseElements;
	}

    public boolean isOnlyWildcard() {
        return (selectClauseElements.size() == 1) && (selectClauseElements.get(0) instanceof SelectClauseElementWildcard);         
    }

    /**
     * Returns true if the select clause contains at least one wildcard.
     * @return true if clause contains wildcard, false if not
     */
    public boolean isUsingWildcard()
    {
        for (SelectClauseElementRaw element : selectClauseElements)
        {
            if (element instanceof SelectClauseElementWildcard)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns indictor whether distinct or not.
     * @return distinct indicator
     */
    public boolean isDistinct()
    {
        return isDistinct;
    }

    /**
     * Sets the indictor whether distinct or not.
     * @param distinct indicator
     */
    public void setDistinct(boolean distinct)
    {
        isDistinct = distinct;
    }
}
