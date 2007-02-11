using System;

namespace net.esper.support.bean
{
	
	public class ISupportAImplSuperGImpl:ISupportAImplSuperG
	{
		override public String g
		{
			get
			{
				return valueG;
			}
			
		}
		override public String a
		{
			get
			{
				return valueA;
			}
			
		}
		override public String baseAB
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
