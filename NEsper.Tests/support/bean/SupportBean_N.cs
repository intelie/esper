using System;

namespace net.esper.support.bean
{
	
	public class SupportBean_N
	{
		virtual public int IntPrimitive
		{
			get
			{
				return intPrimitive;
			}
			
		}
		virtual public Int32 IntBoxed
		{
			get
			{
				return intBoxed;
			}
			
		}
		virtual public double DoublePrimitive
		{
			get
			{
				return doublePrimitive;
			}
			
		}
		virtual public Double DoubleBoxed
		{
			get
			{
				return doubleBoxed;
			}
			
		}
		virtual public bool BoolPrimitive
		{
			get
			{
				return boolPrimitive;
			}
			
		}
		virtual public bool BoolBoxed
		{
			get
			{
				return boolBoxed;
			}
			
		}
		private int intPrimitive;
		private Int32 intBoxed;
		private double doublePrimitive;
		private Double doubleBoxed;
		private bool boolPrimitive;
		private bool boolBoxed;
		
		public SupportBean_N(int intPrimitive, Int32 intBoxed, double doublePrimitive, Double doubleBoxed, bool boolPrimitive, bool boolBoxed)
		{
			this.intPrimitive = intPrimitive;
			this.intBoxed = intBoxed;
			this.doublePrimitive = doublePrimitive;
			this.doubleBoxed = doubleBoxed;
			this.boolPrimitive = boolPrimitive;
			this.boolBoxed = boolBoxed;
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
