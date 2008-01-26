package net.esper.eql.spec;

import net.esper.eql.core.EngineImportService;
import net.esper.eql.variable.VariableService;

import java.util.Map;
import java.util.HashMap;

/**
 * Context for mapping a SODA statement to a statement specification, or multiple for subqueries,
 * and obtaining certain optimization information from a statement.
 */
public class StatementSpecMapContext
{
    private final EngineImportService engineImportService;
    private final VariableService variableService;

    private boolean hasVariables;

    /**
     * Ctor.
     * @param engineImportService engine imports
     * @param variableService variable names
     */
    public StatementSpecMapContext(EngineImportService engineImportService, VariableService variableService)
    {
        this.engineImportService = engineImportService;
        this.variableService = variableService;
    }

    /**
     * Returns the engine import service.
     * @return service
     */
    public EngineImportService getEngineImportService()
    {
        return engineImportService;
    }

    /**
     * Returns the variable service.
     * @return service
     */
    public VariableService getVariableService()
    {
        return variableService;
    }

    /**
     * Returns true if a statement has variables.
     * @return true for variables found
     */
    public boolean isHasVariables()
    {
        return hasVariables;
    }

    /**
     * Set to true to indicate that a statement has variables.
     * @param hasVariables true for variables, false for none
     */
    public void setHasVariables(boolean hasVariables)
    {
        this.hasVariables = hasVariables;
    }
}
