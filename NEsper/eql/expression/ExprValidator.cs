using System;

using net.esper.eql.core;

namespace net.esper.eql.expression
{
	/// <summary>
    /// Validation interface for expression nodes.
    /// </summary>

    public interface ExprValidator
	{
        /// <summary>
        /// Returns the type that the node's evaluate method returns an instance of.
        /// </summary>
        /// <value>The type.</value>
        /// <returns> type returned when evaluated</returns>
        /// <throws>ExprValidationException thrown when validation failed </throws>

        Type ReturnType { get ; }

        /// <summary>
        /// Validate node.
        /// </summary>
        /// <param name="streamTypeService">serves stream event type info</param>
        /// <param name="methodResolutionService">The method resolution service.</param>
        /// <param name="viewResourceDelegate">The view resource delegate.</param>
        /// <throws>ExprValidationException thrown when validation failed </throws>

        void Validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate);
	}
}
