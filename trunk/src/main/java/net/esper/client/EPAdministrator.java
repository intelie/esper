package net.esper.client;

import java.util.List;

/**
 * Administrative interface to the event stream processing engine. Includes methods to create patterns and EQL statements.
 */
public interface EPAdministrator
{
    /**
     * Create a event pattern statement for the expressing string passed.
     * @param onExpression must follow the documented syntax for pattern statements
     * @return EPStatement to poll data from or to add listeners to
     * @throws EPException when the expression was not valid
     */
    public EPStatement createPattern(String onExpression) throws EPException;

    /**
     * Create a query language statement.
     * @param eqlStatement is the query language statement
     * @return EPStatement to poll data from or to add listeners to
     * @throws EPException when the expression was not valid
     */
    public EPStatement createEQL(String eqlStatement) throws EPException;

    /**
     * Returns a list of started and stopped statements.  
     * @return list of statements
     */
    public List<EPStatement> getStatements();
}
