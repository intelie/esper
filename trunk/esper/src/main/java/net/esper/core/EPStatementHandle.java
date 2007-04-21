package net.esper.core;

import net.esper.util.MetaDefItem;
import net.esper.util.ManagedLock;

/**
 * Class exists once per statement and hold statement resource lock(s).
 * <p>
 * Use by {@link EPRuntimeImpl} for determining callback-statement affinity and locking of statement
 * resources. 
 */
public class EPStatementHandle implements MetaDefItem
{
    private final String statementId;
    private final ManagedLock statementLock;
    private final int hashCode;
    private EPStatementDispatch optionalDispatchable;
    // handles self-join (ie. statement where from-clause lists the same event type or a super-type more then once)
    // such that the internal dispatching must occur after both matches are processed
    private boolean canSelfJoin;

    /**
     * Ctor.
     * @param statementId is the statement id uniquely indentifying the handle
     * @param statementLock is the statement resource lock
     * @param expressionText is the expression
     */
    public EPStatementHandle(String statementId, ManagedLock statementLock, String expressionText)
    {
        this.statementId = statementId;
        this.statementLock = statementLock;
        hashCode = expressionText.hashCode() ^ statementLock.hashCode();
    }

    public void setCanSelfJoin(boolean canSelfJoin)
    {
        this.canSelfJoin = canSelfJoin;
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
        if (other.statementId.equals(this.statementId))
        {
            return true;
        }
        return false;
    }

    public int hashCode()
    {
        return hashCode;
    }

    public boolean isCanSelfJoin()
    {
        return canSelfJoin;
    }
}
