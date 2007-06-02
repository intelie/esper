using System;
using System.Collections.Generic;
using System.Collections.Specialized;

using net.esper.compat;
using net.esper.events;
using net.esper.eql.spec;
using net.esper.eql.expression;

using org.apache.commons.logging;

namespace net.esper.eql.core
{
	/// <summary>
    /// Processor for select-clause expressions that handles a list of selection items
    /// represented by expression nodes. Computes results based on matching events.
	/// </summary>
    
    public class SelectExprEvalProcessor : SelectExprProcessor
    {
        private ExprNode[] expressionNodes;
        private String[] columnNames;
        private EventType resultEventType;
        private readonly EventAdapterService eventAdapterService;
	    private readonly bool isUsingWildcard;
		private bool singleStreamWrapper;
		private SelectExprJoinWildcardProcessor joinWildcardProcessor;

	    /**
	     * Ctor.
	     * @param selectionList - list of select-clause items
	     * @param insertIntoDesc - descriptor for insert-into clause contains column names overriding select clause names
	     * @param isUsingWildcard - true if the wildcard (*) appears in the select clause
	     * @param typeService -service for information about streams
	     * @param eventAdapterService - service for generating events and handling event types
	     * @throws net.esper.eql.expression.ExprValidationException thrown if any of the expressions don't validate
	     */
	    public SelectExprEvalProcessor(IList<SelectExprElementCompiledSpec> selectionList,
	                                   InsertIntoDesc insertIntoDesc,
	                                   bool isUsingWildcard, 
	                                   StreamTypeService typeService, 
	                                   EventAdapterService eventAdapterService)
	    {
	        this.eventAdapterService = eventAdapterService;
	        this.isUsingWildcard = isUsingWildcard;

	        if (selectionList.Count == 0 && !isUsingWildcard)
	        {
	            throw new ArgumentException("Empty selection list not supported");
	        }

	        foreach (SelectExprElementCompiledSpec entry in selectionList)
	        {
	            if (entry.AssignedName == null)
	            {
	                throw new IllegalArgumentException("Expected name for each expression has not been supplied");
	            }
	        }

	        // Verify insert into clause
	        if (insertIntoDesc != null)
	        {
	            VerifyInsertInto(insertIntoDesc, selectionList);
	        }
	        
	        // Build a subordinate wildcard processor for joins
	        if(typeService.StreamNames.Length > 1 && isUsingWildcard)
	        {
	        	joinWildcardProcessor = new SelectExprJoinWildcardProcessor(typeService.StreamNames, typeService.EventTypes, eventAdapterService, null);
	        }
	        
	        // Resolve underlying event type in the case of wildcard select
	        EventType underlyingType = null;
	        if(isUsingWildcard)
	        {
	        	if(joinWildcardProcessor != null)
	        	{
	        		underlyingType = joinWildcardProcessor.ResultEventType;
	        	}
	        	else
	        	{
	        		underlyingType = typeService.EventTypes[0];
	        		if(underlyingType is WrapperEventType)
	        		{
	        			singleStreamWrapper = true;
	        		}
	        	}
	        	log.Debug(".ctor underlyingType==" + underlyingType);
	        }
	        log.Debug(".ctor singleStreamWrapper=" + singleStreamWrapper);
	        
	        // This function may modify
	        Init(selectionList, insertIntoDesc, underlyingType, eventAdapterService);
	    }

	    private void Init(IList<SelectExprElementCompiledSpec> selectionList,
	                      InsertIntoDesc insertIntoDesc,
	                      EventType eventType, 
	                      EventAdapterService eventAdapterService)
        {
            // Get expression nodes
            expressionNodes = new ExprNode[selectionList.Count];
            for (int i = 0; i < selectionList.Count; i++)
            {
            	expressionNodes[i] = selectionList[i].SelectExpression;
            }

            // Get column names
            if ((insertIntoDesc != null) && (insertIntoDesc.ColumnNames.Count > 0))
            {
            	columnNames = CollectionHelper.ToArray<string>( insertIntoDesc.ColumnNames ) ;
            }
            else
            {
                columnNames = new String[selectionList.Count];
                for (int i = 0; i < selectionList.Count; i++)
                {
                    columnNames[i] = selectionList[i].AssignedName;
                }
            }

            // Build event type
            EDictionary<String, Type> selPropertyTypes = new EHashDictionary<String, Type>();
            for (int i = 0; i < expressionNodes.Length; i++)
            {
                Type expressionReturnType = expressionNodes[i].ReturnType;
                selPropertyTypes[columnNames[i]] = expressionReturnType;
            }

            // If we have an alias for this type, add it
            if (insertIntoDesc != null)
            {
                try
                {
	                if (isUsingWildcard)
	                {
	                    resultEventType = eventAdapterService.AddWrapperType(insertIntoDesc.EventTypeAlias, eventType, selPropertyTypes);
	                }
	                else
	                {
	                    resultEventType = eventAdapterService.AddMapType(insertIntoDesc.EventTypeAlias, selPropertyTypes);
	                }
                }
                catch (EventAdapterException ex)
                {
                    throw new ExprValidationException(ex.Message);
                }
            }
            else
            {
	            if (isUsingWildcard)
	            {
	        	    resultEventType = eventAdapterService.CreateAnonymousWrapperType(eventType, selPropertyTypes);
	            }
	            else
	            {
	                resultEventType = eventAdapterService.CreateAnonymousMapType(selPropertyTypes);
	            }
	        }

	        log.Debug(".Init resultEventType=" + resultEventType);
        }

        /// <summary>
        /// Computes the select-clause results and returns an event of the result event type that contains, in it's
        /// properties, the selected items.
        /// </summary>
        /// <param name="eventsPerStream"></param>
        /// <returns>
        /// event with properties containing selected items
        /// </returns>
        public EventBean Process(EventBean[] eventsPerStream, bool isNewData)
        {
			// Evaluate all expressions and build a map of name-value pairs
        	EDataDictionary props = new EDataDictionary() ;
            for (int i = 0; i < expressionNodes.Length; i++)
            {
                Object evalResult = expressionNodes[i].Evaluate(eventsPerStream, isNewData);
                props[columnNames[i]] = evalResult;
            }

	        if(isUsingWildcard)
	        {
	        	// In case of a wildcard and single stream that is itself a 
	        	// wrapper bean, we also need to add the map properties
	        	if(singleStreamWrapper)
	        	{
	        		WrapperEventBean wrapper = (WrapperEventBean)eventsPerStream[0];
	        		if(wrapper != null)
	        		{
	        			EDictionary<String, Object> map = (EDictionary<String, Object>)wrapper.UnderlyingMap;
	        			log.Debug(".Process additional properties=" + map);
	        			props.PutAll(map);
	        		}
	        	}
	        	return eventAdapterService.CreateWrapper(GetEvent(eventsPerStream, isNewData), props, resultEventType);
	        }
	        else
	        {
	        	return eventAdapterService.CreateMapFromValues(props, resultEventType);
	        }
        }

        /// <summary>
        /// Returns the event type that represents the select-clause items.
        /// </summary>
        /// <value></value>
        /// <returns> event type representing select-clause items
        /// </returns>
        public EventType ResultEventType
        {
            get
            {
                return resultEventType;
            }
        }

        private static void VerifyInsertInto(InsertIntoDesc insertIntoDesc,
                                             IList<SelectExprElementCompiledSpec> selectionList)
        {
            // Verify all column names are unique
            ISet<String> names = new EHashSet<String>();
            foreach (String element in insertIntoDesc.ColumnNames)
            {
                if (names.Contains(element))
                {
                    throw new ExprValidationException("Property name '" + element + "' appears more then once in insert-into clause");
                }
                names.Add(element);
            }

            // Verify number of columns matches the select clause
            if ((insertIntoDesc.ColumnNames.Count > 0) &&
                (insertIntoDesc.ColumnNames.Count != selectionList.Count))
            {
                throw new ExprValidationException("Number of supplied values in the select clause does not match insert-into clause");
            }
        }

	    private EventBean getEvent(EventBean[] eventsPerStream, bool isNewData)
	    {
	        if(joinWildcardProcessor != null)
	    	{
	    		return joinWildcardProcessor.Process(eventsPerStream, isNewData);
	    	}
	    	else
	    	{
	    		return eventsPerStream[0];
	    	}
	    }		
		
        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
