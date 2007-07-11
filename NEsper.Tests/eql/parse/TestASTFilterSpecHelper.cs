///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using antlr.collections;

using NUnit.Framework;

using net.esper.support.eql.parse;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
	[TestFixture]
	public class TestASTFilterSpecHelper
	{
	    [Test]
	    public void testGetPropertyName()
	    {
	        String PROPERTY = "a('aa').b[1].c";

	        // Should parse and result in the exact same property name
	        AST propertyNameExprNode = SupportParserHelper.ParseEventProperty(PROPERTY);
	        String propertyName = ASTFilterSpecHelper.GetPropertyName(propertyNameExprNode.getFirstChild());
	        Assert.AreEqual(PROPERTY, propertyName);

	        // Try AST with tokens separated, same property name
	        propertyNameExprNode = SupportParserHelper.ParseEventProperty("a(    'aa'   ). b [ 1 ] . c");
	        propertyName = ASTFilterSpecHelper.GetPropertyName(propertyNameExprNode.getFirstChild());
	        Assert.AreEqual(PROPERTY, propertyName);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
