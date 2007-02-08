using System;

using net.esper.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.support.util
{
	
	public class ArrayCompareUtil
	{
		/// <summary> Compare the events in the two object arrays assuming the exact same order.</summary>
		/// <param name="data">is the data to Assert.AreEqualExactOrder against
		/// </param>
		/// <param name="expectedValues">is the expected values
		/// </param>
		public static bool compareEqualsExactOrder(EventBean[] data, EventBean[] expectedValues)
		{
			if ((expectedValues == null) && (data == null))
			{
				return true;
			}
			if (((expectedValues == null) && (data != null)) || ((expectedValues != null) && (data == null)))
			{
				return false;
			}
			
			if (expectedValues.Length != data.Length)
			{
				return false;
			}
			
			for (int i = 0; i < expectedValues.Length; i++)
			{
				if ((data[i] == null) && (expectedValues[i] == null))
				{
					continue;
				}
				
				if (!data[i].Equals(expectedValues[i]))
				{
					return false;
				}
			}
			return true;
		}
		
		/// <summary> Compare the objects in the two object arrays assuming the exact same order.</summary>
		/// <param name="data">is the data to Assert.AreEqualExactOrder against
		/// </param>
		/// <param name="expectedValues">is the expected values
		/// </param>
		public static bool compareRefExactOrder(Object[] data, Object[] expectedValues)
		{
			if ((expectedValues == null) && (data == null))
			{
				return true;
			}
			if (((expectedValues == null) && (data != null)) || ((expectedValues != null) && (data == null)))
			{
				return false;
			}
			
			if (expectedValues.Length != data.Length)
			{
				return false;
			}
			
			for (int i = 0; i < expectedValues.Length; i++)
			{
				if (expectedValues[i] != data[i])
				{
					return false;
				}
			}
			
			return true;
		}
	}
}
