package com.espertech.esper.eql.variable;

/**
 * A wrapper for a thread-local to hold the current version for variables visible for a thread, as well
 * as uncommitted values of variables for a thread. 
 */
public class VariableVersionThreadLocal
{
    private ThreadLocal<VariableVersionThreadEntry> perThreadVersion;

    /**
     * Ctor.
     */
    public VariableVersionThreadLocal()
    {
        perThreadVersion = new ThreadLocal<VariableVersionThreadEntry>()
        {
            protected synchronized VariableVersionThreadEntry initialValue()
            {
                return new VariableVersionThreadEntry(0, null);
            }
        };
    }

    /**
     * Returns the version and uncommitted values for the current thread.
     * @return entry for current thread
     */
    public VariableVersionThreadEntry getCurrentThread()
    {
        VariableVersionThreadEntry entry = perThreadVersion.get();
        if (entry == null)
        {
            entry = new VariableVersionThreadEntry(0, null);
            perThreadVersion.set(entry);
        }
        return entry;        
    }
}
