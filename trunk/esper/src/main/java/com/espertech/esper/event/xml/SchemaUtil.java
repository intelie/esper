/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.xml;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.PropertyAccessException;
import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 * Utility class for querying schema information via Xerces implementation classes.
 * @author pablo
 */
public class SchemaUtil {

    public static Class toReturnType(SchemaItem item)
    {
        if (item instanceof SchemaItemAttribute)
        {
            return SchemaUtil.toReturnType(((SchemaItemAttribute)item).getType());
        }
        else if (item instanceof SchemaElementSimple)
        {
            return SchemaUtil.toReturnType(((SchemaElementSimple)item).getType());
        }
        else if (item instanceof SchemaElementComplex)
        {
            SchemaElementComplex complex = (SchemaElementComplex) item;
            if (complex.getOptionalSimpleType() != null)
            {
                return SchemaUtil.toReturnType(complex.getOptionalSimpleType());
            }
            if (complex.isArray())
            {
                return NodeList.class;
            }
            return Node.class;
        }
        else
        {
            throw new PropertyAccessException("Invalid schema return type:" + item);
        }
    }

    public static Class toReturnType(short xsType)
    {
        switch(xsType)
        {
            case XSSimpleType.PRIMITIVE_BOOLEAN :
                    return Boolean.class;
            case XSSimpleType.PRIMITIVE_STRING :
                    return String.class;
            case XSSimpleType.PRIMITIVE_DECIMAL :
                    return Integer.class;
            case XSSimpleType.PRIMITIVE_FLOAT :
                    return Float.class;
            case XSSimpleType.PRIMITIVE_DOUBLE :
                    return Double.class;
            default:
                return String.class;
        }
    }

    public static Class toReturnType(QName resultType, Class optionalCastToType)
    {
        if (resultType.equals(XPathConstants.NODESET))
            return NodeList.class;
        if (resultType.equals(XPathConstants.NODE))
            return Node.class;

        if (optionalCastToType != null)
        {
            return optionalCastToType;
        }

        if (resultType.equals(XPathConstants.BOOLEAN))
            return Boolean.class;
        if (resultType.equals(XPathConstants.NUMBER))
            return Double.class;
        if (resultType.equals(XPathConstants.STRING))
            return String.class;

        return String.class;
    }

    /**
     * Returns the XPathConstants type for a given Xerces type definition.
     * @param type is the type
     * @return XPathConstants type
     */
    public static QName simpleTypeToQName(short type)
    {
        switch (type)
        {
            case XSSimpleType.PRIMITIVE_BOOLEAN:
                return XPathConstants.BOOLEAN;
            case XSSimpleType.PRIMITIVE_DOUBLE:
                return XPathConstants.NUMBER;
            case XSSimpleType.PRIMITIVE_STRING:
                return XPathConstants.STRING;
            case XSSimpleType.PRIMITIVE_DECIMAL:
                return XPathConstants.NUMBER;
            case XSSimpleType.PRIMITIVE_FLOAT:
                return XPathConstants.NUMBER;
            case XSSimpleType.PRIMITIVE_DATETIME:
                return XPathConstants.STRING;
            case XSSimpleType.PRIMITIVE_DATE:
                return XPathConstants.STRING;
            case XSSimpleType.PRIMITIVE_TIME:
                return XPathConstants.STRING;
            default:
                throw new EPException("Unexpected schema simple type encountered '" + type + "'");
        }
    }

    /**
     * Returns the root element for a given schema given a root element name and namespace.
     * @param schema is the schema to interrogate
     * @param namespace is the namespace of the root element
     * @param elementName is the name of the root element
     * @return declaration of root element
     */
    public static SchemaElementComplex findRootElement(SchemaModel schema, String namespace, String elementName)
    {
        if ((namespace != null) && namespace.length() != 0)
        {
            for (SchemaElementComplex complexElement : schema.getComponents())
            {
                if ((complexElement.getNamespace().equals(namespace)) && (complexElement.getName().equals(elementName)))
                {
                    return complexElement;
                }
            }
        }
        else
        {
            for (SchemaElementComplex complexElement : schema.getComponents())
            {
                if (complexElement.getName().equals(elementName))
                {
                    return complexElement;
                }
            }
        }

        if (elementName.startsWith("//"))
        {
            elementName = elementName.substring(2);
            for (SchemaElementComplex complexElement : schema.getComponents())
            {
                SchemaElementComplex match = recursiveDeepMatch(complexElement, namespace, elementName);
                if (match != null)
                {
                    return match;
                }
            }
        }

        String text = "Could not find root element declaration in schema for element name '" + elementName + '\'';
        if (namespace != null)
        {
            text = text + " in namespace '" + namespace + '\'';
        }
        throw new EPException(text);
    }

    private static SchemaElementComplex recursiveDeepMatch(SchemaElementComplex parent, String namespace, String elementName)
    {
        if ((namespace != null) && namespace.length() != 0)
        {
            for (SchemaElementComplex complexElement : parent.getChildren())
            {
                if ((complexElement.getNamespace().equals(namespace)) && (complexElement.getName().equals(elementName)))
                {
                    return complexElement;
                }
            }
        }
        else
        {
            for (SchemaElementComplex complexElement : parent.getChildren())
            {
                if (complexElement.getName().equals(elementName))
                {
                    return complexElement;
                }
            }
        }

        for (SchemaElementComplex complexElement : parent.getChildren())
        {
            SchemaElementComplex found = recursiveDeepMatch(complexElement, namespace, elementName);
            if (found != null)
            {
                return found;
            }
        }

        return null;
    }


    /**
     * Finds an apropiate definition for the given property, starting at the * given definition.
     * First look if the property es an attribute. If not, look at simple and then child element
     * definitions.
     *
     * @param def the definition to start looking
     * @param property the property to look for
     * @return schema element or null if not found
     */
    public static SchemaItem findPropertyMapping(SchemaElementComplex def, String property) {

        for (SchemaItemAttribute attribute : def.getAttributes())
        {
            if (attribute.getName().equals(property))
            {
                return attribute;
            }
        }

        for (SchemaElementSimple simple : def.getSimpleElements())
        {
            if (simple.getName().equals(property))
            {
                return simple;
            }
        }

        for (SchemaElementComplex complex : def.getChildren())
        {
            if (complex.getName().equals(property))
            {
                return complex;
            }
        }

        //property not found in schema
        return null;
    }

}
