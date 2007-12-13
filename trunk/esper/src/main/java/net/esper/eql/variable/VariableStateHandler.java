package net.esper.eql.variable;

import net.esper.core.StatementExtensionSvcContext;
import net.esper.collection.Pair;

/**
 * Interface for a plug-in to {@link VariableService} to handle variable persistent state.
 */
public interface VariableStateHandler
{
    /**
     * Returns the current variable state plus Boolean.TRUE if there is a current state since the variable
     * may have the value of null; returns Boolean.FALSE and null if there is no current state
     * @param variableName variable name
     * @param variableNumber number of the variable
     * @param type type of the variable
     * @param statementExtContext for caches etc.
     * @return indicator whether the variable is known and it's state, or whether it doesn't have state (false)
     */
    public Pair<Boolean, Object> getHasState(String variableName, int variableNumber, Class type, StatementExtensionSvcContext statementExtContext);

    /**
     * Sets the new variable value
     * @param variableName name of the variable
     * @param variableNumber number of the variable
     * @param newValue new variable value, null values allowed
     */
    public void setState(String variableName, int variableNumber, Object newValue);
}
