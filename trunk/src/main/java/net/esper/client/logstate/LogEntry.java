package net.esper.client.logstate;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Log entry stores the log content for logging engine state.
 * <p>
 * A log entry is uniquely identified within an engine instance by the sequence number.
 * <p>
 * The log key identifies the granular semantic engine state within an engine. All log keys
 * taken together for one engine instance represent the full engine state.
 * Log entries with the same LogKey (equals semantics) are log content with
 * older of newer information for a particular engine state.
 */
public class LogEntry implements Serializable
{
    private long seqNo;
    private LogKey key;
    private LogEntryType type;
    private Serializable state;

    public LogEntry(long seqNo, LogKey key, LogEntryType type, Serializable state)
    {
        this.seqNo = seqNo;
        this.type = type;
        this.key = key;
        this.state = state;
    }

    public long getSeqNo()
    {
        return seqNo;
    }

    public LogEntryType getType()
    {
        return type;
    }

    public LogKey getKey()
    {
        return key;
    }

    public Serializable getState()
    {
        return state;
    }

    public String toString()
    {
        return "seqNo=" + seqNo +
                " type=" + type +
                " key=" + Arrays.toString(key.getArray()) +
                " state=" + state.toString();
    }

    public static String print(LogEntry[] entries)
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < entries.length; i++)
        {
            buffer.append(entries[i].toString() + "\n");
        }
        return buffer.toString();
    }
}
