using System;
using System.Reflection;

using net.esper.eql.core;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.util
{
    [TestFixture]
    public class TestStaticMethodResolver
    {
        private AutoImportService autoImportService;

        [SetUp]
        public virtual void setUp()
        {
            autoImportService = new AutoImportServiceImpl(new String[] { "System" });
        }

        [Test]
        public virtual void testResolveMethod()
        {
            String className = "Math";
            String methodName = "Max";
            Type[] args = new Type[] { typeof(int), typeof(int) };
            MethodInfo expected = typeof(Math).GetMethod(methodName, args);
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(className, methodName, args, autoImportService));

            args = new Type[] { typeof(long), typeof(long) };
            expected = typeof(Math).GetMethod(methodName, args);
            args = new Type[] { typeof(int), typeof(long) };
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(className, methodName, args, autoImportService));

            args = new Type[] { typeof(int), typeof(int) };
            expected = typeof(Math).GetMethod(methodName, args);
            args = new Type[] { typeof(int?), typeof(int?) };
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(className, methodName, args, autoImportService));

            args = new Type[] { typeof(long), typeof(long) };
            expected = typeof(Math).GetMethod(methodName, args);
            args = new Type[] { typeof(int?), typeof(long?) };
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(className, methodName, args, autoImportService));

            args = new Type[] { typeof(float), typeof(float) };
            expected = typeof(Math).GetMethod(methodName, args);
            args = new Type[] { typeof(int?), typeof(float?) };
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(className, methodName, args, autoImportService));

            className = "net.esper.compat.DateTimeHelper";
            methodName = "GetCurrentTimeMillis";
            args = new Type[0];
            expected = typeof(net.esper.compat.DateTimeHelper).GetMethod(methodName, args);
            Assert.AreEqual(expected, StaticMethodResolver.ResolveMethod(className, methodName, args, autoImportService));
        }

        [Test]
        public virtual void testResolveMethodNotFound()
        {
            String className = "String";
            String methodName = "trim";
            Type[] args = null;
            try
            {
                StaticMethodResolver.ResolveMethod(className, methodName, args, autoImportService);
                Assert.Fail();
            }
            catch (System.MethodAccessException e)
            {
                // Expected
            }


            className = "Math";
            methodName = "moox";
            args = new Type[] { typeof(int), typeof(int) };
            try
            {
                StaticMethodResolver.ResolveMethod(className, methodName, args, autoImportService);
                Assert.Fail();
            }
            catch (System.MethodAccessException e)
            {
                // Expected
            }

            className = "Math";
            methodName = "max";
            args = new Type[] { typeof(bool), typeof(bool) };
            try
            {
                StaticMethodResolver.ResolveMethod(className, methodName, args, autoImportService);
                Assert.Fail();
            }
            catch (System.MethodAccessException e)
            {
                // Expected
            }

            className = "Math";
            methodName = "max";
            args = new Type[] { typeof(int), typeof(int), typeof(bool) };
            try
            {
                StaticMethodResolver.ResolveMethod(className, methodName, args, autoImportService);
                Assert.Fail();
            }
            catch (System.MethodAccessException e)
            {
                // Expected
            }
        }
    }
}
