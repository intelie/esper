using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.eql.spec;
using net.esper.eql.expression;

using org.apache.commons.logging;

namespace net.esper.eql.core
{
	/// <summary>
	/// Factory for select expression processors.
	/// </summary>

	public class SelectExprProcessorFactory
	{
		/// <summary>
		/// Gets the processor.
		/// </summary>
		/// <param name="selectionList">the list of select clause elements/items, which are expected to have been validated.</param>
		/// <param name="isUsingWildcard">true if the wildcard (*) occurs in the select clause</param>
		/// <param name="insertIntoDesc">contains column names for the optional insert-into clause (if supplied).</param>
		/// <param name="typeService">serves stream type information.</param>
		/// <param name="eventAdapterService">for generating wrapper instances for events.</param>
		/// <returns>Returns the processor to use for a given select-clause.</returns>

		public static SelectExprProcessor GetProcessor(
			IList<SelectExprElementCompiledSpec> selectionList,
			bool isUsingWildcard,
			InsertIntoDesc insertIntoDesc,
			StreamTypeService typeService,
			EventAdapterService eventAdapterService)
		{
	    	// Wildcard not allowed when insert into specifies column order
	    	if(isUsingWildcard && insertIntoDesc != null && (insertIntoDesc.ColumnNames.Count != 0))
	    	{
	    		throw new ExprValidationException("Wildcard not allowed when insert-into specifies column order");
	    	}
	    	
	        // Determine wildcard processor (select *)
	        if (selectionList.Count == 0)
	        {
	            // For joins
	            if (typeService.StreamNames.Length > 1)
	            {
	                log.Debug(".getProcessor Using SelectExprJoinWildcardProcessor");
	                return new SelectExprJoinWildcardProcessor(typeService.StreamNames, typeService.EventTypes, eventAdapterService, insertIntoDesc);
	            }
	            // Single-table selects with no insert-into 
	            // don't need extra processing
	            else if (insertIntoDesc == null)
	            {
	            	log.Debug(".getProcessor Using no select expr processor");
	                return null;
	            }
	        }

	        // Verify the name used is unique
	        VerifyNameUniqueness(selectionList);

	        // Construct processor
	        log.Debug(".getProcessor Using SelectExprEvalProcessor");
	        return new SelectExprEvalProcessor(selectionList, insertIntoDesc, isUsingWildcard, typeService, eventAdapterService);
		}

		/// <summary>
		/// Verify that each given name occurs exactly one.
		/// </summary>
		/// <param name="selectionList">The list of select items to verify names</param>

		public static void VerifyNameUniqueness( IList<SelectExprElementCompiledSpec> selectionList )
		{
			Set<String> names = new EHashSet<String>();
			foreach ( SelectExprElementCompiledSpec element in selectionList )
			{
				if ( names.Contains( element.AssignedName ) )
				{
					throw new ExprValidationException( "Property alias name '" + element.AssignedName + "' appears more then once in select clause" );
				}
				names.Add( element.AssignedName );
			}
		}

		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
