package com.espertech.esper.core;

import java.util.Set;

/**
 * Service for maintaining references between statement name and event type.
 */
public interface StatementEventTypeRef
{
    /**
     * Returns true if the event type is listed as in-use by any statement, or false if not
     * @param eventTypeAlias alias
     * @return indicator whether type is in use
     */
    public boolean isInUse(String eventTypeAlias);

    /**
     * Returns the set of statement names that use a given event type name.
     * @param eventTypeAlias alias
     * @return set of statements or null if none found
     */
    public Set<String> getStatementNamesForType(String eventTypeAlias);

    /**
     * Add a reference from a statement name to a set of event types.
     * @param statementName name of statement
     * @param eventTypesReferenced types
     */
    public void addReferences(String statementName, Set<String> eventTypesReferenced);

    /**
     * Remove all references for a given statement.
     * @param statementName statement name
     */
    public void removeReferencesStatement(String statementName);

    /**
     * Remove all references for a given event type.
     * @param eventTypeAlias event type name
     */
    public void removeReferencesType(String eventTypeAlias);
}