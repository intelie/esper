using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.eql.parse
{	
	/// <summary>
    /// Represents a wildcard as a parameter.
    /// </summary>
	
    public class WildcardParameter : NumberSetParameter
	{
        public Boolean IsWildcard(int min, int max)
        {
            return true;
        }

        public ISet<int> GetValuesInRange(int min, int max)
        {
            ISet<int> result = new EHashSet<Int32>();
            for (int i = min; i <= max; i++)
            {
                result.Add(i);
            }
            return result;
        }
    }
}