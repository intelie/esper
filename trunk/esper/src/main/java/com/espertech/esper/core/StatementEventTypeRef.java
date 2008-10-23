package com.espertech.esper.core;

import java.util.Set;
import java.util.List;

public interface StatementEventTypeRef
{
    public boolean isInUse(String eventTypeAlias);
    public Set<String> getStatementNamesForType(String eventTypeAlias);

    public void addReferences(String statementName, Set<String> eventTypesReferenced);
    public void removeReferences(String statementName);
}
