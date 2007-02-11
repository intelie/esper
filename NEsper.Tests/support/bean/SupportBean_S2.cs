using System;

namespace net.esper.support.bean
{
	
	public class SupportBean_S2
	{
		virtual public int Id
		{
            get { return _id; }
            set { this._id = value; }
		}

		virtual public String P20
		{
            get { return _p20; }
            set { this._p20 = value; }
		}

		virtual public String P21
		{
            get { return _p21; }
            set { this._p21 = value; }
		}

		virtual public String P22
		{
            get { return _p22; }
            set { this._p22 = value; }
		}

		virtual public String P23
		{
            get { return _p23; }
            set { this._p23 = value; }
		}

        virtual public int id
        {
            get { return _id; }
            set { this._id = value; }
        }

        virtual public String p20
        {
            get { return _p20; }
            set { this._p20 = value; }
        }

        virtual public String p21
        {
            get { return _p21; }
            set { this._p21 = value; }
        }

        virtual public String p22
        {
            get { return _p22; }
            set { this._p22 = value; }
        }

        virtual public String p23
        {
            get { return _p23; }
            set { this._p23 = value; }
        }


		private static int idCounter;

        private int _id;
        private String _p20;
        private String _p21;
        private String _p22;
        private String _p23;
		
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
            this._id = id;
		}
		
		public SupportBean_S2(int id, String p20)
		{
            this._id = id;
            this._p20 = p20;
		}
		
		public SupportBean_S2(int id, String p20, String p21)
		{
            this._id = id;
            this._p20 = p20;
            this._p21 = p21;
		}
	}
}
