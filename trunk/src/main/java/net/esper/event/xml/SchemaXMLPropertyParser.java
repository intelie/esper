package net.esper.event.xml;

import javax.xml.namespace.QName;
import javax.xml.xpath.*;

import com.sun.org.apache.xerces.internal.impl.dv.xs.XSSimpleTypeDecl;
import com.sun.org.apache.xerces.internal.xs.StringList;
import com.sun.org.apache.xerces.internal.xs.XSAttributeUse;
import com.sun.org.apache.xerces.internal.xs.XSComplexTypeDefinition;
import com.sun.org.apache.xerces.internal.xs.XSElementDeclaration;
import com.sun.org.apache.xerces.internal.xs.XSModel;
import com.sun.org.apache.xerces.internal.xs.XSObject;
import com.sun.org.apache.xerces.internal.xs.XSParticle;
import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;

import net.esper.collection.Pair;
import net.esper.eql.generated.EqlTokenTypes;
import net.esper.event.PropertyAccessException;
import net.esper.event.TypedEventPropertyGetter;
import net.esper.type.IntValue;
import net.esper.type.StringValue;
import antlr.collections.AST;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Parses event property names and transforms to XPath expressions using the schema information supplied. Supports the
 * nested, indexed and mapped event properties.
 */
public class SchemaXMLPropertyParser implements EqlTokenTypes
{
    /**
     * Return the xPath corresponding to the given property.
     * The propertyName String may be simple, nested, indexed or mapped.
     *
     * @param propertyName is the event property name
     * @param namespace is the default namespace
     * @param xsModel is the schema model
     * @param xPathFactory is the xpath factory instance to use
     * @param rootElementName is the name of the root element
     * @return xpath expression
     * @throws XPathExpressionException when the XPath expression is invalid
     */
    public static TypedEventPropertyGetter parse(String propertyName, XPathFactory xPathFactory, String rootElementName, String namespace, XSModel xsModel) throws XPathExpressionException
    {
        XPathNamespaceContext ctx = new XPathNamespaceContext();
        StringList namespaces = xsModel.getNamespaces();

        for (int i = 0; i < namespaces.getLength(); i++)
        {
            ctx.addPrefix("n" + i, namespaces.item(i));
        }

        AST ast = SimpleXMLPropertyParser.parse(propertyName);

        XSElementDeclaration root = SchemaUtil.findRootElement(xsModel, namespace, rootElementName);

        if (root == null) {
            throw new PropertyAccessException("Invalid rootElementName - name must match the rootElement defined in the schema and have the correct namespace");
        }

        if (root.getTypeDefinition().getTypeCategory() != XSTypeDefinition.COMPLEX_TYPE) {
            throw new PropertyAccessException("Invalid schema - the root element must have at least either attribute declarations or childs elements");
        }

        XSComplexTypeDefinition complexActualElement = (XSComplexTypeDefinition) root.getTypeDefinition();

        String prefix = ctx.getPrefix(root.getNamespace());
        if (prefix == null) {
            prefix = "";
        }
        else {
            prefix = prefix + ":";
        }

        StringBuffer xPathBuf = new StringBuffer();
        xPathBuf.append("/");
        xPathBuf.append(prefix);
        xPathBuf.append(rootElementName);

        Pair<String, QName> pair = null;
        if (ast.getNumberOfChildren() == 1)
        {
            pair = makeProperty(complexActualElement, ast.getFirstChild(), ctx);
            if (pair == null)
            {
                throw new PropertyAccessException("Failed to locate property '" + propertyName + "' in schema");
            }
            xPathBuf.append(pair.getFirst());
        }
        else
        {
            AST child = ast.getFirstChild();
            do
            {
                pair = makeProperty(complexActualElement, child, ctx);
                if (pair == null)
                {
                    throw new PropertyAccessException("Failed to locate property '" + propertyName + "' nested property part '" + child.toString() + "' in schema");
                }

                String text = child.getFirstChild().getText();
                XSObject obj = SchemaUtil.findPropertyMapping(complexActualElement, text);
                if (obj instanceof XSParticle)
                {
                    XSParticle particle = (XSParticle) obj;
                    if (particle.getTerm() instanceof XSElementDeclaration)
                    {
                        XSElementDeclaration decl = (XSElementDeclaration) particle.getTerm();
                        if (decl.getTypeDefinition().getTypeCategory() == XSTypeDefinition.COMPLEX_TYPE)
                            complexActualElement = (XSComplexTypeDefinition) decl.getTypeDefinition();
                    }
                }
                xPathBuf.append(pair.getFirst());
                child = child.getNextSibling();
            }
            while (child != null);
        }

        String xPath = xPathBuf.toString();
        log.debug(".parse XPath for property '" + propertyName + "' is expression=" + xPath);

        // Compile assembled XPath expression
        XPath path = xPathFactory.newXPath();
        path.setNamespaceContext(ctx);
        XPathExpression expr = path.compile(xPath);

        return new XPathPropertyGetter(propertyName, expr, pair.getSecond());
    }

    private static Pair<String, QName> makeProperty(XSComplexTypeDefinition parent, AST child, XPathNamespaceContext ctx)
    {
        String text = child.getFirstChild().getText();
        XSObject obj = SchemaUtil.findPropertyMapping(parent, text);
        if (obj instanceof XSParticle) {
            return makeElementProperty((XSParticle) obj, child, ctx);
        }
        else if (obj != null) {
            return makeAttributeProperty((XSAttributeUse) obj, child, ctx);
        }
        else
        {
            return null;
        }
    }

    private static Pair<String, QName> makeAttributeProperty(XSAttributeUse use, AST child, XPathNamespaceContext ctx)
    {
        String prefix = ctx.getPrefix(use.getAttrDeclaration().getNamespace());
        if (prefix == null)
            prefix = "";
        else
            prefix = prefix + ":";
        switch (child.getType())
        {
            case EVENT_PROP_SIMPLE:
                QName type = SchemaUtil.simpleTypeToQName((XSSimpleTypeDecl) use.getAttrDeclaration().getTypeDefinition());
                String path = "/@" + prefix + child.getFirstChild().getText();
                return new Pair<String, QName>(path, type);
            case EVENT_PROP_MAPPED:
                throw new RuntimeException("Mapped properties not applicable to attributes");
            case EVENT_PROP_INDEXED:
                throw new RuntimeException("Mapped properties not applicable to attributes");
            default:
                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
        }
    }

    private static Pair<String, QName> makeElementProperty(XSParticle particle, AST child, XPathNamespaceContext ctx)
    {
        XSElementDeclaration decl = (XSElementDeclaration) particle.getTerm();
        QName type = null;
        if (decl.getTypeDefinition().getTypeCategory() == XSTypeDefinition.SIMPLE_TYPE) {
            type = SchemaUtil.simpleTypeToQName((XSSimpleTypeDecl) decl.getTypeDefinition());
        }
        else
        {
            XSComplexTypeDefinition complex = (XSComplexTypeDefinition) decl.getTypeDefinition();
            if (complex.getSimpleType() != null) {
                type = SchemaUtil.simpleTypeToQName((XSSimpleTypeDecl) complex.getSimpleType());
            }
            else
            {
                // The result is a node
                type = XPathConstants.NODE;
            }
        }
        String prefix = ctx.getPrefix(decl.getNamespace());
        if (prefix == null) {
            prefix = "";
        }
        else {
            prefix = prefix + ":";
        }

        switch (child.getType())
        {
            case EVENT_PROP_SIMPLE:
                if ((particle.getMaxOccurs() > 1) || particle.getMaxOccursUnbounded()) {
                    throw new PropertyAccessException("Simple property not allowed in repeating elements");
                }
                return new Pair<String, QName>("/" + prefix + child.getFirstChild().getText(), type);

            case EVENT_PROP_MAPPED:
                if (!((particle.getMaxOccurs() > 1) || (particle.getMaxOccursUnbounded()))) {
                    throw new PropertyAccessException("Element " + child.getFirstChild().getText() + " is not a collection, cannot be used as mapped property");
                }
                String key = StringValue.parseString(child.getFirstChild().getNextSibling().getText());
                return new Pair<String, QName>("/" + prefix + child.getFirstChild().getText() + "[@id='" + key + "']", type);

            case EVENT_PROP_INDEXED:
                if (!((particle.getMaxOccurs() > 1) || (particle.getMaxOccursUnbounded()))) {
                    throw new PropertyAccessException("Element " + child.getFirstChild().getText() + " is not a collection, cannot be used as mapped property");
                }
                int index = IntValue.parseString(child.getFirstChild().getNextSibling().getText());
                int xPathPosition = index + 1;
                return new Pair<String, QName>("/" + prefix + child.getFirstChild().getText() + "[position() = " + xPathPosition + "]", type);

            default:
                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
        }
    }

    private static Log log = LogFactory.getLog(SchemaXMLPropertyParser.class);
}
