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
    private ManagedLock statementLock;
    private final int hashCode;
    private EPStatementDispatch optionalDispatchable;
    // handles self-join (ie. statement where from-clause lists the same event type or a super-type more then once)
    // such that the internal dispatching must occur after both matches are processed
    private boolean canSelfJoin;
    private boolean hasVariables;
    private ManagedLock routedInsertStreamLock;

    /**
     * Ctor.
     * @param statementId is the statement id uniquely indentifying the handle
     * @param statementLock is the statement resource lock
     * @param expressionText is the expression
     */
    public EPStatementHandle(String statementId, ManagedLock statementLock, String expressionText, boolean hasVariables)
    {
        this.statementId = statementId;
        this.statementLock = statementLock;
        this.hasVariables = hasVariables;
        hashCode = expressionText.hashCode() ^ statementLock.hashCode();
    }

    /**
     * Set the statement's self-join flag to indicate the the statement may join to itself,
     * that is a single event may dispatch into multiple streams or patterns for the same statement,
     * requiring internal dispatch logic to not shortcut evaluation of all filters for the statement
     * within one lock, requiring the callback handle to be sorted.
     * @param canSelfJoin is true if the statement potentially self-joins, false if not
     */
    public void setCanSelfJoin(boolean canSelfJoin)
    {
        this.canSelfJoin = canSelfJoin;
    }

    /**
     * Set a insert-into stream lock to use for reserving order in generated streams.
     * @param routedInsertStreamLock is a lock to use to lock the stream when routing events into it
     */
    public void setRoutedInsertStreamLock(ManagedLock routedInsertStreamLock)
    {
        this.routedInsertStreamLock = routedInsertStreamLock;
    }

    /**
     * Returns the insert-into stream lock to use for reserving order in generated streams.
     * @return lock
     */
    public ManagedLock getRoutedInsertStreamLock()
    {
        return routedInsertStreamLock;
    }

    /**
     * Returns statement resource lock.
     * @return lock
     */
    public ManagedLock getStatementLock()
    {
        return statementLock;
    }

    public boolean isHasVariables()
    {
        return hasVariables;
    }

    /**
     * Sets the lock to use for the statement.
     * @param statementLock statement lock
     */
    public void setStatementLock(ManagedLock statementLock)
    {
        this.statementLock = statementLock;
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

    /**
     * Returns true if the statement potentially self-joins amojng the events it processes.
     * @return true for self-joins possible, false for not possible (most statements)
     */
    public boolean isCanSelfJoin()
    {
        return canSelfJoin;
    }
}
