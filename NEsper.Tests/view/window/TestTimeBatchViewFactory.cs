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
	public class TestTimeBatchViewFactory
	{
	    private TimeBatchViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new TimeBatchViewFactory();
	    }

        [Test]
        public void testSetParameters()
        {
            TryParameter(new Object[] { new TimePeriodParameter(2d) }, 2000, null);
            TryParameter(new Object[] { 4 }, 4000, null);
            TryParameter(new Object[] { 3.3d }, 3300, null);
            TryParameter(new Object[] { 1.1f }, 1100, null);
            TryParameter(new Object[] { 99.9d, 364466464L }, 99900, 364466464L);

            TryInvalidParameter("price");
            TryInvalidParameter(true);
            TryInvalidParameter(0);
        }

	    [Test]
	    public void testCanReuse()
	    {
	        factory.SetViewParameters(null, new Object[] {1000});
	        Assert.IsFalse(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	        Assert.IsFalse(factory.CanReuse(new TimeBatchView(factory, SupportStatementContextFactory.MakeContext(), 1, null, null)));
	        Assert.IsTrue(factory.CanReuse(new TimeBatchView(factory, SupportStatementContextFactory.MakeContext(), 1000000, null, null)));

	        factory.SetViewParameters(null, new Object[] {1000, 2000L});
	        Assert.IsFalse(factory.CanReuse(new TimeBatchView(factory, SupportStatementContextFactory.MakeContext(), 1, null, null)));
	        Assert.IsTrue(factory.CanReuse(new TimeBatchView(factory, SupportStatementContextFactory.MakeContext(), 1000000, 2000L, null)));
	    }

	    private void TryInvalidParameter(Object param)
	    {
	        try
	        {
	            TimeBatchViewFactory factory = new TimeBatchViewFactory();
	            factory.SetViewParameters(null, new Object[] {param});
	            Assert.Fail();
	        }
	        catch (ViewParameterException ex)
	        {
	            // expected
	        }
	    }

	    private void TryParameter(Object[] param, long msec, long? referencePoint)
	    {
	        TimeBatchViewFactory factory = new TimeBatchViewFactory();
	        factory.SetViewParameters(null, param);
	        TimeBatchView view = (TimeBatchView) factory.MakeView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(msec, view.MsecIntervalSize);
	        Assert.AreEqual(referencePoint, view.InitialReferencePoint);
	    }
	}
} // End of namespace
