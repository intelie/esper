using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Data;
using System.Data.Common;

using net.esper.client;
using net.esper.compat;
using net.esper.collection;
using net.esper.events;

namespace net.esper.eql.db
{
    /// <summary>
    /// Viewable providing historical data from a database.
    /// </summary>

    public class PollExecStrategyDBQuery : PollExecStrategy
    {
        private readonly EventAdapterService eventAdapterService;
        private readonly String preparedStatementText;
        private readonly IDictionary<String, DBOutputTypeDesc> outputTypes;
        private readonly ConnectionCache connectionCache;
        private readonly EventType eventType;

        private Pair<DbConnection, DbCommand> resources;

        /// <summary> Ctor.</summary>
        /// <param name="eventAdapterService">for generating event beans</param>
        /// <param name="eventType">is the event type that this poll generates</param>
        /// <param name="connectionCache">caches Connection and DbCommand</param>
        /// <param name="preparedStatementText">is the SQL to use for polling</param>
        /// <param name="outputTypes">describe columns selected by the SQL</param>
        public PollExecStrategyDBQuery(EventAdapterService eventAdapterService,
                                       EventType eventType,
                                       ConnectionCache connectionCache,
                                       String preparedStatementText,
                                       IDictionary<String, DBOutputTypeDesc> outputTypes)
        {
            this.eventAdapterService = eventAdapterService;
            this.eventType = eventType;
            this.connectionCache = connectionCache;
            this.preparedStatementText = preparedStatementText;
            this.outputTypes = outputTypes;
        }

        /// <summary>
        /// Start the poll, called before any poll operation.
        /// </summary>
        public virtual void Start()
        {
            resources = connectionCache.GetConnection();
        }

        /// <summary>
        /// Indicate we are done polling and can release resources.
        /// </summary>
        public virtual void Done()
        {
            connectionCache.DoneWith(resources);
        }

        /// <summary>
        /// Indicate we are no going to use this object again.
        /// </summary>
        public virtual void Destroy()
        {
            connectionCache.Destroy();
        }

        /// <summary>
        /// Poll events using the keys provided.
        /// </summary>
        /// <param name="lookupValues">is keys for exeuting a query or such</param>
        /// <returns>a list of events for the keys</returns>
        public IList<EventBean> Poll(Object[] lookupValues)
        {
            IList<EventBean> result = null;
            try
            {
                result = Execute(resources.Second, lookupValues);
            }
            catch (EPException)
            {
                connectionCache.DoneWith(resources);
                throw;
            }

            return result;
        }

        private IList<EventBean> Execute( DbCommand preparedStatement, Object[] lookupValuePerStream )
        {
            // set parameters
            int count = 0;
            for (int i = 0; i < lookupValuePerStream.Length; i++)
            {
                try
                {
                	DbParameter dbParam ;

                	int dbParamCount = preparedStatement.Parameters.Count;
                	if ( dbParamCount <= count )
                	{
                        dbParam = preparedStatement.CreateParameter();
                        dbParam.ParameterName = String.Format("param{0}", i);
                        dbParam.Value = lookupValuePerStream[i];
                		preparedStatement.Parameters.Add( dbParam ) ;
                	} 
                	else
                	{
                        dbParam = preparedStatement.Parameters[count];
                        dbParam.Value = lookupValuePerStream[i];
                    }
                	
                	
                    //preparedStatement.Parameters[count].Value = lookupValuePerStream[i] ;
                    //preparedStatement.setObject(count, lookupValuePerStream[i]);
                }
                catch (DbException ex)
                {
                    throw new EPException("Error setting parameter " + count, ex);
                }

                count++;
            }

            // execute
            try
            {
                using (DbDataReader dataReader = preparedStatement.ExecuteReader())
                {
                    // generate events for result set
                    IList<EventBean> rows = new List<EventBean>();

                    try
                    {
                        while (dataReader.Read())
                        {
                            DataDictionary row = new DataDictionary();
                            foreach (KeyValuePair<String, DBOutputTypeDesc> entry in outputTypes)
                            {
                                String columnName = entry.Key;
                                int columnIndex = dataReader.GetOrdinal(columnName);
                                Object value = dataReader.GetValue(columnIndex);
                                row[columnName] = value;
                            }

                            EventBean eventBeanRow = eventAdapterService.CreateMapFromValues(row, eventType);
                            rows.Add(eventBeanRow);
                        }
                    }
                    catch (DbException ex)
                    {
                        throw new EPException("Error reading results for statement '" + preparedStatementText + "'", ex);
                    }

                    return rows;
                }
            }
            catch (DbException ex)
            {
                throw new EPException("Error executing statement '" + preparedStatementText + "'", ex);
            }
        }
    }
}
