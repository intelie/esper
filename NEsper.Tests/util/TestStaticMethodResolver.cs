using System;

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
		public virtual void  setUp()
		{
			autoImportService = new AutoImportServiceImpl(new String[]{"java.lang.*"});
		}
		
		[Test]
		public virtual void  testResolveMethod()
		{
			String className = "Math";
			String methodName = "max";
			Type[] args = new Type[]{typeof(int), typeof(int)};
			System.Reflection.MethodInfo expected = typeof(Math).GetMethod(methodName, (args == null)?new Type[0]:(Type[]) args);
			Assert.AreEqual(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
			
			args = new Type[]{typeof(long), typeof(long)};
			expected = typeof(Math).GetMethod(methodName, (args == null)?new Type[0]:(Type[]) args);
			args = new Type[]{typeof(int), typeof(long)};
			Assert.AreEqual(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
			
			args = new Type[]{typeof(int), typeof(int)};
			expected = typeof(Math).GetMethod(methodName, (args == null)?new Type[0]:(Type[]) args);
			args = new Type[]{typeof(Int32), typeof(Int32)};
			Assert.AreEqual(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
			
			args = new Type[]{typeof(long), typeof(long)};
			expected = typeof(Math).GetMethod(methodName, (args == null)?new Type[0]:(Type[]) args);
			args = new Type[]{typeof(Int32), typeof(Int64)};
			Assert.AreEqual(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
			
			args = new Type[]{typeof(float), typeof(float)};
			expected = typeof(Math).GetMethod(methodName, (args == null)?new Type[0]:(Type[]) args);
			args = new Type[]{typeof(Int32), typeof(Single)};
			Assert.AreEqual(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
			
			className = "DateTime";
			methodName = "Now";
			args = new Type[0];
			expected = typeof(DateTime).GetMethod(methodName, (args == null)?new Type[0]:(Type[]) args);
			Assert.AreEqual(expected, StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService));
		}
		
		[Test]
		public virtual void  testResolveMethodNotFound()
		{
			String className = "String";
			String methodName = "trim";
			Type[] args = null;
			try
			{
				StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService);
				Assert.Fail();
			}
			catch (System.MethodAccessException e)
			{
				// Expected
			}
			
			
			className = "Math";
			methodName = "moox";
			args = new Type[]{typeof(int), typeof(int)};
			try
			{
				StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService);
				Assert.Fail();
			}
			catch (System.MethodAccessException e)
			{
				// Expected
			}
			
			className = "Math";
			methodName = "max";
			args = new Type[]{typeof(bool), typeof(bool)};
			try
			{
				StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService);
				Assert.Fail();
			}
			catch (System.MethodAccessException e)
			{
				// Expected
			}
			
			className = "Math";
			methodName = "max";
			args = new Type[]{typeof(int), typeof(int), typeof(bool)};
			try
			{
				StaticMethodResolver.resolveMethod(className, methodName, args, autoImportService);
				Assert.Fail();
			}
			catch (System.MethodAccessException e)
			{
				// Expected
			}
		}
	}
}
