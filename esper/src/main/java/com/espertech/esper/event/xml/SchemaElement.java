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

package com.espertech.esper.event.xml;

/**
 * Schema element is a simple or complex element.
 */
public interface SchemaElement extends SchemaItem
{
    /**
     * Returns the namespace.
     * @return namespace
     */
    public String getNamespace();

    /**
     * Returns the name.
     * @return name
     */
    public String getName();

    /**
     * Returns true for unbounded or max>1
     * @return array indicator
     */
    public boolean isArray();
}
