package net.esper.eql.core;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.event.EventAdapterService;
import net.esper.eql.spec.SelectExprElementUnnamedSpec;
import net.esper.eql.spec.InsertIntoDesc;
import net.esper.eql.spec.SelectExprElementNamedSpec;
import net.esper.eql.core.SelectExprEvalProcessor;
import net.esper.eql.core.SelectExprJoinWildcardProcessor;
import net.esper.eql.core.SelectExprProcessor;
import net.esper.eql.expression.ExprValidationException;

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
     * @throws net.esper.eql.expression.ExprValidationException to indicate the select expression cannot be validated
     */
    public static SelectExprProcessor getProcessor(List<SelectExprElementNamedSpec> selectionList,
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
        verifyNameUniqueness(selectionList);

        // Construct processor
        log.debug(".getProcessor Using SelectExprEvalProcessor");
        return new SelectExprEvalProcessor(selectionList, insertIntoDesc, eventAdapterService);
    }

    /**
     * Verify that each given name occurs exactly one.
     * @param selectionList is the list of select items to verify names
     * @throws net.esper.eql.expression.ExprValidationException thrown if a name occured more then once
     */
    protected static void verifyNameUniqueness(List<SelectExprElementNamedSpec> selectionList) throws ExprValidationException
    {
        Set<String> names = new HashSet<String>();
        for (SelectExprElementNamedSpec element : selectionList)
        {
            if (names.contains(element.getAssignedName()))
            {
                throw new ExprValidationException("Property alias name '" + element.getAssignedName() + "' appears more then once in select clause");
            }
            names.add(element.getAssignedName());
        }
    }


    private static final Log log = LogFactory.getLog(SelectExprProcessorFactory.class);
}
