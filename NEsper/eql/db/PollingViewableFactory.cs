using System;
using System.Data;
using System.Data.Common;
using System.Collections.Generic;
using System.Text;

using net.esper.compat;
using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.util;
using net.esper.view;

using org.apache.commons.logging;

namespace net.esper.eql.db
{
    /// <summary>
    /// Factory for a view onto historical data via SQL statement.
    /// </summary>

    public class PollingViewableFactory
    {
        /// <summary> Creates the viewable for polling via database SQL query.</summary>
        /// <param name="streamNumber">is the stream number of the view</param>
        /// <param name="databaseStreamSpec">provides the SQL statement, database name and additional info</param>
        /// <param name="databaseConfigService">for getting database connection and settings</param>
        /// <param name="eventAdapterService">for generating event beans from database information</param>
        /// <returns>viewable providing poll functionality</returns>
        /// <throws>ExprValidationException if the validation failed </throws>

        public static HistoricalEventViewable createDBStatementView(
            int streamNumber,
            DBStatementStreamSpec databaseStreamSpec,
            DatabaseConfigService databaseConfigService,
            EventAdapterService eventAdapterService)
        {
            #region "Constructing the SQL"
            // Parse the SQL for placeholders and text fragments
            IList<PlaceholderParser.Fragment> sqlFragments = null;
            try
            {
                sqlFragments = PlaceholderParser.parsePlaceholder(databaseStreamSpec.SqlWithSubsParams);
            }
            catch (PlaceholderParseException ex)
            {
                String text = "Error parsing SQL";
                throw new ExprValidationException(text + ", reason: " + ex.Message);
            }
            #endregion

            #region "Constructing the database connection"
            // Assemble a DbCommand and parameter list
            String preparedStatementText = createDbCommand(sqlFragments);
            String[] parameters = getParameters(sqlFragments);
            log.Debug(
                ".createDBEventStream preparedStatementText=" + preparedStatementText +
                " parameters=" + CollectionHelper.Render( parameters ) ) ;

            // Get a database connection
            String databaseName = databaseStreamSpec.DatabaseName;
            DatabaseConnectionFactory databaseConnectionFactory = null;
            try
            {
                databaseConnectionFactory = databaseConfigService.getConnectionFactory(databaseName);
            }
            catch (DatabaseConfigException ex)
            {
                String text = "Error connecting to database '" + databaseName + "'";
                log.Error(text, ex);
                throw new ExprValidationException(text + ", reason: " + ex.Message);
            }

            DbConnection connection = null;
            try
            {
                connection = databaseConnectionFactory.Connection;
            }
            catch (DatabaseConfigException ex)
            {
                String text = "Error connecting to database '" + databaseName + "'";
                log.Error(text, ex);
                throw new ExprValidationException(text + ", reason: " + ex.Message);
            }
            #endregion

            #region "Construct the database command and request the schema"
            // Execute prepared statement
            DbCommand prepared = null;
            DbDataReader reader = null;
            try
            {
            	prepared = connection.CreateCommand() ;
            	prepared.CommandText = preparedStatementText;
                reader = prepared.ExecuteReader(CommandBehavior.SchemaOnly);
            }
            catch (DbException ex)
            {
                try
                {
                	connection.Close() ;
                	connection.Dispose() ;
                }
                catch (DbException)
                {
                    // don't handle
                }
                
                String text = "Error executing statement '" + preparedStatementText + "'";
                log.Error(text, ex);
                throw new ExprValidationException(text + ", reason: " + ex.Message);
            }
            #endregion

            #region "Determine input parameters"
            // Interrogate prepared statement - parameters and result
            IList<String> inputParameters = new ELinkedList<String>();
            try
            {
            	IDataParameterCollection parameterMetaData = prepared.Parameters;
            	foreach( IDataParameter parameter in parameterMetaData )
                {
                    inputParameters.Add(parameter.ParameterName);
                }
            }
            catch (Exception ex)
            {
                try
                {
                	prepared.Dispose() ;
                }
                catch (DbException)
                {
                    // don't handle
                }
                try
                {
                	connection.Close() ;
                	connection.Dispose() ;
                }
                catch (DbException)
                {
                    // don't handle
                }
                String text = "Error obtaining parameter metadata from prepared statement '" + preparedStatementText + "'";
                log.Error(text, ex);
                throw new ExprValidationException(text + ", please check the statement, reason: " + ex.Message);
            }
            #endregion

            IDictionary<String, DBOutputTypeDesc> outputProperties = new Dictionary<String, DBOutputTypeDesc>();
            try
            {
                DataTable schemaTable = reader.GetSchemaTable();
                foreach( DataRow dataRow in schemaTable.Rows )
                {
                    String columnName = (String) dataRow["ColumnName"] ;
                    Type columnType = (Type) dataRow["DataType"] ;
                    int providerType = (int) dataRow["ProviderType"];

                    DBOutputTypeDesc outputType = new DBOutputTypeDesc(providerType, columnType);
                    outputProperties[columnName] = outputType;
                }

                reader.Close();
            }
            catch (Exception ex)
            {
                try
                {
                	prepared.Dispose() ;
                }
                catch (DbException)
                {
                    // don't handle
                }
                try
                {
                	connection.Close() ;
                	connection.Dispose() ;
                }
                catch (DbException)
                {
                    // don't handle
                }

                String text = "Error in statement '" + preparedStatementText + "', failed to obtain result metadata";
                log.Error(text, ex);
                throw new ExprValidationException(text + ", please check the statement, reason: " + ex.Message);
            }

            log.Debug(
                ".createDBEventStream" +
                " in=" + inputParameters.ToString() +
                " out=" + outputProperties.ToString());

            // Close statement
            try
            {
                prepared.Dispose();
            }
            catch (DbException e)
            {
                try
                {
                	connection.Close() ;
                	connection.Dispose() ;
                }
                catch (DbException)
                {
                    // don't handle
                }
                String text = "Error clsoing prepared statement";
                log.Error(text, e);
                throw new ExprValidationException(text + ", reason: " + e.Message);
            }

            // Close connection
            try
            {
				connection.Close() ;
				connection.Dispose() ;
            }
            catch (DbException e)
            {
                String text = "Error closing connection";
                log.Error(text, e);
                throw new ExprValidationException(text + ", reason: " + e.Message);
            }

            // Create event type
            // Construct an event type from SQL query result metadata
            EDictionary<String, Type> eventTypeFields = new EHashDictionary<String, Type>();
            foreach (String name in outputProperties.Keys)
            {
                DBOutputTypeDesc dbOutputDesc = outputProperties[name];
                Type clazz = dbOutputDesc.DataType;
                eventTypeFields[name] = clazz;
            }
            EventType eventType = eventAdapterService.CreateAnonymousMapType(eventTypeFields);

            // Get a proper connection and data cache
            ConnectionCache connectionCache = null;
            DataCache dataCache = null;
            try
            {
                connectionCache = databaseConfigService.getConnectionCache(databaseName, preparedStatementText);
                dataCache = databaseConfigService.getDataCache(databaseName);
            }
            catch (DatabaseConfigException e)
            {
                String text = "Error obtaining cache configuration";
                log.Error(text, e);
                throw new ExprValidationException(text + ", reason: " + e.Message);
            }

            PollExecStrategyDBQuery dbPollStrategy = new PollExecStrategyDBQuery(eventAdapterService, eventType, connectionCache, preparedStatementText, outputProperties);

            return new PollingViewable(streamNumber, inputParameters, dbPollStrategy, dataCache, eventType);
        }

        private static String createDbCommand(IEnumerable<PlaceholderParser.Fragment> parseFragments)
        {
            StringBuilder buffer = new StringBuilder();
            foreach (PlaceholderParser.Fragment fragment in parseFragments)
            {
                if (!fragment.IsParameter)
                {
                	buffer.Append(fragment.Value);
                }
                else
                {
                    buffer.Append("?");
                }
            }

            return buffer.ToString();
        }

        private static String[] getParameters(IEnumerable<PlaceholderParser.Fragment> parseFragements)
        {
            List<String> eventPropertyParams = new List<String>();
            foreach (PlaceholderParser.Fragment fragment in parseFragements)
            {
                if (fragment.IsParameter)
                {
                	eventPropertyParams.Add(fragment.Value) ;
                }
            }

            return eventPropertyParams.ToArray();
        }

        private static readonly Log log = LogFactory.GetLog(typeof(PollingViewableFactory));
    }
}
