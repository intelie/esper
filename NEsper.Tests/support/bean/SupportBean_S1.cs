using System;

namespace net.esper.support.bean
{
	public class SupportBean_S1
	{
		virtual public int Id
		{
			get { return _id; }
            set { this._id = value; }
		}

		virtual public String P10
		{
            get { return _p10; }
            set { this._p10 = value; }
		}

		virtual public String P11
		{
            get { return _p11; }
            set { this._p11 = value; }
		}

		virtual public String P12
		{
            get { return _p12; }
            set { this._p12 = value; }
		}

		virtual public String P13
		{
            get { return _p13; }
            set { this._p13 = value; }
		}

		private static int idCounter;

        private int _id;
        private String _p10;
        private String _p11;
        private String _p12;
        private String _p13;
		
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
            this._id = id;
		}
		
		public SupportBean_S1(int id, String p10)
		{
            this._id = id;
            this._p10 = p10;
		}
		
		public SupportBean_S1(int id, String p10, String p11)
		{
            this._id = id;
            this._p10 = p10;
            this._p11 = p11;
		}
	}
}
