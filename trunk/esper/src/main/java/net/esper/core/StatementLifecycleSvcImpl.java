package net.esper.core;

import net.esper.client.EPException;
import net.esper.client.EPStatement;
import net.esper.client.EPStatementException;
import net.esper.client.EPStatementState;
import net.esper.collection.Pair;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.StatementSpec;
import net.esper.util.ManagedLock;
import net.esper.util.ManagedReadWriteLock;
import net.esper.util.UuidGenerator;
import net.esper.view.ViewProcessingException;
import net.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

public class StatementLifecycleSvcImpl implements StatementLifecycleSvc
{
    private static Log log = LogFactory.getLog(StatementLifecycleSvcImpl.class);
    private final EPServicesContext services;
    private final ManagedReadWriteLock eventProcessingRWLock;
    
    private final Map<String, String> stmtNameToIdMap;
    private final ManagedLock stmtCollectionsLock;

    private final Map<String, EPStatementImpl> stmtIdToEQLStatementMap;
    private final Map<String, EPStmtStartMethod> stmtIdToStartMethodMap;
    private final Map<String, EPStatementStopMethod> stmtIdToStopMethodMap;
    
    private final Map<String, EPStatement> stmtNameToStmtMap;

    public StatementLifecycleSvcImpl(EPServicesContext services, ManagedReadWriteLock eventProcessingRWLock)
    {
        this.services = services;
        this.eventProcessingRWLock = eventProcessingRWLock;
        this.stmtIdToEQLStatementMap = new HashMap<String, EPStatementImpl>();
        this.stmtIdToStartMethodMap = new HashMap<String, EPStmtStartMethod>();
        this.stmtIdToStopMethodMap = new HashMap<String, EPStatementStopMethod>();

        this.stmtNameToIdMap = new HashMap<String, String>();
        this.stmtNameToStmtMap = new HashMap<String, EPStatement>();
        this.stmtCollectionsLock = new ManagedLock("StmtCollectionsLock");
    }

    public EPStatement createAndStart(StatementSpec statementSpec, String expression, boolean isPattern, String optStatementName)
    {
        // Generate statement id
        String statementId = UuidGenerator.generate(expression);

        // Determine a statement name, i.e. use the id or use/generate one for the name passed in
        String statementName = statementId;
        if (optStatementName != null)
        {
            statementName = getUniqueStatementName(optStatementName, statementId);
        }

        ManagedLock statementResourceLock = new ManagedLock("EQLStmtLock");
        EPStatementHandle epStatementHandle = new EPStatementHandle(statementResourceLock, expression);

        EPStatementImpl statement;
        EPStmtStartMethod startMethod;

        eventProcessingRWLock.acquireWriteLock();
        try
        {
            // create statement
            statement = new EPStatementImpl(statementId, statementName, expression, isPattern, services.getDispatchService(), this);
            stmtIdToEQLStatementMap.put(statementId, statement);

            // create start method
            startMethod = new EPStmtStartMethod(statementSpec, expression, services, epStatementHandle);
            stmtIdToStartMethodMap.put(statementId, startMethod);

            // try to start the statement - may fail for compilation errors
            start(statementId);
        }
        catch (RuntimeException ex)
        {
            stmtIdToStartMethodMap.remove(statementId);
            stmtIdToEQLStatementMap.remove(statementId);
            stmtNameToIdMap.remove(statementName);
            stmtNameToStmtMap.remove(statementName);
            throw ex;
        }
        finally
        {
            eventProcessingRWLock.releaseWriteLock();
        }

        // save new statement
        stmtCollectionsLock.acquireLock();
        try
        {
            stmtNameToStmtMap.put(statementName, statement);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            stmtCollectionsLock.releaseLock();
        }

        return statement;
    }
        
    public void start(String statementId)
    {
        EPStatementImpl statement = stmtIdToEQLStatementMap.get(statementId);
        if (statement == null)
        {
            throw new IllegalStateException("Statement not found for id " + statementId);
        }
        EPStmtStartMethod startMethod = stmtIdToStartMethodMap.get(statementId);
        if (startMethod == null)
        {
            throw new IllegalStateException("Statement start method not found for id " + statementId);
        }

        // Acquire a lock for event processing as threads may be in the views used by the statement
        // and that could conflict with the destroy of views
        eventProcessingRWLock.acquireWriteLock();
        try
        {
            if (statement.getState() == EPStatementState.DESTROYED)
            {
                throw new IllegalStateException("Cannot start statement, statement is in destroyed state");
            }
            if (statement.getState() == EPStatementState.STARTED)
            {
                throw new IllegalStateException("Statement already started");
            }

            Pair<Viewable, EPStatementStopMethod> pair;
            try
            {
                pair = startMethod.start();
            }
            catch (ExprValidationException ex)
            {
                log.debug(".start Error starting view", ex);
                throw new EPStatementException("Error starting view: " + ex.getMessage(), statement.getText());
            }
            catch (ViewProcessingException ex)
            {
                log.debug(".start Error starting view", ex);
                throw new EPStatementException("Error starting view: " + ex.getMessage(), statement.getText());
            }

            Viewable parentView = pair.getFirst();
            EPStatementStopMethod stopMethod = pair.getSecond();
            stmtIdToStopMethodMap.put(statementId, stopMethod);
            statement.setParentView(parentView);

            statement.setCurrentState(EPStatementState.STARTED);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            eventProcessingRWLock.releaseWriteLock();
        }
    }

    public void stop(String statementId)
    {
        EPStatementImpl statement = stmtIdToEQLStatementMap.get(statementId);
        if (statement == null)
        {
            throw new IllegalStateException("Statement not found for id " + statementId);
        }
        
        EPStatementStopMethod stopMethod = stmtIdToStopMethodMap.get(statementId);
        if (stopMethod == null)
        {
            throw new IllegalStateException("Stop method not found for statement " + statementId);
        }

        // Acquire a lock for event processing as threads may be in the views used by the statement
        // and that could conflict with the destroy of views
        eventProcessingRWLock.acquireWriteLock();
        try
        {
            if (statement.getState() == EPStatementState.DESTROYED)
            {
                throw new IllegalStateException("Cannot stop statement, statement is in destroyed state");
            }
            if (statement.getState() == EPStatementState.STOPPED)
            {
                throw new IllegalStateException("Statement already stopped");
            }

            statement.setParentView(null);
            stopMethod.stop();

            statement.setCurrentState(EPStatementState.STOPPED);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            eventProcessingRWLock.releaseWriteLock();
        }
    }

    public void destroy(String statementId)
    {
        EPStatementImpl statement = stmtIdToEQLStatementMap.get(statementId);

        // Acquire a lock for event processing as threads may be in the views used by the statement
        // and that could conflict with the destroy of views
        eventProcessingRWLock.acquireWriteLock();
        try
        {
            if (statement.getState() == EPStatementState.DESTROYED)
            {
                throw new IllegalStateException("Statement already destroyed");
            }

            if (statement.getState() == EPStatementState.STARTED)
            {
                EPStatementStopMethod stopMethod = stmtIdToStopMethodMap.get(statementId);
                statement.setParentView(null);
                stopMethod.stop();
            }

            statement.setCurrentState(EPStatementState.DESTROYED);
            statement.initialize();
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            eventProcessingRWLock.releaseWriteLock();
        }
    }

    public EPStatement getStatement(String name)
    {
        return stmtNameToStmtMap.get(name);
    }

    public String[] getStatementNames()
    {
        stmtCollectionsLock.acquireLock();

        String[] statements;
        try
        {
            statements = new String[stmtNameToStmtMap.size()];
            int count = 0;
            for (String key : stmtNameToStmtMap.keySet())
            {
                statements[count++] = key;
            }
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            stmtCollectionsLock.releaseLock();
        }

        return statements;
    }

    public void stopAllStatements() throws EPException
    {
        // TODO
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void destroyAllStatements() throws EPException
    {
        // TODO
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private String getUniqueStatementName(String statementName, String statementId)
    {
        String finalStatementName;

        stmtCollectionsLock.acquireLock();
        try
        {
            if (stmtNameToIdMap.containsKey(statementName))
            {
                int count = 0;
                while(true)
                {
                    finalStatementName = statementName + "--" + count;
                    if (!(stmtNameToIdMap.containsKey(finalStatementName)))
                    {
                        break;
                    }
                    if (count > Integer.MAX_VALUE - 2)
                    {
                        throw new EPException("Failed to establish a unique statement name");
                    }
                    count++;
                }
            }
            else
            {
                finalStatementName = statementName;
            }

            stmtNameToIdMap.put(finalStatementName, statementId);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            stmtCollectionsLock.releaseLock();
        }

        return finalStatementName;
    }
}
