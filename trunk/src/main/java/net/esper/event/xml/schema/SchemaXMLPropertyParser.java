package net.esper.event.xml.schema;

import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.sun.org.apache.xerces.internal.impl.dv.xs.XSSimpleTypeDecl;
import com.sun.org.apache.xerces.internal.xs.StringList;
import com.sun.org.apache.xerces.internal.xs.XSAttributeUse;
import com.sun.org.apache.xerces.internal.xs.XSComplexTypeDefinition;
import com.sun.org.apache.xerces.internal.xs.XSElementDeclaration;
import com.sun.org.apache.xerces.internal.xs.XSModel;
import com.sun.org.apache.xerces.internal.xs.XSNamespaceItemList;
import com.sun.org.apache.xerces.internal.xs.XSObject;
import com.sun.org.apache.xerces.internal.xs.XSParticle;
import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;

import net.esper.collection.Pair;
import net.esper.eql.generated.EQLStatementLexer;
import net.esper.eql.generated.EQLStatementParser;
import net.esper.eql.generated.EqlTokenTypes;
import net.esper.event.EventPropertyGetter;
import net.esper.event.PropertyAccessException;
import net.esper.event.xml.XPathPropertyGetter;
import net.esper.type.IntValue;
import net.esper.type.StringValue;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;

public class SchemaXMLPropertyParser implements EqlTokenTypes {

	/**
	 * return the xPath corresponding to the given property.
	 * The propertyName String may be simple, nested, indexed or mapped.
	 * 
	 * @param propertyName
	 * @param namespace 
	 * @param xsModel 
	 * @param p
	 * @return
	 * @throws XPathExpressionException 
	 */ 
	public static EventPropertyGetter parse(String propertyName,XPathFactory xPathFactory, String eventName, String namespace, XSModel xsModel) throws XPathExpressionException
	    {
			XPathNamespaceContext ctx = new XPathNamespaceContext();
			StringList namespaces = xsModel.getNamespaces();
			
			for (int i=0;i<namespaces.getLength();i++) {
				ctx.addPrefix("n"+i,namespaces.item(i));
			}
		
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

	        XSElementDeclaration root = SchemaUtil.findRootElement(xsModel,namespace,eventName);
	        
	        if (root == null)
	        	throw new RuntimeException("Invalid eventName. The name must match the rootElement defined in the schema and have the correct namespace");
	        
	        if(root.getTypeDefinition().getTypeCategory() != XSTypeDefinition.COMPLEX_TYPE)
	        	throw new RuntimeException("Invalid schema. The root element must have at least either attribute declarations or childs elements");
	        
	        XSComplexTypeDefinition complexActualElement = (XSComplexTypeDefinition)root.getTypeDefinition();
	        
	        String prefix = ctx.getPrefix(root.getNamespace());
        	if (prefix == null) //TODO  StringBuffer 
        		prefix = "";
        	else
        		prefix = prefix +":";
	        String xPath ="/" +prefix +eventName;
	        
	        
	        Pair<String,QName> pair = null;
	        if (ast.getNumberOfChildren() == 1)
	        {
	        	pair = makeProperty(complexActualElement,ast.getFirstChild(),ctx);
	        	xPath = xPath + pair.getFirst();
	        } else {
	        	AST child = ast.getFirstChild();
		        do
		        {
		        	pair = makeProperty(complexActualElement,child,ctx);
		        	
		        	String text = child.getFirstChild().getText();
		            XSObject obj = SchemaUtil.findPropertyMapping(complexActualElement, text);
		            if (obj instanceof XSParticle) { //TODO refactor here
		            	XSParticle particle = (XSParticle)obj;
		            	if (particle.getTerm() instanceof XSElementDeclaration) {
		            		XSElementDeclaration decl = (XSElementDeclaration)particle.getTerm();
		            		if (decl.getTypeDefinition().getTypeCategory() == XSTypeDefinition.COMPLEX_TYPE)
		            			complexActualElement = (XSComplexTypeDefinition)decl.getTypeDefinition();
		            	}
		            }
		        	xPath = xPath+ pair.getFirst(); 
		            child = child.getNextSibling();
		        }
		        while (child != null);
	
	        }
	       // System.out.println("XPath: " + xPath);
	        XPath path =xPathFactory.newXPath();
	        path.setNamespaceContext(ctx);
	        XPathExpression expr =path.compile(xPath);
	        return new XPathPropertyGetter(propertyName,expr,pair.getSecond());
	    }
 
		private static Pair<String,QName> makeProperty(XSComplexTypeDefinition parent, AST child , XPathNamespaceContext ctx) {
        	String text = child.getFirstChild().getText();
            XSObject obj = SchemaUtil.findPropertyMapping(parent, text);
            if (obj instanceof XSParticle) {
				return makeElementProperty((XSParticle)obj, child,ctx);
			} else
				return makeAttributeProperty((XSAttributeUse)obj,child,ctx);
           
		}
	
	    private static Pair<String,QName> makeAttributeProperty(XSAttributeUse use, AST child,XPathNamespaceContext ctx) {
	    	String prefix = ctx.getPrefix(use.getAttrDeclaration().getNamespace());
        	if (prefix == null)
        		prefix = "";
        	else
        		prefix = prefix +":";
	    	switch (child.getType()) {
            case EVENT_PROP_SIMPLE:
            	QName type = SchemaUtil.simpleTypeToQName((XSSimpleTypeDecl)use.getAttrDeclaration().getTypeDefinition());
            	String path = "/@"+prefix + child.getFirstChild().getText();
            	return new Pair<String,QName>(path,type);
            case EVENT_PROP_MAPPED:
            	throw new RuntimeException("Mapped properties not applicable to attributes");
            case EVENT_PROP_INDEXED:
            	throw new RuntimeException("Mapped properties not applicable to attributes");
            default:
                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
        }
		}

		private static Pair<String,QName> makeElementProperty(XSParticle particle, AST child, XPathNamespaceContext ctx) {
	    	XSElementDeclaration decl = (XSElementDeclaration) particle.getTerm(); 
	    	QName type = null;
        	if (decl.getTypeDefinition().getTypeCategory() == XSTypeDefinition.SIMPLE_TYPE)
        		type = SchemaUtil.simpleTypeToQName((XSSimpleTypeDecl)decl.getTypeDefinition());
        	else {
        		XSComplexTypeDefinition complex = (XSComplexTypeDefinition) decl.getTypeDefinition();
        		if (complex.getSimpleType() != null)
        			type = SchemaUtil.simpleTypeToQName((XSSimpleTypeDecl)complex.getSimpleType());	 
        	}
        	String prefix = ctx.getPrefix(decl.getNamespace());
        	if (prefix == null)
        		prefix = "";
        	else
        		prefix = prefix +":";
        	
	    	switch (child.getType()) {
	            case EVENT_PROP_SIMPLE:
	            	if((particle.getMaxOccurs() > 1) || particle.getMaxOccursUnbounded())
	            		throw new RuntimeException("Simple property nos allowed in repeating elements");
	            	return new Pair<String,QName>("/" +prefix+ child.getFirstChild().getText(),type);
	            case EVENT_PROP_MAPPED:
	            	if (!((particle.getMaxOccurs() > 1) || (particle.getMaxOccursUnbounded())))
	            		throw new RuntimeException("Element " + child.getFirstChild().getText() + " is not a collection, cannot be used as mapped property");
	            	//TODO: find an identifier attribute?
	                String key = StringValue.parseString(child.getFirstChild().getNextSibling().getText());
	                return new Pair<String,QName>("/" +prefix+ child.getFirstChild().getText() +"[@id='" + key + "']",type); 
	            case EVENT_PROP_INDEXED:
	            	if (!((particle.getMaxOccurs() > 1) || (particle.getMaxOccursUnbounded())))
	            		throw new RuntimeException("Element " + child.getFirstChild().getText() + " is not a collection, cannot be used as mapped property");
	            	int index = IntValue.parseString(child.getFirstChild().getNextSibling().getText());
	                return new Pair<String,QName>("/" +prefix+ child.getFirstChild().getText() +"[position() = " + index + "]",type);
	            default:
	                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
	        } 
		}
}
