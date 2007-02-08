using System;

namespace net.esper.support.bean
{
	
	public class SupportBean_S0
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
		virtual public String P00
		{
			get
			{
				return p00;
			}
			
			set
			{
				this.p00 = value;
			}
			
		}
		virtual public String P01
		{
			get
			{
				return p01;
			}
			
			set
			{
				this.p01 = value;
			}
			
		}
		virtual public String P02
		{
			get
			{
				return p02;
			}
			
			set
			{
				this.p02 = value;
			}
			
		}
		virtual public String P03
		{
			get
			{
				return p03;
			}
			
			set
			{
				this.p03 = value;
			}
			
		}
		private static int idCounter;
		
		private int id;
		private String p00;
		private String p01;
		private String p02;
		private String p03;
		
		public static Object[] makeS0(String propOne, String[] propTwo)
		{
			idCounter++;
			Object[] events = new Object[propTwo.Length];
			for (int i = 0; i < propTwo.Length; i++)
			{
				events[i] = new SupportBean_S0(idCounter, propOne, propTwo[i]);
			}
			return events;
		}
		
		public SupportBean_S0(int id)
		{
			this.id = id;
		}
		
		public SupportBean_S0(int id, String p00)
		{
			this.id = id;
			this.p00 = p00;
		}
		
		public SupportBean_S0(int id, String p00, String p01)
		{
			this.id = id;
			this.p00 = p00;
			this.p01 = p01;
		}
		
		public SupportBean_S0(int id, String p00, String p01, String p02)
		{
			this.id = id;
			this.p00 = p00;
			this.p01 = p01;
			this.p02 = p02;
		}
		
		public SupportBean_S0(int id, String p00, String p01, String p02, String p03)
		{
			this.id = id;
			this.p00 = p00;
			this.p01 = p01;
			this.p02 = p02;
			this.p03 = p03;
		}
	}
}
