package net.esper.client.soda;

import java.util.List;
import java.io.Serializable;
import java.io.StringWriter;

public abstract class EPBaseNamedObject implements Serializable
{
    private String namespace;
    private String name;
    private List<Object> parameters;

    protected EPBaseNamedObject(String namespace, String name, List<Object> parameters)
    {
        this.namespace = namespace;
        this.name = name;
        this.parameters = parameters;
    }

    public String getNamespace()
    {
        return namespace;
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Object> getParameters()
    {
        return parameters;
    }

    public void setParameters(List<Object> parameters)
    {
        this.parameters = parameters;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write(namespace);
        writer.write(':');
        writer.write(name);

        if ((parameters != null) && (parameters.size() != 0))
        {
            String delimiter = "";
            writer.write('(');
            for (Object param : parameters)
            {
                writer.write(delimiter);
                if (param == null)
                {
                    writer.write("null");
                }
                else
                {
                    writer.write(param.toString());
                }
                delimiter = ", ";
            }
            writer.write(')');
        }
    }
}
