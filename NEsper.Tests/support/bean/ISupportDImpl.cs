using System;

namespace net.esper.support.bean
{
	
	public class ISupportDImpl : ISupportD
	{
		virtual public String D
		{
			get
			{
				return valueD;
			}
			
		}
		virtual public String BaseD
		{
			get
			{
				return valueBaseD;
			}
			
		}
		virtual public String BaseDBase
		{
			get
			{
				return valueBaseDBase;
			}
			
		}
		private String valueD;
		private String valueBaseD;
		private String valueBaseDBase;
		
		public ISupportDImpl(String valueD, String valueBaseD, String valueBaseDBase)
		{
			this.valueD = valueD;
			this.valueBaseD = valueBaseD;
			this.valueBaseDBase = valueBaseDBase;
		}
	}
}
