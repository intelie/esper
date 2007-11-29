package net.esper.eql.variable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class VersionedValueList<T>
{
    private static final Log log = LogFactory.getLog(VersionedValueList.class);

    // Variable name and read lock; read lock used when older version then the prior version is requested
    private final String name;
    private final Lock readLock;
    private final int highWatermark;    // used for removing older versions
    private final int lowWatermark;

    // Hold the current and prior version for no-lock reading
    private transient CurrentValue<T> currentAndPriorValue;

    // Holds the older versions
    private ArrayList<VersionedValue<T>> olderVersions;

    public VersionedValueList(String name, int initialVersion, T initialValue, Lock readLock, int highWatermark, int lowWatermark)
    {
        this.name = name;
        this.readLock = readLock;
        this.highWatermark = highWatermark;
        this.lowWatermark = lowWatermark;
        this.olderVersions = new ArrayList<VersionedValue<T>>();

        currentAndPriorValue = new CurrentValue<T>(new VersionedValue<T>(initialVersion, initialValue),
                                                   new VersionedValue<T>(-1, null));
    }

    /**
     * Retrieve a value for the given version or older then then given version.
     * <p>
     * The implementaton only locks the read lock if an older version the the prior version is requested.
     * @param versionAndOlder the version we are looking for
     * @return value for the version or the next older version, ignoring newer versions
     */
    public T getVersion(int versionAndOlder)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".getVersion Thread " + Thread.currentThread().getId() + " for '" + name + "' retrieving version " + versionAndOlder + " or older");
        }

        T resultValue = null;
        CurrentValue<T> current = currentAndPriorValue;

        if (current.getCurrentVersion().getVersion() <= versionAndOlder)
        {
            resultValue = current.getCurrentVersion().getValue();
        }
        else if ((current.getPriorVersion().getVersion() != -1) &&
            (current.getPriorVersion().getVersion() <= versionAndOlder))
        {
            resultValue = current.getPriorVersion().getValue();
        }
        else
        {
            readLock.lock();

            try
            {
                current = currentAndPriorValue;

                if (current.getCurrentVersion().getVersion() <= versionAndOlder)
                {
                    resultValue = current.getCurrentVersion().getValue();
                }
                else if ((current.getPriorVersion().getVersion() != -1) &&
                    (current.getPriorVersion().getVersion() <= versionAndOlder))
                {
                    resultValue = current.getPriorVersion().getValue();
                }
                else
                {
                    boolean found = false;
                    for (int i = olderVersions.size() - 1; i >= 0; i--)
                    {
                        VersionedValue<T> entry = olderVersions.get(i);
                        if (entry.getVersion() <= versionAndOlder)
                        {
                            resultValue = entry.getValue();
                            found = true;
                            break;
                        }
                    }

                    if (!found)
                    {
                        int currentVersion = current.getCurrentVersion().getVersion();
                        int priorVersion = current.getPriorVersion().getVersion();
                        Integer oldestVersion = (olderVersions.size() > 0) ? olderVersions.get(0).getVersion() : null;
                        T oldestValue = olderVersions.get(0).getValue();

                        log.warn("Variable value for version '" + versionAndOlder + "' and older could not be found" +
                            " (currentVersion=" + currentVersion + " priorVersion=" + priorVersion + " oldestVersion=" + oldestVersion + " numOldVersions=" + olderVersions.size() + " oldestValue=" + oldestValue +")");
                        return oldestValue;
                    }
                }
            }
            finally
            {
                readLock.unlock();
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug(".getVersion Thread " + Thread.currentThread().getId() +  " for '" + name + " version " + versionAndOlder + " or older result is " + resultValue);
        }

        return resultValue;
    }

    public void addValue(int version, T value)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".addValue Thread " + Thread.currentThread().getId() + " for '" + name + "' adding version " + version + " at value " + value);
        }

        // push to prior if not already used
        if (currentAndPriorValue.getPriorVersion().getVersion() == -1)
        {
            currentAndPriorValue = new CurrentValue<T>(new VersionedValue<T>(version, value),
              currentAndPriorValue.getCurrentVersion());
            return;
        }

        // add to list
        VersionedValue<T> priorVersion = currentAndPriorValue.getPriorVersion();
        olderVersions.add(priorVersion);

        // check watermarks
        if (olderVersions.size() >= highWatermark)
        {
            while(olderVersions.size() > lowWatermark)
            {
                olderVersions.remove(0);
            }
        }
        
        currentAndPriorValue = new CurrentValue<T>(new VersionedValue<T>(version, value),
                                                   currentAndPriorValue.getCurrentVersion());
    }

    protected CurrentValue<T> getCurrentAndPriorValue()
    {
        return currentAndPriorValue;
    }

    protected ArrayList<VersionedValue<T>> getOlderVersions()
    {
        return olderVersions;
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Variable '").append(name).append("' ");
        buffer.append(" current=").append(currentAndPriorValue.getCurrentVersion().toString());
        buffer.append(" prior=").append(currentAndPriorValue.getCurrentVersion().toString());

        int count = 0;
        for (VersionedValue<T> old : olderVersions)
        {
            buffer.append(" old(").append(count).append(")=").append(old.toString()).append("\n");
            count++;
        }
        return buffer.toString();
    }
}
