using System;

using antlr.collections;

using net.esper.compat;
using net.esper.events;
using net.esper.filter;
using net.esper.support.bean;
using net.esper.support.eql.parse;
using net.esper.support.events;
using net.esper.util;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
	[TestFixture]
    public class TestASTFilterSpecHelper 
    {
        [Test]
        public virtual void testInvalid()
        {
            String classname = typeof(SupportBean).FullName;

            assertIsInvalid("goofy.mickey");
            assertIsInvalid(classname + "(dummy=4)");
            assertIsInvalid(classname + "(boolPrimitive=4)");
            assertIsInvalid(classname + "(intPrimitive=false)");
            assertIsInvalid(classname + "(str in [2:2])");
            assertIsInvalid(classname + "(str=\"a\", str=\"b\")"); // Same attribute twice should be a problem
        }

        [Test]
        public virtual void testValidNoParams()
        {
            String expression = "gum=" + typeof(SupportBean).FullName;

            FilterSpec spec = getFilterSpec(expression, null);
            Assert.AreEqual(typeof(SupportBean), spec.EventType.UnderlyingType);
            Assert.AreEqual(0, spec.Parameters.Count);

            Assert.AreEqual("gum", getEventNameTag(expression));
        }

        [Test]
        public virtual void testValidWithParams()
        {
            String expression = "name=" + typeof(SupportBean).FullName + "(intPrimitive>4, str=\"test\", doublePrimitive in [1:4])";

            FilterSpec spec = getFilterSpec(expression, null);
            Assert.AreEqual(typeof(SupportBean), spec.EventType.UnderlyingType);
            Assert.AreEqual(3, spec.Parameters.Count);

            FilterSpecParam param = spec.Parameters[0];
            Assert.AreEqual("intPrimitive", param.PropertyName);
            Assert.AreEqual(FilterOperator.GREATER, param.FilterOperator);
            Assert.AreEqual(4, param.GetFilterValue(null));

            param = spec.Parameters[1];
            Assert.AreEqual("str", param.PropertyName);
            Assert.AreEqual(FilterOperator.EQUAL, param.FilterOperator);
            Assert.AreEqual("test", param.GetFilterValue(null));

            param = spec.Parameters[2];
            Assert.AreEqual("doublePrimitive", param.PropertyName);
            Assert.AreEqual(FilterOperator.RANGE_CLOSED, param.FilterOperator);
            Assert.AreEqual(new DoubleRange(1, 4), param.GetFilterValue(null));

            Assert.AreEqual("name", getEventNameTag(expression));
        }

        [Test]
        public virtual void testValidUseResultParams()
        {
            String expression = "n1=" + typeof(SupportBean).FullName + "(intPrimitive=n2.intBoxed)";

            EDictionary<String, EventType> taggedEventTypes = new EHashDictionary<String, EventType>();
            taggedEventTypes.Put("n2", SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)));

            FilterSpec spec = getFilterSpec(expression, taggedEventTypes);

            Assert.AreEqual(typeof(SupportBean), spec.EventType.UnderlyingType);
            Assert.AreEqual(1, spec.Parameters.Count);
            FilterSpecParamEventProp eventPropParam = (FilterSpecParamEventProp)spec.Parameters[0];
            Assert.AreEqual("n2", eventPropParam.ResultEventAsName);
            Assert.AreEqual("intBoxed", eventPropParam.ResultEventProperty);
        }

        [Test]
        public virtual void testValidComplexProperty()
        {
            String expression = "n1=" + typeof(SupportBeanComplexProps).FullName + "(mapped('a') = '1')";
            FilterSpec spec = getFilterSpec(expression, null);

            Assert.AreEqual(1, spec.Parameters.Count);
            FilterSpecParamConstant param = (FilterSpecParamConstant)spec.Parameters[0];
            Assert.AreEqual("mapped('a')", param.PropertyName);
        }

        [Test]
        public virtual void testValidRange()
        {
            String expression = "myname=" + typeof(SupportBean).FullName + "(intPrimitive in (1:2), intBoxed in [2:6))";

            FilterSpec spec = getFilterSpec(expression, null);
            Assert.AreEqual(typeof(SupportBean), spec.EventType.UnderlyingType);
            Assert.AreEqual(2, spec.Parameters.Count);

            FilterSpecParam param = spec.Parameters[0];
            Assert.AreEqual("intPrimitive", param.PropertyName);
            Assert.AreEqual(FilterOperator.RANGE_OPEN, param.FilterOperator);

            param = spec.Parameters[1];
            Assert.AreEqual("intBoxed", param.PropertyName);
            Assert.AreEqual(FilterOperator.RANGE_HALF_OPEN, param.FilterOperator);

            Assert.AreEqual("myname", getEventNameTag(expression));
        }

        [Test]
        public virtual void testValidRangeUseResult()
        {
            String expression = "myname=" + typeof(SupportBean).FullName + "(intPrimitive in (asName.intPrimitive:asName.intBoxed))";

            EDictionary<String, EventType> taggedEventTypes = new EHashDictionary<String, EventType>();
            taggedEventTypes.Put("asName", SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)));

            FilterSpec spec = getFilterSpec(expression, taggedEventTypes);
            Assert.AreEqual(typeof(SupportBean), spec.EventType.UnderlyingType);
            Assert.AreEqual(1, spec.Parameters.Count);

            FilterSpecParam param = spec.Parameters[0];
            Assert.AreEqual("intPrimitive", param.PropertyName);
            Assert.AreEqual(FilterOperator.RANGE_OPEN, param.FilterOperator);
            Assert.AreEqual(typeof(DoubleRange), param.GetFilterValueClass(taggedEventTypes));
        }

        [Test]
        public virtual void testGetPropertyName()
        {
            String PROPERTY = "a('aa').b[1].c";

            // Should parse and result in the exact same property name
            AST propertyNameExprNode = SupportParserHelper.parseEventProperty(PROPERTY);
            String propertyName = ASTFilterSpecHelper.getPropertyName(propertyNameExprNode.getFirstChild());
            Assert.AreEqual(PROPERTY, propertyName);

            // Try AST with tokens separated, same property name
            propertyNameExprNode = SupportParserHelper.parseEventProperty("a(    'aa'   ). b [ 1 ] . c");
            propertyName = ASTFilterSpecHelper.getPropertyName(propertyNameExprNode.getFirstChild());
            Assert.AreEqual(PROPERTY, propertyName);
        }

        private void assertIsInvalid(String expression)
        {
            try
            {
                getFilterSpec(expression, null);
                Assert.IsTrue(false);
            }
            catch (System.Exception ex)
            {
                log.Debug("Caught expected exception, type= " + ex.GetType().FullName + "  msg=" + ex.Message);
            }
        }

        private FilterSpec getFilterSpec(string expressionText, EDictionary<String, EventType> taggedEventTypes)
        {
            AST filterNode = parse(expressionText);
            DebugFacility.DumpAST(filterNode);
            return ASTFilterSpecHelper.buildSpec(filterNode, taggedEventTypes, SupportEventAdapterService.Service);
        }

        private String getEventNameTag(String expressionText)
        {
            AST filterNode = parse(expressionText);
            return ASTFilterSpecHelper.getEventNameTag(filterNode);
        }

        private AST parse(String expression)
        {
            log.Debug(".getFilterSpec Parsing expression " + expression);

            AST filterNode = SupportParserHelper.parsePattern(expression);
            return filterNode;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(TestASTFilterSpecHelper));
    }
}
