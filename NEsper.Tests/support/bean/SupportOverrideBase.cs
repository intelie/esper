using System;

namespace net.esper.support.bean
{
	public class SupportOverrideBase
	{
		virtual public String Val
		{
			get { return _val; }
		}

        virtual public String val
        {
        	get { return this.Val; }
        }

		private String _val;
		
		public SupportOverrideBase(String val)
		{
			this._val = val;
		}
	}
}
