/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A select-clause consists of selection expressions and optionally an indicator that a wildcard is selected and an
 * optional stream selector.
 */
public class SelectClause implements Serializable
{
    private static final long serialVersionUID = 0L;

    private StreamSelector streamSelector;
    private List<SelectClauseElement> selectList;
    private boolean isWildcard;

    /**
     * Creates a wildcard select-clause, additional expressions can still be added.
     * @return select-clause
     */
    public static SelectClause createWildcard()
    {
        return new SelectClause(StreamSelector.RSTREAM_ISTREAM_BOTH, true);
    }

    /**
     * Creates an empty select-clause to be added to via add methods.
     * @return select-clause
     */
    public static SelectClause create()
    {
        return new SelectClause(StreamSelector.RSTREAM_ISTREAM_BOTH, false);
    }

    /**
     * Creates a select-clause consisting of a list of property names.
     * @param propertyNames is the names of properties to select
     * @return select-clause
     */
    public static SelectClause create(String ...propertyNames)
    {
        return new SelectClause(StreamSelector.RSTREAM_ISTREAM_BOTH, propertyNames);
    }

    /**
     * Creates a wildcard select-clause, additional expressions can still be added.
     * @param streamSelector can be used to select insert or remove streams
     * @return select-clause
     */
    public static SelectClause createWildcard(StreamSelector streamSelector)
    {
        return new SelectClause(streamSelector, true);
    }

    /**
     * Creates an empty select-clause.
     * @param streamSelector can be used to select insert or remove streams
     * @return select-clause
     */
    public static SelectClause create(StreamSelector streamSelector)
    {
        return new SelectClause(streamSelector, false);
    }

    /**
     * Creates a select-clause consisting of a list of property names.
     * @param propertyNames is the names of properties to select
     * @param streamSelector can be used to select insert or remove streams
     * @return select-clause
     */
    public static SelectClause create(StreamSelector streamSelector, String ...propertyNames)
    {
        return new SelectClause(streamSelector, propertyNames);
    }

    /**
     * Ctor.
     * @param streamSelector selects the stream
     * @param isWildcard is true for wildcard selects
     */
    protected SelectClause(StreamSelector streamSelector, boolean isWildcard)
    {
        this.streamSelector = streamSelector;
        this.selectList = new ArrayList<SelectClauseElement>();
        this.isWildcard = isWildcard;
    }

    /**
     * Ctor.
     * @param streamSelector select the stream
     * @param propertyNames is a list of properties
     */
    public SelectClause(StreamSelector streamSelector, String ...propertyNames)
    {
        this(streamSelector, false);
        for (String name : propertyNames)
        {
            selectList.add(new SelectClauseElement(new PropertyValueExpression(name)));
        }
    }

    /**
     * Adds property names to be selected.
     * @param propertyNames is a list of property names to add
     * @return clause
     */
    public SelectClause add(String ...propertyNames)
    {
        for (String name : propertyNames)
        {
            selectList.add(new SelectClauseElement(new PropertyValueExpression(name)));
        }
        return this;
    }

    /**
     * Adds a single property name and an "as"-asName for the column.
     * @param propertyName name of property
     * @param asName is the "as"-asName for the column
     * @return clause
     */
    public SelectClause addWithAlias(String propertyName, String asName)
    {
        selectList.add(new SelectClauseElement(new PropertyValueExpression(propertyName), asName));
        return this;
    }

    /**
     * Adds an expression to the select clause.
     * @param expression to add
     * @return clause
     */
    public SelectClause add(Expression expression)
    {
        selectList.add(new SelectClauseElement(expression));
        return this;
    }

    /**
     * Adds an expression to the select clause and an "as"-asName for the column.
     * @param expression to add
     * @param asName is the "as"-alias for the column
     * @return clause
     */
    public SelectClause add(Expression expression, String asName)
    {
        selectList.add(new SelectClauseElement(expression, asName));
        return this;
    }

    /**
     * Returns the stream selector.
     * @return stream selector
     */
    public StreamSelector getStreamSelector()
    {
        return streamSelector;
    }

    /**
     * Returns the list of expressions in the select clause.
     * @return list of expressions with column aliases
     */
    public List<SelectClauseElement> getSelectList()
    {
        return selectList;
    }

    /**
     * Returns true is a wildcard is part of the select clause, or false if not.
     * @return true for wildcard
     */
    public boolean isWildcard()
    {
        return isWildcard;
    }

    /**
     * Sets the stream selector.
     * @param streamSelector stream selector to set
     */
    public void setStreamSelector(StreamSelector streamSelector)
    {
        this.streamSelector = streamSelector;
    }

    /**
     * Sets the list of expressions in the select clause.
     * @param selectList list of expressions with column aliases
     */
    public void setSelectList(List<SelectClauseElement> selectList)
    {
        this.selectList = selectList;
    }

    /**
     * Set to true if a wildcard is part of the select clause, or false if not.
     * @param wildcard true for wildcard
     */
    public void setWildcard(boolean wildcard)
    {
        isWildcard = wildcard;
    }    

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        writer.write("select ");

        if (streamSelector == StreamSelector.ISTREAM_ONLY)
        {
            writer.write("istream ");
        }
        else if (streamSelector == StreamSelector.RSTREAM_ONLY)
        {
            writer.write("rstream ");
        }

        String delimiter = "";
        if (isWildcard)
        {
            writer.write("*");
            delimiter = ", ";
        }

        for (SelectClauseElement element : selectList)
        {
            writer.write(delimiter);
            element.toEQL(writer);
            delimiter = ", ";
        }
        writer.write(' ');
    }
}
