using System;

namespace net.esper.support.bean
{
	
	public class SupportBeanWithEnum
	{
		virtual public String Str
		{
            get { return _stringValue; }
		}

		virtual public SupportEnum SupportEnum
		{
            get { return _supportEnum; }
		}

        virtual public String str
        {
            get { return _stringValue; }
        }

        virtual public SupportEnum supportEnum
        {
            get { return _supportEnum; }
        }

		private String _stringValue;
		private SupportEnum _supportEnum;
		
		public SupportBeanWithEnum(String stringValue, SupportEnum supportEnum)
		{
			this._stringValue = stringValue;
			this._supportEnum = supportEnum;
		}
	}
}
