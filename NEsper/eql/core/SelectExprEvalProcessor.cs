using System;
using System.Collections.Generic;
using System.Collections.Specialized;

using net.esper.compat;
using net.esper.events;
using net.esper.eql.spec;
using net.esper.eql.expression;

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

        /// <summary>Ctor.</summary>
        /// <param name="selectionList">list of select-clause items</param>
        /// <param name="eventAdapterService">service for generating events and handling event types</param>
        /// <param name="insertIntoDesc">descriptor for insert-into clause contains column names overriding select clause names</param>
        /// <throws>net.esper.eql.expression.ExprValidationException thrown if any of the expressions don't validate</throws>

        public SelectExprEvalProcessor(IList<SelectExprElementNamedSpec> selectionList,
                                       InsertIntoDesc insertIntoDesc,
                                       EventAdapterService eventAdapterService)
        {
            this.eventAdapterService = eventAdapterService;

            if (selectionList.Count == 0)
            {
                throw new ArgumentException("Empty selection list not supported");
            }

            foreach (SelectExprElementNamedSpec entry in selectionList)
            {
                if (entry.AssignedName == null)
                {
                    throw new ArgumentException("Expected name for each expression has not been supplied");
                }
            }

            // Verify insert into clause
            if (insertIntoDesc != null)
            {
                verifyInsertInto(insertIntoDesc, selectionList);
            }

            // This function may modify
            init(selectionList, insertIntoDesc, eventAdapterService);
        }

        private void init(IList<SelectExprElementNamedSpec> selectionList,
                          InsertIntoDesc insertIntoDesc,
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
                    resultEventType = eventAdapterService.AddMapType(insertIntoDesc.EventTypeAlias, selPropertyTypes);
                }
                catch (EventAdapterException ex)
                {
                    throw new ExprValidationException(ex.Message);
                }
            }
            else
            {
                resultEventType = eventAdapterService.CreateAnonymousMapType(selPropertyTypes);
            }
        }

        /// <summary>
        /// Computes the select-clause results and returns an event of the result event type that contains, in it's
        /// properties, the selected items.
        /// </summary>
        /// <param name="eventsPerStream"></param>
        /// <returns>
        /// event with properties containing selected items
        /// </returns>
        public EventBean Process(EventBean[] eventsPerStream)
        {
        	EDataDictionary props = new EDataDictionary() ;
            for (int i = 0; i < expressionNodes.Length; i++)
            {
                Object evalResult = expressionNodes[i].Evaluate(eventsPerStream);
                props[columnNames[i]] = evalResult;
            }

            EventBean ev = eventAdapterService.CreateMapFromValues(props, resultEventType);
            return ev;
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

        private static void verifyInsertInto(InsertIntoDesc insertIntoDesc,
                                             IList<SelectExprElementNamedSpec> selectionList)
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
    }
}
