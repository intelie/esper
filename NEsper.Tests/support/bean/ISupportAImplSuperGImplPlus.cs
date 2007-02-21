using System;

namespace net.esper.support.bean
{
	[Serializable]
	public class ISupportAImplSuperGImplPlus : ISupportAImplSuperG, ISupportB, ISupportC
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
		virtual public String b
		{
			get
			{
				return valueB;
			}
			
		}
		virtual public String c
		{
			get
			{
				return valueC;
			}
			
		}
		internal String valueG;
		internal String valueA;
		internal String valueBaseAB;
		internal String valueB;
		internal String valueC;
		
		public ISupportAImplSuperGImplPlus()
		{
		}
		
		public ISupportAImplSuperGImplPlus(String valueG, String valueA, String valueBaseAB, String valueB, String valueC)
		{
			this.valueG = valueG;
			this.valueA = valueA;
			this.valueBaseAB = valueBaseAB;
			this.valueB = valueB;
			this.valueC = valueC;
		}
	}
}
