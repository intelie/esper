package com.espertech.esper.core;

import com.espertech.esper.util.ManagedReadWriteLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Service for holding references between statements and their event type use.
 */
public class StatementEventTypeRefImpl implements StatementEventTypeRef
{
    private static final Log log = LogFactory.getLog(StatementEventTypeRefImpl.class);

    private final ManagedReadWriteLock mapLock;
    private final HashMap<String, Set<String>> typeToStmt;
    private final HashMap<String, Set<String>> stmtToType;

    /**
     * Ctor.
     */
    public StatementEventTypeRefImpl()
    {
        typeToStmt = new HashMap<String, Set<String>>();
        stmtToType = new HashMap<String, Set<String>>();
        mapLock = new ManagedReadWriteLock("StatementEventTypeRefImpl", false);
    }

    public void addReferences(String statementName, Set<String> eventTypesReferenced)
    {
        if (eventTypesReferenced.isEmpty())
        {
            return;    
        }

        mapLock.acquireWriteLock();
        try
        {
            for (String ref : eventTypesReferenced)
            {
                addReference(statementName, ref);
            }
        }
        finally
        {
            mapLock.releaseWriteLock();            
        }
    }

    public void removeReferencesStatement(String statementName)
    {
        mapLock.acquireWriteLock();
        try
        {
            Set<String> types = stmtToType.remove(statementName);
            if (types != null)
            {
                for (String type : types)
                {
                    removeReference(statementName, type);
                }
            }
        }
        finally
        {
            mapLock.releaseWriteLock();
        }
    }

    public void removeReferencesType(String alias)
    {
        mapLock.acquireWriteLock();
        try
        {
            Set<String> statementNames = typeToStmt.remove(alias);
            if (statementNames != null)
            {
                for (String statementName : statementNames)
                {
                    removeReference(statementName, alias);
                }
            }
        }
        finally
        {
            mapLock.releaseWriteLock();
        }
    }

    public boolean isInUse(String eventTypeAlias)
    {
        mapLock.acquireReadLock();
        try {
            return typeToStmt.containsKey(eventTypeAlias);
        }
        finally {
            mapLock.releaseReadLock();
        }
    }

    public Set<String> getStatementNamesForType(String eventTypeAlias)
    {
        mapLock.acquireReadLock();
        try {
            Set<String> types = typeToStmt.get(eventTypeAlias);
            if (types == null)
            {
                return Collections.EMPTY_SET;
            }
            return Collections.unmodifiableSet(types);
        }
        finally {
            mapLock.releaseReadLock();
        }
    }
    
    private void addReference(String statementName, String eventTypeAlias)
    {
        // add to types
        Set<String> statements = typeToStmt.get(eventTypeAlias);
        if (statements == null)
        {
            statements = new HashSet<String>();
            typeToStmt.put(eventTypeAlias, statements);
        }
        statements.add(statementName);

        // add to statements
        Set<String> types = stmtToType.get(statementName);
        if (types == null)
        {
            types = new HashSet<String>();
            stmtToType.put(statementName, types);
        }
        types.add(eventTypeAlias);
    }

    private void removeReference(String statementName, String eventTypeAlias)
    {
        // remove from types
        Set<String> statements = typeToStmt.get(eventTypeAlias);
        if (statements != null)
        {
            if (!statements.remove(statementName))
            {
                log.info("Failed to find statement name '" + statementName + "' in collection");
            }

            if (statements.isEmpty())
            {
                typeToStmt.remove(eventTypeAlias);
            }
        }

        // remove from statements
        Set<String> types = stmtToType.get(statementName);
        if (types != null)
        {
            if (!types.remove(eventTypeAlias))
            {
                log.info("Failed to find event type '" + statementName + "' in collection");
            }

            if (types.isEmpty())
            {
                stmtToType.remove(statementName);
            }
        }
    }

    /**
     * For testing, returns the mapping of event type alias to statement names.
     * @return mapping
     */
    protected HashMap<String, Set<String>> getTypeToStmt()
    {
        return typeToStmt;
    }

    /**
     * For testing, returns the mapping of statement names to event type aliases.
     * @return mapping
     */
    protected HashMap<String, Set<String>> getStmtToType()
    {
        return stmtToType;
    }
}