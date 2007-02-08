using System;

namespace net.esper.support.bean
{
	
	public class SupportBean_S3
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
		virtual public String P30
		{
			get
			{
				return p30;
			}
			
			set
			{
				this.p30 = value;
			}
			
		}
		virtual public String P31
		{
			get
			{
				return p31;
			}
			
			set
			{
				this.p31 = value;
			}
			
		}
		virtual public String P32
		{
			get
			{
				return p32;
			}
			
			set
			{
				this.p32 = value;
			}
			
		}
		virtual public String P33
		{
			get
			{
				return p33;
			}
			
			set
			{
				this.p33 = value;
			}
			
		}
		private static int idCounter;
		
		private int id;
		private String p30;
		private String p31;
		private String p32;
		private String p33;
		
		public static Object[] makeS3(String propOne, String[] propTwo)
		{
			idCounter++;
			
			Object[] events = new Object[propTwo.Length];
			for (int i = 0; i < propTwo.Length; i++)
			{
				events[i] = new SupportBean_S3(idCounter, propOne, propTwo[i]);
			}
			return events;
		}
		
		public SupportBean_S3(int id)
		{
			this.id = id;
		}
		
		public SupportBean_S3(int id, String p30)
		{
			this.id = id;
			this.p30 = p30;
		}
		
		public SupportBean_S3(int id, String p30, String p31)
		{
			this.id = id;
			this.p30 = p30;
			this.p31 = p31;
		}
	}
}
