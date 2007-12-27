package net.esper.event.xml;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import net.esper.eql.generated.EQLStatementLexer;
import net.esper.eql.generated.EQLStatementParser;
import net.esper.eql.generated.EqlTokenTypes;
import net.esper.event.PropertyAccessException;
import net.esper.type.IntValue;
import net.esper.type.StringValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringReader;

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
     * @param rootElementName is the name of the root element for generating the XPath expression
     * @param defaultNamespacePrefix is the prefix of the default namespace
     * @param isResolvePropertiesAbsolute is true to indicate to resolve XPath properties as absolute props
     * or relative props
     * @return xpath expression
     */
    public static String parse(String propertyName, String rootElementName, String defaultNamespacePrefix, boolean isResolvePropertiesAbsolute)
    {
        AST ast = parse(propertyName);

        StringBuilder xPathBuf = new StringBuilder();
        xPathBuf.append('/');
        if (isResolvePropertiesAbsolute)
        {
            if (defaultNamespacePrefix != null)
            {
                xPathBuf.append(defaultNamespacePrefix);
                xPathBuf.append(':');
            }
            xPathBuf.append(rootElementName);
        }

        if (ast.getNumberOfChildren() == 1)
        {
            xPathBuf.append(makeProperty(ast.getFirstChild(), defaultNamespacePrefix));
        }
        else
        {
            AST child = ast.getFirstChild();
            do
            {
                xPathBuf.append(makeProperty(child, defaultNamespacePrefix));
                child = child.getNextSibling();
            }
            while (child != null);
        }

        String xPath = xPathBuf.toString();
        if (log.isDebugEnabled())
        {
            log.debug(".parse For property '" + propertyName + "' the xpath is '" + xPath + '\'');
        }

        return xPath;
    }

    private static String makeProperty(AST child, String defaultNamespacePrefix)
    {
        String prefix = "";
        if (defaultNamespacePrefix != null)
        {
            prefix = defaultNamespacePrefix + ":";
        }

        switch (child.getType())
        {
            case EVENT_PROP_DYNAMIC_SIMPLE:
            case EVENT_PROP_SIMPLE:
                return '/' + prefix + child.getFirstChild().getText();
            case EVENT_PROP_DYNAMIC_MAPPED:
            case EVENT_PROP_MAPPED:
                String key = StringValue.parseString(child.getFirstChild().getNextSibling().getText());
                return '/' + prefix + child.getFirstChild().getText() + "[@id='" + key + "']";
            case EVENT_PROP_DYNAMIC_INDEXED:
            case EVENT_PROP_INDEXED:
                int index = IntValue.parseString(child.getFirstChild().getNextSibling().getText());
                return '/' + prefix + child.getFirstChild().getText() + "[position() = " + index + ']';
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
            throw new PropertyAccessException("Failed to parse property name '" + propertyName + '\'', e);
        }
        catch (RecognitionException e)
        {
            throw new PropertyAccessException("Failed to parse property name '" + propertyName + '\'', e);
        }

        return parser.getAST();
    }

    private static final Log log = LogFactory.getLog(SimpleXMLPropertyParser.class);
}
