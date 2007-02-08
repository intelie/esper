using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.eql.parse
{
	/// <summary>
    /// Parameter supplying a single int value is a set of numbers.
    /// </summary>
	
    public class IntParameter : NumberSetParameter
	{
		/// <summary> Returns int value.</summary>
		/// <returns> int value
		/// </returns>
		
        virtual public int IntValue
		{
			get { return intValue; }
		}

        private int intValue;
		
		/// <summary> Ctor.</summary>
		/// <param name="intValue">- single in value
		/// </param>

        public IntParameter(int intValue)
		{
			this.intValue = intValue;
		}
		
		public virtual bool IsWildcard(int min, int max)
		{
			if ((intValue == min) && (intValue == max))
			{
				return true;
			}
			return false;
		}
		
		public ISet<Int32> GetValuesInRange(int min, int max)
		{
			ISet<Int32> values = new EHashSet<Int32>();
			
			if ((intValue >= min) && (intValue <= max))
			{
                values.Add(intValue);
			}
			
			return values;
		}
	}
}