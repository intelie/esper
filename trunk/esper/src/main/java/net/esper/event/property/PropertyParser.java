package net.esper.event.property;

import net.esper.antlr.NoCaseSensitiveStream;
import net.esper.antlr.ASTUtil;
import net.esper.event.BeanEventTypeFactory;
import net.esper.event.PropertyAccessException;
import net.esper.type.IntValue;
import net.esper.type.StringValue;
import net.esper.eql.generated.EsperEPL2GrammarLexer;
import net.esper.eql.generated.EsperEPL2GrammarParser;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Parser for property names that can be simple, nested, mapped or a combination of these.
 * Uses ANTLR parser to parse.
 */
public class PropertyParser
{
    private static final Log log = LogFactory.getLog(PropertyParser.class);

    /**
     * Parse the given property name returning a Property instance for the property.
     * @param propertyName is the property name to parse
     * @param beanEventTypeFactory is the chache and factory for event bean types and event wrappers
     * @return Property instance for property
     */
    public static Property parse(String propertyName, BeanEventTypeFactory beanEventTypeFactory)
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

        EsperEPL2GrammarLexer lex = new EsperEPL2GrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        EsperEPL2GrammarParser g = new EsperEPL2GrammarParser(tokens);
        EsperEPL2GrammarParser.startEventPropertyRule_return r;

        try
        {
             r = g.startEventPropertyRule();
        }
        catch (RecognitionException e)
        {
            throw new PropertyAccessException("Failed to parse property name '" + propertyName + '\'', e);
        }

        Tree tree = (Tree) r.getTree();

        if (log.isDebugEnabled())
        {
            ASTUtil.dumpAST(tree);
        }

        if (tree.getChildCount() == 1)
        {
            return makeProperty(tree.getChild(0), false);
        }

        List<Property> properties = new LinkedList<Property>();
        boolean isRootedInDynamic = false;
        for (int i = 0; i < tree.getChildCount(); i++)
        {
        	Tree child = tree.getChild(i);

            Property property = makeProperty(child, isRootedInDynamic);
            if (property instanceof DynamicSimpleProperty)
            {
                isRootedInDynamic = true;
            }
            properties.add(property);
        }

        return new NestedProperty(properties, beanEventTypeFactory);
    }

    private static Property makeProperty(Tree child, boolean isRootedInDynamic)
    {
        switch (child.getType()) {
            case EsperEPL2GrammarParser.EVENT_PROP_SIMPLE:
                if (!isRootedInDynamic)
                {
                    return new SimpleProperty(child.getChild(0).getText());
                }
                else
                {
                    return new DynamicSimpleProperty(child.getChild(0).getText());
                }
            case EsperEPL2GrammarParser.EVENT_PROP_MAPPED:
                String key = StringValue.parseString(child.getChild(1).getText());
                if (!isRootedInDynamic)
                {
                    return new MappedProperty(child.getChild(0).getText(), key);
                }
                else
                {
                    return new DynamicMappedProperty(child.getChild(0).getText(), key);
                }
            case EsperEPL2GrammarParser.EVENT_PROP_INDEXED:
                int index = IntValue.parseString(child.getChild(1).getText());
                if (!isRootedInDynamic)
                {
                    return new IndexedProperty(child.getChild(0).getText(), index);
                }
                else
                {
                    return new DynamicIndexedProperty(child.getChild(0).getText(), index);
                }
            case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_SIMPLE:
                return new DynamicSimpleProperty(child.getChild(0).getText());
            case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_INDEXED:
                index = IntValue.parseString(child.getChild(1).getText());
                return new DynamicIndexedProperty(child.getChild(0).getText(), index);
            case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_MAPPED:
                key = StringValue.parseString(child.getChild(1).getText());
                return new DynamicMappedProperty(child.getChild(0).getText(), key);
            default:
                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
        }
    }
}
