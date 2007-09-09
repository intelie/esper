package net.esper.client;

/**
 * Precompiled statement that is prepared with substitution parameters and that
 * can be created and started efficiently multiple times with different actual values for parameters.
 * <p>
 * When a precompiled statement is prepared via the prepare method on {@link EPAdministrator},
 * it typically has one or more substitution parameters in the statement text,
 * for which the placeholder character is the question mark. This class provides methods to set
 * the actual value for the substitution parameter.
 * <p>
 * A precompiled statement can only be created and started when actual values for all
 * substitution parameters are set. 
 */
public interface EPPreparedStatement
{
    /**
     * Sets the value of the designated parameter using the given object.
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param value the object containing the input parameter value
     * @throws EPException if the substitution parameter could not be located
     */
    public void setObject(int parameterIndex, Object value) throws EPException;
}