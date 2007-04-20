using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestAutoImportServiceImpl
	{
		internal AutoImportServiceImpl autoImportService;

		[SetUp]
		public virtual void setUp()
		{
			this.autoImportService = new AutoImportServiceImpl( new String[0] );
		}

		[Test]
		public virtual void testResolveClass()
		{
			String className = "Math";
			Type expected = typeof( Math ) ;

			autoImportService.AddNamespace( "System" );
			Assert.AreEqual( expected, autoImportService.ResolveClass( className ) );

			className = "String";
			expected = typeof( String );
			Assert.AreEqual( expected, autoImportService.ResolveClass( className ) );
		}

		[Test]
		public virtual void testResolveClassInvalid()
		{
			String className = "Math";

            try
			{
				autoImportService.ResolveClass( className );
				Assert.Fail();
			}
            catch( NUnit.Framework.AssertionException e )
            {
                throw e;
            }
			catch ( System.Exception e )
			{
				// Expected
			}
		}

		[Test]
		public virtual void testAddImport()
		{
			autoImportService.AddNamespace( "System.Collections" );
			Assert.AreEqual( 1, autoImportService.Namespaces.Count );
			Assert.AreEqual( "System.Collections", autoImportService.Namespaces[0] );

			autoImportService.AddNamespace( "System" );
			Assert.AreEqual( 2, autoImportService.Namespaces.Count );
			Assert.AreEqual( "System.Collections", autoImportService.Namespaces[0] );
			Assert.AreEqual( "System", autoImportService.Namespaces[1] );
		}

		[Test]
		public virtual void testAddImportInvalid()
		{
//			try
//			{
//				autoImportService.AddNamespace( "System.*" );
//				Assert.Fail();
//			}
//			catch ( ArgumentException e )
//			{
//				// Expected
//			}
		}
	}
}