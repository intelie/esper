package net.esper.event.xml;

import net.esper.antlr.NoCaseSensitiveStream;
import net.esper.eql.generated.EsperEPLLexer;
import net.esper.eql.generated.EsperEPLParser;
import net.esper.event.PropertyAccessException;
import net.esper.type.IntValue;
import net.esper.type.StringValue;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Parses event property names and transforms to XPath expressions. Supports
 * nested, indexed and mapped event properties.
 */
public class SimpleXMLPropertyParser
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
        Tree ast = parse(propertyName);

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

        if (ast.getChildCount() == 1)
        {
            xPathBuf.append(makeProperty(ast.getChild(0), defaultNamespacePrefix));
        }
        else
        {
            for (int i = 0; i < ast.getChildCount(); i++)
            {
                xPathBuf.append(makeProperty(ast.getChild(i), defaultNamespacePrefix));
            }
        }

        String xPath = xPathBuf.toString();
        if (log.isDebugEnabled())
        {
            log.debug(".parse For property '" + propertyName + "' the xpath is '" + xPath + '\'');
        }

        return xPath;
    }

    private static String makeProperty(Tree child, String defaultNamespacePrefix)
    {
        String prefix = "";
        if (defaultNamespacePrefix != null)
        {
            prefix = defaultNamespacePrefix + ":";
        }

        switch (child.getType())
        {
            case EsperEPLParser.EVENT_PROP_DYNAMIC_SIMPLE:
            case EsperEPLParser.EVENT_PROP_SIMPLE:
                return '/' + prefix + child.getChild(0).getText();
            case EsperEPLParser.EVENT_PROP_DYNAMIC_MAPPED:
            case EsperEPLParser.EVENT_PROP_MAPPED:
                String key = StringValue.parseString(child.getChild(1).getText());
                return '/' + prefix + child.getChild(0).getText() + "[@id='" + key + "']";
            case EsperEPLParser.EVENT_PROP_DYNAMIC_INDEXED:
            case EsperEPLParser.EVENT_PROP_INDEXED:
                int index = IntValue.parseString(child.getChild(1).getText());
                return '/' + prefix + child.getChild(0).getText() + "[position() = " + index + ']';
            default:
                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
        }
    }

    /**
     * Parses a given property name returning an AST.
     * @param propertyName to parse
     * @return AST syntax tree
     */
    protected static Tree parse(String propertyName)
    {
        CharStream input;
        try
        {
            input = new NoCaseSensitiveStream(new StringReader(propertyName));
        }
        catch (IOException ex)
        {
            throw new PropertyAccessException("IOException parsing property name '" + propertyName + '\'', ex);
        }

        EsperEPLLexer lex = new EsperEPLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        EsperEPLParser g = new EsperEPLParser(tokens);
        EsperEPLParser.startEventPropertyRule_return r;
        
        try
        {
             r = g.startEventPropertyRule();
        }
        catch (RecognitionException e)
        {
            throw new PropertyAccessException("Failed to parse property name '" + propertyName + '\'', e);
        }

        return (Tree) r.getTree();
    }

    private static final Log log = LogFactory.getLog(SimpleXMLPropertyParser.class);
}
