using System;
namespace net.esper.dispatch
{
	
	/// <summary> Implementations are executed when the DispatchService receives a dispatch invocation.</summary>
	public interface Dispatchable
	{
		/// <summary> Execute dispatch. </summary>
		void  execute();
	}
}