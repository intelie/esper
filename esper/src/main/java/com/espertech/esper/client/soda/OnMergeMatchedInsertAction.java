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
import java.util.Collections;
import java.util.List;

public class OnMergeMatchedInsertAction implements OnMergeMatchedAction
{
    private List<String> columnNames = Collections.emptyList();
    private List<SelectClauseElement> selectList = Collections.emptyList();

    public OnMergeMatchedInsertAction(List<String> columnNames, List<SelectClauseElement> selectList) {
        this.columnNames = columnNames;
        this.selectList = selectList;
    }

    public OnMergeMatchedInsertAction() {
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<SelectClauseElement> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<SelectClauseElement> selectList) {
        this.selectList = selectList;
    }

    @Override
    public void toEPL(StringWriter writer) {
        writer.write("when not matched then insert");

        if (columnNames.size() > 0)
        {
            writer.write("(");
            String delimiter = "";
            for (String name : columnNames)
            {
                writer.write(delimiter);
                writer.write(name);
                delimiter = ", ";
            }
            writer.write(")");
        }
        writer.write(" select ");
        String delimiter = "";
        for (SelectClauseElement element : selectList)
        {
            writer.write(delimiter);
            element.toEPLElement(writer);
            delimiter = ", ";
        }
        writer.write(' ');
    }
}