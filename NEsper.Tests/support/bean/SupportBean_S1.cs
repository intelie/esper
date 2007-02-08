using System;

namespace net.esper.support.bean
{
	
	public class SupportBean_S1
	{
		virtual public int Id
		{
			get
			{
				return id;
			}
			
			set
			{
				this.id = value;
			}
			
		}
		virtual public String P10
		{
			get
			{
				return p10;
			}
			
			set
			{
				this.p10 = value;
			}
			
		}
		virtual public String P11
		{
			get
			{
				return p11;
			}
			
			set
			{
				this.p11 = value;
			}
			
		}
		virtual public String P12
		{
			get
			{
				return p12;
			}
			
			set
			{
				this.p12 = value;
			}
			
		}
		virtual public String P13
		{
			get
			{
				return p13;
			}
			
			set
			{
				this.p13 = value;
			}
			
		}
		private static int idCounter;
		
		private int id;
		private String p10;
		private String p11;
		private String p12;
		private String p13;
		
		public static Object[] makeS1(String propOne, String[] propTwo)
		{
			idCounter++;
			
			Object[] events = new Object[propTwo.Length];
			for (int i = 0; i < propTwo.Length; i++)
			{
				events[i] = new SupportBean_S1(idCounter, propOne, propTwo[i]);
			}
			return events;
		}
		
		
		public SupportBean_S1(int id)
		{
			this.id = id;
		}
		
		public SupportBean_S1(int id, String p10)
		{
			this.id = id;
			this.p10 = p10;
		}
		
		public SupportBean_S1(int id, String p10, String p11)
		{
			this.id = id;
			this.p10 = p10;
			this.p11 = p11;
		}
	}
}
