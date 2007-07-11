using System;

namespace net.esper.support.bean
{
	[Serializable]
	public class ISupportAImplSuperGImplPlus : ISupportAImplSuperG, ISupportB, ISupportC
	{
		override public String G
		{
            get { return valueG; }
		}
		override public String A
		{
            get { return valueA; }
		}
		override public String BaseAB
		{
            get { return valueBaseAB; }
		}
		virtual public String B
		{
            get { return valueB; }
		}
		virtual public String C
		{
            get { return valueC; }
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
