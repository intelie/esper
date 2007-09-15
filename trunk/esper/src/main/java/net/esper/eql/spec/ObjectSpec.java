package net.esper.eql.spec;

import net.esper.util.MetaDefItem;

import java.util.List;

/**
 * Encapsulates the information required to specify an object identification and construction.
 * <p>
 * Abstract class for use with any object, such as views, pattern guards or pattern observers.
 * <p>
 * A object construction specification can be equal to another specification. This information can be
 * important to determine reuse of any object.
 */
public abstract class ObjectSpec implements MetaDefItem
{
    private final String objectNamespace;
    private final String objectName;
    private final List<Object> objectParameters;

    /**
     * Constructor.
     * @param namespace if the namespace the object is in
     * @param objectName is the name of the object
     * @param objectParameters is a list of values representing the object parameters
     */
    public ObjectSpec(String namespace, String objectName, List<Object> objectParameters)
    {
        this.objectNamespace = namespace;
        this.objectName = objectName;
        this.objectParameters = objectParameters;
    }

    /**
     * Returns namespace for view object.
     * @return namespace
     */
    public String getObjectNamespace()
    {
        return objectNamespace;
    }

    /**
     * Returns the object name.
     * @return object name
     */
    public final String getObjectName()
    {
        return objectName;
    }

    /**
     * Returns the list of object parameters.
     * @return list of values representing object parameters
     */
    public final List<Object> getObjectParameters()
    {
        return objectParameters;
    }

    public final boolean equals(final Object otherObject)
    {
        if (otherObject == this)
        {
            return true;
        }

        if (otherObject == null)
        {
            return false;
        }

        if (getClass() != otherObject.getClass())
        {
            return false;
        }

        final ObjectSpec other = (ObjectSpec) otherObject;
        if (!(this.objectName).equals(other.objectName))
        {
            return false;
        }

        if (objectParameters.size() != other.objectParameters.size())
        {
            return false;
        }

        // Compare object parameter by object parameter
        int index = 0;
        for (Object thisParam : objectParameters)
        {
            Object otherParam = other.objectParameters.get(index);
            index++;

            if (!(thisParam.equals(otherParam)))
            {
                return false;
            }
        }

        return true;
    }

    public int hashCode()
    {
        int result;
        result = objectNamespace.hashCode();
        result = 31 * result + objectName.hashCode();
        return result;
    }

    public final String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("objectName=" + objectName + "  objectParameters=(");
        char delimiter = ' ';

        if (objectParameters != null)
        {
            for (Object param : objectParameters)
            {
                buffer.append(delimiter + param.toString());
                delimiter = ',';
            }
        }

        buffer.append(')');

        return buffer.toString();
    }
}
