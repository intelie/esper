using System;

namespace net.esper.support.bean
{
	
	public class SupportBean_S6
	{
		virtual public int Id
		{
			get
			{
				return id;
			}
			
		}
		virtual public String P60
		{
			get
			{
				return p60;
			}
			
		}
		virtual public String P61
		{
			get
			{
				return p61;
			}
			
		}
		virtual public String P62
		{
			get
			{
				return p62;
			}
			
		}
		virtual public String P63
		{
			get
			{
				return p63;
			}
			
		}
		private static int idCounter;
		
		private int id;
		private String p60;
		private String p61;
		private String p62;
		private String p63;
		
		public static Object[] makeS6(String propOne, String[] propTwo)
		{
			idCounter++;
			
			Object[] events = new Object[propTwo.Length];
			for (int i = 0; i < propTwo.Length; i++)
			{
				events[i] = new SupportBean_S6(idCounter, propOne, propTwo[i]);
			}
			return events;
		}
		
		public SupportBean_S6(int id)
		{
			this.id = id;
		}
		
		public SupportBean_S6(int id, String p60)
		{
			this.id = id;
			this.p60 = p60;
		}
		
		public SupportBean_S6(int id, String p60, String p61)
		{
			this.id = id;
			this.p60 = p60;
			this.p61 = p61;
		}
	}
}
