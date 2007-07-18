package net.esper.core;

import net.esper.client.EPException;
import net.esper.client.EPStatement;
import net.esper.eql.spec.StatementSpecRaw;

/**
 * Handles statement management.
 */
public interface StatementLifecycleSvc
{
    /**
     * Initialized the service before use.
     */
    public void init();

    /**
     * Create and start the statement.
     * @param statementSpec is the statement definition in bean object form, raw unvalidated and unoptimized.
     * @param expression is the expression text
     * @param isPattern is an indicator on whether this is a pattern statement and thus the iterator must return the last result,
     * versus for non-pattern statements the iterator returns view content.
     * @param optStatementName is an optional statement name, null if none was supplied
     * @return started statement
     */
    public EPStatement createAndStart(StatementSpecRaw statementSpec, String expression, boolean isPattern, String optStatementName);

    /**
     * Start statement by statement id.
     * @param statementId of the statement to start.
     */
    public void start(String statementId);

    /**
     * Stop statement by statement id.
     * @param statementId of the statement to stop.
     */
    public void stop(String statementId);

    /**
     * Destroy statement by statement id.
     * @param statementId statementId of the statement to destroy
     */
    public void destroy(String statementId);

    /**
     * Returns the statement by the given name, or null if no such statement exists.
     * @param name is the statement name
     * @return statement for the given name, or null if no such statement existed
     */
    public EPStatement getStatementByName(String name);

    /**
     * Returns an array of statement names. If no statement has been created, an empty array is returned.
     * <p>
     * Only returns started and stopped statements.
     * @return statement names
     */
    public String[] getStatementNames();

    /**
     * Starts all stopped statements. First statement to fail supplies the exception.
     * @throws EPException to indicate a start error.
     */
    public void startAllStatements() throws EPException;
        
    /**
     * Stops all started statements. First statement to fail supplies the exception.
     * @throws EPException to indicate a start error.
     */
    public void stopAllStatements() throws EPException;

    /**
     * Destroys all started statements. First statement to fail supplies the exception.
     * @throws EPException to indicate a start error.
     */
    public void destroyAllStatements() throws EPException;

    /**
     * Statements indicate that listeners have been added through this method.
     * @param statementId is the statement id for which listeners were added
     * @param listeners is the set of listeners after adding the new listener
     */
    public void updatedListeners(String statementId, EPStatementListenerSet listeners);
}
