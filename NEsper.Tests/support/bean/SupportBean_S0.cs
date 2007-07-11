using System;

namespace net.esper.support.bean
{
	public class SupportBean_S0
	{
		virtual public int Id
		{
            get { return _id; }
            set { this._id = value; }
		}

		virtual public String P00
		{
            get { return _p00; }
            set { this._p00 = value; }
		}

		virtual public String P01
		{
            get { return _p01; }
            set { this._p01 = value; }
		}

		virtual public String P02
		{
            get { return _p02; }
            set { this._p02 = value; }
		}

		virtual public String P03
		{
			get { return _p03; }			
			set { this._p03 = value; }
		}

		private static int idCounter;
		
		private int _id;
        private String _p00;
        private String _p01;
        private String _p02;
        private String _p03;
		
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
            this._id = id;
		}
		
		public SupportBean_S0(int id, String p00)
		{
            this._id = id;
            this._p00 = p00;
		}
		
		public SupportBean_S0(int id, String p00, String p01)
		{
            this._id = id;
            this._p00 = p00;
            this._p01 = p01;
		}
		
		public SupportBean_S0(int id, String p00, String p01, String p02)
		{
            this._id = id;
            this._p00 = p00;
            this._p01 = p01;
            this._p02 = p02;
		}
		
		public SupportBean_S0(int id, String p00, String p01, String p02, String p03)
		{
            this._id = id;
            this._p00 = p00;
            this._p01 = p01;
            this._p02 = p02;
            this._p03 = p03;
		}
	}
}
