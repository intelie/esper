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
        /// <param name="eventAdapterService">for generating event beans
        /// </param>
        /// <param name="preparedStatementText">is the SQL to use for polling
        /// </param>
        /// <param name="outputTypes">describe columns selected by the SQL
        /// </param>
        /// <param name="eventType">is the event type that this poll generates
        /// <param name="connectionCache">caches Connection and DbCommand
        /// </param>
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

        public virtual void Start()
        {
            resources = connectionCache.GetConnection();
        }

        public virtual void Done()
        {
            connectionCache.DoneWith(resources);
        }

        public virtual void Destroy()
        {
            connectionCache.Destroy();
        }

        public IList<EventBean> Poll(Object[] lookupValues)
        {
            IList<EventBean> result = null;
            try
            {
                result = Execute(resources.Second, lookupValues);
            }
            catch (EPException ex)
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
                            EDataDictionary row = new EDataDictionary();
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
