/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

/**
 * Specification for the merge statement.
 */
public class OnTriggerMergeDesc extends OnTriggerWindowDesc
{
    private OnTriggerMergeInsertDesc insert;
    private OnTriggerMergeUpdateDesc update;

    public OnTriggerMergeDesc(String windowName, String optionalAsName, OnTriggerMergeInsertDesc insert, OnTriggerMergeUpdateDesc update) {
        super(windowName, optionalAsName, OnTriggerType.ON_MERGE);
        this.insert = insert;
        this.update = update;
    }

    public OnTriggerMergeInsertDesc getInsert() {
        return insert;
    }

    public OnTriggerMergeUpdateDesc getUpdate() {
        return update;
    }
}

