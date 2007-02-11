using System;

namespace net.esper.support.bean
{
	
	public class ISupportAImpl : ISupportA
	{
		virtual public String a
		{
			get
			{
				return valueA;
			}
			
		}
		virtual public String baseAB
		{
			get
			{
				return valueBaseAB;
			}
			
		}
		private String valueA;
		private String valueBaseAB;
		
		public ISupportAImpl(String valueA, String valueBaseAB)
		{
			this.valueA = valueA;
			this.valueBaseAB = valueBaseAB;
		}
	}
}
