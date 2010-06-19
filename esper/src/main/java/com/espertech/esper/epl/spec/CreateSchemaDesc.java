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
import java.util.Set;

/**
 * Specification for creating an event type/schema.
 */
public class CreateSchemaDesc implements MetaDefItem, Serializable
{
    private static final long serialVersionUID = 8400789369907593190L;
    
    private final String schemaName;
    private final Set<String> types;
    private final List<ColumnDesc> columns;
    private final Set<String> inherits;
    private final boolean variant;

    public CreateSchemaDesc(String schemaName, Set<String> types, List<ColumnDesc> columns, Set<String> inherits, boolean variant)
    {
        this.schemaName = schemaName;
        this.types = types;
        this.columns = columns;
        this.inherits = inherits;
        this.variant = variant;
    }

    public String getSchemaName()
    {
        return schemaName;
    }

    public List<ColumnDesc> getColumns()
    {
        return columns;
    }

    public Set<String> getInherits()
    {
        return inherits;
    }

    public Set<String> getTypes()
    {
        return types;
    }

    public boolean isVariant()
    {
        return variant;
    }
}
