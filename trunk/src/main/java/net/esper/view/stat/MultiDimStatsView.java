package net.esper.view.stat;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.util.*;

import net.esper.view.stat.olap.Cube;
import net.esper.event.*;
import net.esper.view.stat.olap.*;
import net.esper.view.*;
import net.esper.collection.SingleEventIterator;
import net.esper.collection.MultiKeyUntyped;

/**
 * This view compiles OLAP cubes for the specified fields. New data from the parent view is entered into
 * one or more fact cubes (see MultidimCube). Old data from the parent view is removed from a fact cube.
 * The dimensions of the fact cube are specified as parameters to the view. The fact cube can be one-dimensional,
 * two-dimensional (tabular) or 3-dimenstional (tabular with pages).
 * Parameters are:
 *   A mandatory array of derived measure names, such as "count", "stddev" etc., (see ViewFieldEnum)
 *   A mandatory measure field name. This field supplies the fact values in the cells of the cube.
 *   A mandatory column field name. This field supplies the members of dimension zero (column, 1-dim).
 *   An optional row field name. This field supplies the members of dimension one (row, 2-dim).
 *   An optional page field name. This field supplies the members of dimension two (page, 3-dim).
 *
 * The view post new data to child views that contains a Map with the Cube (see Cube). It does not post old data.
 */
public final class MultiDimStatsView extends ViewSupport implements ContextAwareView
{
    private ViewServiceContext viewServiceContext;
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

    private String[] derivedMeasures;
    private String measureField;
    private String columnField;
    private String rowField;
    private String pageField;

    private EventPropertyGetter measureFieldGetter;
    private EventPropertyGetter columnFieldGetter;
    private EventPropertyGetter rowFieldGetter;
    private EventPropertyGetter pageFieldGetter;

    private MultidimCube<BaseStatisticsBean> multidimCube;

    /**
     * Empty constructor - views are Java beans.
     */
    public MultiDimStatsView()
    {
    }

    public ViewServiceContext getViewServiceContext()
    {
        return viewServiceContext;
    }

    public void setViewServiceContext(ViewServiceContext viewServiceContext)
    {
        this.viewServiceContext = viewServiceContext;

        Map<String, Class> schemaMap = new HashMap<String, Class>();
        schemaMap.put(ViewFieldEnum.MULTIDIM_OLAP__CUBE.getName(), Cube.class);
        eventType = viewServiceContext.getEventAdapterService().createAnonymousMapType(schemaMap);
    }

    /**
     * Constructor.
     * @param derivedMeasures is an array of ViewFieldEnum names defining the measures to derive
     * @param measureField defines the field supplying measures
     * @param columnField defines the field supplying column dimension members
     * @param rowField defines an optional field supplying row dimension members
     * @param pageField defines an optional field supplying page dimension members
     */
    public MultiDimStatsView(String[] derivedMeasures, String measureField, String columnField, String rowField, String pageField)
    {
        this.derivedMeasures = derivedMeasures;
        this.measureField = measureField;
        this.columnField = columnField;
        this.rowField = rowField;
        this.pageField = pageField;
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
     * Sets the names of measures to derive from facts.
     * @param derivedMeasures measure names
     */
    public final void setDerivedMeasures(String[] derivedMeasures)
    {
        this.derivedMeasures = derivedMeasures;
    }

    /**
     * Returns the name of the field to extract the measure values from.
     * @return field for measure values
     */
    public final String getMeasureField()
    {
        return measureField;
    }

    /**
     * Sets the name of the field to extract the measure values from.
     * @param measureField field for measure values
     */
    public final void setMeasureField(String measureField)
    {
        this.measureField = measureField;
    }

    /**
     * Returns the name of the field to extract the column values from.
     * @return field for column values
     */
    public final String getColumnField()
    {
        return columnField;
    }

    /**
     * Sets the name of the field to extract the column values from.
     * @param columnField field for column values
     */
    public final void setColumnField(String columnField)
    {
        this.columnField = columnField;
    }

    /**
     * Returns the name of the field to extract the row values from.
     * @return field for row values
     */
    public final String getRowField()
    {
        return rowField;
    }

    /**
     * Sets the name of the field to extract the row values from.
     * @param rowField field for row values
     */
    public final void setRowField(String rowField)
    {
        this.rowField = rowField;
    }

    /**
     * Returns the name of the field to extract the page values from.
     * @return field for page values
     */
    public final String getPageField()
    {
        return pageField;
    }

    /**
     * Sets the name of the field to extract the page values from.
     * @param pageField field for page values
     */
    public final void setPageField(String pageField)
    {
        this.pageField = pageField;
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

        if (parent == null)
        {
            return;
        }

        measureFieldGetter = parent.getEventType().getGetter(measureField);
        columnFieldGetter = parent.getEventType().getGetter(columnField);

        if (rowField != null)
        {
            rowFieldGetter = parent.getEventType().getGetter(rowField);
        }

        if (pageField != null)
        {
            pageFieldGetter = parent.getEventType().getGetter(pageField);
        }

        // Construct fact cube according to the number of dimensions supplied
        if (pageField != null)
        {
            String[] dimensionNames = new String[] {measureField, columnField, rowField, pageField};
            multidimCube = new MultidimCubeImpl<BaseStatisticsBean>(dimensionNames, multidimCubeCellFactory);
            multidimCube.setMembers(2, parent.getEventType().getPropertyType(pageField));
            multidimCube.setMembers(1, parent.getEventType().getPropertyType(rowField));
        }
        else if (rowField != null)
        {
            String[] dimensionNames = new String[] {measureField, columnField, rowField};
            multidimCube = new MultidimCubeImpl<BaseStatisticsBean>(dimensionNames, multidimCubeCellFactory);
            multidimCube.setMembers(1, parent.getEventType().getPropertyType(rowField));
        }
        else
        {
            String[] dimensionNames = new String[] {measureField, columnField};
            multidimCube = new MultidimCubeImpl<BaseStatisticsBean>(dimensionNames, multidimCubeCellFactory);
        }

        multidimCube.setMembers(0, parent.getEventType().getPropertyType(columnField));
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        if (log.isDebugEnabled())
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
        Object columnMember = columnFieldGetter.get(element);
        if (pageFieldGetter != null)
        {
            Object rowMember = rowFieldGetter.get(element);
            Object pageMember = pageFieldGetter.get(element);
            coordinates = new MultiKeyUntyped(columnMember, rowMember, pageMember);
        }
        else if (rowFieldGetter != null)
        {
            Object rowMember = rowFieldGetter.get(element);
            coordinates = new MultiKeyUntyped(columnMember, rowMember);
        }
        else
        {
            coordinates = new MultiKeyUntyped(columnMember);
        }

        // Extract measure value
        double measureValue = ((Number) measureFieldGetter.get(element)).doubleValue();

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
        EventBean eventBean = viewServiceContext.getEventAdapterService().createMapFromValues(result, eventType);
        return eventBean;
    }

    private static final Log log = LogFactory.getLog(MultiDimStatsView.class);
}
