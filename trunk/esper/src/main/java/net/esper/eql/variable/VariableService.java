package net.esper.eql.variable;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * Variables service for reading and writing variables, and for setting a version number for the current thread to
 * consider variables for.
 * <p>
 * See implementation class for further details.
 */
public interface VariableService
{
    public void setLocalVersion();

    public ReadWriteLock getReadWriteLock();

    public void createNewVariable(String variableName, Class type, Object value)
            throws VariableExistsException, VariableTypeException;

    public VariableReader getReader(String variableName);
    
    public void write(int variableNumber, Object newValue);
    public void commit();
    public void rollback();
}
