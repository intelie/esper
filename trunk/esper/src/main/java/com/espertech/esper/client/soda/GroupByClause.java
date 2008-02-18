/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;

/**
 * The group-by clause consists of a list of expressions that provide the grouped-by values.
 */
public class GroupByClause implements Serializable
{
    private static final long serialVersionUID = 0L;

    private List<Expression> groupByExpressions;

    /**
     * Creates an empty group-by clause, to add to via add methods.
     * @return group-by clause
     */
    public static GroupByClause create()
    {
        return new GroupByClause();
    }

    /**
     * Creates a group-by clause from property names.
     * @param properties a list of one or more property names
     * @return group-by clause consisting of the properties
     */
    public static GroupByClause create(String ...properties)
    {
        return new GroupByClause(properties);
    }

    /**
     * Creates a group-by clause from expressions.
     * @param expressions a list of one or more expressions
     * @return group-by clause consisting of the expressions
     */
    public static GroupByClause create(Expression ...expressions)
    {
        return new GroupByClause(expressions);
    }

    /**
     * Ctor - for use to create an expression tree, without child expression.
     * <p>
     * Use add methods to add child expressions to acts upon.
     */   
    public GroupByClause()
    {
        groupByExpressions = new ArrayList<Expression>();
    }

    /**
     * Ctor.
     * @param properties is a list of property names
     */
    public GroupByClause(String ...properties)
    {
        this();
        groupByExpressions.addAll(Expressions.toPropertyExpressions(properties));
    }

    /**
     * Ctor.
     * @param expressions list of expressions
     */
    public GroupByClause(Expression ...expressions)
    {
        this();
        for (int i = 0; i < expressions.length; i++)
        {
            groupByExpressions.add(expressions[i]);
        }
    }

    /**
     * Returns the expressions providing the grouped-by values.
     * @return expressions
     */
    public List<Expression> getGroupByExpressions()
    {
        return groupByExpressions;
    }

    /**
     * Sets the expressions providing the grouped-by values.
     * @param groupByExpressions is the expressions providing the grouped-by values
     */
    public void setGroupByExpressions(List<Expression> groupByExpressions)
    {
        this.groupByExpressions = groupByExpressions;
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEPL(StringWriter writer)
    {
        String delimiter = "";
        for (Expression child : groupByExpressions)
        {
            writer.write(delimiter);
            child.toEPL(writer);
            delimiter = ", ";
        }
    }
}
