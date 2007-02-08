using System;
using System.IO;

using NUnit.Core;
using NUnit.Framework;

using net.esper.compat;

namespace net.esper.client
{
	[TestFixture]
	public class TestConfiguration
	{
		protected internal const String ESPER_TEST_CONFIG = "regression/esper.test.readconfig.cfg.xml";

		private Configuration config;

		[SetUp]
		public virtual void setUp()
		{
			config = new Configuration();
        }

		[Test]
		public virtual void testString()
		{
			config.Configure( ESPER_TEST_CONFIG );
			TestConfigurationParser.assertFileConfig( config );
		}

		[Test]
		public virtual void testURL()
		{
            Uri url = ResourceManager.ResolveResourceURL(ESPER_TEST_CONFIG);
            Assert.IsNotNull(url);

			config.Configure( url );
			TestConfigurationParser.assertFileConfig( config );
		}

		[Test]
		public virtual void testFile()
		{
            FileInfo file = ResourceManager.ResolveResourceFile(ESPER_TEST_CONFIG);
			config.Configure( file );
			TestConfigurationParser.assertFileConfig( config );
		}

		[Test]
		public virtual void testAddEventTypeAlias()
		{
			config.addEventTypeAlias( "AEventType", "BClassName" );

			Assert.AreEqual( 1, config.EventTypeAliases.Count );
			Assert.AreEqual( "BClassName", config.EventTypeAliases.Fetch( "AEventType" ) );
			assertDefaultConfig();
		}

		private void assertDefaultConfig()
		{
			Assert.AreEqual( 3, config.Imports.Count );
			Assert.AreEqual( "System", config.Imports[0] );
			Assert.AreEqual( "System.Collections", config.Imports[1] );
			Assert.AreEqual( "System.Text", config.Imports[2] );
		}
	}
}
