/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.core;

import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.spec.ColumnDesc;
import com.espertech.esper.util.JavaClassHelper;

import java.lang.reflect.Array;
import java.util.*;

public class TypeBuilderUtil
{
    public static Map<String, Object> buildType(List<ColumnDesc> columns) throws ExprValidationException {
        Map<String, Object> typing = new HashMap<String, Object>();
        Set<String> columnNames = new HashSet<String>();
        for (ColumnDesc column : columns) {
            boolean added = columnNames.add(column.getName());
            if (!added) {
                throw new ExprValidationException("Duplicate column name '" + column.getName() + "'");
            }
            Class plain = JavaClassHelper.getClassForSimpleName(column.getType());
            if (plain != null) {
                if (column.isArray()) {
                    plain = Array.newInstance(plain, 0).getClass();
                }
                typing.put(column.getName(), plain);
            }
            else {
                if (column.isArray()) {
                    typing.put(column.getName(), column.getType() + "[]");
                }
                else {
                    typing.put(column.getName(), column.getType());
                }
            }
        }
        return typing;
    }
}
