///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Threading;

namespace net.esper.support.eql
{
	public class SupportStaticMethodLib
	{
		public static Object StaticMethod(Object _object)
		{
			return _object;
		}

		public static void ThrowException()
		{
			throw new Exception("SupportStaticMethod.exceptionThrower throwing a fit");
		}

	    public static bool IsStringEquals(String value, String compareTo)
	    {
	        return value.Equals(compareTo);
	    }

	    public static double MinusOne(double value)
	    {
	        return value - 1;
	    }

	    public static String AppendPipe(String _string, String value)
	    {
	        return _string + "|" + value;
	    }

	    public static long Passthru(long value)
	    {
	        return value;
	    }

	    public static void Sleep(long msec)
	    {
            Thread.Sleep((int) msec);
	    }

	    public static String DelimitPipe(String _string)
	    {
	        return "|" + _string + "|";
	    }
	}
} // End of namespace
