/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.xml;

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
import com.espertech.esper.client.EPException;

/**
 * Utility class for querying schema information via Xerces implementation classes.
 * @author pablo
 */
public class SchemaUtil {

    /**
     * Returns the XPathConstants type for a given Xerces type definition.
     * @param definition is the schema element definition
     * @return XPathConstants type
     */
    public static QName simpleTypeToQName(XSSimpleTypeDecl definition)
    {
        switch (definition.getPrimitiveKind())
        {
            case XSSimpleType.PRIMITIVE_BOOLEAN:
                return XPathConstants.BOOLEAN;
            case XSSimpleType.PRIMITIVE_DOUBLE:
                return XPathConstants.NUMBER;
            case XSSimpleType.PRIMITIVE_STRING:
                return XPathConstants.STRING;
            case XSSimpleType.PRIMITIVE_DECIMAL:
                return XPathConstants.NUMBER;
            default:
                throw new EPException("Unexpected schema simple type encountered '" + definition.getPrimitiveKind() + '\'');
        }
    }

    /**
     * Returns the root element for a given schema given a root element name and namespace.
     * @param schema is the schema to interrogate
     * @param namespace is the namespace of the root element
     * @param elementName is the name of the root element
     * @return declaration of root element
     */
    public static XSElementDeclaration findRootElement(XSModel schema, String namespace, String elementName)
    {
        XSNamedMap elements;

        if ((namespace != null) && namespace.length() != 0)
        {
            elements = schema.getComponentsByNamespace(XSConstants.ELEMENT_DECLARATION,namespace);
            if (elements == null)
            {
                throw new EPException("Empty element declaration list returned by schema for namespace '" + namespace + '\'');
            }
        }
        else
        {
            elements = schema.getComponents(XSConstants.ELEMENT_DECLARATION);
        }

        for (int i=0; i< elements.getLength(); i++)
        {
            XSElementDeclaration decl= (XSElementDeclaration) elements.item(i);
            if (decl.getName().equals(elementName))
            {
                return decl;
            }
        }

        String text = "Could not find root element declaration in schema using element name '" + elementName + '\'';
        if (namespace != null)
        {
            text = text + " in namespace '" + namespace + '\'';
        }
        throw new EPException(text);
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

        XSObjectList attrs = def.getAttributeUses();
        for(int i=0;i<attrs.getLength();i++)
        {
            XSAttributeUse attr = (XSAttributeUse)attrs.item(i);
            String name = attr.getAttrDeclaration().getName();
            if (name.equals(property))
            {
                //is an attribute of the parent element
                return attr;
            }
        }

        if ((def.getContentType() == XSComplexTypeDefinition.CONTENTTYPE_ELEMENT) ||
            (def.getContentType() == XSComplexTypeDefinition.CONTENTTYPE_MIXED))
        {
            // has children
            XSParticle particle = def.getParticle();
            if (particle.getTerm() instanceof XSModelGroup )
            {
                XSModelGroup group = (XSModelGroup)particle.getTerm();
                XSObjectList particles = group.getParticles();
                for (int i=0;i<particles.getLength();i++)
                {
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
