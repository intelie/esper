///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.eql.parse;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.eql
{
	[TestFixture]
	public class TestInvalidEQL
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;

	    [SetUp]
	    public void SetUp()
	    {
            PropertyResolutionStyleHelper.DefaultPropertyResolutionStyle = PropertyResolutionStyle.CASE_INSENSITIVE;
            
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        listener = new SupportUpdateListener();
	    }

	    [Test]
	    public void testSyntaxException()
	    {
	        String exceptionText = GetSyntaxExceptionEQL("select * from *");
	        Assert.AreEqual("unexpected token: * near line 1, column 15 [select * from *]", exceptionText);
	    }

	    [Test]
	    public void testLongTypeConstant()
	    {
	        String stmtText = "select 2512570244 as value from " + typeof(SupportBean).FullName;
	        EPStatement stmt = epService.EPAdministrator.CreateEQL(stmtText);
	        stmt.AddListener(listener);

	        epService.EPRuntime.SendEvent(new SupportBean());
	        Assert.AreEqual(2512570244L, listener.AssertOneGetNewAndReset()["value"]);
	    }

	    [Test]
	    public void testDifferentJoins()
	    {
	        String streamDef = "select * from " +
	                typeof(SupportBean).FullName + ".win:length(3) as sa," +
	                typeof(SupportBean).FullName + ".win:length(3) as sb" +
	                            " where ";

	        String streamDefTwo = "select * from " +
	                typeof(SupportBean).FullName + ".win:length(3)," +
	                typeof(SupportMarketDataBean).FullName + ".win:length(3)" +
	                            " where ";

	        TryInvalid(streamDef + "sa.intPrimitive = sb.string");
	        TryValid(streamDef + "sa.intPrimitive = sb.intBoxed");
	        TryValid(streamDef + "sa.intPrimitive = sb.intPrimitive");
	        TryValid(streamDef + "sa.intPrimitive = sb.longBoxed");

	        TryInvalid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.intBoxed = sa.boolPrimitive");
	        TryValid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.boolBoxed = sa.boolPrimitive");

	        TryInvalid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.intBoxed = sa.intPrimitive and sa.string=sX.string");
	        TryValid(streamDef + "sa.intPrimitive = sb.intPrimitive and sb.intBoxed = sa.intPrimitive and sa.string=sb.string");

	        TryInvalid(streamDef + "sa.intPrimitive = sb.intPrimitive or sa.string=sX.string");
	        TryValid(streamDef + "sa.intPrimitive = sb.intPrimitive or sb.intBoxed = sa.intPrimitive");

	        // try constants
	        TryValid(streamDef + "sa.intPrimitive=5");
	        TryValid(streamDef + "sa.string='4'");
	        TryValid(streamDef + "sa.string=\"4\"");
	        TryValid(streamDef + "sa.boolPrimitive=false");
	        TryValid(streamDef + "sa.longPrimitive=-5L");
	        TryValid(streamDef + "sa.doubleBoxed=5.6d");
	        TryValid(streamDef + "sa.floatPrimitive=-5.6f");

	        TryInvalid(streamDef + "sa.intPrimitive='5'");
	        TryInvalid(streamDef + "sa.string=5");
	        TryInvalid(streamDef + "sa.boolBoxed=f");
	        TryInvalid(streamDef + "sa.intPrimitive=x");
	        TryValid(streamDef + "sa.intPrimitive=5.5");

	        // try addition and subtraction
	        TryValid(streamDef + "sa.intPrimitive=sa.intBoxed + 5");
	        TryValid(streamDef + "sa.intPrimitive=2*sa.intBoxed - sa.intPrimitive/10 + 1");
	        TryValid(streamDef + "sa.intPrimitive=2*(sa.intBoxed - sa.intPrimitive)/(10 + 1)");
	        TryInvalid(streamDef + "sa.intPrimitive=2*(sa.intBoxed");

	        // try comparison
	        TryValid(streamDef + "sa.intPrimitive > sa.intBoxed and sb.doublePrimitive < sb.doubleBoxed");
	        TryValid(streamDef + "sa.intPrimitive >= sa.intBoxed and sa.doublePrimitive <= sa.doubleBoxed");
	        TryValid(streamDef + "sa.intPrimitive > (sa.intBoxed + sb.doublePrimitive)");
	        TryInvalid(streamDef + "sa.intPrimitive >= sa.string");
	        TryInvalid(streamDef + "sa.boolBoxed >= sa.boolPrimitive");

	        // Try some nested
	        TryValid(streamDef + "(sa.intPrimitive=3) or (sa.intBoxed=3 and sa.intPrimitive=1)");
	        TryValid(streamDef + "((sa.intPrimitive>3) or (sa.intBoxed<3)) and sa.boolBoxed=false");
	        TryValid(streamDef + "(sa.intPrimitive<=3 and sa.intPrimitive>=1) or (sa.boolBoxed=false and sa.boolPrimitive=true)");
	        TryInvalid(streamDef + "sa.intPrimitive=3 or (sa.intBoxed=2");
	        TryInvalid(streamDef + "sa.intPrimitive=3 or sa.intBoxed=2)");
	        TryInvalid(streamDef + "sa.intPrimitive=3 or ((sa.intBoxed=2)");

	        // Try some without stream name
	        TryInvalid(streamDef + "intPrimitive=3");
	        TryValid(streamDefTwo + "intPrimitive=3");

	        // Try invalid outer join criteria
	        String outerJoinDef = "select * from " +
	                typeof(SupportBean).FullName + ".win:length(3) as sa " +
	                "left outer join " +
	                typeof(SupportBean).FullName + ".win:length(3) as sb ";
	        TryInvalid(outerJoinDef + "");
	        TryValid(outerJoinDef + "on sa.intPrimitive = sb.intBoxed");
	        TryInvalid(outerJoinDef + "on sa.intPrimitive = sb.XX");
	        TryInvalid(outerJoinDef + "on sa.XX = sb.XX");
	        TryInvalid(outerJoinDef + "on sa.XX = sb.intBoxed");
	        TryInvalid(outerJoinDef + "on sa.boolBoxed = sb.intBoxed");
	        TryValid(outerJoinDef + "on sa.boolPrimitive = sb.boolBoxed");
	        TryInvalid(outerJoinDef + "on sa.boolPrimitive = sb.string");
	        TryInvalid(outerJoinDef + "on sa.intPrimitive <= sb.intBoxed");
	        TryInvalid(outerJoinDef + "on sa.intPrimitive = sa.intBoxed");
	        TryInvalid(outerJoinDef + "on sb.intPrimitive = sb.intBoxed");
	        TryValid(outerJoinDef + "on sb.intPrimitive = sa.intBoxed");
	    }

	    private void TryInvalid(String eqlInvalidEQL)
	    {
	        try
	        {
	            epService.EPAdministrator.CreateEQL(eqlInvalidEQL);
	            Assert.Fail();
	        }
	        catch (EPException ex)
	        {
	            // Expected exception
	        }
	    }

	    private void TryValid(String eqlInvalidEQL)
	    {
	        epService.EPAdministrator.CreateEQL(eqlInvalidEQL);
	    }

	    private String GetSyntaxExceptionEQL(String expression)
	    {
	        String exceptionText = null;
	        try
	        {
	            epService.EPAdministrator.CreateEQL(expression);
	            Assert.Fail();
	        }
	        catch (EPStatementSyntaxException ex)
	        {
	            exceptionText = ex.Message;
	            log.Debug(".getSyntaxExceptionEQL eql=" + expression, ex);
	            // Expected exception
	        }

	        return exceptionText;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
