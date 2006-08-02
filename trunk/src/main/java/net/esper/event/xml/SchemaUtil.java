package net.esper.event.xml;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;

import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
import com.sun.org.apache.xerces.internal.impl.dv.xs.XSSimpleTypeDecl;
import com.sun.org.apache.xerces.internal.xs.XSAttributeUse;
import com.sun.org.apache.xerces.internal.xs.XSComplexTypeDefinition;
import com.sun.org.apache.xerces.internal.xs.XSConstants;
import com.sun.org.apache.xerces.internal.xs.XSElementDeclaration;
import com.sun.org.apache.xerces.internal.xs.XSModel;
import com.sun.org.apache.xerces.internal.xs.XSModelGroup;
import com.sun.org.apache.xerces.internal.xs.XSNamedMap;
import com.sun.org.apache.xerces.internal.xs.XSObject;
import com.sun.org.apache.xerces.internal.xs.XSObjectList;
import com.sun.org.apache.xerces.internal.xs.XSParticle;

/**
 * Utility class for XSObjects 
 * 
 * @author pablo
 *
 */
public class SchemaUtil {
		
	public static QName simpleTypeToQName(XSSimpleTypeDecl definition) {
		switch (definition.getPrimitiveKind()) {
		case XSSimpleType.PRIMITIVE_BOOLEAN:
			return XPathConstants.BOOLEAN;
		case XSSimpleType.PRIMITIVE_DOUBLE:
			return XPathConstants.NUMBER;
		case XSSimpleType.PRIMITIVE_STRING: 
			return XPathConstants.STRING;
		
		case XSSimpleType.PRIMITIVE_DECIMAL:
			return XPathConstants.NUMBER;
		default:
			throw new RuntimeException("Not implemented Simple Type: " + definition.getPrimitiveKind());
		}
	}
	
	
	public static XSElementDeclaration findRootElement(XSModel schema, String namespace, String elementName) {
		XSNamedMap elements;
		if ((namespace != null) && !namespace.equals(""))
			elements = schema.getComponentsByNamespace(XSConstants.ELEMENT_DECLARATION,namespace);
		else
			elements = schema.getComponents(XSConstants.ELEMENT_DECLARATION);
		for (int i=0; i< elements.getLength(); i++) {
			XSElementDeclaration decl= (XSElementDeclaration) elements.item(i);
			if (decl.getName().equals(elementName) )
				return decl;
		}
		return null;
	}

	
	/**
	 * Finds an apropiate definition for the given property, starting at the 
	 * given definition.
	 * First look if the property es an attribute. If not, look at child element
	 * definitions.
	 * 
	 * @param def the definition to start looking
	 * @param property the property to look for 
	 * @return either an XSAttributeUse if the property is an attribute,XSParticle if is an element, or null if not found in schema
	 */
	public static XSObject findPropertyMapping(XSComplexTypeDefinition def, String property) {
		try {
		XSObjectList attrs = def.getAttributeUses();
		for(int i=0;i<attrs.getLength();i++) {
			XSAttributeUse attr = (XSAttributeUse)attrs.item(i);
			String name = attr.getAttrDeclaration().getName();
			if (name.equals(property)) {
				//is an attribute of the parent element
				return attr;
			}
		} } catch (Exception e) {
			e.printStackTrace();
		}
		if ((def.getContentType() == XSComplexTypeDefinition.CONTENTTYPE_ELEMENT)
				|| (def.getContentType() == XSComplexTypeDefinition.CONTENTTYPE_MIXED)) {
			//has childrens
			XSParticle particle = def.getParticle();
			if (particle.getTerm() instanceof XSModelGroup ) {
				XSModelGroup group = (XSModelGroup)particle.getTerm();
				XSObjectList particles = group.getParticles();
				for(int i=0;i<particles.getLength();i++) {
					XSParticle childParticle = (XSParticle)particles.item(i);
					if (childParticle.getTerm().getName().equals(property)) {
						//is a child element of the parent node
						return childParticle;
					}
				}
			}
		}
		//property not found in schema
		return null;
	}
	
}
