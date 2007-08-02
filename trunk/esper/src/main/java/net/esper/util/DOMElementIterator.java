package net.esper.util;

import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.NoSuchElementException;

public class DOMElementIterator
{
    private int index;
    private NodeList nodeList;

    public DOMElementIterator(NodeList nodeList) {
        this.nodeList = nodeList;
    }

    public boolean hasNext() {
        positionNext();
        return index < nodeList.getLength();
    }

    public Element next() {
        if (index >= nodeList.getLength())
        {
            throw new NoSuchElementException();
        }
        Element result = (Element) nodeList.item(index);
        index++;
        return result;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    private void positionNext()
    {
        while (index < nodeList.getLength())
        {
            Node node = nodeList.item(index);
            if (node instanceof Element)
            {
                break;
            }
            index++;
        }
    }
}
