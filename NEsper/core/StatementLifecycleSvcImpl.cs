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
using net.esper.collection;
using net.esper.compat;
using net.esper.eql.expression;
using net.esper.eql.spec;
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

        private readonly EDictionary<String, String> stmtNameToIdMap;
        private readonly EDictionary<String, EPStatementDesc> stmtIdToDescMap;
        private readonly EDictionary<String, EPStatement> stmtNameToStmtMap;

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

        /// <summary>
        /// Create and start the statement.
        /// </summary>
        /// <param name="statementSpec">is the statement definition in bean object form, raw unvalidated and unoptimized.</param>
        /// <param name="expression">is the expression text</param>
        /// <param name="isPattern">is an indicator on whether this is a pattern statement and thus the iterator must return the last result,
        /// versus for non-pattern statements the iterator returns view content.</param>
        /// <param name="optStatementName">is an optional statement name, null if none was supplied</param>
        /// <returns>started statement</returns>
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
                if (log.IsDebugEnabled)
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

                StatementContext statementContext = services.StatementContextFactory.MakeContext(statementId, statementName, expression, services);
                StatementSpecCompiled compiledSpec = Compile(statementSpec, expression, statementContext);

                // In a join statements if the same event type or it's deep super types are used in the join more then once,
                // then this is a self-join and the statement handle must know to dispatch the results together
                bool canSelfJoin = IsPotentialSelfJoin(compiledSpec.StreamSpecs);
                statementContext.EpStatementHandle.CanSelfJoin = canSelfJoin;

                eventProcessingRWLock.AcquireWriteLock();
                try
                {
                    // create statement - may fail for parser and simple validation errors
                    EPStatementSPI statement = new EPStatementImpl(statementId, statementName, expression, isPattern, services.DispatchService, this);

                    // create start method
                    EPStatementStartMethod startMethod;
                    startMethod = new EPStatementStartMethod(compiledSpec, services, statementContext);

                    statementDesc = new EPStatementDesc(statement, startMethod, null);
                    stmtIdToDescMap[statementId] = statementDesc;
                    stmtNameToStmtMap[statementName] = statement;
                    stmtNameToIdMap[statementName] = statementId;
                }
                catch (Exception)
                {
                    stmtIdToDescMap.Remove(statementId);
                    stmtNameToIdMap.Remove(statementName);
                    stmtNameToStmtMap.Remove(statementName);
                    throw;
                }
                finally
                {
                    eventProcessingRWLock.ReleaseWriteLock();
                }

                return statementDesc;
            }
        }

        private static bool IsPotentialSelfJoin(IList<StreamSpecCompiled> streamSpecs)
        {
            // not a join (pattern doesn't count)
            if (streamSpecs.Count == 1)
            {
                return false;
            }

            // join - determine types joined
            List<EventType> filteredTypes = new List<EventType>();
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

            if (filteredTypes.Count == 1)
            {
                return false;
            }
            // pattern-only streams are not self-joins
            if (!hasFilterStream)
            {
                return false;
            }

            // is type overlap
            for (int i = 0; i < filteredTypes.Count; i++)
            {
                for (int j = i + 1; j < filteredTypes.Count; j++)
                {
                    EventType typeOne = filteredTypes[i];
                    EventType typeTwo = filteredTypes[j];
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

        /// <summary>
        /// Start statement by statement id.
        /// </summary>
        /// <param name="statementId">of the statement to start.</param>
        public void Start(String statementId)
        {
            lock (this)
            {
                if (log.IsDebugEnabled)
                {
                    log.Debug(".start Starting statement " + statementId);
                }

                // Acquire a lock for event processing as threads may be in the views used by the statement
                // and that could conflict with the destroy of views
                eventProcessingRWLock.AcquireWriteLock();
                try
                {
                    EPStatementDesc desc = stmtIdToDescMap.Fetch(statementId);
                    if (desc == null)
                    {
                        throw new IllegalStateException("Cannot start statement, statement is in destroyed state");
                    }
                    StartInternal(statementId, desc);
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
            if (log.IsDebugEnabled)
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
            finally
            {
                eventProcessingRWLock.ReleaseWriteLock();
            }
        }

        /// <summary>
        /// Internal start mechanism.
        /// </summary>
        /// <param name="statementId">The statement id.</param>
        /// <param name="desc">The desc.</param>
        private void StartInternal(String statementId, EPStatementDesc desc)
        {
            if (log.IsDebugEnabled)
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
            desc.StopMethod = stopMethod;
            statement.ParentView = parentView;
            statement.CurrentState = EPStatementState.STARTED;
        }

        /// <summary>
        /// Stop statement by statement id.
        /// </summary>
        /// <param name="statementId">of the statement to stop.</param>
        public void Stop(String statementId)
        {
            lock (this)
            {
                // Acquire a lock for event processing as threads may be in the views used by the statement
                // and that could conflict with the destroy of views
                eventProcessingRWLock.AcquireWriteLock();
                try
                {
                    EPStatementDesc desc = stmtIdToDescMap.Fetch(statementId);
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

                    statement.ParentView = null;
                    stopMethod();
                    desc.StopMethod = null;

                    statement.CurrentState = EPStatementState.STOPPED;
                }
                finally
                {
                    eventProcessingRWLock.ReleaseWriteLock();
                }
            }
        }

        /// <summary>
        /// Destroy statement by statement id.
        /// </summary>
        /// <param name="statementId">statementId of the statement to destroy</param>
        public void Destroy(String statementId)
        {
            lock (this)
            {
                // Acquire a lock for event processing as threads may be in the views used by the statement
                // and that could conflict with the destroy of views
                eventProcessingRWLock.AcquireWriteLock();
                try
                {
                    EPStatementDesc desc = stmtIdToDescMap.Fetch(statementId);
                    if (desc == null)
                    {
                        throw new IllegalStateException("Statement already destroyed");
                    }

                    EPStatementSPI statement = desc.EpStatement;
                    if (statement.State == EPStatementState.STARTED)
                    {
                        EPStatementStopMethod stopMethod = desc.StopMethod;
                        statement.ParentView = null;
                        stopMethod() ;
                    }

                    statement.CurrentState = EPStatementState.DESTROYED;

                    stmtNameToStmtMap.Remove(statement.Name);
                    stmtNameToIdMap.Remove(statement.Name);
                    stmtIdToDescMap.Remove(statementId);
                }
                catch (Exception ex)
                {
                    throw ex;
                }
                finally
                {
                    eventProcessingRWLock.ReleaseWriteLock();
                }
            }
        }

        /// <summary>
        /// Returns the statement by the given name, or null if no such statement exists.
        /// </summary>
        /// <param name="name">is the statement name</param>
        /// <returns>
        /// statement for the given name, or null if no such statement existed
        /// </returns>
        public EPStatement GetStatementByName(String name)
        {
            lock (this)
            {
                return stmtNameToStmtMap.Fetch(name);
            }
        }

        /// <summary>Returns the statement given a statement id.</summary>
        /// <param name="id">is the statement id</param>
        /// <returns>statement</returns>
        public EPStatementSPI GetStatementById(String id)
        {
        	return this.stmtIdToDescMap[id].EpStatement;
        }

        /// <summary>
        /// Returns an array of statement names. If no statement has been created, an empty array is returned.
        /// <para>
        /// Only returns started and stopped statements.
        /// </para>
        /// </summary>
        /// <value>The statement names.</value>
        /// <returns>statement names</returns>
        public IList<string> StatementNames
        {
        	get
        	{
        		lock (this)
	            {
	                String[] statements = new String[stmtNameToStmtMap.Count];
	                int count = 0;
	                foreach (String key in stmtNameToStmtMap.Keys)
	                {
	                    statements[count++] = key;
	                }
	                return statements;
	            }
        	}
        }

        /// <summary>
        /// Starts all stopped statements. First statement to fail supplies the exception.
        /// </summary>
        /// <throws>EPException to indicate a start error.</throws>
        public void StartAllStatements()
        {
            lock (this)
            {
                IList<String> statementIds = StatementIds;
                foreach( string statementId in statementIds )
                {
                	EPStatement statement = stmtIdToDescMap[statementId].EpStatement;
                    if (statement.State == EPStatementState.STOPPED)
                    {
                        Start(statementId);
                    }
                }
            }
        }

        /// <summary>
        /// Stops all started statements. First statement to fail supplies the exception.
        /// </summary>
        /// <throws>EPException to indicate a start error.</throws>
        public void StopAllStatements()
        {
            lock (this)
            {
                IList<String> statementIds = StatementIds;
                foreach( string statementId in statementIds )
                {
                	EPStatement statement = stmtIdToDescMap[statementId].EpStatement;
                    if (statement.State == EPStatementState.STARTED)
                    {
                        Stop(statementId);
                    }
                }
            }
        }

        /// <summary>
        /// Destroys all started statements. First statement to fail supplies the exception.
        /// </summary>
        /// <throws>EPException to indicate a start error.</throws>
        public void DestroyAllStatements()
        {
            lock (this)
            {
                IList<String> statementIds = StatementIds;
                foreach( string statementId in statementIds )
                {
                    Destroy(statementId);
                }
            }
        }

        /// <summary>
        /// Gets the statement ids.
        /// </summary>
        /// <value>The statement ids.</value>
        private IList<string> StatementIds
        {
        	get
        	{
	            String[] statementIds = new String[stmtNameToStmtMap.Count];
	            int count = 0;
	            foreach (String id in stmtNameToIdMap.Values)
	            {
	                statementIds[count++] = id;
	            }
	            return statementIds;
        	}
        }

        /// <summary>
        /// Gets the name of the unique statement.
        /// </summary>
        /// <param name="statementName">Name of the statement.</param>
        /// <param name="statementId">The statement id.</param>
        /// <returns></returns>
        private string GetUniqueStatementName(string statementName, string statementId)
	    {
	        string finalStatementName;

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
	                if (count > Int32.MaxValue - 2)
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

	        stmtNameToIdMap[finalStatementName] = statementId;
	        return finalStatementName;
	    }

        /// <summary>
        /// Statements indicate that listeners have been added through this method.
        /// </summary>
        /// <param name="statementId">is the statement id for which listeners were added</param>
        /// <param name="listeners">is the set of listeners after adding the new listener</param>
        public void UpdatedListeners(string statementId, Set<UpdateListener> listeners)
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
            public EPStatementSPI EpStatement
            {
            	get { return epStatement; }
            }

            /// <summary>Returns the start method.</summary>
            /// <returns>start method</returns>
            public EPStatementStartMethod StartMethod
            {
            	get { return startMethod; }
            }

            /// <summary>Gets or sets the stop method.</summary>
            /// <returns>stop method</returns>
            public EPStatementStopMethod StopMethod
            {
            	get { return stopMethod; }
            	set { stopMethod = value ; }
            }
        }

        private static StatementSpecCompiled Compile(StatementSpecRaw spec, String eqlStatement, StatementContext statementContext)
        {
            List<StreamSpecCompiled> compiledStreams;

            try
            {
                compiledStreams = new List<StreamSpecCompiled>();
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
            catch (Exception ex)
            {
                String text = "Unexpected error compiling statement";
                log.Error(".compile " + text, ex);
                throw new EPStatementException(text + ":" + ex.GetType().FullName + ":" + ex.Message, eqlStatement);
            }

            // Look for expressions with sub-selects in select expression list and filter expression
            // Recursively compile the statement within the statement.
            ExprNodeSubselectVisitor visitor = new ExprNodeSubselectVisitor();
            foreach (SelectExprElementRawSpec raw in spec.SelectClauseSpec.SelectList)
            {
                raw.SelectExpression.Accept(visitor);
            }
            if (spec.FilterExprRootNode != null)
            {
                spec.FilterExprRootNode.Accept(visitor);
            }
            foreach (ExprSubselectNode subselect in visitor.Subselects)
            {
                StatementSpecRaw raw = subselect.StatementSpecRaw;
                StatementSpecCompiled compiled = Compile(raw, eqlStatement, statementContext);
                subselect.StatementSpecCompiled = compiled;
            }

            return new StatementSpecCompiled(
                    spec.InsertIntoDesc,
                    spec.SelectStreamSelectorEnum,
                    spec.SelectClauseSpec,
                    compiledStreams,
                    spec.OuterJoinDescList,
                    spec.FilterExprRootNode,
                    spec.GroupByExpressions,
                    spec.HavingExprRootNode,
                    spec.OutputLimitSpec,
                    spec.OrderByList,
                    visitor.Subselects
                    );
        }
    }
} // End of namespace
