using System;

using net.esper.eql.core;

namespace net.esper.eql.expression
{
	/// <summary>
    /// Validation interface for filter nodes.
    /// </summary>
	
    public interface ExprValidator
	{
        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated
        /// </returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>

        Type ReturnType
        {
            get;
        }

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="autoImportService">- for resolving class names in library method invocations</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>
		
        void Validate(StreamTypeService streamTypeService, AutoImportService autoImportService);
	}
}