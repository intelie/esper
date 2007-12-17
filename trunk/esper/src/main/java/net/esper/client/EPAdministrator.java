/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import net.esper.client.soda.EPStatementObjectModel;

/**
 * Administrative interface to the event stream processing engine. Includes methods to create patterns and EQL statements.
 */
public interface EPAdministrator
{
    /**
     * Create and starts an event pattern statement for the expressing string passed.
     * <p>
     * The engine assigns a unique name to the statement.
     * @param onExpression must follow the documented syntax for pattern statements
     * @return EPStatement to poll data from or to add listeners to
     * @throws EPException when the expression was not valid
     */
    public EPStatement createPattern(String onExpression) throws EPException;

    /**
     * Creates and starts an EQL statement.
     * <p>
     * The engine assigns a unique name to the statement. The returned statement is in started state.
     * @param eqlStatement is the query language statement
     * @return EPStatement to poll data from or to add listeners to
     * @throws EPException when the expression was not valid
     */
    public EPStatement createEQL(String eqlStatement) throws EPException;

    /**
     * Create and starts an event pattern statement for the expressing string passed and assign the name passed.
     * <p>
     * The statement name is optimally a unique name. If a statement of the same name
     * has already been created, the engine assigns a postfix to create a unique statement name. 
     * @param onExpression must follow the documented syntax for pattern statements
     * @param statementName is the name to assign to the statement for use in manageing the statement
     * @return EPStatement to poll data from or to add listeners to
     * @throws EPException when the expression was not valid
     */
    public EPStatement createPattern(String onExpression, String statementName) throws EPException;

    /**
     * Create and starts an EQL statement.
     * <p>
     * The statement name is optimally a unique name. If a statement of the same name
     * has already been created, the engine assigns a postfix to create a unique statement name.
     * @param eqlStatement is the query language statement
     * @param statementName is the name to assign to the statement for use in manageing the statement
     * @return EPStatement to poll data from or to add listeners to
     * @throws EPException when the expression was not valid
     */
    public EPStatement createEQL(String eqlStatement, String statementName) throws EPException;

    /**
     * Creates and starts an EQL statement.
     * <p>
     * The statement name is optimally a unique name. If a statement of the same name
     * has already been created, the engine assigns a postfix to create a unique statement name.
     * @param sodaStatement is the statement object model
     * @param statementName is the name to assign to the statement for use in managing the statement
     * @return EPStatement to poll data from or to add listeners to
     * @throws EPException when the expression was not valid
     */
    public EPStatement create(EPStatementObjectModel sodaStatement, String statementName) throws EPException;

    /**
     * Creates and starts an EQL statement.
     * @param sodaStatement is the statement object model
     * @return EPStatement to poll data from or to add listeners to
     * @throws EPException when the expression was not valid
     */
    public EPStatement create(EPStatementObjectModel sodaStatement) throws EPException;

    /**
     * Compiles a given EQL into an object model representation of the query.
     * @param eqlExpression is the statement text to compile
     * @return object model of statement
     * @throws EPException indicates compilation errors.
     */
    public EPStatementObjectModel compileEQL(String eqlExpression) throws EPException;

    /**
     * Prepares a statement for the given EQL, which can include substitution parameters marked via question mark '?'.
     * @param eqlExpression is the statement text to prepare
     * @return prepared statement
     * @throws EPException indicates compilation errors.
     */
    public EPPreparedStatement prepareEQL(String eqlExpression) throws EPException;

    /**
     * Prepares a statement for the given pattern, which can include substitution parameters marked via question mark '?'.
     * @param patternExpression is the statement text to prepare
     * @return prepared statement
     * @throws EPException indicates compilation errors.
     */
    public EPPreparedStatement preparePattern(String patternExpression) throws EPException;

    /**
     * Creates and starts a prepared statement.
     * <p>
     * The statement name is optimally a unique name. If a statement of the same name
     * has already been created, the engine assigns a postfix to create a unique statement name.
     * @param prepared is the prepared statement for which all substitution values have been provided
     * @param statementName is the name to assign to the statement for use in manageing the statement
     * @return EPStatement to poll data from or to add listeners to
     * @throws EPException when the prepared statement was not valid
     */
    public EPStatement create(EPPreparedStatement prepared, String statementName) throws EPException;

    /**
     * Creates and starts a prepared statement.
     * @param prepared is the prepared statement for which all substitution values have been provided
     * @return EPStatement to poll data from or to add listeners to
     * @throws EPException when the expression was not valid
     */
    public EPStatement create(EPPreparedStatement prepared) throws EPException;

    /**
     * Returns the statement by the given statement name. Returns null if a statement of that name has not
     * been created, or if the statement by that name has been destroyed.
     * @param name is the statement name to return the statement for
     * @return statement for the given name, or null if no such started or stopped statement exists
     */
    public EPStatement getStatement(String name);

    /**
     * Returns the statement names of all started and stopped statements.
     * <p>
     * This excludes the name of destroyed statements.
     * @return statement names
     */
    public String[] getStatementNames();

    /**
     * Starts all statements that are in stopped state. Statements in started state
     * are not affected by this method.
     * @throws EPException when an error occured starting statements. 
     */
    public void startAllStatements() throws EPException;

    /**
     * Stops all statements that are in started state. Statements in stopped state are not affected by this method.
     * @throws EPException when an error occured stopping statements
     */
    public void stopAllStatements() throws EPException;

    /**
     * Stops and destroys all statements.
     * @throws EPException when an error occured stopping or destroying statements
     */
    public void destroyAllStatements() throws EPException;

    /**
     * Returns configuration operations for runtime engine configuration.
     * @return runtime engine configuration operations
     */
    public ConfigurationOperations getConfiguration();
}
