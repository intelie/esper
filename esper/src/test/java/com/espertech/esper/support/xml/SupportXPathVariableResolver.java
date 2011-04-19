package com.espertech.esper.support.xml;

import javax.xml.xpath.XPathVariableResolver;
import javax.xml.namespace.QName;

public class SupportXPathVariableResolver implements XPathVariableResolver
{
    public Object resolveVariable(QName variableName) {
        return "value";    
    }
}
