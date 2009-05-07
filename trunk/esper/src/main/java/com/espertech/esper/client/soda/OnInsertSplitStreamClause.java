/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;

/**
 * A clause to insert into zero, one or more streams based on criteria.
 */
public class OnInsertSplitStreamClause extends OnClause
{
    private static final long serialVersionUID = 0L;

    private boolean isFirst;
    private List<OnInsertSplitStreamItem> items;

    public static OnInsertSplitStreamClause create(boolean isFirst, List<OnInsertSplitStreamItem> items)
    {
        return new OnInsertSplitStreamClause(isFirst, items);
    }

    public static OnInsertSplitStreamClause create()
    {
        return new OnInsertSplitStreamClause(true, new ArrayList<OnInsertSplitStreamItem>());
    }

    /**
     * Ctor.
     */
    public OnInsertSplitStreamClause(boolean isFirst, List<OnInsertSplitStreamItem> items)
    {
        this.isFirst = isFirst;
        this.items = items;
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEPL(StringWriter writer)
    {
        String delimiter = "";
        for (OnInsertSplitStreamItem item : items)
        {
            writer.append(delimiter);
            item.getInsertInto().toEPL(writer);
            item.getSelectClause().toEPL(writer);
            if (item.getWhereClause() != null)
            {
                writer.append(" where ");
                item.getWhereClause().toEPL(writer);
            }
            delimiter = " ";
        }

        if (!isFirst)
        {
            writer.append(" output all");
        }
    }

    public boolean isFirst()
    {
        return isFirst;
    }

    public void setFirst(boolean first)
    {
        isFirst = first;
    }

    public List<OnInsertSplitStreamItem> getItems()
    {
        return items;
    }

    public void setItems(List<OnInsertSplitStreamItem> items)
    {
        this.items = items;
    }

    public void addItem(OnInsertSplitStreamItem item)
    {
        items.add(item);
    }
}
