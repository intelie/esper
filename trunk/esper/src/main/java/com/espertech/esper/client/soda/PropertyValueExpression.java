/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.StringWriter;

/**
 * Expression returning a property value.
 */
public class PropertyValueExpression extends ExpressionBase
{
    private String propertyName;

    /**
     * Ctor.
     * @param propertyName is the name of the property
     */
    public PropertyValueExpression(String propertyName)
    {
        this.propertyName = propertyName.trim();
    }

    /**
     * Returns the property name.
     * @return name of the property
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    /**
     * Sets the property name.
     * @param propertyName name of the property
     */
    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write(propertyName);
    }
}
