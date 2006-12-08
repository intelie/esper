package net.esper.eql.core;

import net.esper.event.*;
import net.esper.eql.spec.InsertIntoDesc;
import net.esper.eql.spec.SelectExprElementNamedSpec;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;

import java.util.*;

/**
 * Processor for select-clause expressions that handles a list of selection items represented by
 * expression nodes. Computes results based on matching events.
 */
public class SelectExprEvalProcessor implements SelectExprProcessor
{
    private ExprNode[] expressionNodes;
    private String[] columnNames;
    private EventType resultEventType;
    private final EventAdapterService eventAdapterService;

    /**
     * Ctor.
     * @param selectionList - list of select-clause items
     * @param eventAdapterService - service for generating events and handling event types
     * @param insertIntoDesc - descriptor for insert-into clause contains column names overriding select clause names
     * @throws net.esper.eql.expression.ExprValidationException thrown if any of the expressions don't validate
     */
    public SelectExprEvalProcessor(List<SelectExprElementNamedSpec> selectionList,
                                   InsertIntoDesc insertIntoDesc,
                                   EventAdapterService eventAdapterService) throws ExprValidationException
    {
        this.eventAdapterService = eventAdapterService;

        if (selectionList.size() == 0)
        {
            throw new IllegalArgumentException("Empty selection list not supported");
        }

        for (SelectExprElementNamedSpec entry : selectionList)
        {
            if (entry.getAssignedName() == null)
            {
                throw new IllegalArgumentException("Expected name for each expression has not been supplied");
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

    private void init(List<SelectExprElementNamedSpec> selectionList,
                      InsertIntoDesc insertIntoDesc,
                      EventAdapterService eventAdapterService)
        throws ExprValidationException
    {
        // Get expression nodes
        expressionNodes = new ExprNode[selectionList.size()];
        for (int i = 0; i < selectionList.size(); i++)
        {
            expressionNodes[i] = selectionList.get(i).getSelectExpression();
        }

        // Get column names
        if ((insertIntoDesc != null) && (insertIntoDesc.getColumnNames().size() > 0))
        {
            columnNames = insertIntoDesc.getColumnNames().toArray(new String[0]);
        }
        else
        {
            columnNames = new String[selectionList.size()];
            for (int i = 0; i < selectionList.size(); i++)
            {
                columnNames[i] = selectionList.get(i).getAssignedName();
            }
        }

        // Build event type
        Map<String, Class> selPropertyTypes = new HashMap<String, Class>();
        for (int i = 0; i < expressionNodes.length; i++)
        {
            Class expressionReturnType = expressionNodes[i].getType();
            selPropertyTypes.put(columnNames[i], expressionReturnType);
        }

        // If we have an alias for this type, add it
        if (insertIntoDesc != null)
        {
            try
            {
                resultEventType = eventAdapterService.addMapType(insertIntoDesc.getEventTypeAlias(), selPropertyTypes);
            }
            catch (EventAdapterException ex)
            {
                throw new ExprValidationException(ex.getMessage());
            }
        }
        else
        {
            resultEventType = eventAdapterService.createAnonymousMapType(selPropertyTypes);
        }
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData)
    {
        Map<String, Object> props = new HashMap<String, Object>();
        for (int i = 0; i < expressionNodes.length; i++)
        {
            Object evalResult = expressionNodes[i].evaluate(eventsPerStream, isNewData);
            props.put(columnNames[i], evalResult);
        }

        EventBean event = eventAdapterService.createMapFromValues(props, resultEventType);
        return event;
    }

    public EventType getResultEventType()
    {
        return resultEventType;
    }

    private static void verifyInsertInto(InsertIntoDesc insertIntoDesc,
                                         List<SelectExprElementNamedSpec> selectionList)
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
        if ( (insertIntoDesc.getColumnNames().size() > 0) &&
             (insertIntoDesc.getColumnNames().size() != selectionList.size()) )
        {
            throw new ExprValidationException("Number of supplied values in the select clause does not match insert-into clause");
        }
    }
}
