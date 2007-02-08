using System;
namespace net.esper.pattern.observer
{
	
	/// <summary> Observers observe and indicate other external events such as timing events.</summary>
	public interface EventObserver
	{
		/// <summary> Start observing.</summary>
		void  StartObserve();
		
		/// <summary> Stop observing.</summary>
		void  StopObserve();
	}
}