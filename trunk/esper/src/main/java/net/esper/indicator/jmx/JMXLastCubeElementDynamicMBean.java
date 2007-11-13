/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.indicator.jmx;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;
import javax.management.openmbean.OpenMBeanAttributeInfo;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanInfoSupport;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;

import net.esper.event.EventBean;
import net.esper.indicator.pretty.CubeCellStringRenderer;
import net.esper.indicator.pretty.CubeTabularDataRenderer;
import net.esper.view.ViewFieldEnum;
import net.esper.view.stat.olap.Cube;
import net.esper.util.ExecutionPathDebugLog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * JMX dynamic MBean that displays tabular data and/or cells that represent a rendered Cube.
 */
public final class JMXLastCubeElementDynamicMBean implements JMXLastElementObserver
{
    private OpenMBeanInfoSupport mBeanInfo;
    private Map<String, Double> cells;
    private Map<String, TabularData> tables;

    /**
     * Constructor. Initailizes mbean info and cells and tables list.
     */
    public JMXLastCubeElementDynamicMBean()
    {
        cells = new HashMap<String, Double>();
        tables = new HashMap<String, TabularData>();
        setupMBeanInfo();
    }

    /**
     * Set last element containing the cube information to render.
     * @param element is the last element
     */
    public final void setLastValue(EventBean element)
    {
        Cube cube = (Cube) element.get(ViewFieldEnum.MULTIDIM_OLAP__CUBE.getName());
        setCube(cube);
    }

    /**
     * Set the cube to display.
     * @param cube to to render
     */
    protected final void setCube(Cube cube)
    {
        cells = CubeCellStringRenderer.renderCube(cube);

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug (".setCube Cells...");
            for (String attribute : cells.keySet())
            {
                log.debug (".setCube Cell " + attribute + '=' + cells.get(attribute));
            }
        }

        tables = CubeTabularDataRenderer.renderCube(cube);

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug (".setCube Tables...");
            for (String attribute : tables.keySet())
            {
                log.debug (".setCube Table " + attribute + '=' + tables.get(attribute));
            }
        }

        setupMBeanInfo();

        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug (".setCube mBeanInfo.getAttributes().length=" + mBeanInfo.getAttributes().length);
        }
    }

    public final Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException
    {
        if (cells.containsKey(attribute))
        {
            Object value = cells.get(attribute);
            return value;
        }
        if (tables.containsKey(attribute))
        {
            return tables.get(attribute);
        }
        throw new AttributeNotFoundException("Attribute named " + attribute + " was not found");
    }

    public final void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
    {
        throw new AttributeNotFoundException("Not supported");
    }

    public final AttributeList getAttributes(String[] attributes)
    {
        return null;
    }

    public final AttributeList setAttributes(AttributeList attributes)
    {
        return null;
    }

    public final Object invoke(String actionName, Object params[], String signature[]) throws MBeanException, ReflectionException
    {
        return null;
    }

    public final MBeanInfo getMBeanInfo()
    {
        return mBeanInfo;
    }

    private void setupMBeanInfo()
    {
        // If no new attributes are added, don't need to create a new mbean info
        int newAttributeCount = cells.size() + tables.size();
        if ((mBeanInfo != null) && (newAttributeCount == mBeanInfo.getAttributes().length))
        {
            return;
        }

        List<OpenMBeanAttributeInfo> infoList = new LinkedList<OpenMBeanAttributeInfo>();

        // Create mbean attribute info for all cells
        int index = 0;
        for (Map.Entry<String, Double> entry : cells.entrySet())
        {
            OpenMBeanAttributeInfoSupport info = new OpenMBeanAttributeInfoSupport(entry.getKey(),
                "cell #" + index,  SimpleType.DOUBLE, true, false, false);
            infoList.add(info);
            index++;
        }

        // Create mbean attribute info for all tables
        index = 0;
        for (Map.Entry<String, TabularData> entry : tables.entrySet())
        {
            OpenMBeanAttributeInfoSupport info = new OpenMBeanAttributeInfoSupport(entry.getKey(),
                "table #" + index, entry.getValue().getTabularType(), true, false, false);
            infoList.add(info);
            index++;
        }

        // Construct MBeanInfo
        mBeanInfo = new OpenMBeanInfoSupport(this.getClass().getName(),
                "Exposes cube as flattened-out cells and tables (typed JMX Open Bean TabularData)",
                infoList.toArray(new OpenMBeanAttributeInfoSupport[0]),
                null, null, null);
    }

    private static final Log log = LogFactory.getLog(JMXLastCubeElementDynamicMBean.class);
}
