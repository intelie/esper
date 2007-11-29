package net.esper.eql.variable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VariableReader
{
    private static final Log log = LogFactory.getLog(VariableReader.class);
    private final String variableName;
    private final int variableNumber;
    private final ThreadLocal<Integer> versionThreadLocal;
    private transient VersionedValueList<Object> versionsHigh;
    private transient VersionedValueList<Object> versionsLow;
    private final Class type;

    public VariableReader(ThreadLocal<Integer> versionThreadLocal, Class type, String variableName, int variableNumber, VersionedValueList<Object> versions)
    {
        this.variableName = variableName;
        this.variableNumber = variableNumber;
        this.versionThreadLocal = versionThreadLocal;
        this.type = type;
        this.versionsLow = versions;
        this.versionsHigh = null;
    }

    public int getVariableNumber()
    {
        return variableNumber;
    }

    public Class getType()
    {
        return type;
    }

    public void setVersionsHigh(VersionedValueList<Object> versionsHigh)
    {
        this.versionsHigh = versionsHigh;
    }

    public void setVersionsLow(VersionedValueList<Object> versionsLow)
    {
        this.versionsLow = versionsLow;
    }

    public Object getValue()
    {
        int myVersion = versionThreadLocal.get();

        VersionedValueList<Object> versions = versionsLow;
        if (myVersion >= VariableService.ROLLOVER_READER_BOUNDARY)
        {
            if (versionsHigh != null)
            {
                versions = versionsHigh;
            }
        }
        return versions.getVersion(myVersion);
    }    
}
