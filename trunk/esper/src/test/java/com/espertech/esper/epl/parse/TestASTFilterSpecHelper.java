package com.espertech.esper.epl.parse;

import junit.framework.TestCase;
import com.espertech.esper.support.epl.parse.SupportParserHelper;
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

    public void testGetPropertyNameEscaped() throws Exception
    {
        final String PROPERTY = "a('aa')\\.b[1]\\.c";
        Tree propertyNameExprNode = SupportParserHelper.parseEventProperty(PROPERTY);
        ASTUtil.dumpAST(propertyNameExprNode);
        String propertyName = ASTFilterSpecHelper.getPropertyName(propertyNameExprNode, 0);
        assertEquals(PROPERTY, propertyName);
    }

    public void testEscapeDot() throws Exception
    {
        String [][] inout = new String[][] {
                {"a", "a"},
                {"", ""},
                {" ", " "},
                {".", "\\."},
                {".", "\\."},
                {"a.", "a\\."},
                {".a", "\\.a"},
                {"a.b", "a\\.b"},
                {"a..b", "a\\.\\.b"},
                {"a\\.b", "a\\.b"},
                {"a\\..b", "a\\.\\.b"},
                {"a.\\..b", "a\\.\\.\\.b"},
                {"a.b.c", "a\\.b\\.c"}
        };

        for (int i = 0; i < inout.length; i++)
        {
            String in = inout[i][0];
            String expected = inout[i][1];
            assertEquals("for input " + in, expected, ASTFilterSpecHelper.escapeDot(in));
        }
    }


    private static final Log log = LogFactory.getLog(TestASTFilterSpecHelper.class);
}
