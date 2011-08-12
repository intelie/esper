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

import java.io.Serializable;
import java.util.List;

/**
 * Specification for creating a named window.
 */
public class CreateIndexDesc implements MetaDefItem, Serializable
{
    private static final long serialVersionUID = -6758785746637089810L;
    
    private final String indexName;
    private final String windowName;
    private final List<CreateIndexItem> columns;

    /**
     * Ctor.
     * @param indexName index name
     * @param windowName window name
     * @param columns properties to index
     */
    public CreateIndexDesc(String indexName, String windowName, List<CreateIndexItem> columns)
    {
        this.indexName = indexName;
        this.windowName = windowName;
        this.columns = columns;
    }

    /**
     * Returns index name.
     * @return index name
     */
    public String getIndexName()
    {
        return indexName;
    }

    /**
     * Returns window name.
     * @return window name
     */
    public String getWindowName()
    {
        return windowName;
    }

    /**
     * Returns columns.
     * @return columns
     */
    public List<CreateIndexItem> getColumns()
    {
        return columns;
    }
}
