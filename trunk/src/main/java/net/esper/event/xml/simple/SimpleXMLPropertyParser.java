package net.esper.event.xml.simple;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

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

public class SimpleXMLPropertyParser implements EqlTokenTypes {

	/**
	 * return the xPath corresponding to the given property.
	 * The propertyName String may be simple, nested, indexed or mapped.
	 * 
	 * @param propertyName
	 * @param p
	 * @return
	 * @throws XPathExpressionException 
	 */ 
	public static XPathExpression parse(String propertyName,XPathFactory xPathFactory, String eventName) throws XPathExpressionException
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

	        AST ast = parser.getAST();
	        
	        String xPath ="/" + eventName; //TODO StringBuffer
	        if (ast.getNumberOfChildren() == 1)
	        {
	            xPath = xPath + makeProperty(ast.getFirstChild()); 
	        } else {
	        	AST child = ast.getFirstChild();
		        do
		        {
		        	xPath = xPath+ makeProperty(child);
		            child = child.getNextSibling();
		        }
		        while (child != null);
	
	        }
	        return xPathFactory.newXPath().compile(xPath);
	    }

	    private static String makeProperty(AST child)
	    {
	        switch (child.getType()) {
	            case EVENT_PROP_SIMPLE:
	            	return "/" + child.getFirstChild().getText();
	            case EVENT_PROP_MAPPED:
	                String key = StringValue.parseString(child.getFirstChild().getNextSibling().getText());
	                return "/" + child.getFirstChild().getText() +"[@id='" + key + "']"; 
	            case EVENT_PROP_INDEXED:
	                int index = IntValue.parseString(child.getFirstChild().getNextSibling().getText());
	                return "/" + child.getFirstChild().getText() +"[position() = " + index + "]";
	            default:
	                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
	        }
	    }

}
