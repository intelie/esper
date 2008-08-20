/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.csv;

import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.TestCase;

public class TestPropertyOrderHelper extends TestCase
{
	private Map<String, Class> propertyTypes;
	protected void setUp() throws ClassNotFoundException
	{
		propertyTypes = new LinkedHashMap<String, Class>();
		propertyTypes.put("myInt", Integer.class);
		propertyTypes.put("myDouble", Double.class);
		propertyTypes.put("myString", String.class);
	}

	public void testResolveTitleRow()
	{
		// Use first row
		String[] firstRow = new String[] { "myDouble", "myInt", "myString" };
		assertEquals(firstRow, CSVPropertyOrderHelper.resolvePropertyOrder(firstRow, propertyTypes));
	}
}
