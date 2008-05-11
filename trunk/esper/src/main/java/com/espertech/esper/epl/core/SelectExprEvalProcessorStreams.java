package com.espertech.esper.epl.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.spec.SelectClauseExprCompiledSpec;
import com.espertech.esper.epl.spec.InsertIntoDesc;
import com.espertech.esper.epl.spec.SelectClauseStreamCompiledSpec;
import com.espertech.esper.event.*;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.collection.Pair;

import java.util.*;

/**
 * Processor for select-clause expressions that handles a list of selection items represented by
 * expression nodes. Computes results based on matching events.
 */
public class SelectExprEvalProcessorStreams implements SelectExprProcessor
{
	private static final Log log = LogFactory.getLog(SelectExprEvalProcessorStreams.class);

    private final EventAdapterService eventAdapterService;
    private final List<SelectClauseStreamCompiledSpec> aliasedStreams;
    private final List<SelectClauseStreamCompiledSpec> unaliasedStreams;
    private boolean singleStreamWrapper;
    private boolean isUsingWildcard;

    private ExprNode[] expressionNodes;
    private String[] columnNames;
    private EventType resultEventType;
    private EventType underlyingEventType;
    private int underlyingStreamNumber;
    private boolean underlyingIsTaggedEvent;
    private EventPropertyGetter underlyingPropertyEventGetter;

    /**
     * Ctor.
     * @param selectionList - list of select-clause items
     * @param insertIntoDesc - descriptor for insert-into clause contains column names overriding select clause names
     * @param isUsingWildcard - true if the wildcard (*) appears in the select clause
     * @param typeService -service for information about streams
     * @param eventAdapterService - service for generating events and handling event types
     * @param selectedStreams - list of stream selectors (e.g. select alias.* from Event as alias)
     * @throws com.espertech.esper.epl.expression.ExprValidationException thrown if any of the expressions don't validate
     */
    public SelectExprEvalProcessorStreams(List<SelectClauseExprCompiledSpec> selectionList,
                                   List<SelectClauseStreamCompiledSpec> selectedStreams,
                                   InsertIntoDesc insertIntoDesc,
                                   boolean isUsingWildcard,
                                   StreamTypeService typeService,
                                   EventAdapterService eventAdapterService) throws ExprValidationException
    {
        this.eventAdapterService = eventAdapterService;
        this.isUsingWildcard = isUsingWildcard;

        // Get the un-aliased stream selectors (i.e. select s0.* from S0 as s0)
        unaliasedStreams = new ArrayList<SelectClauseStreamCompiledSpec>();
        aliasedStreams = new ArrayList<SelectClauseStreamCompiledSpec>();
        for (SelectClauseStreamCompiledSpec spec : selectedStreams)
        {
            if (spec.getOptionalAliasName() == null)
            {
                unaliasedStreams.add(spec);
            }
            else
            {
                aliasedStreams.add(spec);
                if (spec.isProperty())
                {
                    throw new ExprValidationException("The property wildcard syntax must be used without alias");
                }
            }
        }

        // Verify insert into clause
        if (insertIntoDesc != null)
        {
            verifyInsertInto(insertIntoDesc, selectionList, aliasedStreams);
        }

        // Error if there are more then one un-aliased streams (i.e. select s0.*, s1.* from S0 as s0, S1 as s1)
        // Thus there is only 1 unaliased stream selector maximum.
        if (unaliasedStreams.size() > 1)
        {
            throw new ExprValidationException("A column alias must be supplied for all but one stream if multiple streams are selected via the stream.* notation");
        }

        // Resolve underlying event type in the case of wildcard or non-aliased stream select.
        // Determine if the we are considering a tagged event or a stream name.
        if((isUsingWildcard) || (!unaliasedStreams.isEmpty()))
        {
            if (!unaliasedStreams.isEmpty())
            {
                // the tag.* syntax for :  select tag.* from pattern [tag = A]
                underlyingStreamNumber = unaliasedStreams.get(0).getStreamNumber();
                if (unaliasedStreams.get(0).isTaggedEvent())
                {
                    TaggedCompositeEventType comp = (TaggedCompositeEventType) typeService.getEventTypes()[underlyingStreamNumber];
                    Pair<EventType, String> pair = comp.getTaggedEventTypes().get(unaliasedStreams.get(0).getStreamAliasName());
                    if (pair != null)
                    {
                        underlyingEventType = pair.getFirst();
                    }
                    underlyingIsTaggedEvent = true;
                }
                // the property.* syntax for :  select property.* from A
                else if (unaliasedStreams.get(0).isProperty())
                {
                    String propertyName = unaliasedStreams.get(0).getStreamAliasName();
                    Class propertyType = unaliasedStreams.get(0).getPropertyType();
                    int streamNumber = unaliasedStreams.get(0).getStreamNumber();

                    if (JavaClassHelper.isJavaBuiltinDataType(unaliasedStreams.get(0).getPropertyType()))
                    {
                        throw new ExprValidationException("The property wildcard syntax cannot be used on built-in types as returned by property '" + propertyName + "'");
                    }

                    // create or get an underlying type for that Class
                    underlyingEventType = eventAdapterService.addBeanType(propertyType.getName(), propertyType);
                    underlyingPropertyEventGetter = typeService.getEventTypes()[streamNumber].getGetter(propertyName);
                    if (underlyingPropertyEventGetter == null)
                    {
                        throw new ExprValidationException("Unexpected error resolving property getter for property " + propertyName);
                    }
                }
                // the stream.* syntax for:  select a.* from A as a
                else
                {
                    underlyingEventType = typeService.getEventTypes()[underlyingStreamNumber];
                }
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

    private void init(List<SelectClauseExprCompiledSpec> selectionList,
                      List<SelectClauseStreamCompiledSpec> aliasedStreams,
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
            for (SelectClauseExprCompiledSpec aSelectionList : selectionList)
            {
                columnNames[count] = aSelectionList.getAssignedName();
                count++;
            }
            for (SelectClauseStreamCompiledSpec aSelectionList : aliasedStreams)
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
        Map<String, Object> selPropertyTypes = new HashMap<String, Object>();
        int count = 0;
        for (ExprNode expressionNode : expressionNodes)
        {
            Class expressionReturnType = expressionNode.getType();
            selPropertyTypes.put(columnNames[count], expressionReturnType);
            count++;
        }
        for (SelectClauseStreamCompiledSpec element : aliasedStreams)
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
                    resultEventType = eventAdapterService.addNestableMapType(insertIntoDesc.getEventTypeAlias(), selPropertyTypes);
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

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize)
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
        for (SelectClauseStreamCompiledSpec element : aliasedStreams)
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

            EventBean event = null;
            if (underlyingIsTaggedEvent)
            {
                TaggedCompositeEventBean eventBean = (TaggedCompositeEventBean) eventsPerStream[underlyingStreamNumber];
                event = eventBean.getEventBean(unaliasedStreams.get(0).getStreamAliasName());
            }
            else if (underlyingPropertyEventGetter != null)
            {
                Object value = underlyingPropertyEventGetter.get(eventsPerStream[underlyingStreamNumber]);
                if (value != null)
                {
                    event = eventAdapterService.adapterForBean(value);
                }
            }
            else
            {
                event = eventsPerStream[underlyingStreamNumber];
            }

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
                                         List<SelectClauseExprCompiledSpec> selectionList,
                                         List<SelectClauseStreamCompiledSpec> aliasedStreams)
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

        // Verify number of columns matches the select clause
        if ( (!insertIntoDesc.getColumnNames().isEmpty()) &&
             (insertIntoDesc.getColumnNames().size() != (selectionList.size() + aliasedStreams.size())) )
        {
            throw new ExprValidationException("Number of supplied values in the select clause does not match insert-into clause");
        }
    }
}
