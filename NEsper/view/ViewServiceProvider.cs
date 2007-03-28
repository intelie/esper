using System;
namespace net.esper.view
{
	/// <summary>
    /// Static factory for implementations of the <seealso cref="net.esper.view.ViewService"/> interface.
    /// </summary>

    public sealed class ViewServiceProvider
	{
		/// <summary> Creates an implementation of the ViewService interface.</summary>
		/// <returns> implementation
		/// </returns>
		public static ViewService newService()
		{
			return new ViewServiceImpl();
		}
	}
}