/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import java.util.List;

/**
 * Specification for the merge statement insert-part.
 */
public class OnTriggerMergeInsertDesc
{
    private final List<String> columns;
    private final List<SelectClauseElementRaw> selectClause;
    private List<SelectClauseElementCompiled> selectClauseCompiled;

    public OnTriggerMergeInsertDesc(List<String> columns, List<SelectClauseElementRaw> selectClause) {
        this.columns = columns;
        this.selectClause = selectClause;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<SelectClauseElementRaw> getSelectClause() {
        return selectClause;
    }

    public void setSelectClauseCompiled(List<SelectClauseElementCompiled> selectClauseCompiled) {
        this.selectClauseCompiled = selectClauseCompiled;
    }

    public List<SelectClauseElementCompiled> getSelectClauseCompiled() {
        return selectClauseCompiled;
    }
}

