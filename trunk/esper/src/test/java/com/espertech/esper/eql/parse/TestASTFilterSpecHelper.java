package com.espertech.esper.eql.parse;

import junit.framework.TestCase;
import com.espertech.esper.support.eql.parse.SupportParserHelper;
import com.espertech.esper.antlr.ASTUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.antlr.runtime.tree.Tree;

public class TestASTFilterSpecHelper extends TestCase
{
    public void testGetPropertyName() throws Exception
    {
        final String PROPERTY = "a('aa').b[1].c";

        // Should parse and result in the exact same property name
        Tree propertyNameExprNode = SupportParserHelper.parseEventProperty(PROPERTY);
        ASTUtil.dumpAST(propertyNameExprNode);
        String propertyName = ASTFilterSpecHelper.getPropertyName(propertyNameExprNode, 0);
        assertEquals(PROPERTY, propertyName);

        // Try AST with tokens separated, same property name
        propertyNameExprNode = SupportParserHelper.parseEventProperty("a(    'aa'   ). b [ 1 ] . c");
        propertyName = ASTFilterSpecHelper.getPropertyName(propertyNameExprNode, 0);
        assertEquals(PROPERTY, propertyName);
    }

    private static final Log log = LogFactory.getLog(TestASTFilterSpecHelper.class);
}
