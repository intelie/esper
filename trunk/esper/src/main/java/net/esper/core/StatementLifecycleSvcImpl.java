package net.esper.core;

import net.esper.client.*;
import net.esper.collection.Pair;
import net.esper.collection.RefCountedMap;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.*;
import net.esper.util.ManagedLock;
import net.esper.eql.expression.ExprNodeSubselectVisitor;
import net.esper.eql.expression.ExprSubselectNode;
import net.esper.util.ManagedReadWriteLock;
import net.esper.util.UuidGenerator;
import net.esper.util.ManagedLockImpl;
import net.esper.view.ViewProcessingException;
import net.esper.view.Viewable;
import net.esper.event.EventType;
import net.esper.pattern.EvalFilterNode;
import net.esper.pattern.EvalNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Provides statement lifecycle services.
 */
public class StatementLifecycleSvcImpl implements StatementLifecycleSvc
{
    private static Log log = LogFactory.getLog(StatementLifecycleSvcImpl.class);
    private final EPServicesContext services;
    private final ManagedReadWriteLock eventProcessingRWLock;

    private final Map<String, String> stmtNameToIdMap;
    private final Map<String, EPStatementDesc> stmtIdToDescMap;
    private final Map<String, EPStatement> stmtNameToStmtMap;
    private final RefCountedMap<String, ManagedLock> insertIntoStreams;

    public void init() {}

    /**
     * Ctor.
     * @param services is engine services
     */
    public StatementLifecycleSvcImpl(EPServicesContext services)
    {
        this.services = services;

        // lock for starting and stopping statements
        this.eventProcessingRWLock = services.getEventProcessingRWLock();

        this.stmtIdToDescMap = new HashMap<String, EPStatementDesc>();
        this.stmtNameToStmtMap = new HashMap<String, EPStatement>();
        this.stmtNameToIdMap = new HashMap<String, String>();
        this.insertIntoStreams = new RefCountedMap<String, ManagedLock>();
    }

    public synchronized EPStatement createAndStart(StatementSpecRaw statementSpec, String expression, boolean isPattern, String optStatementName)
    {
        // Generate statement id
        String statementId = UuidGenerator.generate(expression);

        // Determine a statement name, i.e. use the id or use/generate one for the name passed in
        String statementName = statementId;
        if (optStatementName != null)
        {
            statementName = getUniqueStatementName(optStatementName, statementId);
        }

        EPStatementDesc desc = createStopped(statementSpec, expression, isPattern, statementName, statementId);
        start(statementId, desc);
        return desc.getEpStatement();
    }

    /**
     * Creates a started statement.
     * @param statementSpec is the statement def
     * @param expression is the expression text
     * @param isPattern is true for patterns, 
     * @param statementName is the statement name
     * @param statementId is the statement id
     * @return statement 
     */
    protected synchronized EPStatement createStarted(StatementSpecRaw statementSpec, String expression, boolean isPattern, String statementName, String statementId)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".start Creating and starting statement " + statementId);
        }
        EPStatementDesc desc = createStopped(statementSpec, expression, isPattern, statementName, statementId);
        start(statementId, desc);
        return desc.getEpStatement();
    }

    /**
     * Create stopped statement.
     * @param statementSpec - statement definition
     * @param expression is the expression text
     * @param isPattern is true for patterns, false for non-patterns
     * @param statementName is the statement name assigned or given
     * @param statementId is the statement id
     * @return stopped statement
     */
    protected synchronized EPStatementDesc createStopped(StatementSpecRaw statementSpec, String expression, boolean isPattern, String statementName, String statementId)
    {
        EPStatementDesc statementDesc;
        EPStatementStartMethod startMethod;

        StatementContext statementContext =  services.getStatementContextFactory().makeContext(statementId, statementName, expression, services);
        StatementSpecCompiled compiledSpec = compile(statementSpec, expression, statementContext);

        // For insert-into streams, create a lock taken out as soon as an event is inserted
        // Makes the processing between chained statements more predictable.
        if (statementSpec.getInsertIntoDesc() != null)
        {
            String insertIntoStreamName = statementSpec.getInsertIntoDesc().getEventTypeAlias();
            ManagedLock insertIntoStreamLock = insertIntoStreams.get(insertIntoStreamName);
            if (insertIntoStreamLock == null)
            {
                insertIntoStreamLock = new ManagedLockImpl("insert_stream_" + insertIntoStreamName);
                insertIntoStreams.put(insertIntoStreamName, insertIntoStreamLock);
            }
            else
            {
                insertIntoStreams.reference(insertIntoStreamName);
            }
            statementContext.getEpStatementHandle().setRoutedInsertStreamLock(insertIntoStreamLock);
        }

        // In a join statements if the same event type or it's deep super types are used in the join more then once,
        // then this is a self-join and the statement handle must know to dispatch the results together
        boolean canSelfJoin = isPotentialSelfJoin(compiledSpec.getStreamSpecs());
        statementContext.getEpStatementHandle().setCanSelfJoin(canSelfJoin);

        eventProcessingRWLock.acquireWriteLock();
        try
        {
            // create statement - may fail for parser and simple validation errors
            boolean preserveDispatchOrder = services.getEngineSettingsService().getEngineSettings().getThreading().isListenerDispatchPreserveOrder();
            long blockingTimeout = services.getEngineSettingsService().getEngineSettings().getThreading().getListenerDispatchTimeout();
            EPStatementSPI statement = new EPStatementImpl(statementId, statementName, expression, isPattern,
                    services.getDispatchService(), this, preserveDispatchOrder, blockingTimeout);

            // create start method
            startMethod = new EPStatementStartMethod(compiledSpec, services, statementContext);

            // keep track of the insert-into statements supplying streams.
            // these may need to lock to get more predictable behavior for multithreaded processing. 
            String insertIntoStreamName = null;
            if (statementSpec.getInsertIntoDesc() != null)
            {
                insertIntoStreamName = statementSpec.getInsertIntoDesc().getEventTypeAlias();
            }

            statementDesc = new EPStatementDesc(statement, startMethod, null, insertIntoStreamName);
            stmtIdToDescMap.put(statementId, statementDesc);
            stmtNameToStmtMap.put(statementName, statement);
            stmtNameToIdMap.put(statementName, statementId);
        }
        catch (RuntimeException ex)
        {
            stmtIdToDescMap.remove(statementId);
            stmtNameToIdMap.remove(statementName);
            stmtNameToStmtMap.remove(statementName);
            throw ex;
        }
        finally
        {
            eventProcessingRWLock.releaseWriteLock();
        }

        return statementDesc;
    }

    private boolean isPotentialSelfJoin(List<StreamSpecCompiled> streamSpecs)
    {
        // not a join (pattern doesn't count)
        if (streamSpecs.size() == 1)
        {
            return false;
        }

        // join - determine types joined
        List<EventType> filteredTypes = new ArrayList<EventType>();
        boolean hasFilterStream = false;
        for (StreamSpecCompiled streamSpec : streamSpecs)
        {
            if (streamSpec instanceof FilterStreamSpecCompiled)
            {
                EventType type = ((FilterStreamSpecCompiled) streamSpec).getFilterSpec().getEventType();
                filteredTypes.add(type);
                hasFilterStream = true;
            }
            else if (streamSpec instanceof PatternStreamSpecCompiled)
            {
                List<EvalFilterNode> filterNodes = EvalNode.recusiveFilterChildNodes(((PatternStreamSpecCompiled)streamSpec).getEvalNode());
                for (EvalFilterNode filterNode : filterNodes)
                {
                    filteredTypes.add(filterNode.getFilterSpec().getEventType());
                }
            }
            else if (streamSpec instanceof DBStatementStreamSpec)
            {
                // no action for these
            }            
        }

        if (filteredTypes.size() == 1)
        {
            return false;
        }
        // pattern-only streams are not self-joins
        if (!hasFilterStream)
        {
            return false;
        }

        // is type overlap
        for (int i = 0; i < filteredTypes.size(); i++)
        {
            for (int j = i + 1; j < filteredTypes.size(); j++)
            {
                EventType typeOne = filteredTypes.get(i);
                EventType typeTwo = filteredTypes.get(j);
                if (typeOne == typeTwo)
                {
                    return true;
                }

                if (typeOne.getSuperTypes() != null)
                {
                    for (EventType typeOneSuper : typeOne.getSuperTypes())
                    {
                        if (typeOneSuper == typeTwo)
                        {
                            return true;
                        }
                    }
                }
                if (typeTwo.getSuperTypes() != null)
                {
                    for (EventType typeTwoSuper : typeTwo.getSuperTypes())
                    {
                        if (typeOne == typeTwoSuper)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public synchronized void start(String statementId)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".start Starting statement " + statementId);
        }

        // Acquire a lock for event processing as threads may be in the views used by the statement
        // and that could conflict with the destroy of views
        eventProcessingRWLock.acquireWriteLock();
        try
        {
            EPStatementDesc desc = stmtIdToDescMap.get(statementId);
            if (desc == null)
            {
                throw new IllegalStateException("Cannot start statement, statement is in destroyed state");
            }
            startInternal(statementId, desc);
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

    /**
     * Start the given statement.
     * @param statementId is the statement id
     * @param desc is the cached statement info
     */
    public void start(String statementId, EPStatementDesc desc)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".start Starting statement " + statementId + " from desc=" + desc);
        }

        // Acquire a lock for event processing as threads may be in the views used by the statement
        // and that could conflict with the destroy of views
        eventProcessingRWLock.acquireWriteLock();
        try
        {
            startInternal(statementId, desc);
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

    private void startInternal(String statementId, EPStatementDesc desc)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".startInternal Starting statement " + statementId + " from desc=" + desc);
        }

        if (desc.getStartMethod() == null)
        {
            throw new IllegalStateException("Statement start method not found for id " + statementId);
        }

        EPStatementSPI statement = desc.getEpStatement();
        if (statement.getState() == EPStatementState.STARTED)
        {
            throw new IllegalStateException("Statement already started");
        }

        Pair<Viewable, EPStatementStopMethod> pair;
        try
        {
            pair = desc.getStartMethod().start();
        }
        catch (ExprValidationException ex)
        {
            stmtIdToDescMap.remove(statementId);
            stmtNameToIdMap.remove(statement.getName());
            stmtNameToStmtMap.remove(statement.getName());
            log.debug(".start Error starting view", ex);
            throw new EPStatementException("Error starting view: " + ex.getMessage(), statement.getText());
        }
        catch (ViewProcessingException ex)
        {
            stmtIdToDescMap.remove(statementId);
            stmtNameToIdMap.remove(statement.getName());
            stmtNameToStmtMap.remove(statement.getName());
            log.debug(".start Error starting view", ex);
            throw new EPStatementException("Error starting view: " + ex.getMessage(), statement.getText());
        }

        Viewable parentView = pair.getFirst();
        EPStatementStopMethod stopMethod = pair.getSecond();
        desc.setStopMethod(stopMethod);
        statement.setParentView(parentView);
        statement.setCurrentState(EPStatementState.STARTED);
    }

    public synchronized void stop(String statementId)
    {
        // Acquire a lock for event processing as threads may be in the views used by the statement
        // and that could conflict with the destroy of views
        eventProcessingRWLock.acquireWriteLock();
        try
        {
            EPStatementDesc desc = stmtIdToDescMap.get(statementId);
            if (desc == null)
            {
                throw new IllegalStateException("Cannot stop statement, statement is in destroyed state");
            }

            EPStatementSPI statement = desc.getEpStatement();
            EPStatementStopMethod stopMethod = desc.getStopMethod();
            if (stopMethod == null)
            {
                throw new IllegalStateException("Stop method not found for statement " + statementId);
            }

            if (statement.getState() == EPStatementState.STOPPED)
            {
                throw new IllegalStateException("Statement already stopped");
            }

            statement.setParentView(null);
            stopMethod.stop();
            desc.setStopMethod(null);

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

    public synchronized void destroy(String statementId)
    {
        // Acquire a lock for event processing as threads may be in the views used by the statement
        // and that could conflict with the destroy of views
        eventProcessingRWLock.acquireWriteLock();
        try
        {
            EPStatementDesc desc = stmtIdToDescMap.get(statementId);
            if (desc == null)
            {
                throw new IllegalStateException("Statement already destroyed");
            }

            EPStatementSPI statement = desc.getEpStatement();
            if (statement.getState() == EPStatementState.STARTED)
            {
                EPStatementStopMethod stopMethod = desc.getStopMethod();
                statement.setParentView(null);
                stopMethod.stop();
            }

            statement.setCurrentState(EPStatementState.DESTROYED);

            stmtNameToStmtMap.remove(statement.getName());
            stmtNameToIdMap.remove(statement.getName());
            stmtIdToDescMap.remove(statementId);

            // For insert-into streams, create a lock taken out as soon as an event is inserted
            // Makes the processing between chained statements more predictable.
            String insertIntoStreamName = desc.getOptInsertIntoStream();
            if (insertIntoStreamName != null)
            {
                insertIntoStreams.dereference(insertIntoStreamName);
            }            
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

    public synchronized EPStatement getStatementByName(String name)
    {
        return stmtNameToStmtMap.get(name);
    }

    /**
     * Returns the statement given a statement id.
     * @param id is the statement id
     * @return statement
     */
    public EPStatementSPI getStatementById(String id)
    {
        return this.stmtIdToDescMap.get(id).getEpStatement();
    }

    public synchronized String[] getStatementNames()
    {
        String[] statements = new String[stmtNameToStmtMap.size()];
        int count = 0;
        for (String key : stmtNameToStmtMap.keySet())
        {
            statements[count++] = key;
        }
        return statements;
    }

    public synchronized void startAllStatements() throws EPException
    {
        String[] statementIds = getStatementIds();
        for (int i = 0; i < statementIds.length; i++)
        {
            EPStatement statement = stmtIdToDescMap.get(statementIds[i]).getEpStatement();
            if (statement.getState() == EPStatementState.STOPPED)
            {
                start(statementIds[i]);
            }
        }
    }

    public synchronized void stopAllStatements() throws EPException
    {
        String[] statementIds = getStatementIds();
        for (int i = 0; i < statementIds.length; i++)
        {
            EPStatement statement = stmtIdToDescMap.get(statementIds[i]).getEpStatement();
            if (statement.getState() == EPStatementState.STARTED)
            {
                stop(statementIds[i]);
            }
        }
    }

    public synchronized void destroyAllStatements() throws EPException
    {
        String[] statementIds = getStatementIds();
        for (int i = 0; i < statementIds.length; i++)
        {
            destroy(statementIds[i]);
        }
    }

    private String[] getStatementIds()
    {
        String[] statementIds = new String[stmtNameToStmtMap.size()];
        int count = 0;
        for (String id : stmtNameToIdMap.values())
        {
            statementIds[count++] = id;
        }
        return statementIds;
    }

    private String getUniqueStatementName(String statementName, String statementId)
    {
        String finalStatementName;

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
        return finalStatementName;
    }

    public void updatedListeners(String statementId, Set<UpdateListener> listeners)
    {
        log.debug(".updatedListeners No action for base implementation");
    }

    /**
     * Statement information.
     */
    public class EPStatementDesc
    {
        private EPStatementSPI epStatement;
        private EPStatementStartMethod startMethod;
        private EPStatementStopMethod stopMethod;
        private String optInsertIntoStream;

        /**
         * Ctor.
         * @param epStatement the statement
         * @param startMethod the start method
         * @param stopMethod the stop method
         * @param optInsertIntoStream is the insert-into stream name, or null if not using insert-into
         */
        public EPStatementDesc(EPStatementSPI epStatement, EPStatementStartMethod startMethod, EPStatementStopMethod stopMethod, String optInsertIntoStream)
        {
            this.epStatement = epStatement;
            this.startMethod = startMethod;
            this.stopMethod = stopMethod;
            this.optInsertIntoStream = optInsertIntoStream;
        }

        /**
         * Returns the statement.
         * @return statement.
         */
        public EPStatementSPI getEpStatement()
        {
            return epStatement;
        }

        /**
         * Returns the start method.
         * @return start method
         */
        public EPStatementStartMethod getStartMethod()
        {
            return startMethod;
        }

        /**
         * Returns the stop method.
         * @return stop method
         */
        public EPStatementStopMethod getStopMethod()
        {
            return stopMethod;
        }

        /**
         * Return the insert-into stream name, or null if no insert-into
         * @return stream name
         */
        public String getOptInsertIntoStream()
        {
            return optInsertIntoStream;
        }

        /**
         * Sets the stop method.
         * @param stopMethod to set
         */
        public void setStopMethod(EPStatementStopMethod stopMethod)
        {
            this.stopMethod = stopMethod;
        }
    }

    private static StatementSpecCompiled compile(StatementSpecRaw spec, String eqlStatement, StatementContext statementContext) throws EPStatementException
    {
        List<StreamSpecCompiled> compiledStreams;

        try
        {
            compiledStreams = new ArrayList<StreamSpecCompiled>();
            for (StreamSpecRaw rawSpec : spec.getStreamSpecs())
            {
                StreamSpecCompiled compiled = rawSpec.compile(statementContext.getEventAdapterService(), statementContext.getMethodResolutionService());
                compiledStreams.add(compiled);
            }
        }
        catch (ExprValidationException ex)
        {
            throw new EPStatementException(ex.getMessage(), eqlStatement);
        }
        catch (RuntimeException ex)
        {
            String text = "Unexpected error compiling statement";
            log.error(".compile " + text, ex);
            throw new EPStatementException(text + ":" + ex.getClass().getName() + ":" + ex.getMessage(), eqlStatement);
        }

        // Look for expressions with sub-selects in select expression list and filter expression
        // Recursively compile the statement within the statement.
        ExprNodeSubselectVisitor visitor = new ExprNodeSubselectVisitor();
        for (SelectExprElementRawSpec raw : spec.getSelectClauseSpec().getSelectList())
        {
            raw.getSelectExpression().accept(visitor);
        }
        if (spec.getFilterRootNode() != null)
        {
            spec.getFilterRootNode().accept(visitor);
        }
        for (ExprSubselectNode subselect : visitor.getSubselects())
        {
            StatementSpecRaw raw = subselect.getStatementSpecRaw();
            StatementSpecCompiled compiled = compile(raw, eqlStatement, statementContext);
            subselect.setStatementSpecCompiled(compiled);
        }

        return new StatementSpecCompiled(
                spec.getInsertIntoDesc(),
                spec.getSelectStreamSelectorEnum(),
                spec.getSelectClauseSpec(),
                compiledStreams,
                spec.getOuterJoinDescList(),
                spec.getFilterRootNode(),
                spec.getGroupByExpressions(),
                spec.getHavingExprRootNode(),
                spec.getOutputLimitSpec(),
                spec.getOrderByList(),
                visitor.getSubselects()
                );
    }    
}
