using System;

namespace net.esper.support.bean
{
	
	public class ISupportDImpl : ISupportD
	{
		virtual public String d
		{
			get
			{
				return valueD;
			}
			
		}
		virtual public String baseD
		{
			get
			{
				return valueBaseD;
			}
			
		}
		virtual public String baseDBase
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
