using System;

namespace net.esper.support.bean
{
	public class SupportBean_S5
	{
		virtual public int Id
		{
            get { return _id; }
            set { this._id = value; }
		}

		virtual public String P50
		{
            get { return _p50; }
            set { this._p50 = value; }
		}

		virtual public String P51
		{
            get { return _p51; }
		}

		virtual public String P52
		{
            get { return _p52; }			
		}

		virtual public String P53
		{
			get { return _p53; }
		}

        private static int idCounter;
		
		private int _id;
        private String _p50;
        private String _p51;
        private String _p52;
        private String _p53;
		
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
            this._id = id;
		    this._p50 = null;
		    this._p51 = null;
		    this._p52 = null;
		    this._p53 = null;
		}
		
		public SupportBean_S5(int id, String p50)
		{
            this._id = id;
            this._p50 = p50;
            this._p51 = null;
            this._p52 = null;
            this._p53 = null;
        }
		
		public SupportBean_S5(int id, String p50, String p51)
		{
            this._id = id;
            this._p50 = p50;
            this._p51 = p51;
            this._p52 = null;
            this._p53 = null;
        }
	}
}
