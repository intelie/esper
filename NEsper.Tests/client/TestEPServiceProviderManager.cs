using System;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.client
{
    [TestFixture]
    public class TestEPServiceProviderManager
    {
        [Test]
        public virtual void testGetInstance()
        {
            Configuration configuration = new Configuration();

            EPServiceProvider runtimeDef1 = EPServiceProviderManager.GetDefaultProvider();
            EPServiceProvider runtimeA1 = EPServiceProviderManager.GetProvider("A");
            EPServiceProvider runtimeB = EPServiceProviderManager.GetProvider("B");
            EPServiceProvider runtimeA2 = EPServiceProviderManager.GetProvider("A");
            EPServiceProvider runtimeDef2 = EPServiceProviderManager.GetDefaultProvider(configuration);
            EPServiceProvider runtimeA3 = EPServiceProviderManager.GetProvider("A", configuration);

            Assert.IsNotNull(runtimeDef1);
            Assert.IsNotNull(runtimeA1);
            Assert.IsNotNull(runtimeB);
            Assert.IsTrue(runtimeDef1 == runtimeDef2);
            Assert.IsTrue(runtimeA1 == runtimeA2);
            Assert.IsTrue(runtimeA1 == runtimeA3);
            Assert.IsFalse(runtimeA1 == runtimeDef1);
            Assert.IsFalse(runtimeA1 == runtimeB);
        }

        [Test]
        public virtual void testInvalid()
        {
            Configuration configuration = new Configuration();
            configuration.AddEventTypeAlias("x", "xxx.noclass");

            try
            {
                EPServiceProviderManager.GetProvider("someURI", configuration);
                Assert.Fail();
            }
            catch (ConfigurationException ex)
            {
                // Expected
            }
        }
    }
}