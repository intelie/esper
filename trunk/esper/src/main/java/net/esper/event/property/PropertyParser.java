package net.esper.event.property;

import net.esper.eql.generated.EQLStatementLexer;
import net.esper.eql.generated.EQLStatementParser;
import net.esper.eql.generated.EqlTokenTypes;
import net.esper.type.IntValue;
import net.esper.type.StringValue;
import net.esper.event.PropertyAccessException;
import net.esper.event.BeanEventTypeFactory;

import java.io.StringReader;
import java.util.List;
import java.util.LinkedList;

import antlr.collections.AST;
import antlr.TokenStreamException;
import antlr.RecognitionException;

/**
 * Parser for property names that can be simple, nested, mapped or a combination of these.
 * Uses ANTLR parser to parse.
 */
public class PropertyParser implements EqlTokenTypes
{
    /**
     * Parse the given property name returning a Property instance for the property.
     * @param propertyName is the property name to parse
     * @param beanEventTypeFactory is the chache and factory for event bean types and event wrappers
     * @return Property instance for property
     */
    public static Property parse(String propertyName, BeanEventTypeFactory beanEventTypeFactory)
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

        AST ast = parser.getAST();
        //DebugFacility.dumpAST(ast);

        if (ast.getNumberOfChildren() == 1)
        {
            return makeProperty(ast.getFirstChild(), false);
        }

        AST child = ast.getFirstChild();
        List<Property> properties = new LinkedList<Property>();

        boolean isRootedInDynamic = false;
        do
        {
            Property property = makeProperty(child, isRootedInDynamic);
            if (property instanceof DynamicSimpleProperty)
            {
                isRootedInDynamic = true;
            }
            properties.add(property);
            child = child.getNextSibling();
        }
        while (child != null);

        return new NestedProperty(properties, beanEventTypeFactory);
    }

    private static Property makeProperty(AST child, boolean isRootedInDynamic)
    {
        switch (child.getType()) {
            case EVENT_PROP_SIMPLE:
                if (!isRootedInDynamic)
                {
                    return new SimpleProperty(child.getFirstChild().getText());
                }
                else
                {
                    return new DynamicSimpleProperty(child.getFirstChild().getText());
                }
            case EVENT_PROP_MAPPED:
                String key = StringValue.parseString(child.getFirstChild().getNextSibling().getText());
                if (!isRootedInDynamic)
                {
                    return new MappedProperty(child.getFirstChild().getText(), key);
                }
                else
                {
                    return new DynamicMappedProperty(child.getFirstChild().getText(), key);
                }
            case EVENT_PROP_INDEXED:
                int index = IntValue.parseString(child.getFirstChild().getNextSibling().getText());
                if (!isRootedInDynamic)
                {
                    return new IndexedProperty(child.getFirstChild().getText(), index);
                }
                else
                {
                    return new DynamicIndexedProperty(child.getFirstChild().getText(), index);
                }
            case EVENT_PROP_DYNAMIC_SIMPLE:
                return new DynamicSimpleProperty(child.getFirstChild().getText());
            case EVENT_PROP_DYNAMIC_INDEXED:
                index = IntValue.parseString(child.getFirstChild().getNextSibling().getText());
                return new DynamicIndexedProperty(child.getFirstChild().getText(), index);
            case EVENT_PROP_DYNAMIC_MAPPED:
                key = StringValue.parseString(child.getFirstChild().getNextSibling().getText());
                return new DynamicMappedProperty(child.getFirstChild().getText(), key);
            default:
                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
        }
    }
}
