package net.esper.event.xml;

import java.io.StringReader;

import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.esper.eql.generated.EQLStatementLexer;
import net.esper.eql.generated.EQLStatementParser;
import net.esper.eql.generated.EqlTokenTypes;
import net.esper.event.PropertyAccessException;
import net.esper.type.IntValue;
import net.esper.type.StringValue;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Parses event property names and transforms to XPath expressions. Supports
 * nested, indexed and mapped event properties.
 */
public class SimpleXMLPropertyParser implements EqlTokenTypes
{
    /**
     * Return the xPath corresponding to the given property.
     * The propertyName String may be simple, nested, indexed or mapped.
     *
     * @param propertyName is the property name to parse
     * @param xPathFactory for compiling an XPath expression
     * @param rootElementName is the name of the root element for generating the XPath expression
     * @return xpath expression
     * @throws XPathExpressionException
     */
    public static XPathExpression parse(String propertyName, XPathFactory xPathFactory, String rootElementName) throws XPathExpressionException
    {
        AST ast = parse(propertyName);

        StringBuffer xPathBuf = new StringBuffer();
        xPathBuf.append("/");
        xPathBuf.append(rootElementName);

        if (ast.getNumberOfChildren() == 1)
        {
            xPathBuf.append(makeProperty(ast.getFirstChild()));
        }
        else
        {
            AST child = ast.getFirstChild();
            do
            {
                xPathBuf.append(makeProperty(child));
                child = child.getNextSibling();
            }
            while (child != null);
        }

        String xPath = xPathBuf.toString();
        log.debug(".parse For property '" + propertyName + "' the xpath is '" + xPath + "'");

        return xPathFactory.newXPath().compile(xPath);
    }

    private static String makeProperty(AST child)
    {
        switch (child.getType())
        {
            case EVENT_PROP_SIMPLE:
                return "/" + child.getFirstChild().getText();
            case EVENT_PROP_MAPPED:
                String key = StringValue.parseString(child.getFirstChild().getNextSibling().getText());
                return "/" + child.getFirstChild().getText() + "[@id='" + key + "']";
            case EVENT_PROP_INDEXED:
                int index = IntValue.parseString(child.getFirstChild().getNextSibling().getText());
                return "/" + child.getFirstChild().getText() + "[position() = " + index + "]";
            default:
                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
        }
    }

    /**
     * Parses a given property name returning an AST.
     * @param propertyName to parse
     * @return AST syntax tree
     */
    protected static AST parse(String propertyName)
    {
        EQLStatementLexer lexer = new EQLStatementLexer(new StringReader(propertyName));
        EQLStatementParser parser = new EQLStatementParser(lexer);

        try
        {
            parser.startEventPropertyRule();
        }
        catch (TokenStreamException e)
        {
            throw new PropertyAccessException("Failed to parse property name '" + propertyName + "'", e);
        }
        catch (RecognitionException e)
        {
            throw new PropertyAccessException("Failed to parse property name '" + propertyName + "'", e);
        }

        return parser.getAST();
    }

    private static final Log log = LogFactory.getLog(SimpleXMLPropertyParser.class);
}
