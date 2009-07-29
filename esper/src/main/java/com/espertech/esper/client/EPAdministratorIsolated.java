/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

public interface EPAdministratorIsolated
{
    /**
     * Create and starts an EPL statement.
     * <p>
     * The statement name is optimally a unique name. If a statement of the same name
     * has already been created, the engine assigns a postfix to create a unique statement name.
     * <p>
     * Accepts an application defined user data object associated with the statement. The <em>user
     * object</em> is a single, unnamed field that is stored with every statement.
     * Applications may put arbitrary objects in this field or a null value.
     * @param eplStatement is the query language statement
     * @param userObject is the application-defined user object
     * @return EPStatement to poll data from or to add listeners to
     * @throws com.espertech.esper.client.EPException when the expression was not valid
     */
    public EPStatement createEPL(String eplStatement, String statementName, Object userObject) throws EPException;

    /**
     * Returns the statement names of all started and stopped statements.
     * <p>
     * This excludes the name of destroyed statements.
     * @return statement names
     */
    public String[] getStatementNames();

    public void addStatement(EPStatement stmt);

    public void removeStatement(EPStatement stmt);

    public void addStatement(EPStatement[] statements);

    public void removeStatement(EPStatement[] stmt);
}
