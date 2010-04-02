/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import com.espertech.esper.epl.spec.ColumnDesc;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Set;
import java.util.List;

/**
 * Represents a create-schema syntax for creating a new event type.
 */
public class CreateSchemaClause implements Serializable
{
    private static final long serialVersionUID = 0L;

    private String schemaName;
    private Set<String> types;
    private List<SchemaColumnDesc> columns;
    private Set<String> inherits;
    private boolean variant;

    /**
     * Ctor.
     */
    public CreateSchemaClause() {
    }

    public CreateSchemaClause(String schemaName, Set<String> types, boolean variant)
    {
        this.schemaName = schemaName;
        this.types = types;
        this.variant = variant;
    }

    public CreateSchemaClause(String schemaName, List<SchemaColumnDesc> columns, Set<String> inherits)
    {
        this.schemaName = schemaName;
        this.columns = columns;
        this.inherits = inherits;
    }

    public CreateSchemaClause(String schemaName, Set<String> types, List<SchemaColumnDesc> columns, Set<String> inherits, boolean variant)
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

    public void setSchemaName(String schemaName)
    {
        this.schemaName = schemaName;
    }

    public Set<String> getTypes()
    {
        return types;
    }

    public void setTypes(Set<String> types)
    {
        this.types = types;
    }

    public List<SchemaColumnDesc> getColumns()
    {
        return columns;
    }

    public void setColumns(List<SchemaColumnDesc> columns)
    {
        this.columns = columns;
    }

    public Set<String> getInherits()
    {
        return inherits;
    }

    public void setInherits(Set<String> inherits)
    {
        this.inherits = inherits;
    }

    public boolean isVariant()
    {
        return variant;
    }

    public void setVariant(boolean variant)
    {
        this.variant = variant;
    }

    /**
     * Render as EPL.
     * @param writer to output to
     */
    public void toEPL(StringWriter writer)
    {
        writer.append("create");
        if (variant) {
            writer.append(" variant");
        }
        writer.append(" schema ");
        writer.append(schemaName);
        writer.append(" as ");
        if ((types != null) && (!types.isEmpty())) {
            String delimiter = "";
            for (String type : types) {
                writer.append(delimiter);
                writer.append(type);
                delimiter = ", ";
            }
        }
        else {
            writer.append("(");
            String delimiter = "";
            for (SchemaColumnDesc col : columns) {
                writer.append(delimiter);
                col.toEPL(writer);
                delimiter = ", ";
            }
            writer.append(")");
        }

        if ((inherits != null) && (!inherits.isEmpty())) {
            writer.append(" inherits ");
            String delimiter = "";
            for (String name : inherits) {
                writer.append(delimiter);
                writer.append(name);
                delimiter = ", ";
            }
        }
    }
}
