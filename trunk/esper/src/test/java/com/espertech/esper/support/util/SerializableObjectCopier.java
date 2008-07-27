package com.espertech.esper.support.util;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class SerializableObjectCopier
{
    public static Object copy(Object orig) throws IOException, ClassNotFoundException
    {
        SimpleByteArrayOutputStream fbos = new SimpleByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(fbos);
        out.writeObject(orig);
        out.flush();
        out.close();

        ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());
        Object result = in.readObject();
        return result;
    }
}
