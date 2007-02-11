using System;

namespace net.esper.support.bean
{
	[Serializable]
	public class ISupportABCImpl : ISupportA, ISupportB, ISupportC
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
