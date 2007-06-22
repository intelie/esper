///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.support.eql;
using net.esper.support.events;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestSelectExprProcessorFactory
	{
	    [Test]
	    public void TestGetProcessorInvalid()
	    {
	        IList<SelectExprElementCompiledSpec> selectionList = new List<SelectExprElementCompiledSpec>();
	        ExprNode identNode = SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0");
	        ExprNode mathNode = SupportExprNodeFactory.MakeMathNode();
	        selectionList.Add(new SelectExprElementCompiledSpec(identNode, "result"));
	        selectionList.Add(new SelectExprElementCompiledSpec(mathNode, "result"));

	        try
	        {
	            SelectExprProcessorFactory.GetProcessor(selectionList, false, null,
	                    new SupportStreamTypeSvc3Stream(), null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void TestGetProcessorWildcard()
	    {
	        IList<SelectExprElementCompiledSpec> selectionList = new List<SelectExprElementCompiledSpec>();
	        SelectExprProcessor processor = SelectExprProcessorFactory.GetProcessor(selectionList, false, null,
	                new SupportStreamTypeSvc3Stream(), SupportEventAdapterService.GetService());
	        Assert.IsTrue(processor is SelectExprJoinWildcardProcessor);
	    }

	    [Test]
	    public void TestGetProcessorValid()
	    {
	        IList<SelectExprElementCompiledSpec> selectionList = new List<SelectExprElementCompiledSpec>();
	        ExprNode identNode = SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0");
	        selectionList.Add(new SelectExprElementCompiledSpec(identNode, "result"));
	        SelectExprProcessor processor = SelectExprProcessorFactory.GetProcessor(selectionList, false, null,
	                new SupportStreamTypeSvc3Stream(), SupportEventAdapterService.GetService());
	        Assert.IsTrue(processor != null);
	    }

	    [Test]
	    public void TestVerifyNameUniqueness()
	    {
	        // try valid case
	        IList<SelectExprElementCompiledSpec> elements = new List<SelectExprElementCompiledSpec>();
	        elements.Add(new SelectExprElementCompiledSpec(null, "xx"));
	        elements.Add(new SelectExprElementCompiledSpec(null, "yy"));

	        SelectExprProcessorFactory.VerifyNameUniqueness(elements);

	        // try invalid case
	        elements.Add(new SelectExprElementCompiledSpec(null, "yy"));
	        try
	        {
	            SelectExprProcessorFactory.VerifyNameUniqueness(elements);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
