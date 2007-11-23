/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.core;

import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.InsertIntoDesc;
import net.esper.eql.spec.SelectExprElementCompiledSpec;
import net.esper.eql.spec.SelectExprElementStreamCompiledSpec;
import net.esper.event.EventAdapterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Factory for select expression processors.
 */
public class SelectExprProcessorFactory
{
    /**
     * Returns the processor to use for a given select-clause.
     * @param selectionList - the list of select clause elements/items, which are expected to have been validated
     * @param selectedStreams - list of stream selectors (e.g. select alias.* from Event as alias)
     * @param isUsingWildcard - true if the wildcard (*) occurs in the select clause
     * @param insertIntoDesc - contains column names for the optional insert-into clause (if supplied)
     * @param typeService - serves stream type information
     * @param eventAdapterService - for generating wrapper instances for events
     * @return select-clause expression processor
     * @throws net.esper.eql.expression.ExprValidationException to indicate the select expression cannot be validated
     */
    public static SelectExprProcessor getProcessor(List<SelectExprElementCompiledSpec> selectionList,
                                                   List<SelectExprElementStreamCompiledSpec> selectedStreams,
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
        if ((selectionList.isEmpty() && (selectedStreams.isEmpty())))
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

        // Verify the assigned or alias name used is unique
        verifyNameUniqueness(selectionList, selectedStreams);

        // Construct processor
        log.debug(".getProcessor Using SelectExprEvalProcessor");
        if (selectedStreams.size() == 0)
        {
            // This one only deals with wildcards and expressions in the selection
            return new SelectExprEvalProcessor(selectionList, insertIntoDesc, isUsingWildcard, typeService, eventAdapterService);
        }
        else
        {
            // This one also deals with stream selectors (e.g. select *, p1, s0.* from S0 as s0)
            return new SelectExprEvalProcessorStreams(selectionList, selectedStreams, insertIntoDesc, isUsingWildcard, typeService, eventAdapterService);
        }
    }

    /**
     * Verify that each given name occurs exactly one.
     * @param selectionList is the list of select items to verify names
     * @param selectedStreams - list of stream selectors (e.g. select alias.* from Event as alias)
     * @throws net.esper.eql.expression.ExprValidationException thrown if a name occured more then once
     */
    protected static void verifyNameUniqueness(List<SelectExprElementCompiledSpec> selectionList, List<SelectExprElementStreamCompiledSpec> selectedStreams) throws ExprValidationException
    {
        Set<String> names = new HashSet<String>();
        for (SelectExprElementCompiledSpec element : selectionList)
        {
            if (names.contains(element.getAssignedName()))
            {
                throw new ExprValidationException("Property alias name '" + element.getAssignedName() + "' appears more then once in select clause");
            }
            names.add(element.getAssignedName());
        }
        for (SelectExprElementStreamCompiledSpec element : selectedStreams)
        {
            if (element.getOptionalAliasName() == null)
            {
                continue; // ignore no-alias stream selectors
            }
            if (names.contains(element.getOptionalAliasName()))
            {
                throw new ExprValidationException("Property alias name '" + element.getOptionalAliasName() + "' appears more then once in select clause");
            }
            names.add(element.getOptionalAliasName());
        }
    }


    private static final Log log = LogFactory.getLog(SelectExprProcessorFactory.class);
}
