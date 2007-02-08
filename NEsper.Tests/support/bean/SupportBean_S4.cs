using System;

namespace net.esper.support.bean
{
	
	public class SupportBean_S4
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
		virtual public String P40
		{
			get
			{
				return p40;
			}
			
			set
			{
				this.p40 = value;
			}
			
		}
		virtual public String P41
		{
			get
			{
				return p41;
			}
			
			set
			{
				this.p41 = value;
			}
			
		}
		virtual public String P42
		{
			get
			{
				return p42;
			}
			
			set
			{
				this.p42 = value;
			}
			
		}
		virtual public String P43
		{
			get
			{
				return p43;
			}
			
			set
			{
				this.p43 = value;
			}
			
		}
		private static int idCounter;
		
		private int id;
		private String p40;
		private String p41;
		private String p42;
		private String p43;
		
		public static Object[] makeS4(String propOne, String[] propTwo)
		{
			idCounter++;
			
			Object[] events = new Object[propTwo.Length];
			for (int i = 0; i < propTwo.Length; i++)
			{
				events[i] = new SupportBean_S4(idCounter, propOne, propTwo[i]);
			}
			return events;
		}
		
		public SupportBean_S4(int id)
		{
			this.id = id;
		}
		
		public SupportBean_S4(int id, String p40)
		{
			this.id = id;
			this.p40 = p40;
		}
		
		public SupportBean_S4(int id, String p40, String p41)
		{
			this.id = id;
			this.p40 = p40;
			this.p41 = p41;
		}
	}
}
