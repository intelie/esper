package com.espertech.esper.epl.metric;

import com.espertech.esper.client.metric.StatementMetric;
import com.espertech.esper.util.ManagedReadWriteLock;

import java.util.HashSet;
import java.util.Set;

// Changes to StatementMetric instances must be done in a read-lock:
//   getRwLock.readLock.lock()
//   metric = getAddMetric(index)
//   metric.accountFor(cpu, wall, etc)
//   getRwLock.readLock.unlock()
// All other changes are done under write lock for this class.

// backed by array:
//      grows 50% each time expanded
//      maintain free/busy list of statement ids
//      maintain element number of last used element
//      flush copies all array, thereby keeping array size
//      statement ids are only removed on the next flush
public class StatementMetricArray
{
    private final String engineURI;

    // Lock
    //  Read lock applies to each current transaction on a StatementMetric instance
    //  Write lock applies to flush and to add a new statement
    private final ManagedReadWriteLock rwLock;

    // Active statements
    private String[] statementNames;

    // Count of active statements
    private int currentLastElement;

    // Flushed metric per statement
    private volatile StatementMetric[] metrics;

    // Statements ids to remove with the next flush
    private Set<String> removedStatementNames;

    public StatementMetricArray(String engineURI, String name, int initialSize)
    {
        this.engineURI = engineURI;
        metrics = new StatementMetric[initialSize];
        statementNames = new String[initialSize];
        currentLastElement = -1;
        rwLock = new ManagedReadWriteLock("StatementMetricArray-" + name, true);
        removedStatementNames = new HashSet<String>();
    }

    public void removeStatement(String statementName)
    {
        rwLock.acquireWriteLock();
        try
        {
            removedStatementNames.add(statementName);
        }
        finally
        {
            rwLock.releaseWriteLock();
        }
    }

    public int addStatementGetIndex(String statementName)
    {
        rwLock.acquireWriteLock();
        try
        {
            // see if there is room
            if ((currentLastElement + 1) < metrics.length)
            {
                currentLastElement++;
                statementNames[currentLastElement] = statementName;
                return currentLastElement;
            }

            // no room, try to use an existing slot of a removed statement
            for (int i = 0; i < statementNames.length; i++)
            {
                if (statementNames[i] == null)
                {
                    statementNames[i] = statementName;
                    if ((i + 1) > currentLastElement)
                    {
                        currentLastElement = i;
                    }
                    return i;
                }
            }

            // still no room, expand storage by 50%
            int newSize = (int) (metrics.length * 1.5);
            String[] newStatementNames = new String[newSize];
            StatementMetric[] newMetrics = new StatementMetric[newSize];
            System.arraycopy(statementNames, 0, newStatementNames, 0, statementNames.length);
            System.arraycopy(metrics, 0, newMetrics, 0, metrics.length);

            statementNames = newStatementNames;
            metrics = newMetrics;

            currentLastElement++;
            statementNames[currentLastElement] = statementName;

            return currentLastElement;
        }
        finally
        {
            rwLock.releaseWriteLock();
        }
    }

    public StatementMetric[] flushMetrics()
    {
        rwLock.acquireWriteLock();
        try
        {
            boolean isEmpty = false;
            if (currentLastElement == -1)
            {
                isEmpty = true;
            }

            // remove statement ids that disappeared during the interval
            if (currentLastElement > -1)
            {
                for (int i = 0; i <= currentLastElement; i++)
                {
                    if (removedStatementNames.contains(statementNames[i]))
                    {
                        statementNames[i] = null;
                    }
                }
            }

            // adjust last used element
            while ((currentLastElement != -1) && (statementNames[currentLastElement] == null))
            {
                currentLastElement--;
            }

            if (isEmpty)
            {
                return null;    // no copies made if empty collection
            }
            
            // perform flush
            StatementMetric[] newMetrics = new StatementMetric[metrics.length];
            StatementMetric[] oldMetrics = metrics;
            metrics = newMetrics;
            return oldMetrics;
        }
        finally
        {
            rwLock.releaseWriteLock();
        }
    }

    public ManagedReadWriteLock getRwLock()
    {
        return rwLock;
    }

    public StatementMetric getAddMetric(int index)
    {
        StatementMetric metric = metrics[index];
        if (metric == null)
        {
            metric = new StatementMetric(engineURI, statementNames[index]);
            metrics[index] = metric;
        }
        return metric;
    }

    public int size()
    {
        return currentLastElement + 1;
    }
}
