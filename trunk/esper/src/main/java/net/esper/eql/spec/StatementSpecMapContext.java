package net.esper.eql.spec;

import net.esper.eql.core.EngineImportService;
import net.esper.eql.variable.VariableService;

import java.util.Map;
import java.util.HashMap;

public class StatementSpecMapContext
{
    private final EngineImportService engineImportService;
    private final VariableService variableService;

    private boolean hasVariables;

    public StatementSpecMapContext(EngineImportService engineImportService, VariableService variableService)
    {
        this.engineImportService = engineImportService;
        this.variableService = variableService;
    }

    public EngineImportService getEngineImportService()
    {
        return engineImportService;
    }

    public VariableService getVariableService()
    {
        return variableService;
    }

    public boolean isHasVariables()
    {
        return hasVariables;
    }

    public void setHasVariables(boolean hasVariables)
    {
        this.hasVariables = hasVariables;
    }
}
