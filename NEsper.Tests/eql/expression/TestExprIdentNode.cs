///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.compat;
using net.esper.eql.core;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using org.apache.commons.logging;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprIdentNode
	{
		private ExprIdentNode[] identNodes;
	    private StreamTypeService streamTypeService;

	    [SetUp]
	    public void SetUp()
	    {
	        identNodes = new ExprIdentNode[4];
	        identNodes[0] = new ExprIdentNode("mapped('a')");
	        identNodes[1] = new ExprIdentNode("nestedValue", "nested");
	        identNodes[2] = new ExprIdentNode("indexed[1]", "s2");
	        identNodes[3] = new ExprIdentNode("intPrimitive", "s0");

	        streamTypeService = new SupportStreamTypeSvc3Stream();
	    }

	    [Test]
	    public void testValidateInvalid()
	    {
	        try
	        {
	            int voidId = identNodes[0].StreamId;
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }

	        try
	        {
	            Type voidType = identNodes[0].ReturnType;
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }

	        try
	        {
                string voidData = identNodes[0].ResolvedStreamName;
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }

	        try
	        {
                string voidData = identNodes[0].ResolvedPropertyName;
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testValidate()
	    {
	        identNodes[0].Validate(streamTypeService, null, null);
	        Assert.AreEqual(2, identNodes[0].StreamId);
	        Assert.AreEqual(typeof(String), identNodes[0].ReturnType);
	        Assert.AreEqual("mapped('a')", identNodes[0].ResolvedPropertyName);

	        identNodes[1].Validate(streamTypeService, null, null);
	        Assert.AreEqual(2, identNodes[1].StreamId);
	        Assert.AreEqual(typeof(String), identNodes[1].ReturnType);
	        Assert.AreEqual("nested.nestedValue", identNodes[1].ResolvedPropertyName);

	        identNodes[2].Validate(streamTypeService, null, null);
	        Assert.AreEqual(2, identNodes[2].StreamId);
	        Assert.AreEqual(typeof(int?), identNodes[2].ReturnType);
	        Assert.AreEqual("indexed[1]", identNodes[2].ResolvedPropertyName);

	        identNodes[3].Validate(streamTypeService, null, null);
	        Assert.AreEqual(0, identNodes[3].StreamId);
	        Assert.AreEqual(typeof(int?), identNodes[3].ReturnType);
	        Assert.AreEqual("intPrimitive", identNodes[3].ResolvedPropertyName);

	        TryInvalidValidate(new ExprIdentNode(""));
	        TryInvalidValidate(new ExprIdentNode("dummy"));
	        TryInvalidValidate(new ExprIdentNode("nested", "s0"));
	        TryInvalidValidate(new ExprIdentNode("dummy", "s2"));
	        TryInvalidValidate(new ExprIdentNode("intPrimitive", "s2"));
	        TryInvalidValidate(new ExprIdentNode("intPrimitive", "s3"));
	    }

	    [Test]
	    public void testGetType()
	    {
	        // test success
	        identNodes[0].Validate(streamTypeService, null, null);
	        Assert.AreEqual(typeof(String), identNodes[0].ReturnType);
	    }

	    [Test]
	    public void testEvaluate()
	    {
	        EventBean[] events = new EventBean[] {MakeEvent(10)};

	        identNodes[3].Validate(streamTypeService, null, null);
	        Assert.AreEqual(10, identNodes[3].Evaluate(events, false));
	        Assert.IsNull(identNodes[3].Evaluate(new EventBean[2], false));
	    }

	    [Test]
	    public void testEvaluatePerformance()
	    {
	        // test performance of evaluate for indexed events
	        // fails if the getter is not in place

	        EventBean[] events = SupportStreamTypeSvc3Stream.SampleEvents;
	        identNodes[2].Validate(streamTypeService, null, null);

	        long startTime = DateTimeHelper.CurrentTimeMillis;
	        for (int i = 0; i < 100000; i++)
	        {
	            identNodes[2].Evaluate(events, false);
	        }
	        long endTime = DateTimeHelper.CurrentTimeMillis;
	        long delta = endTime - startTime;
	        log.Info(".testEvaluate delta=" + delta);
	        Assert.IsTrue(delta < 500);
	    }

	    [Test]
	    public void testToExpressionString()
	    {
	        for (int i = 0; i < identNodes.Length; i++)
	        {
	            identNodes[i].Validate(streamTypeService, null, null);
	        }
	        Assert.AreEqual("mapped('a')", identNodes[0].ExpressionString);
	        Assert.AreEqual("nested.nestedValue", identNodes[1].ExpressionString);
	        Assert.AreEqual("s2.indexed[1]", identNodes[2].ExpressionString);
	        Assert.AreEqual("s0.intPrimitive", identNodes[3].ExpressionString);
	    }

	    [Test]
	    public void testEqualsNode()
	    {
	        identNodes[0].Validate(streamTypeService, null, null);
	        identNodes[2].Validate(streamTypeService, null, null);
	        identNodes[3].Validate(streamTypeService, null, null);
	        Assert.IsTrue(identNodes[3].EqualsNode(identNodes[3]));
	        Assert.IsFalse(identNodes[0].EqualsNode(identNodes[2]));
	    }

	    private EventBean MakeEvent(int intPrimitive)
	    {
	        SupportBean _event = new SupportBean();
	        _event.SetIntPrimitive(intPrimitive);
	        return SupportEventBeanFactory.CreateObject(_event);
	    }

	    private void TryInvalidValidate(ExprIdentNode identNode)
	    {
	        try
	        {
	            identNode.Validate(streamTypeService, null, null);
	            Assert.Fail();
	        }
	        catch(ExprValidationException ex)
	        {
	            // expected
	        }
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
