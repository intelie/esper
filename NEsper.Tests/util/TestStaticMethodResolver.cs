///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Reflection;

using NUnit.Framework;

using net.esper.compat;
using net.esper.eql.core;

namespace net.esper.util
{
	[TestFixture]
	public class TestStaticMethodResolver
	{
		[Test]
		public void testResolveMethod()
		{
	        Type declClass = typeof(Math);
			String methodName = "Max";
			Type[] args = new Type[] { typeof(int), typeof(int) };
			MethodInfo expected = typeof(Math).GetMethod(methodName, args);
			Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(declClass, methodName, args));

			args = new Type[] { typeof(long), typeof(long) };
			expected = typeof(Math).GetMethod(methodName, args);
			args = new Type[] { typeof(int), typeof(long) };
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(declClass, methodName, args));

			args = new Type[] { typeof(int), typeof(int) };
			expected = typeof(Math).GetMethod(methodName, args);
			args = new Type[] { typeof(int?), typeof(int?) };
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(declClass, methodName, args));

			args = new Type[] { typeof(long), typeof(long) };
			expected = typeof(Math).GetMethod(methodName, args);
			args = new Type[] { typeof(int?), typeof(long?) };
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(declClass, methodName, args));

			args = new Type[] { typeof(float), typeof(float) };
			expected = typeof(Math).GetMethod(methodName, args);
			args = new Type[] { typeof(int?), typeof(float?) };
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(declClass, methodName, args));

	        declClass = typeof(DateTimeHelper);
			methodName = "GetCurrentTimeMillis";
			args = new Type[0];
			expected = typeof(DateTimeHelper).GetMethod(methodName, args);
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(declClass, methodName, args));
		}

		[Test]
		public void testResolveMethodNotFound()
		{
	        Type declClass = typeof(String);
			String methodName = "trim";
			Type[] args = null;
			try
			{
				StaticMethodResolver.ResolveMethod(declClass, methodName, args);
				Assert.Fail();
			}
            catch (MethodAccessException e)
			{
				// Expected
			}


			declClass = typeof(Math);
			methodName = "moox";
			args = new Type[] { typeof(int), typeof(int) };
			try
			{
				StaticMethodResolver.ResolveMethod(declClass, methodName, args);
				Assert.Fail();
			}
            catch (MethodAccessException e)
			{
				// Expected
			}

			methodName = "max";
			args = new Type[] { typeof(bool), typeof(bool) };
			try
			{
				StaticMethodResolver.ResolveMethod(declClass, methodName, args);
				Assert.Fail();
			}
            catch (MethodAccessException e)
			{
				// Expected
			}

			methodName = "max";
			args = new Type[] { typeof(int), typeof(int), typeof(bool) };
			try
			{
				StaticMethodResolver.ResolveMethod(declClass, methodName, args);
				Assert.Fail();
			}
            catch (MethodAccessException e)
			{
				// Expected
			}
		}
	}
} // End of namespace
