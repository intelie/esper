using System;

namespace net.esper.support.bean
{
	
	public class ISupportBaseABImpl : ISupportBaseAB
	{
		virtual public String BaseAB
		{
			get
			{
				return valueBaseAB;
			}
			
		}
		private String valueBaseAB;
		
		public ISupportBaseABImpl(String valueBaseAB)
		{
			this.valueBaseAB = valueBaseAB;
		}
	}
}
