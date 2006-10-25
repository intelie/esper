package net.esper.client.logstate;

import java.io.*;
import java.util.ArrayList;

/**
 * Utility class for serializing and de-serializing {@link LogEntry}.
 */
public class LogEntrySerializer
{
    public static LogEntry[] deserialize(byte[] bytes) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream stream = new ObjectInputStream(byteStream);

        ArrayList<LogEntry> logEntries = new ArrayList<LogEntry>();
        LogEntry logEntry = null;
        do
        {
            try
            {
                Object obj = stream.readObject();
                logEntry = (LogEntry) obj;
                logEntries.add(logEntry);
            }
            catch (EOFException ex)
            {
                // expected
                logEntry = null;
            }
        }
        while (logEntry != null);

        stream.close();
        return logEntries.toArray(new LogEntry[0]);
    }

    public static byte[] serialize(LogEntry[] entries) throws IOException
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(byteStream);
        for (LogEntry entry : entries)
        {
            try
            {
                stream.writeObject(entry);
            }
            catch (NotSerializableException ex)
            {
                String message = "Error serializing object: " + entry.getState().getClass().getName();
                System.out.println(message);
                throw ex;
            }
        }
        stream.close();
        return byteStream.toByteArray();
    }
}
