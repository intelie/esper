package net.esper.client.soda;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class View extends EPBaseNamedObject
{
    public static View create(String namespace, String name)
    {
        return new View(namespace, name, new ArrayList<Object>());
    }

    public static View create(String namespace, String name, List<Object> parameters)
    {
        return new View(namespace, name, parameters);
    }

    public static View create(String namespace, String name, Object ...parameters)
    {
        return new View(namespace, name, Arrays.asList(parameters));
    }

    public View(String namespace, String name, List<Object> parameters)
    {
        super(namespace, name, parameters);
    }
}
