package net.esper.core;

import net.esper.util.ManagedLock;
import net.esper.dispatch.Dispatchable;

public class EPStatementHandle
{
    private final ManagedLock statementLock;
    private final int hashCode;
    private Dispatchable optionalDispatchable;

    public EPStatementHandle(ManagedLock statementLock, String expressionText)
    {
        this.statementLock = statementLock;
        hashCode = expressionText.hashCode() ^ statementLock.hashCode();
    }

    public ManagedLock getStatementLock()
    {
        return statementLock;
    }

    public void setOptionalDispatchable(Dispatchable optionalDispatchable)
    {
        this.optionalDispatchable = optionalDispatchable;
    }

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
