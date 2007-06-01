///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections;
using System.Collections.Generic;

using net.esper.client;
using net.esper.eql.spec;
using net.esper.collection;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.pattern;
using net.esper.util;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.core
{
	/// <summary>Provides statement lifecycle services.</summary>
    public class StatementLifecycleSvcImpl : StatementLifecycleSvc
    {
        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        private readonly EPServicesContext services;
        private readonly ManagedReadWriteLock eventProcessingRWLock;

        private readonly IDictionary<String, String> stmtNameToIdMap;
        private readonly IDictionary<String, EPStatementDesc> stmtIdToDescMap;
        private readonly IDictionary<String, EPStatement> stmtNameToStmtMap;

        /// <summary>Ctor.</summary>
        /// <param name="services">is engine services</param>
        public StatementLifecycleSvcImpl(EPServicesContext services)
        {
            this.services = services;

            // lock for starting and stopping statements
            this.eventProcessingRWLock = services.EventProcessingRWLock;

            this.stmtIdToDescMap = new EHashDictionary<String, EPStatementDesc>();
            this.stmtNameToStmtMap = new EHashDictionary<String, EPStatement>();
            this.stmtNameToIdMap = new EHashDictionary<String, String>();
        }

        public EPStatement CreateAndStart(StatementSpecRaw statementSpec, String expression, bool isPattern, String optStatementName)
        {
            lock (this)
            {
                // Generate statement id
                String statementId = UuidGenerator.Generate(expression);

                // Determine a statement name, i.e. use the id or use/generate one for the name passed in
                String statementName = statementId;
                if (optStatementName != null)
                {
                    statementName = GetUniqueStatementName(optStatementName, statementId);
                }

                EPStatementDesc desc = CreateStopped(statementSpec, expression, isPattern, statementName, statementId);
                Start(statementId, desc);
                return desc.EpStatement;
            }
        }

        /// <summary>Creates a started statement.</summary>
        /// <param name="statementSpec">is the statement def</param>
        /// <param name="expression">is the expression text</param>
        /// <param name="isPattern">is true for patterns,</param>
        /// <param name="statementName">is the statement name</param>
        /// <param name="statementId">is the statement id</param>
        /// <returns>statement</returns>
        protected EPStatement CreateStarted(StatementSpecRaw statementSpec, String expression, bool isPattern, String statementName, String statementId)
        {
            lock (this)
            {
                if (log.IsDebugEnabled())
                {
                    log.Debug(".start Creating and starting statement " + statementId);
                }
                EPStatementDesc desc = CreateStopped(statementSpec, expression, isPattern, statementName, statementId);
                Start(statementId, desc);
                return desc.EpStatement;
            }
        }

        /// <summary>Create stopped statement.</summary>
        /// <param name="statementSpec">statement definition</param>
        /// <param name="expression">is the expression text</param>
        /// <param name="isPattern">is true for patterns, false for non-patterns</param>
        /// <param name="statementName">is the statement name assigned or given</param>
        /// <param name="statementId">is the statement id</param>
        /// <returns>stopped statement</returns>
        protected EPStatementDesc CreateStopped(StatementSpecRaw statementSpec, String expression, bool isPattern, String statementName, String statementId)
        {
            lock (this)
            {
                EPStatementDesc statementDesc;
                EPStatementStartMethod startMethod;

                StatementContext statementContext = services.StatementContextFactory.MakeContext(statementId, statementName, expression, services);
                StatementSpecCompiled compiledSpec = Compile(statementSpec, expression, statementContext);

                // In a join statements if the same event type or it's deep super types are used in the join more then once,
                // then this is a self-join and the statement handle must know to dispatch the results together
                bool canSelfJoin = IsPotentialSelfJoin(compiledSpec.StreamSpecs);
                statementContext.EpStatementHandleCanSelfJoin = canSelfJoin;

                eventProcessingRWLock.AcquireWriteLock();
                try
                {
                    // create statement - may fail for parser and simple validation errors
                    EPStatementSPI statement = new EPStatementImpl(statementId, statementName, expression, isPattern, services.DispatchService, this);

                    // create start method
                    startMethod = new EPStatementStartMethod(compiledSpec, services, statementContext);

                    statementDesc = new EPStatementDesc(statement, startMethod, null);
                    stmtIdToDescMap.Put(statementId, statementDesc);
                    stmtNameToStmtMap.Put(statementName, statement);
                    stmtNameToIdMap.Put(statementName, statementId);
                }
                catch (RuntimeException ex)
                {
                    stmtIdToDescMap.Remove(statementId);
                    stmtNameToIdMap.Remove(statementName);
                    stmtNameToStmtMap.Remove(statementName);
                    throw ex;
                }
                finally
                {
                    eventProcessingRWLock.ReleaseWriteLock();
                }

                return statementDesc;
            }
        }

        private bool IsPotentialSelfJoin(List<StreamSpecCompiled> streamSpecs)
        {
            // not a join (pattern doesn't count)
            if (streamSpecs.Size() == 1)
            {
                return false;
            }

            // join - determine types joined
            List<EventType> filteredTypes = new ArrayList<EventType>();
            bool hasFilterStream = false;
            foreach (StreamSpecCompiled streamSpec in streamSpecs)
            {
                if (streamSpec is FilterStreamSpecCompiled)
                {
                    EventType type = ((FilterStreamSpecCompiled)streamSpec).FilterSpec.EventType;
                    filteredTypes.Add(type);
                    hasFilterStream = true;
                }
                else if (streamSpec is PatternStreamSpecCompiled)
                {
                    List<EvalFilterNode> filterNodes = EvalNode.RecusiveFilterChildNodes(((PatternStreamSpecCompiled)streamSpec).EvalNode);
                    foreach (EvalFilterNode filterNode in filterNodes)
                    {
                        filteredTypes.Add(filterNode.FilterSpec.EventType);
                    }
                }
                else if (streamSpec is DBStatementStreamSpec)
                {
                    // no action for these
                }
            }

            if (filteredTypes.Size() == 1)
            {
                return false;
            }
            // pattern-only streams are not self-joins
            if (!hasFilterStream)
            {
                return false;
            }

            // is type overlap
            for (int i = 0; i < filteredTypes.Size(); i++)
            {
                for (int j = i + 1; j < filteredTypes.Size(); j++)
                {
                    EventType typeOne = filteredTypes.Get(i);
                    EventType typeTwo = filteredTypes.Get(j);
                    if (typeOne == typeTwo)
                    {
                        return true;
                    }

                    foreach (EventType typeOneSuper in typeOne.SuperTypes)
                    {
                        if (typeOneSuper == typeTwo)
                        {
                            return true;
                        }
                    }
                    foreach (EventType typeTwoSuper in typeTwo.SuperTypes)
                    {
                        if (typeOne == typeTwoSuper)
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public void Start(String statementId)
        {
            lock (this)
            {
                if (log.IsDebugEnabled())
                {
                    log.Debug(".start Starting statement " + statementId);
                }

                // Acquire a lock for event processing as threads may be in the views used by the statement
                // and that could conflict with the destroy of views
                eventProcessingRWLock.AcquireWriteLock();
                try
                {
                    EPStatementDesc desc = stmtIdToDescMap.Get(statementId);
                    if (desc == null)
                    {
                        throw new IllegalStateException("Cannot start statement, statement is in destroyed state");
                    }
                    StartInternal(statementId, desc);
                }
                catch (RuntimeException ex)
                {
                    throw ex;
                }
                finally
                {
                    eventProcessingRWLock.ReleaseWriteLock();
                }
            }
        }

        /// <summary>Start the given statement.</summary>
        /// <param name="statementId">is the statement id</param>
        /// <param name="desc">is the cached statement info</param>
        public void Start(String statementId, EPStatementDesc desc)
        {
            if (log.IsDebugEnabled())
            {
                log.Debug(".start Starting statement " + statementId + " from desc=" + desc);
            }

            // Acquire a lock for event processing as threads may be in the views used by the statement
            // and that could conflict with the destroy of views
            eventProcessingRWLock.AcquireWriteLock();
            try
            {
                StartInternal(statementId, desc);
            }
            catch (RuntimeException ex)
            {
                throw ex;
            }
            finally
            {
                eventProcessingRWLock.ReleaseWriteLock();
            }
        }

        private void StartInternal(String statementId, EPStatementDesc desc)
        {
            if (log.IsDebugEnabled())
            {
                log.Debug(".startInternal Starting statement " + statementId + " from desc=" + desc);
            }

            if (desc.StartMethod == null)
            {
                throw new IllegalStateException("Statement start method not found for id " + statementId);
            }

            EPStatementSPI statement = desc.EpStatement;
            if (statement.State == EPStatementState.STARTED)
            {
                throw new IllegalStateException("Statement already started");
            }

            Pair<Viewable, EPStatementStopMethod> pair;
            try
            {
                pair = desc.StartMethod.Start();
            }
            catch (ExprValidationException ex)
            {
                stmtIdToDescMap.Remove(statementId);
                stmtNameToIdMap.Remove(statement.Name);
                stmtNameToStmtMap.Remove(statement.Name);
                log.Debug(".start Error starting view", ex);
                throw new EPStatementException("Error starting view: " + ex.Message, statement.Text);
            }
            catch (ViewProcessingException ex)
            {
                stmtIdToDescMap.Remove(statementId);
                stmtNameToIdMap.Remove(statement.Name);
                stmtNameToStmtMap.Remove(statement.Name);
                log.Debug(".start Error starting view", ex);
                throw new EPStatementException("Error starting view: " + ex.Message, statement.Text);
            }

            Viewable parentView = pair.First;
            EPStatementStopMethod stopMethod = pair.Second;
            descStopMethod = stopMethod;
            statementParentView = parentView;
            statementCurrentState = EPStatementState.STARTED;
        }

        public void Stop(String statementId)
        {
            lock (this)
            {
                // Acquire a lock for event processing as threads may be in the views used by the statement
                // and that could conflict with the destroy of views
                eventProcessingRWLock.AcquireWriteLock();
                try
                {
                    EPStatementDesc desc = stmtIdToDescMap.Get(statementId);
                    if (desc == null)
                    {
                        throw new IllegalStateException("Cannot stop statement, statement is in destroyed state");
                    }

                    EPStatementSPI statement = desc.EpStatement;
                    EPStatementStopMethod stopMethod = desc.StopMethod;
                    if (stopMethod == null)
                    {
                        throw new IllegalStateException("Stop method not found for statement " + statementId);
                    }

                    if (statement.State == EPStatementState.STOPPED)
                    {
                        throw new IllegalStateException("Statement already stopped");
                    }

                    statementParentView = null;
                    stopMethod.Stop();
                    descStopMethod = null;

                    statementCurrentState = EPStatementState.STOPPED;
                }
                catch (RuntimeException ex)
                {
                    throw ex;
                }
                finally
                {
                    eventProcessingRWLock.ReleaseWriteLock();
                }
            }
        }

        public void Destroy(String statementId)
        {
            lock (this)
            {
                // Acquire a lock for event processing as threads may be in the views used by the statement
                // and that could conflict with the destroy of views
                eventProcessingRWLock.AcquireWriteLock();
                try
                {
                    EPStatementDesc desc = stmtIdToDescMap.Get(statementId);
                    if (desc == null)
                    {
                        throw new IllegalStateException("Statement already destroyed");
                    }

                    EPStatementSPI statement = desc.EpStatement;
                    if (statement.State == EPStatementState.STARTED)
                    {
                        EPStatementStopMethod stopMethod = desc.StopMethod;
                        statementParentView = null;
                        stopMethod.Stop();
                    }

                    statementCurrentState = EPStatementState.DESTROYED;

                    stmtNameToStmtMap.Remove(statement.Name);
                    stmtNameToIdMap.Remove(statement.Name);
                    stmtIdToDescMap.Remove(statementId);
                }
                catch (RuntimeException ex)
                {
                    throw ex;
                }
                finally
                {
                    eventProcessingRWLock.ReleaseWriteLock();
                }
            }
        }

        public EPStatement GetStatementByName(String name)
        {
            lock (this)
            {
                return stmtNameToStmtMap.Get(name);
            }
        }

        /// <summary>Returns the statement given a statement id.</summary>
        /// <param name="id">is the statement id</param>
        /// <returns>statement</returns>
        public EPStatementSPI GetStatementById(String id)
        {
            return this.stmtIdToDescMap.Get(id).EpStatement;
        }

        public String[] GetStatementNames()
        {
            lock (this)
            {
                String[] statements = new String[stmtNameToStmtMap.Size()];
                int count = 0;
                foreach (String key in stmtNameToStmtMap.KeySet())
                {
                    statements[count++] = key;
                }
                return statements;
            }
        }

        public void StartAllStatements()
        {
            lock (this)
            {
                String[] statementIds = GetStatementIds();
                for (int i = 0; i < statementIds.length; i++)
                {
                    EPStatement statement = stmtIdToDescMap.Get(statementIds[i]).EpStatement;
                    if (statement.State == EPStatementState.STOPPED)
                    {
                        Start(statementIds[i]);
                    }
                }
            }
        }

        public void StopAllStatements()
        {
            lock (this)
            {
                String[] statementIds = GetStatementIds();
                for (int i = 0; i < statementIds.length; i++)
                {
                    EPStatement statement = stmtIdToDescMap.Get(statementIds[i]).EpStatement;
                    if (statement.State == EPStatementState.STARTED)
                    {
                        Stop(statementIds[i]);
                    }
                }
            }
        }

        public void DestroyAllStatements()
        {
            lock (this)
            {
                String[] statementIds = GetStatementIds();
                for (int i = 0; i < statementIds.length; i++)
                {
                    Destroy(statementIds[i]);
                }
            }
        }

        private String[] GetStatementIds()
        {
            String[] statementIds = new String[stmtNameToStmtMap.Size()];
            int count = 0;
            foreach (String id in stmtNameToIdMap.Values())
            {
                statementIds[count++] = id;
            }
            return statementIds;
        }

        private String GetUniqueStatementName(String statementName, String statementId)
	    {
	        String finalStatementName;

	        if (stmtNameToIdMap.ContainsKey(statementName))
	        {
	            int count = 0;
	            while(true)
	            {
	                finalStatementName = statementName + "--" + count;
	                if (!(stmtNameToIdMap.ContainsKey(finalStatementName)))
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

	        stmtNameToIdMap.Put(finalStatementName, statementId);
	        return finalStatementName;
	    }

        public void UpdatedListeners(String statementId, Set<UpdateListener> listeners)
        {
            log.Debug(".updatedListeners No action for base implementation");
        }

        /// <summary>Statement information.</summary>
        public class EPStatementDesc
        {
            private EPStatementSPI epStatement;
            private EPStatementStartMethod startMethod;
            private EPStatementStopMethod stopMethod;

            /// <summary>Ctor.</summary>
            /// <param name="epStatement">the statement</param>
            /// <param name="startMethod">the start method</param>
            /// <param name="stopMethod">the stop method</param>
            public EPStatementDesc(EPStatementSPI epStatement, EPStatementStartMethod startMethod, EPStatementStopMethod stopMethod)
            {
                this.epStatement = epStatement;
                this.startMethod = startMethod;
                this.stopMethod = stopMethod;
            }

            /// <summary>Returns the statement.</summary>
            /// <returns>statement.</returns>
            public EPStatementSPI GetEpStatement()
            {
                return epStatement;
            }

            /// <summary>Returns the start method.</summary>
            /// <returns>start method</returns>
            public EPStatementStartMethod GetStartMethod()
            {
                return startMethod;
            }

            /// <summary>Returns the stop method.</summary>
            /// <returns>stop method</returns>
            public EPStatementStopMethod GetStopMethod()
            {
                return stopMethod;
            }

            /// <summary>Sets the stop method.</summary>
            /// <param name="stopMethod">to set</param>
            public void SetStopMethod(EPStatementStopMethod stopMethod)
            {
                this.stopMethod = stopMethod;
            }
        }

        private static StatementSpecCompiled Compile(StatementSpecRaw spec, String eqlStatement, StatementContext statementContext)
        {
            List<StreamSpecCompiled> compiledStreams;

            try
            {
                compiledStreams = new ArrayList<StreamSpecCompiled>();
                foreach (StreamSpecRaw rawSpec in spec.StreamSpecs)
                {
                    StreamSpecCompiled compiled = rawSpec.Compile(statementContext.EventAdapterService, statementContext.MethodResolutionService);
                    compiledStreams.Add(compiled);
                }
            }
            catch (ExprValidationException ex)
            {
                throw new EPStatementException(ex.Message, eqlStatement);
            }
            catch (RuntimeException ex)
            {
                String text = "Unexpected error compiling statement";
                log.Error(".compile " + text, ex);
                throw new EPStatementException(text + ":" + ex.Class.Name + ":" + ex.Message, eqlStatement);
            }

            // Look for expressions with sub-selects in select expression list and filter expression
            // Recursively compile the statement within the statement.
            ExprNodeSubselectVisitor visitor = new ExprNodeSubselectVisitor();
            foreach (SelectExprElementRawSpec raw in spec.SelectClauseSpec.SelectList)
            {
                raw.SelectExpression.Accept(visitor);
            }
            if (spec.FilterRootNode != null)
            {
                spec.FilterRootNode.Accept(visitor);
            }
            foreach (ExprSubselectNode subselect in visitor.Subselects)
            {
                StatementSpecRaw raw = subselect.StatementSpecRaw;
                StatementSpecCompiled compiled = Compile(raw, eqlStatement, statementContext);
                subselectStatementSpecCompiled = compiled;
            }

            return new StatementSpecCompiled(
                    spec.InsertIntoDesc,
                    spec.SelectStreamSelectorEnum,
                    spec.SelectClauseSpec,
                    compiledStreams,
                    spec.OuterJoinDescList,
                    spec.FilterRootNode,
                    spec.GroupByExpressions,
                    spec.HavingExprRootNode,
                    spec.OutputLimitSpec,
                    spec.OrderByList,
                    visitor.Subselects
                    );
        }
    }
} // End of namespace
