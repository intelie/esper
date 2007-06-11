using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.util;

namespace net.esper.eql.core
{
    /// <summary>
    /// Processor for select-clause expressions that handles wildcards. Computes results based on matching events.
    /// </summary>
    
    public class SelectExprJoinWildcardProcessor : SelectExprProcessor
    {
        /// <summary>
        /// Returns the event type that represents the select-clause items.
        /// </summary>
        /// <value></value>
        /// <returns> event type representing select-clause items
        /// </returns>
        virtual public EventType ResultEventType
        {
            get { return resultEventType; }
        }

        private readonly String[] streamNames;
        private readonly EventType resultEventType;
        private readonly EventAdapterService eventAdapterService;

	    /**
	     * Ctor.
	     * @param streamNames - name of each stream
	     * @param streamTypes - type of each stream
	     * @param eventAdapterService - service for generating events and handling event types
	     * @param insertIntoDesc - describes the insert-into clause
	     * @throws ExprValidationException if the expression validation failed 
	     */
	    public SelectExprJoinWildcardProcessor(
			String[] streamNames, 
			EventType[] streamTypes, 
			EventAdapterService eventAdapterService, 
			InsertIntoDesc insertIntoDesc)
        {
            if ((streamNames.Length < 2) || (streamTypes.Length < 2) || (streamNames.Length != streamTypes.Length))
            {
                throw new ArgumentException("Stream names and types parameter length is invalid, expected use of this class is for join statements");
            }

            this.streamNames = streamNames;
            this.eventAdapterService = eventAdapterService;

            // Create EventType of result join events
            EDictionary<String, Type> eventTypeMap = new EHashDictionary<String, Type>();
            for (int i = 0; i < streamTypes.Length; i++)
            {
                eventTypeMap[streamNames[i]] = streamTypes[i].UnderlyingType;
            }

	        // If we have an alias for this type, add it
	        if (insertIntoDesc != null)
	        {
	        	try
	            {
	                resultEventType = eventAdapterService.AddMapType(insertIntoDesc.EventTypeAlias, eventTypeMap);
	            }
	            catch (EventAdapterException ex)
	            {
	                throw new ExprValidationException(ex.Message);
	            }
	        }
	        else
	        {
	            resultEventType = eventAdapterService.CreateAnonymousMapType(eventTypeMap);
	        }
        }

        /// <summary>
        /// Computes the select-clause results and returns an event of the result event type that contains, in it's
        /// properties, the selected items.
        /// </summary>
        /// <param name="eventsPerStream">is per stream the event</param>
        /// <param name="isNewData">indicates whether we are dealing with new data (istream) or old data (rstream)</param>
        /// <returns>
        /// event with properties containing selected items
        /// </returns>
        public virtual EventBean Process(EventBean[] eventsPerStream, bool isNewData)
        {
        	EDataDictionary tuple = new EDataDictionary() ;
            for (int i = 0; i < streamNames.Length; i++)
            {
                if (streamNames[i] == null)
	            {
	                throw new IllegalStateException("Event name for stream " + i + " is null");
	            }

                if (eventsPerStream[i] != null)
                {
                    tuple[streamNames[i]] = eventsPerStream[i].Underlying;
                }
                else
                {
                    tuple[streamNames[i]] = null;
                }
            }

            return eventAdapterService.CreateMapFromValues(tuple, resultEventType);
        }
    }
}
