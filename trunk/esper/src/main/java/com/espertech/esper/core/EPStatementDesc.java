package com.espertech.esper.core;

/**
 * Statement information for use to perform statement lifecycle management.
 */
public class EPStatementDesc
{
    private EPStatementSPI epStatement;
    private EPStatementStartMethod startMethod;
    private EPStatementStopMethod stopMethod;
    private String optInsertIntoStream;

    /**
     * Ctor.
     * @param epStatement the statement
     * @param startMethod the start method
     * @param stopMethod the stop method
     * @param optInsertIntoStream is the insert-into stream name, or null if not using insert-into
     */
    public EPStatementDesc(EPStatementSPI epStatement, EPStatementStartMethod startMethod, EPStatementStopMethod stopMethod, String optInsertIntoStream)
    {
        this.epStatement = epStatement;
        this.startMethod = startMethod;
        this.stopMethod = stopMethod;
        this.optInsertIntoStream = optInsertIntoStream;
    }

    /**
     * Returns the statement.
     * @return statement.
     */
    public EPStatementSPI getEpStatement()
    {
        return epStatement;
    }

    /**
     * Returns the start method.
     * @return start method
     */
    public EPStatementStartMethod getStartMethod()
    {
        return startMethod;
    }

    /**
     * Returns the stop method.
     * @return stop method
     */
    public EPStatementStopMethod getStopMethod()
    {
        return stopMethod;
    }

    /**
     * Return the insert-into stream name, or null if no insert-into
     * @return stream name
     */
    public String getOptInsertIntoStream()
    {
        return optInsertIntoStream;
    }

    /**
     * Sets the stop method.
     * @param stopMethod to set
     */
    public void setStopMethod(EPStatementStopMethod stopMethod)
    {
        this.stopMethod = stopMethod;
    }
}
