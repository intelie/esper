package net.esper.indicator.pretty;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import net.esper.view.ViewProcessingException;
import net.esper.view.stat.olap.Cube;
import net.esper.view.stat.olap.Dimension;
import net.esper.view.stat.olap.DimensionMember;
import net.esper.client.EPException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Renders a Cube multidimensional table a TabularData from the JMX OpenMbean interface.
 * Cubes can be 1-dimenstion to n-dimensional. This implementation can render cubes up to only 4 dimensions.
 * 4-dimensional cubes are created as multiple TabularData instances for each member in the page dimension.
 * The cell dimension (assumed to be dimension 0) and the row dimension (assumed to be dimension 2) are both
 * placed as rows in a table. The column dimension (dimension 1) is use for filling columns.
 */
public final class CubeTabularDataRenderer
{
    /**
     * Name of the table entry for cubes that are less then 4-dimensional.
     */
    private final static String TABLE_NAME = "Table";

    // The row index column name contains a (0) to match the generate column names
    private final static String ROW_INDEX_COLUMN_NAME = "(0) row";
    private final static String KEY_COLUMN_NAME = "key";
    private final static String CELL_ROW_PRETTY_DELIMITER = "--";

    /**
     * Render the Cube as a collection of OpenMBean TabularData tables, each table carries a string key
     * that contains the page dimension member, if present, else just the string TABLE_NAME.
     * @param cube
     * @return map of rendered page dimension member as key and TabularData containing table as value
     */
    public static Map<String, TabularData> renderCube(Cube cube)
    {
        Map<String, TabularData> renderedCube = new LinkedHashMap<String, TabularData>();

        Dimension[] dimension = cube.getDimensions();
        if (dimension.length > 4)
        {
            throw new UnsupportedOperationException("Cannot renderCube cubes of more than 4 dimensions");
        }

        if (dimension.length < 4)
        {
            TabularData tableData = renderTable(cube, 0);
            renderedCube.put(TABLE_NAME, tableData);
        }
        else
        {
            int sizeLastDimension = dimension[3].getMembers().length;
            int ordinalOffset = (int) cube.getMeasures().length / sizeLastDimension;

            for (int i = 0; i < sizeLastDimension; i++)
            {
                TabularData tableData = renderTable(cube, ordinalOffset * i);
                String renderedMember = DimensionMemberRenderHelper.renderMember(dimension[3].getMembers()[i]);
                String renderedTable = TABLE_NAME + " " + i + " : " + renderedMember;

                renderedCube.put(renderedTable, tableData);
            }
        }

        return renderedCube;
    }

    private static TabularData renderTable(Cube cube, int ordinalOffset)
    {
        // Determine column names and types
        String[] columnNames = createColumnNames(cube);
        OpenType[] columnTypes = createColumnTypes(cube);

        // Create composite type representing a row
        CompositeType rowCompositeType = null;
        try
        {
            rowCompositeType = new CompositeType("rows",
                    "rows",
                    columnNames,
                    columnNames,
                    columnTypes);
        }
        catch (OpenDataException ex)
        {
            log.fatal("Error creating row CompositeType", ex);
            throw new EPException(ex);
        }

        // Create tabular type - the index column is ROW_INDEX_COLUMN_NAME
        TabularType tabularType = null;
        try
        {
            tabularType = new TabularType("cubeType", "cube tabular rendering", rowCompositeType,
                    new String[] { ROW_INDEX_COLUMN_NAME });
        }
        catch (OpenDataException ex)
        {
            log.fatal("Error creating row TabularType", ex);
            throw new EPException(ex);
        }

        // Create table
        TabularDataSupport tabularData = new TabularDataSupport(tabularType);

        // Determine rows
        List<Map<String, Object>> rows = null;
        if (cube.getDimensions().length == 2)
        {
            rows = makeRows_1Dimensional(cube, columnNames);
        }
        else
        {
            rows = makeRows_2Dimensional(cube, columnNames, ordinalOffset);
        }

        // Add rows to table
        for (Map itemMap : rows)
        {
            CompositeDataSupport row = null;
            try
            {
                row = new CompositeDataSupport(rowCompositeType, itemMap);
            }
            catch (OpenDataException ex)
            {
                log.fatal("Error adding row", ex);
                throw new EPException(ex);
            }

            // Add row to table
            tabularData.put(row);
        }

        return tabularData;
    }

    private static List<Map<String, Object>> makeRows_1Dimensional(Cube cube,
                                                   String[] columnHeaders)
    {
        Dimension rowDimension = cube.getDimensions()[0];
        Dimension columnDimension = cube.getDimensions()[1];
        List<Map<String, Object>> rows = new LinkedList<Map<String, Object>>();

        // Iterate over all rows
        for (int rowIndex = 0; rowIndex < rowDimension.getMembers().length; rowIndex++)
        {
            Map<String, Object> rowData = new HashMap<String, Object>();

            // Store index and row key in map
            rowData.put(columnHeaders[0], rowIndex);
            String rendered = DimensionMemberRenderHelper.renderMember(rowDimension.getMembers()[rowIndex]);
            rowData.put(columnHeaders[1], rendered);

            // Iterate over all columns
            for (int columnIndex = 0; columnIndex < columnDimension.getMembers().length; columnIndex++)
            {
                String columnHeader = columnHeaders[columnIndex + 2];
                int ordinal = columnIndex + rowIndex * columnDimension.getMembers().length;
                double value = cube.getMeasures()[ordinal].getValue();
                rowData.put(columnHeader, value);
            }

            rows.add(rowData);
        }

        return rows;
    }

    private static List<Map<String, Object>> makeRows_2Dimensional(Cube cube,
                                                   String[] columnHeaders,
                                                   int ordinalOffset)
    {
        Dimension cellDimension = cube.getDimensions()[0];
        Dimension columnDimension = cube.getDimensions()[1];
        Dimension rowDimension = cube.getDimensions()[2];

        int cellDimSize = cellDimension.getMembers().length;
        int columnDimSize = columnDimension.getMembers().length;

        List<Map<String, Object>> rows = new LinkedList<Map<String, Object>>();

        // Add a row for each row and cell combination
        int index = 0;
        for (int rowIndex = 0; rowIndex < rowDimension.getMembers().length; rowIndex++)
        {
            for (int cellIndex = 0; cellIndex < cellDimension.getMembers().length; cellIndex++)
            {
                Map<String, Object> rowData = new HashMap<String, Object>();

                rowData.put(columnHeaders[0], index);

                String renderedRow = DimensionMemberRenderHelper.renderMember(rowDimension.getMembers()[rowIndex]);
                String renderedCell = DimensionMemberRenderHelper.renderMember(cellDimension.getMembers()[cellIndex]);
                String renderedRowKey = renderedRow + CELL_ROW_PRETTY_DELIMITER + renderedCell;
                rowData.put(columnHeaders[1], renderedRowKey);

                for (int columnIndex = 0; columnIndex < columnDimension.getMembers().length; columnIndex++)
                {
                    String columnHeader = columnHeaders[columnIndex + 2];

                    int ordinal = ordinalOffset +
                            cellIndex +
                            columnIndex * cellDimSize +
                            rowIndex * cellDimSize * columnDimSize;

                    double value = cube.getMeasures()[ordinal].getValue();
                    rowData.put(columnHeader, value);
                }

                rows.add(rowData);
                index++;
            }
        }

        return rows;
    }

    // Compile list of column names from dimension members
    private static String[] createColumnNames(Cube cube)
    {
        Dimension columnDimension = cube.getDimensions()[1];
        List<String> columnNameList = new LinkedList<String>();

        // Add a column for the numerical row index
        columnNameList.add(ROW_INDEX_COLUMN_NAME);

        // Add a column for the row key
        columnNameList.add(renderColumnId(1) + " " + KEY_COLUMN_NAME);

        // Add a column for each member of the column dimension
        for (int memberIndex = 0; memberIndex < columnDimension.getMembers().length; memberIndex++)
        {
            DimensionMember dimensionMember = columnDimension.getMembers()[memberIndex];
            int columnIndex = memberIndex + 2;
            String rendered = renderColumnId(columnIndex) + " " + DimensionMemberRenderHelper.renderMember(dimensionMember);
            columnNameList.add(rendered);
        }

        String[] columnNames = columnNameList.toArray(new String[0]);

        return columnNames;
    }

    // Compile list of column types from dimension members
    private static OpenType[] createColumnTypes(Cube cube)
    {
        Dimension columnDimension = cube.getDimensions()[1];
        List<OpenType> columnTypeList = new LinkedList<OpenType>();

        // Add a column for the numerical row index
        columnTypeList.add(SimpleType.INTEGER);

        // Add a column for the row key
        columnTypeList.add(SimpleType.STRING);

        // Add a column for each member of the column dimension
        for (int i = 0; i < columnDimension.getMembers().length; i++)
        {
            columnTypeList.add(SimpleType.DOUBLE);
        }

        OpenType[] columnTypes = columnTypeList.toArray(new OpenType[0]);
        return columnTypes;
    }

    private static String renderColumnId(int columnId)
    {
        String result = "(" + columnId + ")";
        return result;
    }

    private static final Log log = LogFactory.getLog(CubeTabularDataRenderer.class);
}
