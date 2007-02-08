using System;

namespace net.esper.support.bean
{
	
	[Serializable]
	public class ISupportABCImpl : ISupportA, ISupportB, ISupportC
	{
		virtual public String A
		{
			get
			{
				return valueA;
			}
			
		}
		virtual public String BaseAB
		{
			get
			{
				return valueBaseAB;
			}
			
		}
		virtual public String B
		{
			get
			{
				return valueB;
			}
			
		}
		virtual public String C
		{
			get
			{
				return valueC;
			}
			
		}
		private String valueA;
		private String valueB;
		private String valueBaseAB;
		private String valueC;
		
		public ISupportABCImpl(String valueA, String valueB, String valueBaseAB, String valueC)
		{
			this.valueA = valueA;
			this.valueB = valueB;
			this.valueBaseAB = valueBaseAB;
			this.valueC = valueC;
		}
	}
}
