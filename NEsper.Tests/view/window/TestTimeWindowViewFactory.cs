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

using net.esper.eql.parse;
using net.esper.support.view;
using net.esper.view.std;
using net.esper.view;

namespace net.esper.view.window
{
	[TestFixture]
	public class TestTimeWindowViewFactory
	{
	    private TimeWindowViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new TimeWindowViewFactory();
	    }

        [Test]
        public void testSetParameters()
        {
            TryParameter(new TimePeriodParameter(2d), 2000);
            TryParameter(4, 4000);
            TryParameter(3.3d, 3300);
            TryParameter(1.1f, 1100);

            TryInvalidParameter("price");
            TryInvalidParameter(true);
        }

	    [Test]
	    public void testCanReuse()
	    {
	        factory.SetViewParameters(null, new Object[] {1000});
	        Assert.IsFalse(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	        Assert.IsFalse(factory.CanReuse(new TimeBatchView(null, SupportStatementContextFactory.MakeContext(), 1000, null, null)));
	        Assert.IsTrue(factory.CanReuse(new TimeWindowView(SupportStatementContextFactory.MakeContext(), factory, 1000000, null)));
	    }

	    private void TryInvalidParameter(Object param)
	    {
	        try
	        {
	            TimeWindowViewFactory factory = new TimeWindowViewFactory();
	            factory.SetViewParameters(null, new Object[] {param});
	            Assert.Fail();
	        }
	        catch (ViewParameterException ex)
	        {
	            // expected
	        }
	    }

	    private void TryParameter(Object param, long msec)
	    {
	        TimeWindowViewFactory factory = new TimeWindowViewFactory();
	        factory.SetViewParameters(null, new Object[] {param});
	        TimeWindowView view = (TimeWindowView) factory.MakeView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(msec, view.MillisecondsBeforeExpiry);
	    }
	}
} // End of namespace
