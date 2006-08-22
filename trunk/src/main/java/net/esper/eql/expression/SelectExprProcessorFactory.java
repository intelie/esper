package net.esper.eql.expression;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;

/**
 * Factory for select expression processors.
 */
public class SelectExprProcessorFactory
{
    /**
     * Returns the processor to use for a given select-clause.
     * @param selectionList - the list of select clause elements/items, which are expected to have been validated
     * @param typeService - serves stream type information
     * @param insertIntoDesc - contains column names for the optional insert-into clause (if supplied)
     * @param eventAdapterService - for generating wrapper instances for events
     * @return select-clause expression processor
     * @throws ExprValidationException to indicate the select expression cannot be validated
     */
    public static SelectExprProcessor getProcessor(List<SelectExprElement> selectionList,
                                                   InsertIntoDesc insertIntoDesc,
                                                   StreamTypeService typeService,
                                                   EventAdapterService eventAdapterService)
        throws ExprValidationException
    {
        // Determin wildcard processor (select *)
        if (selectionList.size() == 0)
        {
            // Wildcard and insert-into not allowed as combination
            if (insertIntoDesc != null)
            {
                throw new ExprValidationException("Wildcard not allowed in combination with insert-into");
            }

            // For joins
            if (typeService.getStreamNames().length > 1)
            {
            	log.debug(".getProcessor Using SelectExprJoinWildcardProcessor");
                return new SelectExprJoinWildcardProcessor(typeService.getStreamNames(), typeService.getEventTypes(), eventAdapterService);
            }
            // Single-table selects don't need extra processing
            else
            {
            	log.debug(".getProcessor Using no select expr processor");
                return null;
            }
        }

        // Verify the name used is unique
        SelectExprElement.verifyNameUniqueness(selectionList);

        // Construct processor
    	log.debug(".getProcessor Using SelectExprEvalProcessor");
        return new SelectExprEvalProcessor(selectionList, insertIntoDesc, eventAdapterService);
    }

    private static final Log log = LogFactory.getLog(SelectExprProcessorFactory.class);
}
