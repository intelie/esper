using System;
using System.Collections.Generic;

using net.esper.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.events.property
{
	
	[TestFixture]
	public class TestPropertyParser 
	{
		private BeanEventAdapter beanEventAdapter;
		
		[SetUp]
		public virtual void  setUp()
		{
			beanEventAdapter = new BeanEventAdapter(null);
		}
		
		[Test]
		public virtual void  testParse()
		{
			Property property = PropertyParser.Parse("i[1]", beanEventAdapter);
			Assert.AreEqual("i", ((IndexedProperty) property).PropertyName);
			Assert.AreEqual(1, ((IndexedProperty) property).Index);

            property = PropertyParser.Parse("m('key')", beanEventAdapter);
			Assert.AreEqual("m", ((MappedProperty) property).PropertyName);
			Assert.AreEqual("key", ((MappedProperty) property).Key);

            property = PropertyParser.Parse("a", beanEventAdapter);
			Assert.AreEqual("a", ((SimpleProperty) property).PropertyName);

            property = PropertyParser.Parse("a.b[2].c('m')", beanEventAdapter);
      IList<Property> nested = ((NestedProperty)property).Properties;
			Assert.AreEqual(3, nested.Count);
			Assert.AreEqual("a", ((SimpleProperty) nested[0]).PropertyName);
			Assert.AreEqual("b", ((IndexedProperty) nested[1]).PropertyName);
			Assert.AreEqual(2, ((IndexedProperty) nested[1]).Index);
			Assert.AreEqual("c", ((MappedProperty) nested[2]).PropertyName);
			Assert.AreEqual("m", ((MappedProperty) nested[2]).Key);
		}
		
		[Test]
		public virtual void  testParseMapKey()
		{
			Assert.AreEqual("a", tryKey("a"));
		}
		
		private String tryKey(String key)
		{
			String propertyName = "m(\"" + key + "\")";
			log.Debug(".tryKey propertyName=" + propertyName + " key=" + key);
            Property property = PropertyParser.Parse(propertyName, beanEventAdapter);
			return ((MappedProperty) property).Key;
		}

		private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
