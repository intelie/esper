package net.esper.core;

import net.esper.util.ManagedLock;

/**
 * Class exists once per statement and hold statement resource lock(s).
 * <p>
 * Use by {@link EPRuntimeImpl} for determining callback-statement affinity and locking of statement
 * resources. 
 */
public class EPStatementHandle
{
    private final ManagedLock statementLock;
    private final int hashCode;
    private EPStatementDispatch optionalDispatchable;

    /**
     * Ctor.
     * @param statementLock is the statement resource lock
     * @param expressionText is the expression
     */
    public EPStatementHandle(ManagedLock statementLock, String expressionText)
    {
        this.statementLock = statementLock;
        hashCode = expressionText.hashCode() ^ statementLock.hashCode();
    }

    /**
     * Returns statement resource lock.
     * @return lock
     */
    public ManagedLock getStatementLock()
    {
        return statementLock;
    }

    /**
     * Provides a callback for use when statement processing for filters and schedules is done,
     * for use by join statements that require an explicit indicator that all
     * joined streams results have been processed. 
     * @param optionalDispatchable is the instance for calling onto after statement callback processing
     */
    public void setOptionalDispatchable(EPStatementDispatch optionalDispatchable)
    {
        this.optionalDispatchable = optionalDispatchable;
    }

    /**
     * Invoked by {@link net.esper.client.EPRuntime} to indicate that a statements's
     * filer and schedule processing is done, and now it's time to process join results.
     */
    public void internalDispatch()
    {
        if (optionalDispatchable != null)
        {
            optionalDispatchable.execute();
        }
    }

    public boolean equals(Object otherObj)
    {
        if (!(otherObj instanceof EPStatementHandle))
        {
            return false;
        }

        EPStatementHandle other = (EPStatementHandle) otherObj;
        if (other.statementLock == this.statementLock)
        {
            return true;
        }
        return false;
    }

    public int hashCode()
    {
        return hashCode;
    }
}
