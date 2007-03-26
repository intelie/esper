using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.events
{
    [TestFixture]
    public class TestBeanEventType
    {
        private BeanEventAdapter beanEventAdapter;
        private BeanEventType eventTypeSimple;
        private BeanEventType eventTypeComplex;
        private BeanEventType eventTypeNested;

        private EventBean eventSimple;
        private EventBean eventComplex;
        private EventBean eventNested;

        private SupportBeanSimple objSimple;
        private SupportBeanComplexProps objComplex;
        private SupportBeanCombinedProps objCombined;

        [SetUp]
        public virtual void setUp()
        {
            beanEventAdapter = new BeanEventAdapter(null);

            eventTypeSimple = new BeanEventType(typeof(SupportBeanSimple), beanEventAdapter, null);
            eventTypeComplex = new BeanEventType(typeof(SupportBeanComplexProps), beanEventAdapter, null);
            eventTypeNested = new BeanEventType(typeof(SupportBeanCombinedProps), beanEventAdapter, null);

            objSimple = new SupportBeanSimple("a", 20);
            objComplex = SupportBeanComplexProps.makeDefaultBean();
            objCombined = SupportBeanCombinedProps.makeDefaultBean();

            eventSimple = beanEventAdapter.AdapterForBean(objSimple);
            eventComplex = beanEventAdapter.AdapterForBean(objComplex);
            eventNested = beanEventAdapter.AdapterForBean(objCombined);
        }

        [Test]
        public virtual void testGetPropertyNames()
        {
            ICollection<string> properties = eventTypeSimple.PropertyNames;
            IEnumerator<string> propertiesEnum = properties.GetEnumerator();
            Assert.IsTrue(properties.Count == 4);
            Assert.IsTrue(properties.Contains("myInt"));
            Assert.IsTrue(properties.Contains("myString"));
            Assert.IsTrue(properties.Contains("MyInt"));
            Assert.IsTrue(properties.Contains("MyString"));

            ArrayAssertionUtil.assertEqualsAnyOrder(
                SupportBeanComplexProps.PROPERTIES,
                eventTypeComplex.PropertyNames);

            ArrayAssertionUtil.assertEqualsAnyOrder(
                SupportBeanCombinedProps.PROPERTIES,
                eventTypeNested.PropertyNames);
        }

        [Test]
        public virtual void testGetUnderlyingType()
        {
            Assert.AreEqual(typeof(SupportBeanSimple), eventTypeSimple.UnderlyingType);
        }

        [Test]
        public virtual void testGetPropertyType()
        {
            Assert.AreEqual(typeof(String), eventTypeSimple.GetPropertyType("myString"));
            Assert.IsNull(eventTypeSimple.GetPropertyType("dummy"));
        }

        [Test]
        public virtual void testIsValidProperty()
        {
            Assert.IsTrue(eventTypeSimple.IsProperty("myString"));
            Assert.IsFalse(eventTypeSimple.IsProperty("dummy"));
        }

        [Test]
        public virtual void testGetGetter()
        {
            Assert.AreEqual(null, eventTypeSimple.GetGetter("dummy"));

            EventPropertyGetter getter = eventTypeSimple.GetGetter("myInt");
            Assert.AreEqual(20, getter.GetValue(eventSimple));
            getter = eventTypeSimple.GetGetter("myString");
            Assert.AreEqual("a", getter.GetValue(eventSimple));

            try
            {
                // test mismatch between bean and object
                EventBean eventBean = beanEventAdapter.AdapterForBean(new Object());
                Object temp = getter.GetValue(eventBean);
                Assert.Fail();
            }
            catch (PropertyAccessException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testProperties()
        {
            Type nestedOne = typeof(SupportBeanCombinedProps.NestedLevOne);
            Type nestedOneArr = typeof(SupportBeanCombinedProps.NestedLevOne[]);
            Type nestedTwo = typeof(SupportBeanCombinedProps.NestedLevTwo);

            // test nested/combined/indexed/mapped properties
            // PropertyName                 isProperty              getType         hasGetter   getterValue
            IList<PropTestDesc> tests = new List<PropTestDesc>();

            tests = new List<PropTestDesc>();
            tests.Add(new PropTestDesc("simpleProperty", true, typeof(String), true, "simple"));
            tests.Add(new PropTestDesc("dummy", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("indexed", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("indexed[1]", true, typeof(int), true, 2));
            tests.Add(new PropTestDesc("nested", true, typeof(SupportBeanComplexProps.SupportBeanSpecialGetterNested), true, objComplex.Nested));
            tests.Add(new PropTestDesc("nested.nestedValue", true, typeof(String), true, objComplex.Nested.NestedValue));
            tests.Add(new PropTestDesc("nested.nestedNested", true, typeof(SupportBeanComplexProps.SupportBeanSpecialGetterNestedNested), true, objComplex.Nested.NestedNested));
            tests.Add(new PropTestDesc("nested.nestedNested.nestedNestedValue", true, typeof(String), true, objComplex.Nested.NestedNested.NestedNestedValue));
            tests.Add(new PropTestDesc("nested.dummy", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("mapped", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("mapped('keyOne')", true, typeof(String), true, "valueOne"));
            tests.Add(new PropTestDesc("arrayProperty", true, typeof(int[]), true, objComplex.ArrayProperty));
            tests.Add(new PropTestDesc("arrayProperty[1]", true, typeof(int), true, 20));
            tests.Add(new PropTestDesc("mapProperty('xOne')", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("google('x')", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("mapped('x')", true, typeof(String), true, (Object)null));
            tests.Add(new PropTestDesc("mapped('x').x", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("mapProperty", true, typeof(System.Collections.IDictionary), true, objComplex.MapProperty));
            runTest(tests, eventTypeComplex, eventComplex);

            tests = new List<PropTestDesc>();
            tests.Add(new PropTestDesc("dummy", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("myInt", true, typeof(int), true, objSimple.MyInt));
            tests.Add(new PropTestDesc("myString", true, typeof(String), true, objSimple.MyString));
            tests.Add(new PropTestDesc("dummy('a')", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("dummy[1]", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("dummy.nested", false, null, false, (Object)null));
            runTest(tests, eventTypeSimple, eventSimple);

            tests = new List<PropTestDesc>();
            tests.Add(new PropTestDesc("indexed", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("indexed[1]", true, nestedOne, true, objCombined.getIndexed(1)));
            tests.Add(new PropTestDesc("indexed.mapped", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("indexed[1].mapped", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("array", true, nestedOneArr, true, objCombined.Array));
            tests.Add(new PropTestDesc("array.mapped", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("array[0]", true, nestedOne, true, objCombined.Array[0]));
            tests.Add(new PropTestDesc("array[1].mapped", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("array[1].mapped('x')", true, nestedTwo, true, objCombined.Array[1].getMapped("x")));
            tests.Add(new PropTestDesc("array[1].mapped('1mb')", true, nestedTwo, true, objCombined.Array[1].getMapped("1mb")));
            tests.Add(new PropTestDesc("indexed[1].mapped('x')", true, nestedTwo, true, objCombined.getIndexed(1).getMapped("x")));
            tests.Add(new PropTestDesc("indexed[1].mapped('x').value", true, typeof(String), true, (Object)null));
            tests.Add(new PropTestDesc("indexed[1].mapped('1mb')", true, nestedTwo, true, objCombined.getIndexed(1).getMapped("1mb")));
            tests.Add(new PropTestDesc("indexed[1].mapped('1mb').value", true, typeof(String), true, objCombined.getIndexed(1).getMapped("1mb").Value));
            tests.Add(new PropTestDesc("array[1].mapprop", true, typeof(System.Collections.IDictionary), true, objCombined.getIndexed(1).getMapprop()));
            tests.Add(new PropTestDesc("array[1].mapprop('a')", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("array[1].mapprop('a').value", false, null, false, (Object)null));
            tests.Add(new PropTestDesc("indexed[1].mapprop", true, typeof(System.Collections.IDictionary), true, objCombined.getIndexed(1).getMapprop()));
            runTest(tests, eventTypeNested, eventNested);

            tryInvalidIsProperty(eventTypeComplex, "x[");
            tryInvalidIsProperty(eventTypeComplex, "dummy()");
            tryInvalidIsProperty(eventTypeComplex, "nested.xx['a']");
            tryInvalidGetPropertyType(eventTypeComplex, "x[");
            tryInvalidGetPropertyType(eventTypeComplex, "dummy()");
            tryInvalidGetPropertyType(eventTypeComplex, "nested.xx['a']");
            tryInvalidGetPropertyType(eventTypeNested, "dummy[(");
            tryInvalidGetPropertyType(eventTypeNested, "array[1].mapprop[x].value");
        }

        [Test]
        public virtual void testGetDeepSuperTypes()
        {
            BeanEventType type = new BeanEventType(typeof(ISupportAImplSuperGImplPlus), beanEventAdapter, null);

            List<EventType> deepSuperTypes = new List<EventType>();
            deepSuperTypes.AddRange(type.DeepSuperTypes);

            Assert.AreEqual(5, deepSuperTypes.Count);
            ArrayAssertionUtil.assertEqualsAnyOrder(
                deepSuperTypes,
                new EventType[] {
                    beanEventAdapter.CreateOrGetBeanType(typeof(ISupportAImplSuperG)), 
                    beanEventAdapter.CreateOrGetBeanType(typeof(ISupportBaseAB)), 
                    beanEventAdapter.CreateOrGetBeanType(typeof(ISupportA)), 
                    beanEventAdapter.CreateOrGetBeanType(typeof(ISupportB)), 
                    beanEventAdapter.CreateOrGetBeanType(typeof(ISupportC))
                });
        }

        [Test]
        public virtual void testGetSuper()
        {
            LinkedHashSet<Type> classes = new LinkedHashSet<Type>();
            BeanEventType.GetSuper(typeof(ISupportAImplSuperGImplPlus), classes);

            List<Type> listOfClasses = new List<Type>(classes);

            Assert.AreEqual(6, classes.Count);
            ArrayAssertionUtil.assertEqualsAnyOrder(
                (ICollection<Type>) classes.ToArray(),
                (ICollection<Type>) new Type[] {
                    typeof(ISupportAImplSuperG),
                    typeof(ISupportBaseAB),
                    typeof(ISupportA),
                    typeof(ISupportB),
                    typeof(ISupportC),
                    typeof(Object)
                });

            classes.Clear();
            BeanEventType.GetSuper(typeof(Object), classes);
            Assert.AreEqual(0, classes.Count);
        }

        [Test]
        public virtual void testGetSuperTypes()
        {
            eventTypeSimple = new BeanEventType(typeof(ISupportAImplSuperGImplPlus), beanEventAdapter, null);

            List<EventType> superTypes = new List<EventType>(eventTypeSimple.SuperTypes);
            Assert.AreEqual(5, superTypes.Count);
            Assert.AreEqual(typeof(ISupportAImplSuperG), superTypes[0].UnderlyingType);
            Assert.AreEqual(typeof(ISupportA), superTypes[1].UnderlyingType);
            Assert.AreEqual(typeof(ISupportBaseAB), superTypes[2].UnderlyingType);
            Assert.AreEqual(typeof(ISupportB), superTypes[3].UnderlyingType);
            Assert.AreEqual(typeof(ISupportC), superTypes[4].UnderlyingType);

            eventTypeSimple = new BeanEventType(typeof(Object), beanEventAdapter, null);
            superTypes = new List<EventType>(eventTypeSimple.SuperTypes);
            Assert.AreEqual(0, superTypes.Count);

            BeanEventType type = new BeanEventType(typeof(ISupportD), beanEventAdapter, null);
            Assert.AreEqual(3, type.PropertyNames.Count);
            ArrayAssertionUtil.assertEqualsAnyOrder(type.PropertyNames, new String[] { "d", "baseD", "baseDBase" });
        }

        private static void tryInvalidGetPropertyType(BeanEventType type, String property)
        {
            try
            {
                type.GetPropertyType(property);
                Assert.Fail();
            }
            catch (PropertyAccessException ex)
            {
                // expected
            }
        }

        private static void tryInvalidIsProperty(BeanEventType type, String property)
        {
            try
            {
                type.GetPropertyType(property);
                Assert.Fail();
            }
            catch (PropertyAccessException ex)
            {
                // expected
            }
        }

        private static void runTest(IList<PropTestDesc> tests, BeanEventType eventType, EventBean eventBean)
        {
            foreach (PropTestDesc desc in tests)
            {
                runTest(desc, eventType, eventBean);
            }
        }

        private static void runTest(PropTestDesc test, BeanEventType eventType, EventBean eventBean)
        {
            String propertyName = test.PropertyName;

            Assert.AreEqual(test.Property, eventType.IsProperty(propertyName),
                "isProperty mismatch on '" + propertyName + "',");
            //Assert.AreEqual(test.Clazz, eventType.GetPropertyType(propertyName),
            //    "GetPropertyType mismatch on '" + propertyName + "',");

            EventPropertyGetter getter = eventType.GetGetter(propertyName);
            if (getter == null)
            {
                Assert.IsFalse(test.HasGetter, "GetGetter null on '" + propertyName + "'," );
            }
            else
            {
                Assert.IsTrue(test.HasGetter, "GetGetter not null on '" + propertyName + "'," );
                if (test.GetterReturnValue == typeof(System.NullReferenceException))
                {
                    try
                    {
                        Object temp = getter.GetValue(eventBean);
						Assert.Fail( "GetGetter not throwing null pointer on '" + propertyName );
                    }
                    catch (System.NullReferenceException ex)
                    {
                        // expected
                    }
                }
                else
                {
                    Object value = getter.GetValue(eventBean);
                    Assert.AreEqual(test.GetterReturnValue, value, "getter value mismatch on '" + propertyName + "',");
                }
            }
        }

        public class PropTestDesc
        {
            virtual public String PropertyName
            {
                get
                {
                    return propertyName;
                }

            }
            virtual public bool Property
            {
                get
                {
                    return isProperty;
                }

            }
            virtual public Type Clazz
            {
                get
                {
                    return clazz;
                }

            }
            virtual public bool HasGetter
            {
                get
                {
                    return hasGetter;
                }

            }
            virtual public Object GetterReturnValue
            {
                get
                {
                    return getterReturnValue;
                }

            }
            private String propertyName;
            private bool isProperty;
            private Type clazz;
            private bool hasGetter;
            private Object getterReturnValue;

            public PropTestDesc(String propertyName, bool property, Type clazz, bool hasGetter, Object getterReturnValue)
            {
                this.propertyName = propertyName;
                isProperty = property;
                this.clazz = clazz;
                this.hasGetter = hasGetter;
                this.getterReturnValue = getterReturnValue;
            }
        }
    }
}

