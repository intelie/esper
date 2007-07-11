using System;

namespace net.esper.support.bean
{
	public class SupportBean_S4
	{
		virtual public int Id
		{
            get { return _id; }
            set { this._id = value; }
		}

		virtual public String P40
		{
            get { return _p40; }
            set { this._p40 = value; }
		}

		virtual public String P41
		{
            get { return _p41; }
            set { this._p41 = value; }
		}

		virtual public String P42
		{
            get { return _p42; }
            set { this._p42 = value; }
		}

		virtual public String P43
		{
            get { return _p43; }
            set { this._p43 = value; }
		}

        private static int idCounter;
		
		private int _id;
        private String _p40;
        private String _p41;
        private String _p42;
        private String _p43;
		
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
            this._id = id;
		}
		
		public SupportBean_S4(int id, String p40)
		{
            this._id = id;
            this._p40 = p40;
		}
		
		public SupportBean_S4(int id, String p40, String p41)
		{
            this._id = id;
            this._p40 = p40;
            this._p41 = p41;
		}
	}
}
