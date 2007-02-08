using System;

namespace net.esper.support.bean
{
	
	public class SupportBean_S2
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
		virtual public String P20
		{
			get
			{
				return p20;
			}
			
			set
			{
				this.p20 = value;
			}
			
		}
		virtual public String P21
		{
			get
			{
				return p21;
			}
			
			set
			{
				this.p21 = value;
			}
			
		}
		virtual public String P22
		{
			get
			{
				return p22;
			}
			
			set
			{
				this.p22 = value;
			}
			
		}
		virtual public String P23
		{
			get
			{
				return p23;
			}
			
			set
			{
				this.p23 = value;
			}
			
		}
		private static int idCounter;
		
		private int id;
		private String p20;
		private String p21;
		private String p22;
		private String p23;
		
		public static Object[] makeS2(String propOne, String[] propTwo)
		{
			idCounter++;
			
			Object[] events = new Object[propTwo.Length];
			for (int i = 0; i < propTwo.Length; i++)
			{
				events[i] = new SupportBean_S2(idCounter, propOne, propTwo[i]);
			}
			return events;
		}
		
		public SupportBean_S2(int id)
		{
			this.id = id;
		}
		
		public SupportBean_S2(int id, String p20)
		{
			this.id = id;
			this.p20 = p20;
		}
		
		public SupportBean_S2(int id, String p20, String p21)
		{
			this.id = id;
			this.p20 = p20;
			this.p21 = p21;
		}
	}
}
