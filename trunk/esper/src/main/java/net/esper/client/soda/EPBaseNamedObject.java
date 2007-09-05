package net.esper.client.soda;

import net.esper.type.NumberSetParameter;
import net.esper.type.EQLParameterType;

import java.util.List;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * Base class for named engine objects such as views, patterns guards and observers.
 */
public abstract class EPBaseNamedObject implements Serializable
{
    private String namespace;
    private String name;
    private List<Object> parameters;

    /**
     * Ctor.
     * @param namespace is the namespace of the object, i.e. view namespace or pattern object namespace
     * @param name is the name of the object, such as the view name
     * @param parameters is the optional parameters to the view or pattern object, or empty list for no parameters
     */
    protected EPBaseNamedObject(String namespace, String name, List<Object> parameters)
    {
        this.namespace = namespace;
        this.name = name;
        this.parameters = parameters;
    }

    /**
     * Returns the object namespace name.
     * @return namespace name
     */
    public String getNamespace()
    {
        return namespace;
    }

    /**
     * Sets the object namespace name
     * @param namespace to set
     */
    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    /**
     * Returns the object name.
     * @return object name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the object name.
     * @param name is the object name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the object parameters.
     * @return parameters for object, empty list for no parameters
     */
    public List<Object> getParameters()
    {
        return parameters;
    }

    /**
     * Sets the parameters for the object.
     * @param parameters parameters for object, empty list for no parameters
     */
    public void setParameters(List<Object> parameters)
    {
        this.parameters = parameters;
    }

    /**
     * Writes the object in EQL-syntax in the format "namespace:name(parameter, parameter, ..., parameter)"
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        writer.write(namespace);
        writer.write(':');
        writer.write(name);

        String delimiter = "";
        writer.write('(');
        for (Object param : parameters)
        {
            writer.write(delimiter);
            if (param == null)
            {
                writer.write("null");
            }
            else if (param instanceof EQLParameterType)
            {
                EQLParameterType numSet = (EQLParameterType) param;
                numSet.toEQL(writer);
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
