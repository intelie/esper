package net.esper.eql.db;

import java.util.Arrays;

/**
 * Hold a raw SQL-statements parameter information that were specified in the form ${name}.
 */
public class SQLParameterDesc
{
    private final String[] parameters;
    private final String[] builtinIdentifiers;

    /**
     * Ctor.
     * @param parameters is the name of parameters
     * @param builtinIdentifiers is the names of built-in predefined values
     */
    public SQLParameterDesc(String[] parameters, String[] builtinIdentifiers)
    {
        this.parameters = parameters;
        this.builtinIdentifiers = builtinIdentifiers;
    }

    /**
     * Returns parameter names.
     * @return parameter names
     */
    public String[] getParameters()
    {
        return parameters;
    }

    /**
     * Returns built-in identifiers.
     * @return built-in identifiers
     */
    public String[] getBuiltinIdentifiers()
    {
        return builtinIdentifiers;
    }

    public String toString()
    {
        return "params=" + Arrays.toString(parameters) +
                " builtin=" + Arrays.toString(builtinIdentifiers);
    }
}
