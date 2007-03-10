package net.esper.util;

import net.esper.core.StatementLockFactory;

public interface ManagedLock
{
    public void acquireLock(StatementLockFactory statementLockFactory);
    public void releaseLock(StatementLockFactory statementLockFactory);
}
