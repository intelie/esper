/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.core;

/**
 * Type of the statement.
 */
public enum StatementType
{
    /**
     * Pattern statement.
     */
    PATTERN,

    /**
     * Select statement that may contain one or more patterns.
     */
    SELECT,

    /**
     * Insert-into statement.
     */
    INSERT_INTO,

    /**
     * Create a named window statement.
     */
    CREATE_WINDOW,

    /**
     * Create a variable statement.
     */
    CREATE_VARIABLE,

    /**
     * Create-schema statement.
     */
    CREATE_SCHEMA,

    /**
     * Create-index statement.
     */
    CREATE_INDEX,

    /**
     * On-merge statement.
     */
    ON_MERGE,

    /**
     * On-merge statement.
     */
    ON_SPLITSTREAM,

    /**
     * On-delete statement.
     */
    ON_DELETE,

    /**
     * On-select statement.
     */
    ON_SELECT,

    /**
     * On-insert statement.
     */
    ON_INSERT,

    /**
     * On-set statement.
     */
    ON_SET,

    /**
     * On-update statement.
     */
    ON_UPDATE,

    /**
     * Update statement.
     */
    UPDATE;
}
