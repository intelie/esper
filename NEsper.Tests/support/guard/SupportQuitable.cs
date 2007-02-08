using System;

using net.esper.pattern.guard;

namespace net.esper.support.guard
{
	
	public class SupportQuitable : Quitable
	{
		virtual public int AndResetQuitCounter
		{
			get
			{
				return quitCounter;
			}
			
		}
		public int quitCounter = 0;
		
		public virtual void  guardQuit()
		{
			quitCounter++;
		}
	}
}
