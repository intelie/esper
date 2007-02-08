using System;

namespace net.esper.support.bean
{
	
	public class ISupportAImplSuperGImpl:ISupportAImplSuperG
	{
		override public String G
		{
			get
			{
				return valueG;
			}
			
		}
		override public String A
		{
			get
			{
				return valueA;
			}
			
		}
		override public String BaseAB
		{
			get
			{
				return valueBaseAB;
			}
			
		}
		private String valueG;
		private String valueA;
		private String valueBaseAB;
		
		public ISupportAImplSuperGImpl(String valueG, String valueA, String valueBaseAB)
		{
			this.valueG = valueG;
			this.valueA = valueA;
			this.valueBaseAB = valueBaseAB;
		}
	}
}
