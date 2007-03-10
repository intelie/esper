package net.esper.eql.parse;

import antlr.collections.AST;
import junit.framework.TestCase;
import net.esper.support.eql.parse.SupportParserHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestASTFilterSpecHelper extends TestCase
{
    public void testGetPropertyName() throws Exception
    {
        final String PROPERTY = "a('aa').b[1].c";

        // Should parse and result in the exact same property name
        AST propertyNameExprNode = SupportParserHelper.parseEventProperty(PROPERTY);
        String propertyName = ASTFilterSpecHelper.getPropertyName(propertyNameExprNode.getFirstChild());
        assertEquals(PROPERTY, propertyName);

        // Try AST with tokens separated, same property name
        propertyNameExprNode = SupportParserHelper.parseEventProperty("a(    'aa'   ). b [ 1 ] . c");
        propertyName = ASTFilterSpecHelper.getPropertyName(propertyNameExprNode.getFirstChild());
        assertEquals(PROPERTY, propertyName);
    }

    private static final Log log = LogFactory.getLog(TestASTFilterSpecHelper.class);
}
