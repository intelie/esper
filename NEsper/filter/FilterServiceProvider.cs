using System;
namespace net.esper.filter
{
	
	/// <summary> Static factory for implementations of the <seealso cref="FilterService"/> interface.</summary>
	public sealed class FilterServiceProvider
	{
		/// <summary> Creates an implementation of the FilterEvaluationService interface.</summary>
		/// <returns> implementation
		/// </returns>
		public static FilterService newService()
		{
			return new FilterServiceImpl();
		}
	}
}