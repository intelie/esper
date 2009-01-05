package com.espertech.esper.event.util;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.util.XMLEventRenderer;
import com.espertech.esper.client.util.XMLRenderingOptions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Array;
import java.util.Stack;

public class XMLRendererImpl implements XMLEventRenderer
{
    private static final Log log = LogFactory.getLog(XMLRendererImpl.class);
    private final static String NEWLINE = System.getProperty("line.separator");
    private final RendererMeta meta;
    private final XMLRenderingOptions options;

    public XMLRendererImpl(EventType eventType, XMLRenderingOptions options)
    {
        meta = new RendererMeta(eventType, new Stack<EventTypePropertyPair>(), new RendererMetaOptions(options.isPreventLooping(), true));
        this.options = options;
    }

    public String render(String rootElementName, EventBean event)
    {
        if (options.isDefaultAsAttribute())
        {
            return renderAttributeXML(rootElementName, event);
        }
        return renderElementXML(rootElementName, event);
    }

    public String renderElementXML(String rootElementName, EventBean event)
    {
        StringBuilder buf = new StringBuilder();

        buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buf.append(NEWLINE);

        buf.append('<');
        buf.append(rootElementName);
        buf.append('>');
        buf.append(NEWLINE);

        recursiveRender(event, buf, 1, meta);
            
        buf.append("</");
        buf.append(getFirstWord(rootElementName));
        buf.append('>');

        return buf.toString();
    }

    public String renderAttributeXML(String rootElementName, EventBean event)
    {
        StringBuilder buf = new StringBuilder();

        buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buf.append(NEWLINE);

        buf.append('<');
        buf.append(rootElementName);
        renderAttributes(event, buf, meta);

        String inner = renderElements(event, 1, meta);

        if ((inner == null) || (inner.trim().length() == 0))
        {
            buf.append("/>");
            buf.append(NEWLINE);
        }
        else
        {
            buf.append(">");
            buf.append(NEWLINE);
            buf.append(inner);
            buf.append("</");
            buf.append(rootElementName);
            buf.append('>');
        }

        return buf.toString();
    }

    private String renderElements(EventBean event, int level, RendererMeta meta)
    {
        StringBuilder buf = new StringBuilder();

        GetterPair[] indexProps = meta.getIndexProperties();
        for (GetterPair indexProp : indexProps)
        {
            Object value = indexProp.getGetter().get(event);

            if (value == null)
            {
                continue;
            }
            if (!value.getClass().isArray())
            {
                log.warn("Property '" + indexProp.getName() + "' returned a non-array object");
                continue;
            }
            for (int i = 0; i < Array.getLength(value); i++)
            {
                Object arrayItem = Array.get(value, i);

                if (arrayItem == null)
                {
                    continue;
                }

                ident(buf, level);
                buf.append('<');
                buf.append(indexProp.getName());
                buf.append('>');
                indexProp.getOutput().render(arrayItem, buf);
                buf.append("</");
                buf.append(indexProp.getName());
                buf.append('>');
                buf.append(NEWLINE);
            }
        }

        NestedGetterPair[] nestedProps = meta.getNestedProperties();
        for (NestedGetterPair nestedProp : nestedProps)
        {
            Object value = nestedProp.getGetter().getFragment(event);

            if (value == null)
            {
                continue;
            }

            if (!nestedProp.isArray())
            {
                if (!(value instanceof EventBean))
                {
                    log.warn("Property '" + nestedProp.getName() + "' expected to return EventBean and returned " + value.getClass() + " instead");
                    buf.append("null");
                    continue;
                }
                EventBean nestedEventBean = (EventBean) value;
                renderInner(buf, level + 1, nestedEventBean, nestedProp);
            }
            else
            {
                if (!(value instanceof EventBean[]))
                {
                    log.warn("Property '" + nestedProp.getName() + "' expected to return EventBean[] and returned " + value.getClass() + " instead");
                    buf.append("null");
                    continue;
                }

                EventBean[] nestedEventArray = (EventBean[]) value;
                for (int i = 0; i < nestedEventArray.length; i++)
                {
                    EventBean arrayItem = nestedEventArray[i];
                    renderInner(buf, level + 1, arrayItem, nestedProp);
                }
            }
        }

        return buf.toString();
    }

    private void renderAttributes(EventBean event, StringBuilder buf, RendererMeta meta)
    {
        String delimiter = " ";
        GetterPair[] simpleProps = meta.getSimpleProperties();
        for (GetterPair simpleProp : simpleProps)
        {
            Object value = simpleProp.getGetter().get(event);

            if (value == null)
            {
                continue;
            }

            buf.append(delimiter);
            buf.append(simpleProp.getName());
            buf.append("=\"");
            simpleProp.getOutput().render(value, buf);
            buf.append('"');
        }
    }

    private static void ident(StringBuilder buf, int level)
    {
        for (int i = 0; i < level; i++)
        {
            indentChar(buf);
        }
    }

    private static void indentChar(StringBuilder buf)
    {
        buf.append(' ');
        buf.append(' ');
    }

    private static void recursiveRender(EventBean event, StringBuilder buf, int level, RendererMeta meta)
    {
        GetterPair[] simpleProps = meta.getSimpleProperties();
        for (GetterPair simpleProp : simpleProps)
        {
            Object value = simpleProp.getGetter().get(event);

            if (value == null)
            {
                continue;
            }

            ident(buf, level);
            buf.append('<');
            buf.append(simpleProp.getName());
            buf.append('>');
            simpleProp.getOutput().render(value, buf);
            buf.append("</");
            buf.append(simpleProp.getName());
            buf.append('>');
            buf.append(NEWLINE);
        }

        GetterPair[] indexProps = meta.getIndexProperties();
        for (GetterPair indexProp : indexProps)
        {
            Object value = indexProp.getGetter().get(event);

            if (value == null)
            {
                continue;
            }
            if (!value.getClass().isArray())
            {
                log.warn("Property '" + indexProp.getName() + "' returned a non-array object");
                continue;
            }
            for (int i = 0; i < Array.getLength(value); i++)
            {
                Object arrayItem = Array.get(value, i);

                if (arrayItem == null)
                {
                    continue;
                }

                ident(buf, level);
                buf.append('<');
                buf.append(indexProp.getName());
                buf.append('>');
                indexProp.getOutput().render(arrayItem, buf);
                buf.append("</");
                buf.append(indexProp.getName());
                buf.append('>');
                buf.append(NEWLINE);
            }
        }

        NestedGetterPair[] nestedProps = meta.getNestedProperties();
        for (NestedGetterPair nestedProp : nestedProps)
        {
            Object value = nestedProp.getGetter().getFragment(event);

            if (value == null)
            {
                continue;
            }

            if (!nestedProp.isArray())
            {
                if (!(value instanceof EventBean))
                {
                    log.warn("Property '" + nestedProp.getName() + "' expected to return EventBean and returned " + value.getClass() + " instead");
                    buf.append("null");
                    continue;
                }
                EventBean nestedEventBean = (EventBean) value;
                ident(buf, level);
                buf.append('<');
                buf.append(nestedProp.getName());
                buf.append('>');
                buf.append(NEWLINE);

                recursiveRender(nestedEventBean, buf, level + 1, nestedProp.getMetadata());

                ident(buf, level);
                buf.append("</");
                buf.append(nestedProp.getName());
                buf.append('>');
                buf.append(NEWLINE);
            }
            else
            {
                if (!(value instanceof EventBean[]))
                {
                    log.warn("Property '" + nestedProp.getName() + "' expected to return EventBean[] and returned " + value.getClass() + " instead");
                    buf.append("null");
                    continue;
                }

                EventBean[] nestedEventArray = (EventBean[]) value;
                for (int i = 0; i < nestedEventArray.length; i++)
                {
                    EventBean arrayItem = nestedEventArray[i];

                    ident(buf, level);
                    buf.append('<');
                    buf.append(nestedProp.getName());
                    buf.append('>');
                    buf.append(NEWLINE);

                    recursiveRender(arrayItem, buf, level + 1, nestedProp.getMetadata());

                    ident(buf, level);
                    buf.append("</");
                    buf.append(nestedProp.getName());
                    buf.append('>');
                    buf.append(NEWLINE);
                }
            }
        }
    }

    private void renderInner(StringBuilder buf, int level, EventBean nestedEventBean, NestedGetterPair nestedProp)
    {
        ident(buf, level);
        buf.append('<');
        buf.append(nestedProp.getName());

        renderAttributes(nestedEventBean, buf, meta);

        String inner = renderElements(nestedEventBean, level + 1, nestedProp.getMetadata());

        if ((inner == null) || (inner.trim().length() == 0))
        {
            buf.append("/>");
            buf.append(NEWLINE);
        }
        else
        {
            buf.append(">");
            buf.append(NEWLINE);
            buf.append(inner);

            ident(buf, level);
            buf.append("</");
            buf.append(nestedProp.getName());
            buf.append('>');
            buf.append(NEWLINE);
        }
    }

    private String getFirstWord(String rootElementName)
    {
        if ((rootElementName == null) || (rootElementName.trim().length() == 0))
        {
            return rootElementName;
        }
        int index = rootElementName.indexOf(' ');
        if (index < 0)
        {
            return rootElementName;
        }
        return rootElementName.substring(1, index);
    }
}
