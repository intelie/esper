/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.indicator.pretty;

import com.espertech.esper.view.stat.olap.Cube;
import com.espertech.esper.view.stat.olap.Cell;
import com.espertech.esper.view.stat.olap.DimensionMember;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Utility class that takes a cube and renders it as a flattened table of string and double.
 * The double value
 */
public final class CubeCellStringRenderer
{
    private static final String MEMBER_DELIMITER = "__";

    /**
     * Render a multidimensional table (see Cube) as flatted out structure.
     * Renders each cell's intersecting members of each dimension into a single String.
     * @param cube is the cube to render
     * @return map containing rendered cell/column/row/page dimension members as key and cell values as values
     */
    public static Map<String, Double> renderCube(Cube cube)
    {
        Map<String, Double> renderedCube = new LinkedHashMap<String, Double>();

        Cell[] measures = cube.getMeasures();

        for (int ordinal = 0; ordinal < measures.length; ordinal++)
        {
            DimensionMember[] members = cube.getMembers(ordinal);

            String ordinalString = String.format("%1$4s", ordinal);
            String renderedCell = "Cell " + ordinalString + " : ";
            String renderedMember = renderMembers(members);
            String key = renderedCell + renderedMember;

            renderedCube.put(key, measures[ordinal].getValue());
        }

        return renderedCube;
    }

    private static String renderMembers(DimensionMember[] members)
    {
        StringBuilder buffer = new StringBuilder();
        String memberDelimiter = "";

        for (int i = 0; i < members.length; i++)
        {
            String renderedValues = DimensionMemberRenderHelper.renderMember(members[i]);
            buffer.append(memberDelimiter).append(renderedValues);
            memberDelimiter = MEMBER_DELIMITER;
        }

        return buffer.toString();
    }

}
