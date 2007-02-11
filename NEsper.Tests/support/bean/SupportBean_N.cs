using System;

namespace net.esper.support.bean
{
	public class SupportBean_N
	{
		virtual public int intPrimitive
		{
			get { return _intPrimitive; }
		}

		virtual public Int32 intBoxed
		{
			get { return _intBoxed; }
		}

		virtual public double doublePrimitive
		{
			get { return _doublePrimitive; }
		}

		virtual public Double doubleBoxed
		{
			get { return _doubleBoxed; }
		}

		virtual public bool boolPrimitive
		{
			get { return _boolPrimitive; }
		}

		virtual public bool boolBoxed
		{
			get { return _boolBoxed; }
		}

		private int _intPrimitive;
		private Int32 _intBoxed;
		private double _doublePrimitive;
		private Double _doubleBoxed;
		private bool _boolPrimitive;
		private bool _boolBoxed;
		
		public SupportBean_N(int intPrimitive, Int32 intBoxed, double doublePrimitive, Double doubleBoxed, bool boolPrimitive, bool boolBoxed)
		{
			this._intPrimitive = intPrimitive;
			this._intBoxed = intBoxed;
			this._doublePrimitive = doublePrimitive;
			this._doubleBoxed = doubleBoxed;
			this._boolPrimitive = boolPrimitive;
			this._boolBoxed = boolBoxed;
		}
		
		public override String ToString()
		{
			return
			  "intPrim=" + intPrimitive +
			  " intBoxed=" + intBoxed +
			  " doublePrim=" + doublePrimitive + 
			  " doubleBoxed=" + doubleBoxed + 
			  " boolPrim=" + boolPrimitive + 
			  " boolBoxed=" + boolBoxed;
		}
	}
}
