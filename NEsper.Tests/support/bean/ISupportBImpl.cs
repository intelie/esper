using System;

namespace net.esper.support.bean
{
	
	public class ISupportBImpl : ISupportB
	{
		virtual public String b
		{
			get
			{
				return valueB;
			}
			
		}
		virtual public String baseAB
		{
			get
			{
				return valueBaseAB;
			}
			
		}
		private String valueB;
		private String valueBaseAB;
		
		public ISupportBImpl(String valueB, String valueBaseAB)
		{
			this.valueB = valueB;
			this.valueBaseAB = valueBaseAB;
		}
	}
}
