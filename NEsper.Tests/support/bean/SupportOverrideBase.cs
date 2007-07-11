using System;

namespace net.esper.support.bean
{
	public class SupportOverrideBase
	{
		virtual public String Val
		{
			get { return _val; }
		}

        private String _val;
		
		public SupportOverrideBase(String val)
		{
			this._val = val;
		}
	}
}
