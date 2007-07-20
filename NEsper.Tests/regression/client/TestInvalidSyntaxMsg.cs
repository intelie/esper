// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.eql.parse;

namespace net.esper.regression.client
{
	[TestFixture]
	public class TestInvalidSyntaxMsg
	{
	    private EPServiceProvider epService;

	    [SetUp]
	    public void SetUp()
	    {
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	    }

	    [Test]
	    public void testSyntaxError()
	    {
	        // This could be a better exception - outlining viable alternatives in ANTLR 2.7.5 cannot be done
	        // Use ANTLR version 3 once out.
	        TryCompile("select * from pattern[A -> B - C]",
	                   "unexpected token: B near line 1, column 28 [select * from pattern[A -> B - C]]");

	        TryCompile("insert into A (a",
	                   "end of input when expecting a closing parenthesis ')' near line 1, column 17 [insert into A (a]");

	        TryCompile("select case when 1>2 from A",
	                   "expecting \"then\", found 'from' near line 1, column 22 [select case when 1>2 from A]");

	        TryCompile("select * from A full outer join B on A.field < B.field",
	                   "expecting an equals '=', found '<' near line 1, column 46 [select * from A full outer join B on A.field < B.field]");

	        TryCompile("select a.b('aa\") from A",
	                   "end of input when expecting a singe quote \"'\" near line 1, column 24 [select a.b('aa\") from A]");

	        TryCompile("select a.b('aa\") from A",
	                   "end of input when expecting a singe quote \"'\" near line 1, column 24 [select a.b('aa\") from A]");

	        TryCompile("select * from A, sql:mydb [\"",
	                   "end of input when expecting '\"' near line 1, column 29 [select * from A, sql:mydb [\"]");

	        TryCompile("select Prior(A, x) from A",
	                   "expecting a numeric literal, found 'A' near line 1, column 14 [select Prior(A, x) from A]");

	        TryCompile("select * from A, into",
	                   "unexpected token: into near line 1, column 18 [select * from A, into]");

	        TryCompile("select * from pattern [",
	                   "end of input near line 1, column 24 [select * from pattern []");

	        TryCompile("select * google",
	                   "expecting \"from\", found 'google' near line 1, column 10 [select * google]");

	        TryCompile("insert into into",
	                   "expecting an identifier, found 'into' near line 1, column 13 [insert into into]");
	    }

	    private void TryCompile(String expression, String expectedMsg)
	    {
	        try
	        {
	            epService.EPAdministrator.CreateEQL(expression);
	            Assert.Fail();
	        }
	        catch (EPStatementSyntaxException ex)
	        {
	            Assert.AreEqual(expectedMsg, ex.Message);
	            // expected
	        }
	    }

	}
} // End of namespace
