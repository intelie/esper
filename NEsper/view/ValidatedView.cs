using System;
using StreamTypeService = net.esper.eql.core.StreamTypeService;
using ExprValidationException = net.esper.eql.expression.ExprValidationException;
namespace net.esper.view
{
	
	/// <summary> Interface for views that require validation against stream event types.</summary>
	public interface ValidatedView
	{
		/// <summary> Validate the view.</summary>
		/// <param name="streamTypeService">supplies the types of streams against which to validate
		/// </param>
		/// <throws>  ExprValidationException is thrown to indicate an exception in validating the view </throws>
		void  Validate(StreamTypeService streamTypeService);
	}
}