using System;

namespace net.esper.support.bean
{
	
	public class SupportBean_S5
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
		virtual public String P50
		{
			get
			{
				return p50;
			}
			
			set
			{
				this.p50 = value;
			}
			
		}
		virtual public String P51
		{
			get
			{
				return p51;
			}
			
		}
		virtual public String P52
		{
			get
			{
				return p52;
			}
			
		}
		virtual public String P53
		{
			get
			{
				return p53;
			}
			
		}
		private static int idCounter;
		
		private int id;
		private String p50;
		private String p51;
		private String p52;
		private String p53;
		
		public static Object[] makeS5(String propOne, String[] propTwo)
		{
			idCounter++;
			
			Object[] events = new Object[propTwo.Length];
			for (int i = 0; i < propTwo.Length; i++)
			{
				events[i] = new SupportBean_S5(idCounter, propOne, propTwo[i]);
			}
			return events;
		}
		
		public SupportBean_S5(int id)
		{
			this.id = id;
		}
		
		public SupportBean_S5(int id, String p50)
		{
			this.id = id;
			this.p50 = p50;
		}
		
		public SupportBean_S5(int id, String p50, String p51)
		{
			this.id = id;
			this.p50 = p50;
			this.p51 = p51;
		}
	}
}
