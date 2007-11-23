package net.esper.eql.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.SelectExprElementCompiledSpec;
import net.esper.eql.spec.InsertIntoDesc;
import net.esper.eql.spec.SelectExprElementStreamCompiledSpec;
import net.esper.event.*;
import net.esper.util.ExecutionPathDebugLog;

import java.util.*;

/**
 * Processor for select-clause expressions that handles a list of selection items represented by
 * expression nodes. Computes results based on matching events.
 */
public class SelectExprEvalProcessorStreams implements SelectExprProcessor
{
	private static final Log log = LogFactory.getLog(SelectExprEvalProcessorStreams.class);

    private final EventAdapterService eventAdapterService;
    private final List<SelectExprElementStreamCompiledSpec> aliasedStreams;
    private final List<SelectExprElementStreamCompiledSpec> unaliasedStreams;
    private boolean singleStreamWrapper;
    private boolean isUsingWildcard;

    private ExprNode[] expressionNodes;
    private String[] columnNames;
    private EventType resultEventType;
    private EventType underlyingEventType;
    private int underlyingStreamNumber;

    /**
     * Ctor.
     * @param selectionList - list of select-clause items
     * @param insertIntoDesc - descriptor for insert-into clause contains column names overriding select clause names
     * @param isUsingWildcard - true if the wildcard (*) appears in the select clause
     * @param typeService -service for information about streams
     * @param eventAdapterService - service for generating events and handling event types
     * @param selectedStreams - list of stream selectors (e.g. select alias.* from Event as alias)
     * @throws net.esper.eql.expression.ExprValidationException thrown if any of the expressions don't validate
     */
    public SelectExprEvalProcessorStreams(List<SelectExprElementCompiledSpec> selectionList,
                                   List<SelectExprElementStreamCompiledSpec> selectedStreams,
                                   InsertIntoDesc insertIntoDesc,
                                   boolean isUsingWildcard,
                                   StreamTypeService typeService,
                                   EventAdapterService eventAdapterService) throws ExprValidationException
    {
        this.eventAdapterService = eventAdapterService;
        this.isUsingWildcard = isUsingWildcard;

        // Get the un-aliased stream selectors (i.e. select s0.* from S0 as s0)
        unaliasedStreams = new ArrayList<SelectExprElementStreamCompiledSpec>();
        aliasedStreams = new ArrayList<SelectExprElementStreamCompiledSpec>();
        for (SelectExprElementStreamCompiledSpec spec : selectedStreams)
        {
            if (spec.getOptionalAliasName() == null)
            {
                unaliasedStreams.add(spec);
            }
            else
            {
                aliasedStreams.add(spec);
            }
        }

        // Verify insert into clause
        if (insertIntoDesc != null)
        {
            verifyInsertInto(insertIntoDesc, selectionList, aliasedStreams, isUsingWildcard, typeService);
        }

        // Error if there are more then one un-aliased streams (i.e. select s0.*, s1.* from S0 as s0, S1 as s1)
        // Thus there is only 1 unaliased stream selector maximum.
        if (unaliasedStreams.size() > 1)
        {
            throw new ExprValidationException("A column alias must be supplied for all but one stream if multiple streams are selected via the stream.* notation");
        }

        // Resolve underlying event type in the case of wildcard or non-aliased stream select
        if((isUsingWildcard) || (!unaliasedStreams.isEmpty()))
        {
            if (!unaliasedStreams.isEmpty())
            {
                underlyingStreamNumber = unaliasedStreams.get(0).getStreamNumber();
                underlyingEventType = typeService.getEventTypes()[underlyingStreamNumber];
            }
            else
            {
                // no un-aliases stream selectors, but a wildcard was specified
                if (typeService.getEventTypes().length == 1)
                {
                    // not a join, we are using the selected event 
                    underlyingEventType = typeService.getEventTypes()[0];
                    if(underlyingEventType instanceof WrapperEventType)
                    {
                        singleStreamWrapper = true;
                    }
                }
                else
                {
                    // For joins, all results are placed in a map with properties for each stream
                    underlyingEventType = null;
                }
            }
        }

        init(selectionList, aliasedStreams, insertIntoDesc, eventAdapterService, typeService);
    }

    private void init(List<SelectExprElementCompiledSpec> selectionList,
                      List<SelectExprElementStreamCompiledSpec> aliasedStreams,
                      InsertIntoDesc insertIntoDesc,
                      EventAdapterService eventAdapterService,
                      StreamTypeService typeService)
        throws ExprValidationException
    {
        // Get expression nodes
        expressionNodes = new ExprNode[selectionList.size()];
        for (int i = 0; i < selectionList.size(); i++)
        {
            expressionNodes[i] = selectionList.get(i).getSelectExpression();
        }

        // Get column names
        if ((insertIntoDesc != null) && (!insertIntoDesc.getColumnNames().isEmpty()))
        {
            columnNames = insertIntoDesc.getColumnNames().toArray(new String[0]);
        }
        else
        {
            int numStreamColumnsJoin = 0;
            if (isUsingWildcard && typeService.getEventTypes().length > 1)
            {
                numStreamColumnsJoin = typeService.getEventTypes().length;
            }
            columnNames = new String[selectionList.size() + aliasedStreams.size() + numStreamColumnsJoin];
            int count = 0;
            for (SelectExprElementCompiledSpec aSelectionList : selectionList)
            {
                columnNames[count] = aSelectionList.getAssignedName();
                count++;
            }
            for (SelectExprElementStreamCompiledSpec aSelectionList : aliasedStreams)
            {
                columnNames[count] = aSelectionList.getOptionalAliasName();
                count++;
            }
            // for wildcard joins, add the streams themselves
            if (isUsingWildcard && typeService.getEventTypes().length > 1)
            {
                for (String streamName : typeService.getStreamNames())
                {
                    columnNames[count] = streamName;
                    count++;
                }
            }
        }

        // Build event type that reflects all selected properties
        Map<String, Class> selPropertyTypes = new HashMap<String, Class>();
        int count = 0;
        for (ExprNode expressionNode : expressionNodes)
        {
            Class expressionReturnType = expressionNode.getType();
            selPropertyTypes.put(columnNames[count], expressionReturnType);
            count++;
        }
        for (SelectExprElementStreamCompiledSpec element : aliasedStreams)
        {
            EventType eventTypeStream = typeService.getEventTypes()[element.getStreamNumber()];
            Class expressionReturnType = eventTypeStream.getUnderlyingType();
            selPropertyTypes.put(columnNames[count], expressionReturnType);
            count++;
        }
        if (isUsingWildcard && typeService.getEventTypes().length > 1)
        {
            for (int i = 0; i < typeService.getEventTypes().length; i++)
            {
                EventType eventTypeStream = typeService.getEventTypes()[i];
                Class expressionReturnType = eventTypeStream.getUnderlyingType();
                selPropertyTypes.put(columnNames[count], expressionReturnType);
                count++;
            }
        }

        // If we have an alias for this type, add it
        if (insertIntoDesc != null)
        {
            try
            {
                if (underlyingEventType != null)
                {
                    resultEventType = eventAdapterService.addWrapperType(insertIntoDesc.getEventTypeAlias(), underlyingEventType, selPropertyTypes);
                }
                else
                {
                    resultEventType = eventAdapterService.addMapType(insertIntoDesc.getEventTypeAlias(), selPropertyTypes);
                }
            }
            catch (EventAdapterException ex)
            {
                throw new ExprValidationException(ex.getMessage());
            }
        }
        else
        {
            if (underlyingEventType != null)
            {
        	    resultEventType = eventAdapterService.createAnonymousWrapperType(underlyingEventType, selPropertyTypes);
            }
            else
            {
                resultEventType = eventAdapterService.createAnonymousMapType(selPropertyTypes);
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug(".init resultEventType=" + resultEventType);
        }
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData)
    {
        // Evaluate all expressions and build a map of name-value pairs
        Map<String, Object> props = new HashMap<String, Object>();
        int count = 0;
        for (ExprNode expressionNode : expressionNodes)
        {
            Object evalResult = expressionNode.evaluate(eventsPerStream, isNewData);
            props.put(columnNames[count], evalResult);
            count++;
        }
        for (SelectExprElementStreamCompiledSpec element : aliasedStreams)
        {
            Object value = eventsPerStream[element.getStreamNumber()].getUnderlying();
            props.put(columnNames[count], value);
            count++;
        }
        if (isUsingWildcard && eventsPerStream.length > 1)
        {
            for (EventBean anEventsPerStream : eventsPerStream)
            {
                Object value = anEventsPerStream.getUnderlying();
                props.put(columnNames[count], value);
                count++;
            }
        }

        if(underlyingEventType != null)
        {
        	// In case of a wildcard and single stream that is itself a
        	// wrapper bean, we also need to add the map properties
        	if(singleStreamWrapper)
        	{
        		DecoratingEventBean wrapper = (DecoratingEventBean)eventsPerStream[0];
        		if(wrapper != null)
        		{
        			Map<String, Object> map = wrapper.getDecoratingProperties();
                    if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
                    {
        			    log.debug(".process additional properties=" + map);
                    }
                    props.putAll(map);
        		}
        	}

            EventBean event = eventsPerStream[underlyingStreamNumber];

            // Using a wrapper bean since we cannot use the same event type else same-type filters match.
            // Wrapping it even when not adding properties is very inexpensive.
            return eventAdapterService.createWrapper(event, props, resultEventType);
        }
        else
        {
        	return eventAdapterService.createMapFromValues(props, resultEventType);
        }
    }

    public EventType getResultEventType()
    {
        return resultEventType;
    }

    private static void verifyInsertInto(InsertIntoDesc insertIntoDesc,
                                         List<SelectExprElementCompiledSpec> selectionList,
                                         List<SelectExprElementStreamCompiledSpec> aliasedStreams,
                                         boolean isUsingWildcard,
                                         StreamTypeService typeService)
        throws ExprValidationException
    {
        // Verify all column names are unique
        Set<String> names = new HashSet<String>();
        for (String element : insertIntoDesc.getColumnNames())
        {
            if (names.contains(element))
            {
                throw new ExprValidationException("Property name '" + element + "' appears more then once in insert-into clause");
            }
            names.add(element);
        }

        int numStreamColumnsJoin = 0;
        if (isUsingWildcard && typeService.getEventTypes().length > 1)
        {
            numStreamColumnsJoin = typeService.getEventTypes().length;
        }

        // Verify number of columns matches the select clause
        if ( (!insertIntoDesc.getColumnNames().isEmpty()) &&
             (insertIntoDesc.getColumnNames().size() != (selectionList.size() + aliasedStreams.size())) )
        {
            throw new ExprValidationException("Number of supplied values in the select clause does not match insert-into clause");
        }
    }
}
