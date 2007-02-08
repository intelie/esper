using System;

namespace net.esper.support.bean
{
	
	public class SupportBeanSimple
	{
		virtual public String MyString
		{
			get
			{
				return myString;
			}
			
			set
			{
				this.myString = value;
			}
			
		}
		virtual public int MyInt
		{
			get
			{
				return myInt;
			}
			
			set
			{
				this.myInt = value;
			}
			
		}
		private String myString;
		private int myInt;
		
		public SupportBeanSimple(String myString, int myInt)
		{
			this.myString = myString;
			this.myInt = myInt;
		}
	}
}
