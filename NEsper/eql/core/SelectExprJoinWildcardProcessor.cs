using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.core
{
    /// <summary>
    /// Processor for select-clause expressions that handles wildcards. Computes results based on matching events.
    /// </summary>
    
    public class SelectExprJoinWildcardProcessor : SelectExprProcessor
    {
        virtual public EventType ResultEventType
        {
            get { return resultEventType; }
        }

        private readonly String[] streamNames;
        private readonly EventType resultEventType;
        private readonly EventAdapterService eventAdapterService;

        /// <summary> Ctor.</summary>
        /// <param name="streamNames">- name of each stream
        /// </param>
        /// <param name="streamTypes">- type of each stream
        /// </param>
        /// <param name="eventAdapterService">- service for generating events and handling event types
        /// </param>
        public SelectExprJoinWildcardProcessor(String[] streamNames, EventType[] streamTypes, EventAdapterService eventAdapterService)
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
            resultEventType = eventAdapterService.CreateAnonymousMapType(eventTypeMap);
        }

        public virtual EventBean Process(EventBean[] eventsPerStream)
        {
        	EDataDictionary tuple = new EDataDictionary() ;
            for (int i = 0; i < streamNames.Length; i++)
            {
                AssertionFacility.assertTrue(streamNames[i] != null, "Event name for stream " + i + " is null");

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
