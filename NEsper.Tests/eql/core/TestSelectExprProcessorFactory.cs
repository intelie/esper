using System;
using System.Collections.Generic;

using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	[TestFixture]
    public class TestSelectExprProcessorFactory 
    {
        [Test]
        public virtual void testGetProcessorInvalid()
        {
            IList<SelectExprElementNamedSpec> selectionList = new List<SelectExprElementNamedSpec>();
            ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
            ExprNode mathNode = SupportExprNodeFactory.makeMathNode();
            selectionList.Add(new SelectExprElementNamedSpec(identNode, "result"));
            selectionList.Add(new SelectExprElementNamedSpec(mathNode, "result"));

            try
            {
                SelectExprProcessorFactory.getProcessor(selectionList, null, new SupportStreamTypeSvc3Stream(), null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testGetProcessorWildcard()
        {
            IList<SelectExprElementNamedSpec> selectionList = new List<SelectExprElementNamedSpec>();
            SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, null, new SupportStreamTypeSvc3Stream(), SupportEventAdapterService.Service);
            Assert.IsTrue(processor is SelectExprJoinWildcardProcessor);
        }

        [Test]
        public virtual void testGetProcessorValid()
        {
            IList<SelectExprElementNamedSpec> selectionList = new List<SelectExprElementNamedSpec>();
            ExprNode identNode = SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0");
            selectionList.Add(new SelectExprElementNamedSpec(identNode, "result"));
            SelectExprProcessor processor = SelectExprProcessorFactory.getProcessor(selectionList, null, new SupportStreamTypeSvc3Stream(), SupportEventAdapterService.Service);
            Assert.IsTrue(processor != null);
        }

        [Test]
        public virtual void testVerifyNameUniqueness()
        {
            // try valid case
            IList<SelectExprElementNamedSpec> elements = new List<SelectExprElementNamedSpec>();
            elements.Add(new SelectExprElementNamedSpec(null, "xx"));
            elements.Add(new SelectExprElementNamedSpec(null, "yy"));

            SelectExprProcessorFactory.verifyNameUniqueness(elements);

            // try invalid case
            elements.Add(new SelectExprElementNamedSpec(null, "yy"));
            try
            {
                SelectExprProcessorFactory.verifyNameUniqueness(elements);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // expected
            }
        }
    }
}
