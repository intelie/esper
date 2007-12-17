package net.esper.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility class for copying serializable objects via object input and output streams.
 */
public class SerializableObjectCopier
{
    /**
     * Deep copies the input object.
     * @param orig is the object to be copied, must be serializable
     * @return copied object
     * @throws IOException if the streams returned an exception
     * @throws ClassNotFoundException if the de-serialize fails
     */
    public static Object copy(Object orig) throws IOException, ClassNotFoundException
    {
        SimpleByteArrayOutputStream fbos = new SimpleByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(fbos);
        out.writeObject(orig);
        out.flush();
        out.close();

        ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());
        return in.readObject();
    }
}
