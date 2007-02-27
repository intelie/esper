using System;

using net.esper.client;
using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.regression.view
{
    [TestFixture]
    public class TestViewGroupByTypes
    {
        private EPServiceProvider epService;

        [SetUp]
        public virtual void setUp()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();
            epService.Initialize();
        }

        [Test]
        public virtual void testType()
        {
            String viewStmt = "select * from " + typeof(SupportBean).FullName + ".std:groupby('intPrimitive').win:length(4).std:groupby('longBoxed').std:size()";
            EPStatement stmt = epService.EPAdministrator.CreateEQL(viewStmt);

            Assert.AreEqual(typeof(int), stmt.EventType.GetPropertyType("intPrimitive"));
            Assert.AreEqual(typeof(long?), stmt.EventType.GetPropertyType("longBoxed"));
            Assert.AreEqual(typeof(long), stmt.EventType.GetPropertyType("size"));
            Assert.AreEqual(3, stmt.EventType.PropertyNames.Count);
        }
    }
}