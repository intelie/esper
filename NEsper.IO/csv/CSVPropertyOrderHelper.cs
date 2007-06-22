using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.adapter.csv
{
    /// <summary>
    /// A utility for resolving property order information based on a
    /// propertyTypes map and the first record of a CSV file (which 
    /// might represent the title row).
    /// </summary>
    
    public class CSVPropertyOrderHelper
    {
        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary>Resolve the order of the properties that appear in the CSV file,from the first row of the CSV file.</summary>
        /// <param name="firstRow">the first record of the CSV file</param>
        /// <param name="propertyTypes">describes the event to send into the EPRuntime</param>
        /// <returns>the property names in the order in which they occur in the file</returns>

        public static String[] ResolvePropertyOrder(String[] firstRow, IDictionary<String, Type> propertyTypes)
        {
            log.Debug(".ResolvePropertyOrder firstRow==" + CollectionHelper.Render(firstRow));
            String[] result = null;

            if (IsValidTitleRow(firstRow, propertyTypes))
            {
                result = firstRow;
                log.Debug(".ResolvePropertyOrder using valid title row, propertyOrder==" + CollectionHelper.Render(result));
            }
            else
            {
                throw new EPException("Cannot resolve the order of properties in the CSV file");
            }

            return result;
        }

        private static bool IsValidTitleRow(String[] row, IDictionary<String, Type> propertyTypes)
        {
            if (propertyTypes == null)
            {
                return true;
            }
            else
            {
                return IsValidRowLength(row, propertyTypes) && EachPropertyNameRepresented(row, propertyTypes);
            }
        }

        private static bool EachPropertyNameRepresented(String[] row, IDictionary<String, Type> propertyTypes)
        {
            Set<String> rowSet = new HashSet<String>(row);
            return CollectionHelper.ContainsAll(rowSet, propertyTypes.Keys);
        }

        private static bool IsValidRowLength(String[] row, IDictionary<String, Type> propertyTypes)
        {
            log.Debug(".IsValidRowLength");
            if (row == null)
            {
                return false;
            }
            return (row.Length == propertyTypes.Count) || (row.Length == propertyTypes.Count + 1);
        }
    }
}
