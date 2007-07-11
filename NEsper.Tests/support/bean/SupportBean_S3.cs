using System;

namespace net.esper.support.bean
{
	public class SupportBean_S3
	{
		virtual public int Id
		{
            get { return _id; }
            set { this._id = value; }
		}

		virtual public String P30
		{
            get { return _p30; }
            set { this._p30 = value; }
		}

		virtual public String P31
		{
            get { return _p31; }
            set { this._p31 = value; }
		}

		virtual public String P32
		{
            get { return _p32; }
            set { this._p32 = value; }
		}

		virtual public String P33
		{
            get { return _p33; }
            set { this._p33 = value; }
		}

		private static int idCounter;

        private int _id;
		private String _p30;
        private String _p31;
        private String _p32;
        private String _p33;
		
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
            this._id = id;
		}
		
		public SupportBean_S3(int id, String p30)
		{
            this._id = id;
            this._p30 = p30;
		}
		
		public SupportBean_S3(int id, String p30, String p31)
		{
            this._id = id;
            this._p30 = p30;
            this._p31 = p31;
		}
	}
}
