// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.support.view;
using net.esper.view;

namespace net.esper.view.std
{
	[TestFixture]
	public class TestUniqueByPropertyViewFactory
	{
	    private UniqueByPropertyViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new UniqueByPropertyViewFactory();
	    }

	    [Test]
	    public void testSetParameters()
	    {
	        TryParameter("price", "price");
	        TryInvalidParameter(1.1d);
	    }

	    [Test]
	    public void testCanReuse()
	    {
	        factory.SetViewParameters(null, new Object[] {"a"});
	        Assert.IsFalse(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	        Assert.IsTrue(factory.CanReuse(new UniqueByPropertyView("a")));
	        Assert.IsFalse(factory.CanReuse(new UniqueByPropertyView("c")));
	    }

	    private void TryInvalidParameter(Object param)
	    {
	        try
	        {
	            UniqueByPropertyViewFactory factory = new UniqueByPropertyViewFactory();
	            factory.SetViewParameters(null, new Object[] {param});
	            Assert.Fail();
	        }
	        catch (ViewParameterException ex)
	        {
	            // expected
	        }
	    }

	    private void TryParameter(Object param, String fieldName)
	    {
	        UniqueByPropertyViewFactory factory = new UniqueByPropertyViewFactory();
	        factory.SetViewParameters(null, new Object[] {param});
	        UniqueByPropertyView view = (UniqueByPropertyView) factory.MakeView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(fieldName, view.UniqueFieldName);
	    }
	}
} // End of namespace
