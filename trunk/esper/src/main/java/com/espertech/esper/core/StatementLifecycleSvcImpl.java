package com.espertech.esper.core;

import com.espertech.esper.client.*;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.named.NamedWindowService;
import com.espertech.esper.epl.spec.*;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.MapEventType;
import com.espertech.esper.filter.FilterSpecCompiled;
import com.espertech.esper.filter.FilterSpecParam;
import com.espertech.esper.pattern.EvalFilterNode;
import com.espertech.esper.pattern.EvalNode;
import com.espertech.esper.pattern.EvalNodeAnalysisResult;
import com.espertech.esper.util.ManagedReadWriteLock;
import com.espertech.esper.util.UuidGenerator;
import com.espertech.esper.view.ViewProcessingException;
import com.espertech.esper.view.Viewable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Provides statement lifecycle services.
 */
public class StatementLifecycleSvcImpl implements StatementLifecycleSvc
{
    private static Log log = LogFactory.getLog(StatementLifecycleSvcImpl.class);

    /**
     * Services context for statement lifecycle management.
     */
    protected final EPServicesContext services;

    /**
     * Maps of statement id to descriptor.
     */
    protected final Map<String, EPStatementDesc> stmtIdToDescMap;

    /**
     * Map of statement name to statement.
     */
    protected final Map<String, EPStatement> stmtNameToStmtMap;

    private final EPServiceProvider epServiceProvider;
    private final ManagedReadWriteLock eventProcessingRWLock;

    private final Map<String, String> stmtNameToIdMap;

    // Observers to statement-related events
    private final Set<StatementLifecycleObserver> observers;

    /**
     * Ctor.
     * @param epServiceProvider is the engine instance to hand to statement-aware listeners
     * @param services is engine services
     */
    public StatementLifecycleSvcImpl(EPServiceProvider epServiceProvider, EPServicesContext services)
    {
        this.services = services;
        this.epServiceProvider = epServiceProvider;

        // lock for starting and stopping statements
        this.eventProcessingRWLock = services.getEventProcessingRWLock();

        this.stmtIdToDescMap = new HashMap<String, EPStatementDesc>();
        this.stmtNameToStmtMap = new HashMap<String, EPStatement>();
        this.stmtNameToIdMap = new HashMap<String, String>();

        observers = new CopyOnWriteArraySet<StatementLifecycleObserver>();
    }

    public void addObserver(StatementLifecycleObserver observer)
    {
        observers.add(observer);
    }

    public void destroy()
    {
        this.destroyAllStatements();
    }

    public void init()
    {
        // called after services are activated, to begin statement loading from store
    }

    public synchronized EPStatement createAndStart(StatementSpecRaw statementSpec, String expression, boolean isPattern, String optStatementName)
    {
        // Generate statement id
        String statementId = UuidGenerator.generate(expression);
        return createAndStart(statementSpec, expression, isPattern, optStatementName, statementId, null);
    }

    /**
     * Creates and starts statement.
     * @param statementSpec defines the statement
     * @param expression is the EPL
     * @param isPattern is true for patterns
     * @param optStatementName is the optional statement name
     * @param statementId is the statement id
     * @param optAdditionalContext additional context for use by the statement context
     * @return started statement
     */
    protected synchronized EPStatement createAndStart(StatementSpecRaw statementSpec, String expression, boolean isPattern, String optStatementName, String statementId, Map<String, Object> optAdditionalContext)
    {
        // Determine a statement name, i.e. use the id or use/generate one for the name passed in
        String statementName = statementId;
        if (optStatementName != null)
        {
            statementName = getUniqueStatementName(optStatementName, statementId);
        }

        EPStatementDesc desc = createStopped(statementSpec, expression, isPattern, statementName, statementId, optAdditionalContext);
        start(statementId, desc, true);
        return desc.getEpStatement();
    }

    /**
     * Creates a started statement.
     * @param statementSpec is the statement def
     * @param expression is the expression text
     * @param isPattern is true for patterns,
     * @param statementName is the statement name
     * @param statementId is the statement id
     * @param optAdditionalContext additional context for use by the statement context
     * @return statement
     */
    protected synchronized EPStatement createStarted(StatementSpecRaw statementSpec, String expression, boolean isPattern, String statementName, String statementId, Map<String, Object> optAdditionalContext)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".start Creating and starting statement " + statementId);
        }
        EPStatementDesc desc = createStopped(statementSpec, expression, isPattern, statementName, statementId, optAdditionalContext);
        start(statementId, desc, true);
        return desc.getEpStatement();
    }

    /**
     * Create stopped statement.
     * @param statementSpec - statement definition
     * @param expression is the expression text
     * @param isPattern is true for patterns, false for non-patterns
     * @param statementName is the statement name assigned or given
     * @param statementId is the statement id
     * @param optAdditionalContext additional context for use by the statement context
     * @return stopped statement
     */
    protected synchronized EPStatementDesc createStopped(StatementSpecRaw statementSpec, String expression, boolean isPattern, String statementName, String statementId, Map<String, Object> optAdditionalContext)
    {
        EPStatementDesc statementDesc;
        EPStatementStartMethod startMethod;

        StatementContext statementContext =  services.getStatementContextFactory().makeContext(statementId, statementName, expression, statementSpec.isHasVariables(), services, optAdditionalContext, statementSpec.getOnTriggerDesc(), statementSpec.getCreateWindowDesc());
        StatementSpecCompiled compiledSpec = null;
        try
        {
            compiledSpec = compile(statementSpec, expression, statementContext);
        }
        catch (EPStatementException ex)
        {
            stmtNameToIdMap.remove(statementName); // Clean out the statement name as it's already assigned
            throw ex;
        }

        // For insert-into streams, create a lock taken out as soon as an event is inserted
        // Makes the processing between chained statements more predictable.
        if (statementSpec.getInsertIntoDesc() != null)
        {
            String insertIntoStreamName = statementSpec.getInsertIntoDesc().getEventTypeAlias();
            String latchFactoryName = "insert_stream_" + insertIntoStreamName + "_" + statementId;
            long msecTimeout = services.getEngineSettingsService().getEngineSettings().getThreading().getInsertIntoDispatchTimeout();
            ConfigurationEngineDefaults.Threading.Locking locking = services.getEngineSettingsService().getEngineSettings().getThreading().getInsertIntoDispatchLocking();
            InsertIntoLatchFactory latchFactory = new InsertIntoLatchFactory(latchFactoryName, msecTimeout, locking);
            statementContext.getEpStatementHandle().setInsertIntoLatchFactory(latchFactory);
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
            boolean isSpinLocks = services.getEngineSettingsService().getEngineSettings().getThreading().getListenerDispatchLocking() == ConfigurationEngineDefaults.Threading.Locking.SPIN;
            long blockingTimeout = services.getEngineSettingsService().getEngineSettings().getThreading().getListenerDispatchTimeout();
            long timeLastStateChange = services.getSchedulingService().getTime();
            EPStatementSPI statement = new EPStatementImpl(statementId, statementName, expression, isPattern,
                    services.getDispatchService(), this, timeLastStateChange, preserveDispatchOrder, isSpinLocks, blockingTimeout,
                    statementContext.getEpStatementHandle(), statementContext.getVariableService(), statementContext.getStatementResultService());

            boolean isInsertInto = statementSpec.getInsertIntoDesc() != null;
            statementContext.getStatementResultService().setContext(statement, epServiceProvider,
                    isInsertInto, isPattern);

            // create start method
            startMethod = new EPStatementStartMethod(compiledSpec, services, statementContext);

            // keep track of the insert-into statements supplying streams.
            // these may need to lock to get more predictable behavior for multithreaded processing.
            String insertIntoStreamName = null;
            if (statementSpec.getInsertIntoDesc() != null)
            {
                insertIntoStreamName = statementSpec.getInsertIntoDesc().getEventTypeAlias();
            }

            statementDesc = new EPStatementDesc(statement, startMethod, null, insertIntoStreamName, statementContext.getEpStatementHandle());
            stmtIdToDescMap.put(statementId, statementDesc);
            stmtNameToStmtMap.put(statementName, statement);
            stmtNameToIdMap.put(statementName, statementId);

            dispatchStatementLifecycleEvent(new StatementLifecycleEvent(statement, StatementLifecycleEvent.LifecycleEventType.CREATE));
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
                EvalNodeAnalysisResult evalNodeAnalysisResult = EvalNode.recursiveAnalyzeChildNodes(((PatternStreamSpecCompiled)streamSpec).getEvalNode());
                List<EvalFilterNode> filterNodes = evalNodeAnalysisResult.getFilterNodes();
                for (EvalFilterNode filterNode : filterNodes)
                {
                    filteredTypes.add(filterNode.getFilterSpec().getEventType());
                }
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
            startInternal(statementId, desc, false);
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
     * @param isNewStatement indicator whether the statement is new or a stop-restart statement
     */
    public void start(String statementId, EPStatementDesc desc, boolean isNewStatement)
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
            startInternal(statementId, desc, isNewStatement);
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

    private void startInternal(String statementId, EPStatementDesc desc, boolean isNewStatement)
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
            log.debug(".startInternal - Statement already started");
            return;
        }

        Pair<Viewable, EPStatementStopMethod> pair;
        try
        {
            pair = desc.getStartMethod().start(isNewStatement);
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
        long timeLastStateChange = services.getSchedulingService().getTime();
        statement.setCurrentState(EPStatementState.STARTED, timeLastStateChange);

        dispatchStatementLifecycleEvent(new StatementLifecycleEvent(statement, StatementLifecycleEvent.LifecycleEventType.STATECHANGE));
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
                log.debug(".startInternal - Statement already stopped");
                return;
            }

            stopMethod.stop();
            statement.setParentView(null);
            desc.setStopMethod(null);

            long timeLastStateChange = services.getSchedulingService().getTime();
            statement.setCurrentState(EPStatementState.STOPPED, timeLastStateChange);

            dispatchStatementLifecycleEvent(new StatementLifecycleEvent(statement, StatementLifecycleEvent.LifecycleEventType.STATECHANGE));
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
                log.debug(".startInternal - Statement already destroyed");
                return;
            }

            EPStatementSPI statement = desc.getEpStatement();
            if (statement.getState() == EPStatementState.STARTED)
            {
                EPStatementStopMethod stopMethod = desc.getStopMethod();
                statement.setParentView(null);
                stopMethod.stop();
            }

            long timeLastStateChange = services.getSchedulingService().getTime();
            statement.setCurrentState(EPStatementState.DESTROYED, timeLastStateChange);

            stmtNameToStmtMap.remove(statement.getName());
            stmtNameToIdMap.remove(statement.getName());
            stmtIdToDescMap.remove(statementId);

            dispatchStatementLifecycleEvent(new StatementLifecycleEvent(statement, StatementLifecycleEvent.LifecycleEventType.STATECHANGE));
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
        String[] statementIds = new String[stmtNameToIdMap.size()];
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

    public void updatedListeners(String statementId, String statementName, EPStatementListenerSet listeners)
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
        private EPStatementHandle statementHandle;

        /**
         * Ctor.
         * @param epStatement the statement
         * @param startMethod the start method
         * @param stopMethod the stop method
         * @param optInsertIntoStream is the insert-into stream name, or null if not using insert-into
         * @param statementHandle is the locking handle for the statement
         */
        public EPStatementDesc(EPStatementSPI epStatement, EPStatementStartMethod startMethod, EPStatementStopMethod stopMethod, String optInsertIntoStream, EPStatementHandle statementHandle)
        {
            this.epStatement = epStatement;
            this.startMethod = startMethod;
            this.stopMethod = stopMethod;
            this.optInsertIntoStream = optInsertIntoStream;
            this.statementHandle = statementHandle;
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

        /**
         * Returns the statements handle.
         * @return statement handle
         */
        public EPStatementHandle getStatementHandle()
        {
            return statementHandle;
        }
    }

    private static StatementSpecCompiled compile(StatementSpecRaw spec, String eplStatement, StatementContext statementContext) throws EPStatementException
    {
        List<StreamSpecCompiled> compiledStreams;

        try
        {
            compiledStreams = new ArrayList<StreamSpecCompiled>();
            for (StreamSpecRaw rawSpec : spec.getStreamSpecs())
            {
                StreamSpecCompiled compiled = rawSpec.compile(statementContext.getEventAdapterService(), statementContext.getMethodResolutionService(), statementContext.getPatternResolutionService(), statementContext.getSchedulingService(), statementContext.getNamedWindowService(), statementContext.getVariableService());
                compiledStreams.add(compiled);
            }
        }
        catch (ExprValidationException ex)
        {
            throw new EPStatementException(ex.getMessage(), eplStatement);
        }
        catch (RuntimeException ex)
        {
            String text = "Unexpected error compiling statement";
            log.error(".compile " + text, ex);
            throw new EPStatementException(text + ":" + ex.getClass().getName() + ":" + ex.getMessage(), eplStatement);
        }

        // for create window statements, we switch the filter to a new event type
        if (spec.getCreateWindowDesc() != null)
        {
            try
            {
                FilterStreamSpecCompiled filterStreamSpec = (FilterStreamSpecCompiled) compiledStreams.get(0);
                EventType selectFromType = filterStreamSpec.getFilterSpec().getEventType();
                Pair<FilterSpecCompiled, SelectClauseSpecRaw> newFilter = handleCreateWindow(selectFromType, spec, eplStatement, statementContext);
                filterStreamSpec.setFilterSpec(newFilter.getFirst());
                spec.setSelectClauseSpec(newFilter.getSecond());

                // view must be non-empty list
                if (spec.getCreateWindowDesc().getViewSpecs().isEmpty())
                {
                    throw new ExprValidationException(NamedWindowService.ERROR_MSG_DATAWINDOWS);
                }
                filterStreamSpec.getViewSpecs().addAll(spec.getCreateWindowDesc().getViewSpecs());
            }
            catch (ExprValidationException e)
            {
                throw new EPStatementException(e.getMessage(), eplStatement);
            }
        }

        // Look for expressions with sub-selects in select expression list and filter expression
        // Recursively compile the statement within the statement.
        ExprNodeSubselectVisitor visitor = new ExprNodeSubselectVisitor();
        List<SelectClauseElementCompiled> selectElements = new ArrayList<SelectClauseElementCompiled>();
        SelectClauseSpecCompiled selectClauseCompiled = new SelectClauseSpecCompiled(selectElements);
        for (SelectClauseElementRaw raw : spec.getSelectClauseSpec().getSelectExprList())
        {
            if (raw instanceof SelectClauseExprRawSpec)
            {
                SelectClauseExprRawSpec rawExpr = (SelectClauseExprRawSpec) raw;
                rawExpr.getSelectExpression().accept(visitor);
                selectElements.add(new SelectClauseExprCompiledSpec(rawExpr.getSelectExpression(), rawExpr.getOptionalAsName()));
            }
            else if (raw instanceof SelectClauseStreamRawSpec)
            {
                SelectClauseStreamRawSpec rawExpr = (SelectClauseStreamRawSpec) raw;
                selectElements.add(new SelectClauseStreamCompiledSpec(rawExpr.getStreamAliasName(), rawExpr.getOptionalAsName()));
            }
            else if (raw instanceof SelectClauseElementWildcard)
            {
                SelectClauseElementWildcard wildcard = (SelectClauseElementWildcard) raw;
                selectElements.add(wildcard);
            }
            else
            {
                throw new IllegalStateException("Unexpected select clause element class : " + raw.getClass().getName());
            }
        }
        if (spec.getFilterRootNode() != null)
        {
            spec.getFilterRootNode().accept(visitor);
        }
        for (ExprSubselectNode subselect : visitor.getSubselects())
        {
            StatementSpecRaw raw = subselect.getStatementSpecRaw();
            StatementSpecCompiled compiled = compile(raw, eplStatement, statementContext);
            subselect.setStatementSpecCompiled(compiled);
        }

        return new StatementSpecCompiled(
                spec.getOnTriggerDesc(),
                spec.getCreateWindowDesc(),
                spec.getCreateVariableDesc(),
                spec.getInsertIntoDesc(),
                spec.getSelectStreamSelectorEnum(),
                selectClauseCompiled,
                compiledStreams,
                spec.getOuterJoinDescList(),
                spec.getFilterRootNode(),
                spec.getGroupByExpressions(),
                spec.getHavingExprRootNode(),
                spec.getOutputLimitSpec(),
                spec.getOrderByList(),
                visitor.getSubselects(),
                spec.isHasVariables()
                );
    }

    // The create window command:
    //      create window windowName[.window_view_list] as [select properties from] type
    //
    // This section expected s single FilterStreamSpecCompiled representing the selected type.
    // It creates a new event type representing the window type and a sets the type selected on the filter stream spec.
    private static Pair<FilterSpecCompiled, SelectClauseSpecRaw> handleCreateWindow(EventType selectFromType,
                                           StatementSpecRaw spec,
                                           String eplStatement,
                                           StatementContext statementContext)
            throws ExprValidationException
    {
        String typeName = spec.getCreateWindowDesc().getWindowName();
        EventType targetType = null;

        // Validate the select expressions which consists of properties only
        List<SelectClauseExprCompiledSpec> select = compileLimitedSelect(spec.getSelectClauseSpec(), eplStatement, selectFromType);

        // Create Map or Wrapper event type from the select clause of the window.
        // If no columns selected, simply create a wrapper type
        // Build a list of properties
        SelectClauseSpecRaw newSelectClauseSpecRaw = new SelectClauseSpecRaw();
        Map<String, Object> properties = new HashMap<String, Object>();
        for (SelectClauseExprCompiledSpec selectElement : select)
        {
            properties.put(selectElement.getAssignedName(), selectElement.getSelectExpression().getType());

            // Add any properties to the new select clause for use by consumers to the statement itself
            newSelectClauseSpecRaw.add(new SelectClauseExprRawSpec(new ExprIdentNode(selectElement.getAssignedName()), null));
        }

        // Create Map or Wrapper event type from the select clause of the window.
        // If no columns selected, simply create a wrapper type
        boolean isWildcard = spec.getSelectClauseSpec().isUsingWildcard();
        if (isWildcard)
        {
            targetType = statementContext.getEventAdapterService().addWrapperType(typeName, selectFromType, properties);
        }
        else
        {
            // Some columns selected, use the types of the columns
            if (spec.getSelectClauseSpec().getSelectExprList().size() > 0)
            {
                targetType = statementContext.getEventAdapterService().addNestableMapType(typeName, properties);
            }
            else
            {
                // No columns selected, no wildcard, use the type as is or as a wrapped type
                if (selectFromType instanceof MapEventType)
                {
                    MapEventType mapType = (MapEventType) selectFromType;
                    targetType = statementContext.getEventAdapterService().addNestableMapType(typeName, mapType.getTypes());
                }
                else
                {
                    Map<String, Object> addOnTypes = new HashMap<String, Object>();
                    targetType = statementContext.getEventAdapterService().addWrapperType(typeName, selectFromType, addOnTypes);
                }
            }
        }

        FilterSpecCompiled filter = new FilterSpecCompiled(targetType, new ArrayList<FilterSpecParam>());
        return new Pair<FilterSpecCompiled, SelectClauseSpecRaw>(filter, newSelectClauseSpecRaw);
    }

    private static List<SelectClauseExprCompiledSpec> compileLimitedSelect(SelectClauseSpecRaw spec, String eplStatement, EventType singleType)
    {
        List<SelectClauseExprCompiledSpec> selectProps = new LinkedList<SelectClauseExprCompiledSpec>();
        StreamTypeService streams = new StreamTypeServiceImpl(new EventType[] {singleType}, new String[] {"stream_0"});

        for (SelectClauseElementRaw raw : spec.getSelectExprList())
        {
            if (!(raw instanceof SelectClauseExprRawSpec))
            {
                continue;
            }
            SelectClauseExprRawSpec exprSpec = (SelectClauseExprRawSpec) raw;
            ExprNode validatedExpression = null;
            try
            {
                validatedExpression = exprSpec.getSelectExpression().getValidatedSubtree(streams, null, null, null, null);
            }
            catch (ExprValidationException e)
            {
                throw new EPStatementException(e.getMessage(), eplStatement);
            }

            // determine an element name if none assigned
            String asName = exprSpec.getOptionalAsName();
            if (asName == null)
            {
                asName = validatedExpression.toExpressionString();
            }

            SelectClauseExprCompiledSpec validatedElement = new SelectClauseExprCompiledSpec(validatedExpression, asName);
            selectProps.add(validatedElement);
        }

        return selectProps;
    }

    public void dispatchStatementLifecycleEvent(StatementLifecycleEvent event)
    {
        for (StatementLifecycleObserver observer : observers)
        {
            observer.observe(event);
        }
    }
}
