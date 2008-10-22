package com.espertech.esper.core;

import com.espertech.esper.util.ManagedReadWriteLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class StatementEventTypeRefImpl implements StatementEventTypeRef
{
    private static final Log log = LogFactory.getLog(StatementEventTypeRefImpl.class);

    private final ManagedReadWriteLock mapLock;
    private final HashMap<String, Set<String>> typeToStmt;

    public StatementEventTypeRefImpl()
    {
        typeToStmt = new HashMap<String, Set<String>>();
        mapLock = new ManagedReadWriteLock("StatementEventTypeRefImpl", false);
    }

    public void addReferences(String statementName, Set<String> eventTypesReferenced)
    {
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

    public void removeReferences(String statementName, Set<String> eventTypesReferenced)
    {
        mapLock.acquireWriteLock();
        try
        {
            for (String ref : eventTypesReferenced)
            {
                removeReference(statementName, ref);
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
    }

    private void removeReference(String statementName, String eventTypeAlias)
    {
        // remove to types
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
    }
}