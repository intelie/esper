package net.esper.eql.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.InsertIntoDesc;
import net.esper.eql.spec.SelectExprElementNamedSpec;
import net.esper.event.EventAdapterService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for select expression processors.
 */
public class SelectExprProcessorFactory
{
    /**
     * Returns the processor to use for a given select-clause.
     * @param selectionList - the list of select clause elements/items, which are expected to have been validated
     * @param isUsingWildcard - true if the wildcard (*) occurs in the select clause
     * @param insertIntoDesc - contains column names for the optional insert-into clause (if supplied)
     * @param typeService - serves stream type information
     * @param eventAdapterService - for generating wrapper instances for events
     * @return select-clause expression processor
     * @throws net.esper.eql.expression.ExprValidationException to indicate the select expression cannot be validated
     */
    public static SelectExprProcessor getProcessor(List<SelectExprElementNamedSpec> selectionList,
                                                   boolean isUsingWildcard,
                                                   InsertIntoDesc insertIntoDesc,
                                                   StreamTypeService typeService, 
                                                   EventAdapterService eventAdapterService)
        throws ExprValidationException
    {
    	// Wildcard not allowed when insert into specifies column order
    	if(isUsingWildcard && insertIntoDesc != null && !insertIntoDesc.getColumnNames().isEmpty())
    	{
    		throw new ExprValidationException("Wildcard not allowed when insert-into specifies column order");
    	}
    	
        // Determine wildcard processor (select *)
        if (selectionList.size() == 0)
        {
            // For joins
            if (typeService.getStreamNames().length > 1)
            {
                log.debug(".getProcessor Using SelectExprJoinWildcardProcessor");
                return new SelectExprJoinWildcardProcessor(typeService.getStreamNames(), typeService.getEventTypes(), eventAdapterService, insertIntoDesc);
            }
            // Single-table selects with no insert-into 
            // don't need extra processing
            else if (insertIntoDesc == null)
            {
            	log.debug(".getProcessor Using no select expr processor");
                return null;
            }
        }

        // Verify the name used is unique
        verifyNameUniqueness(selectionList);

        // Construct processor
        log.debug(".getProcessor Using SelectExprEvalProcessor");
        return new SelectExprEvalProcessor(selectionList, insertIntoDesc, isUsingWildcard, typeService, eventAdapterService);
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
