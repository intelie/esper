using System;

namespace net.esper.support.bean
{
	public class SupportBeanString
	{
		virtual public String Str
		{
			get { return stringValue; }
			set { this.stringValue = value; }
		}

        virtual public String str
        {
            get { return stringValue; }
            set { this.stringValue = value; }
        }

		private String stringValue;
		
		public SupportBeanString(String stringValue)
		{
			this.stringValue = stringValue;
		}
		
		public override String ToString()
		{
			return "SupportBeanString string=" + stringValue;
		}
	}
}
