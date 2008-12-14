/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.csv;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.util.JavaClassHelper;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

/**
 * Coercer for type conversion.
 */
public abstract class AbstractTypeCoercer {

    /**
     * For logging.
     */
    protected static final Log log = LogFactory.getLog(AbstractTypeCoercer.class);

    /**
     * Constructors for coercion.
     */
    protected Map<String, FastConstructor> propertyConstructors;

    /**
     * Ctor.
     * @param propertyTypes the type conversion to be done
     */
    public void setPropertyTypes(Map<String, Object> propertyTypes) {
		this.propertyConstructors = createPropertyConstructors(propertyTypes);
	}

    /**
     * Convert a value.
     * @param property property name
     * @param source value to convert
     * @return object value
     * @throws Exception if coercion failed
     */
    abstract Object coerce(String property, String source) throws Exception;

	private Map<String, FastConstructor> createPropertyConstructors(Map<String, Object> propertyTypes)
	{
		Map<String, FastConstructor> constructors = new HashMap<String, FastConstructor>();

        Class[] parameterTypes = new Class[] { String.class };
        for(String property : propertyTypes.keySet())
		{
			log.debug(".createPropertyConstructors property==" + property + ", type==" + propertyTypes.get(property	));
			FastClass fastClass = FastClass.create(Thread.currentThread().getContextClassLoader(), JavaClassHelper.getBoxedType((Class) propertyTypes.get(property)));
			FastConstructor constructor = fastClass.getConstructor(parameterTypes);
			constructors.put(property, constructor);
		}
		return constructors;
	}
}
