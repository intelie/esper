/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

/**
 * Statement interface that provides methods to start, stop and destroy a statement as well as
 * get statement information such as statement name, expression text and current state.
 * <p>
 * Statements have 3 states: STARTED, STOPPED and DESTROYED.
 * <p>
 * In started state, statements are actively evaluating event streams according to the statement expression. Started
 * statements can be stopped and destroyed.
 * <p>
 * In stopped state, statements are inactive. Stopped statements can either be started, in which case
 * they begin to actively evaluate event streams, or destroyed.
 * <p>
 * Destroyed statements have relinguished all statement resources and cannot be started or stopped. 
 */
public interface EPStatement extends EPListenable, EPIterable
{
    /**
     * Start the statement.
     */
    public void start();

    /**
     * Stop the statement.
     */
    public void stop();

    /**
     * Destroy the statement releasing all statement resources.
     * <p>
     * A destroyed statement cannot be started again.
     */
    public void destroy();

    /**
     * Returns the statement's current state.
     * @return state enum
     */
    public EPStatementState getState();

    /**
     * Returns the underlying expression text.
     * @return expression text
     */
    public String getText();

    /**
     * Returns the statement name.
     * @return statement name
     */
    public String getName();
}

