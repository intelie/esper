package com.espertech.esper.epl.variable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Reads and writes variable values.
 * <p>
 * Works closely with {@link VariableService} in determining the version to read.  
 */
public class VariableReader
{
    private static final Log log = LogFactory.getLog(VariableReader.class);
    private final String variableName;
    private final int variableNumber;
    private final VariableVersionThreadLocal versionThreadLocal;
    private transient VersionedValueList<Object> versionsHigh;
    private transient VersionedValueList<Object> versionsLow;
    private final Class type;

    /**
     * Ctor.
     * @param versionThreadLocal service for returning the threads current version of variable
     * @param type is the type of the variable returned
     * @param variableName variable name
     * @param variableNumber number of the variable
     * @param versions a list of versioned-values to ask for the version
     */
    public VariableReader(VariableVersionThreadLocal versionThreadLocal, Class type, String variableName, int variableNumber, VersionedValueList<Object> versions)
    {
        this.variableName = variableName;
        this.variableNumber = variableNumber;
        this.versionThreadLocal = versionThreadLocal;
        this.type = type;
        this.versionsLow = versions;
        this.versionsHigh = null;
    }

    /**
     * Returns the variable name.
     * @return variable name
     */
    public String getVariableName()
    {
        return variableName;
    }

    /**
     * Returns the variable number.
     * @return variable index number
     */
    public int getVariableNumber()
    {
        return variableNumber;
    }

    /**
     * Returns the type of the variable.
     * @return type
     */
    public Class getType()
    {
        return type;
    }

    /**
     * For roll-over (overflow) in version numbers, sets a new collection of versioned-values for the variable
     * to use when requests over the version rollover boundary are made.
     * @param versionsHigh the list of versions for roll-over
     */
    public void setVersionsHigh(VersionedValueList<Object> versionsHigh)
    {
        this.versionsHigh = versionsHigh;
    }

    /**
     * Sets a new list of versioned-values to inquire against, for use when version numbers roll-over.
     * @param versionsLow the list of versions for read
     */
    public void setVersionsLow(VersionedValueList<Object> versionsLow)
    {
        this.versionsLow = versionsLow;
    }

    /**
     * Returns the value of a variable.
     * <p>
     * Considers the version set via thread-local for the thread's atomic read of variable values.
     * @return value of variable at the version applicable for the thead
     */
    public Object getValue()
    {
        VariableVersionThreadEntry entry = versionThreadLocal.getCurrentThread();
        if (entry.getUncommitted() != null)
        {
            // Check existance as null values are allowed
            if (entry.getUncommitted().containsKey(variableNumber))
            {
                return entry.getUncommitted().get(variableNumber);
            }
        }

        int myVersion = entry.getVersion();
        VersionedValueList<Object> versions = versionsLow;
        if (myVersion >= VariableServiceImpl.ROLLOVER_READER_BOUNDARY)
        {
            if (versionsHigh != null)
            {
                versions = versionsHigh;
            }
        }
        return versions.getVersion(myVersion);
    }    
}
