using System;

namespace net.esper.support.bean
{
	
	[Serializable]
	public class ISupportBCImpl : ISupportB, ISupportC
	{
		virtual public String B
		{
			get
			{
				return valueB;
			}
			
		}
		virtual public String BaseAB
		{
			get
			{
				return valueBaseAB;
			}
			
		}
		virtual public String C
		{
			get
			{
				return valueC;
			}
			
		}
		private String valueB;
		private String valueBaseAB;
		private String valueC;
		
		public ISupportBCImpl(String valueB, String valueBaseAB, String valueC)
		{
			this.valueB = valueB;
			this.valueBaseAB = valueBaseAB;
			this.valueC = valueC;
		}
	}
}
