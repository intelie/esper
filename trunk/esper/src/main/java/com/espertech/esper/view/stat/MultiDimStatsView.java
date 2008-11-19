/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.stat;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.util.*;

import com.espertech.esper.view.stat.olap.Cube;
import com.espertech.esper.event.*;
import com.espertech.esper.view.stat.olap.*;
import com.espertech.esper.view.*;
import com.espertech.esper.collection.SingleEventIterator;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.core.StatementContext;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.epl.expression.ExprNode;

/**
 * This view compiles OLAP cubes for the specified fields. New data from the parent view is entered into
 * one or more fact cubes (see MultidimCube). Old data from the parent view is removed from a fact cube.
 * The dimensions of the fact cube are specified as parameters to the view. The fact cube can be one-dimensional,
 * two-dimensional (tabular) or 3-dimenstional (tabular with pages).
 * Parameters are:
 *   A mandatory array of derived measure names, such as "datapoints", "stddev" etc., (see ViewFieldEnum)
 *   A mandatory measure field name. This field supplies the fact values in the cells of the cube.
 *   A mandatory column field name. This field supplies the members of dimension zero (column, 1-dim).
 *   An optional row field name. This field supplies the members of dimension one (row, 2-dim).
 *   An optional page field name. This field supplies the members of dimension two (page, 3-dim).
 *
 * The view post new data to child views that contains a Map with the Cube (see Cube). It does not post old data.
 */
public final class MultiDimStatsView extends ViewSupport implements CloneableView
{
    private EventType eventType;
    private static final MultidimCubeCellFactory<BaseStatisticsBean> multidimCubeCellFactory;

    static
    {
        multidimCubeCellFactory = new MultidimCubeCellFactory<BaseStatisticsBean>()
        {
            public BaseStatisticsBean newCell()
            {
                return new BaseStatisticsBean();
            }

            public BaseStatisticsBean[] newCells(int numElements)
            {
                return new BaseStatisticsBean[numElements];
            }
        };
    }

    private final StatementContext statementContext;
    private final String[] derivedMeasures;
    private final ExprNode measureField;
    private final ExprNode columnField;
    private final ExprNode rowField;
    private final ExprNode pageField;
    private final EventBean[] eventsPerStream = new EventBean[1];

    private MultidimCube<BaseStatisticsBean> multidimCube;

    /**
     * Constructor.
     * @param derivedMeasures is an array of ViewFieldEnum names defining the measures to derive
     * @param measureExpr defines the field supplying measures
     * @param columnExpr defines the field supplying column dimension members
     * @param rowExpr defines an optional field supplying row dimension members
     * @param pagexpr defines an optional field supplying page dimension members
     * @param statementContext contains required view services
     */
    public MultiDimStatsView(StatementContext statementContext,
                             String[] derivedMeasures, ExprNode measureExpr, ExprNode columnExpr, ExprNode rowExpr, ExprNode pagexpr)
    {
        this.statementContext = statementContext;
        this.derivedMeasures = derivedMeasures;
        this.measureField = measureExpr;
        this.columnField = columnExpr;
        this.rowField = rowExpr;
        this.pageField = pagexpr;
        eventType = createEventType(statementContext);
    }

    public View cloneView(StatementContext statementContext)
    {
        return new MultiDimStatsView(statementContext, derivedMeasures, measureField, columnField, rowField, pageField);
    }

    /**
     * Returns the names of measures to derive from facts.
     * @return measure names
     */
    public final String[] getDerivedMeasures()
    {
        return derivedMeasures;
    }

    /**
     * Returns the name of the field to extract the measure values from.
     * @return field for measure values
     */
    public final ExprNode getMeasureField()
    {
        return measureField;
    }

    /**
     * Returns the name of the field to extract the column values from.
     * @return field for column values
     */
    public final ExprNode getColumnField()
    {
        return columnField;
    }

    /**
     * Returns the name of the field to extract the row values from.
     * @return field for row values
     */
    public final ExprNode getRowField()
    {
        return rowField;
    }

    /**
     * Returns the name of the field to extract the page values from.
     * @return field for page values
     */
    public final ExprNode getPageField()
    {
        return pageField;
    }

    /**
     * For unit testing, returns fact cube.
     * @return fact cube
     */
    protected final MultidimCube<BaseStatisticsBean> getFactCube()
    {
        return multidimCube;
    }

    public final void setParent(Viewable parent)
    {
        super.setParent(parent);

        // Construct fact cube according to the number of dimensions supplied
        if (pageField != null)
        {
            String[] dimensionNames = new String[] {measureField.toExpressionString(), columnField.toExpressionString(), rowField.toExpressionString(), pageField.toExpressionString()};
            multidimCube = new MultidimCubeImpl<BaseStatisticsBean>(dimensionNames, multidimCubeCellFactory);
            multidimCube.setMembers(2, parent.getEventType().getPropertyType(pageField.toExpressionString()));
            multidimCube.setMembers(1, parent.getEventType().getPropertyType(rowField.toExpressionString()));
        }
        else if (rowField != null)
        {
            String[] dimensionNames = new String[] {measureField.toExpressionString(), columnField.toExpressionString(), rowField.toExpressionString()};
            multidimCube = new MultidimCubeImpl<BaseStatisticsBean>(dimensionNames, multidimCubeCellFactory);
            multidimCube.setMembers(1, parent.getEventType().getPropertyType(rowField.toExpressionString()));
        }
        else
        {
            String[] dimensionNames = new String[] {measureField.toExpressionString(), columnField.toExpressionString()};
            multidimCube = new MultidimCubeImpl<BaseStatisticsBean>(dimensionNames, multidimCubeCellFactory);
        }

        multidimCube.setMembers(0, columnField.getType());
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".update Received update, " +
                    "  newData.length==" + ((newData == null) ? 0 : newData.length) +
                    "  oldData.length==" + ((oldData == null) ? 0 : oldData.length));
        }

        if (newData != null)
        {
            for (EventBean newValue : newData)
            {
                processElement(newValue, true);
            }
        }

        if (oldData != null)
        {
            for (EventBean oldValue : oldData)
            {
                processElement(oldValue, false);
            }
        }

        if (hasViews())
        {
            EventBean postNewData = populateEvent();
            updateChildren(new EventBean[] { postNewData }, null);
        }
    }

    public final EventType getEventType()
    {
        return eventType;
    }

    public final Iterator<EventBean> iterator()
    {
        return new SingleEventIterator(populateEvent());
    }

    private void processElement(EventBean element, boolean isNewData)
    {
        MultiKeyUntyped coordinates = null;

        // Extract member values for each dimension
        eventsPerStream[0] = element;
        Object columnMember = columnField.evaluate(eventsPerStream, true);
        if (pageField != null)
        {
            Object rowMember = rowField.evaluate(eventsPerStream, true);
            Object pageMember = pageField.evaluate(eventsPerStream, true);
            coordinates = new MultiKeyUntyped(columnMember, rowMember, pageMember);
        }
        else if (rowField != null)
        {
            Object rowMember = rowField.evaluate(eventsPerStream, true);
            coordinates = new MultiKeyUntyped(columnMember, rowMember);
        }
        else
        {
            coordinates = new MultiKeyUntyped(columnMember);
        }

        // Extract measure value
        double measureValue = ((Number) measureField.evaluate(eventsPerStream, true)).doubleValue();

        // Add or remove from cube
        BaseStatisticsBean cell = multidimCube.getCellAddMembers(coordinates);
        if (isNewData)
        {
            cell.addPoint(measureValue);
        }
        else
        {
            cell.removePoint(measureValue);
        }
    }

    private EventBean populateEvent()
    {
        CubeImpl cube = new CubeImpl(multidimCube, derivedMeasures);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ViewFieldEnum.MULTIDIM_OLAP__CUBE.getName(), cube);
        return statementContext.getEventAdapterService().createMapFromValues(result, eventType);
    }

    /**
     * Creates the event type for this view.
     * @param statementContext is the event adapter service
     * @return event type of view
     */
    protected static EventType createEventType(StatementContext statementContext)
    {
        Map<String, Object> schemaMap = new HashMap<String, Object>();
        schemaMap.put(ViewFieldEnum.MULTIDIM_OLAP__CUBE.getName(), Cube.class);
        return statementContext.getEventAdapterService().createAnonymousMapType(schemaMap);
    }

    private static final Log log = LogFactory.getLog(MultiDimStatsView.class);
}
