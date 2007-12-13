package net.esper.eql.variable;

import net.esper.core.StatementExtensionSvcContext;
import net.esper.collection.Pair;

public interface VariableStateHandler
{
    public Pair<Boolean, Object> getHasState(String variableName, int variableNumber, Class type, StatementExtensionSvcContext statementExtContext);

    public void setState(String variableName, int variableNumber, Object newValue);
}
