package com.espertech.esper.support.xml;

import javax.xml.xpath.XPathVariableResolver;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathFunction;
import javax.xml.namespace.QName;

public class SupportXPathFunctionResolver implements XPathFunctionResolver
{
    public XPathFunction resolveFunction(QName functionName, int arity) {
        return null;
    }
}