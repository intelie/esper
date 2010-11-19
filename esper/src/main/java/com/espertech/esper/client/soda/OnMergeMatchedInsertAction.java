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
    private static final long serialVersionUID = 0L;

    private List<String> columnNames = Collections.emptyList();
    private List<SelectClauseElement> selectList = Collections.emptyList();
    private Expression optionalCondition;

    public OnMergeMatchedInsertAction(List<String> columnNames, List<SelectClauseElement> selectList, Expression optionalCondition) {
        this.columnNames = columnNames;
        this.selectList = selectList;
        this.optionalCondition = optionalCondition;
    }

    public OnMergeMatchedInsertAction() {
    }

    public Expression getOptionalCondition() {
        return optionalCondition;
    }

    public void setOptionalCondition(Expression optionalCondition) {
        this.optionalCondition = optionalCondition;
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
        writer.write("when not matched");

        if (optionalCondition != null) {
            writer.write(" and ");
            optionalCondition.toEPL(writer, ExpressionPrecedenceEnum.MINIMUM);
        }
        writer.write(" then insert");

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
    }
}