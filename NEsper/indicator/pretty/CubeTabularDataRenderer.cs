using System;
using System.Collections.Generic;
using System.Data;

using net.esper.view;
using net.esper.view.stat;
using net.esper.view.stat.olap;

using net.esper.client;
using net.esper.compat;

using org.apache.commons.logging ;

namespace net.esper.indicator.pretty
{
    /// <summary>
    /// Renders a Cube multidimensional table a TabularData from the JMX OpenMbean interface.
    /// Cubes can be 1-dimenstion to n-dimensional. This implementation can render cubes up to only 4 dimensions.
    /// 4-dimensional cubes are created as multiple TabularData instances for each member in the page dimension.
    /// The cell dimension (assumed to be dimension 0) and the row dimension (assumed to be dimension 2) are both
    /// placed as rows in a table. The column dimension (dimension 1) is use for filling columns.
    /// </summary>

    public sealed class CubeTabularDataRenderer
    {
        /// <summary>Name of the table entry for cubes that are less then 4-dimensional.</summary>

        private const String TABLE_NAME = "Table";

        // The row index column name contains a (0) to match the generate column names
        private const String ROW_INDEX_COLUMN_NAME = "(0) row";
        private const String KEY_COLUMN_NAME = "key";
        private const String CELL_ROW_PRETTY_DELIMITER = "--";

        /// <summary>
        /// Render the Cube as a collection of OpenMBean TabularData tables, each table carries
        /// a string key that contains the page dimension member, if present, else just the string
        /// TABLE_NAME.
        /// <returns>
        /// map of rendered page dimension member as key and TabularData containing table
        /// as value
        /// </returns>
        /// </summary>

        public static IDictionary<String, DataTable> RenderCube(Cube cube)
        {
            IDictionary<String, DataTable> renderedCube = new LinkedDictionary<String, DataTable>();

            IList<Dimension> dimension = cube.Dimensions;
            if (dimension.Count > 4)
            {
                throw new NotSupportedException("Cannot renderCube cubes of more than 4 dimensions");
            }

            if (dimension.Count < 4)
            {
                DataTable tableData = renderTable(cube, 0);
                renderedCube[TABLE_NAME] = tableData;
            }
            else
            {
                int sizeLastDimension = dimension[3].GetMembers().Length;
                int ordinalOffset = (int)cube.Measures.Count / sizeLastDimension;

                for (int i = 0; i < sizeLastDimension; i++)
                {
                    DataTable tableData = renderTable(cube, ordinalOffset * i);
                    String renderedMember = DimensionMemberRenderHelper.renderMember(dimension[3].GetMembers()[i]);
                    String renderedTable = TABLE_NAME + " " + i + " : " + renderedMember;

                    renderedCube[renderedTable] = tableData;
                }
            }

            return renderedCube;
        }

        private static DataTable renderTable(Cube cube, int ordinalOffset)
        {
            // Create table
            DataTable tabularData = new DataTable( "CubeTable" ) ;
            foreach( DataColumn dataColumn in createDataColumns( cube ) )
            {
            	tabularData.Columns.Add( dataColumn ) ;
            }
            
            // Determine rows
            if (cube.Dimensions.Count == 2)
            {
                makeRows_1Dimensional(cube, tabularData);
            }
            else
            {
                makeRows_2Dimensional(cube, tabularData, ordinalOffset);
            }

            return tabularData;
        }

        private static void makeRows_1Dimensional( Cube cube, DataTable dataTable )
        {
            Dimension rowDimension = cube.Dimensions[0];
            Dimension columnDimension = cube.Dimensions[1];

            dataTable.BeginLoadData() ;
            
            DataColumnCollection dataColumns = dataTable.Columns;
            
            // Iterate over all rows
            DimensionMember[] rowDimensionMembers = rowDimension.GetMembers() ;
            for (int rowIndex = 0; rowIndex < rowDimensionMembers.Length; rowIndex++)
            {
            	DataRow dataRow = dataTable.NewRow() ;
            	            	
                // Store index and row key in map
                dataRow[0] = rowIndex;
                dataRow[1] = DimensionMemberRenderHelper.renderMember(rowDimensionMembers[rowIndex]);

                // Iterate over all columns
                DimensionMember[] columnDimensionMembers = columnDimension.GetMembers() ;
                for( int columnIndex = 0; columnIndex < columnDimensionMembers.Length ; columnIndex++ )
                {
                    int ordinal = columnIndex + rowIndex * columnDimensionMembers.Length;
                    double value = cube.Measures[ordinal].Value;
                    dataRow[columnIndex + 2] = value;
                }

                dataTable.Rows.Add( dataRow ) ;
            }
            
            dataTable.EndLoadData() ;
        }

        private static void makeRows_2Dimensional( Cube cube, DataTable dataTable, int ordinalOffset )
        {
            Dimension cellDimension = cube.Dimensions[0];
            Dimension columnDimension = cube.Dimensions[1];
            Dimension rowDimension = cube.Dimensions[2];

            int cellDimSize = cellDimension.GetMembers().Length;
            int columnDimSize = columnDimension.GetMembers().Length;

            dataTable.BeginLoadData() ;
            
            DataColumnCollection dataColumns = dataTable.Columns;

            // Add a row for each row and cell combination
            int index = 0;

            DimensionMember[] rowDimensionMembers = rowDimension.GetMembers() ;
            for (int rowIndex = 0; rowIndex < rowDimensionMembers.Length; rowIndex++)
            {
	            DimensionMember[] cellDimensionMembers = cellDimension.GetMembers() ;
                for (int cellIndex = 0; cellIndex < cellDimensionMembers.Length; cellIndex++)
                {
	            	DataRow dataRow = dataTable.NewRow() ;
            	            	
    	            // Store index and row key in map
        	        dataRow[0] = index;

                    String renderedRow = DimensionMemberRenderHelper.renderMember(rowDimensionMembers[rowIndex]);
                    String renderedCell = DimensionMemberRenderHelper.renderMember(cellDimensionMembers[cellIndex]);
                    String renderedRowKey = renderedRow + CELL_ROW_PRETTY_DELIMITER + renderedCell;
                    dataRow[1] = renderedRowKey;

	                DimensionMember[] columnDimensionMembers = columnDimension.GetMembers() ;
                    for (int columnIndex = 0; columnIndex < columnDimensionMembers.Length; columnIndex++)
                    {
                        int ordinal =
                        	ordinalOffset +
                            cellIndex +
                            columnIndex * cellDimSize +
                            rowIndex * cellDimSize * columnDimSize;

                        double value = cube.Measures[ordinal].Value;
                        dataRow[columnIndex + 2] = value;
                    }

                    dataTable.Rows.Add( dataRow ) ;
                    index++;
                }
            }
            
            dataTable.EndLoadData() ;
        }
        
        /// <summary>
        /// Creates dataColumns that match the names of columns as defined
        /// by the cube.
        /// </summary>
        /// <param name="cube"></param>
        /// <returns></returns>

        private static IList<DataColumn> createDataColumns( Cube cube )
        {
        	IList<DataColumn> dataColumnList = new List<DataColumn>() ;

            Dimension columnDimension = cube.Dimensions[1];
            DataColumn dataColumn = null ;;
            
            // Add a column for the numerical row index
            dataColumn = new DataColumn() ;
            dataColumn.DataType = typeof(Int32) ;
            dataColumn.ColumnName = ROW_INDEX_COLUMN_NAME;
            dataColumnList.Add( dataColumn ) ;

            // Add a column for the row key
            dataColumn = new DataColumn() ;
            dataColumn.DataType = typeof(String);
            dataColumn.ColumnName = String.Format( "{0} {1}", renderColumnId(1), KEY_COLUMN_NAME ) ;
            dataColumnList.Add( dataColumn ) ;
            
            int pseudoColumnIndex = 2 ;
            
            foreach( DimensionMember dimensionMember in columnDimension.GetMembers() )
            {
            	dataColumn = new DataColumn() ;
            	dataColumn.DataType = typeof(double);
            	dataColumn.ColumnName = String.Format(
            		"{0} {1}",
            		renderColumnId( pseudoColumnIndex ),
            		DimensionMemberRenderHelper.renderMember(dimensionMember) ) ;
            	dataColumnList.Add( dataColumn ) ;
            }

        	return dataColumnList ;
        }

        private static String renderColumnId(int columnId)
        {
            String result = "(" + columnId + ")";
            return result;
        }

        private static readonly Log log = LogFactory.GetLog(typeof(CubeTabularDataRenderer));
    }
}
