using System;

namespace net.esper.support.bean
{
	public class SupportBean_S6
	{
		virtual public int Id
		{
			get { return _id; }
		}

		virtual public String P60
		{
            get { return _p60; }
		}

		virtual public String P61
		{
            get { return _p61; }
		}

		virtual public String P62
		{
            get { return _p62; }
		}

		virtual public String P63
		{
            get { return _p63; }
		}

        private static int idCounter;

        private int _id;
        private String _p60;
        private String _p61;
        private String _p62;
        private String _p63;
		
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
            this._id = id;
		}
		
		public SupportBean_S6(int id, String p60)
		{
            this._id = id;
            this._p60 = p60;
		}
		
		public SupportBean_S6(int id, String p60, String p61)
		{
            this._id = id;
            this._p60 = p60;
            this._p61 = p61;
		}
	}
}
