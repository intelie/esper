using System;
using System.Collections.Generic;

using antlr;
using antlr.collections;

using net.esper.compat;
using net.esper.eql.generated;
using net.esper.events;
using net.esper.type;

namespace net.esper.events.property
{
    /// <summary>
    /// Parser for property names that can be simple, nested, mapped or a combination of these.
    /// Uses ANTLR parser to parse.
    /// </summary>

    public class PropertyParser : EqlTokenTypes
    {
        /// <summary> Parse the given property name returning a Property instance for the property.</summary>
        /// <param name="propertyName">is the property name to parse
        /// </param>
        /// <param name="beanEventAdapter">is the chache and factory for event bean types and event wrappers
        /// </param>
        /// <returns> Property instance for property
        /// </returns>
        public static Property Parse(String propertyName, BeanEventAdapter beanEventAdapter)
        {
            EQLStatementLexer lexer = new EQLStatementLexer(new System.IO.StringReader(propertyName));
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

            AST ast = parser.getAST();
            //DebugFacility.dumpAST(ast);

            if (ast.getNumberOfChildren() == 1)
            {
                return makeProperty(ast.getFirstChild());
            }

            AST child = ast.getFirstChild();
            IList<Property> properties = new List<Property>();

            do
            {
                Property property = makeProperty(child);
                properties.Add(property);
                child = child.getNextSibling();
            }
            while (child != null);

            return new NestedProperty(properties, beanEventAdapter);
        }

        private static Property makeProperty(AST child)
        {
            switch (child.Type)
            {

                case EqlTokenTypes.EVENT_PROP_SIMPLE:
                    return new SimpleProperty(child.getFirstChild().getText());

                case EqlTokenTypes.EVENT_PROP_MAPPED:
                    String key = StringValue.ParseString(child.getFirstChild().getNextSibling().getText());
                    return new MappedProperty(child.getFirstChild().getText(), key);

                case EqlTokenTypes.EVENT_PROP_INDEXED:
                    int index = IntValue.ParseString(child.getFirstChild().getNextSibling().getText());
                    return new IndexedProperty(child.getFirstChild().getText(), index);

                default:
                    throw new SystemException("Event property AST node not recognized, type=" + child.Type);

            }
        }
    }
}
