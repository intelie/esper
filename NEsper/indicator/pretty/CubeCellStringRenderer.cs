using System;
using System.Collections.Generic;
using System.Text;

using net.esper.compat;
using net.esper.view.stat.olap;

namespace net.esper.indicator.pretty
{
	/// <summary> Utility class that takes a cube and renders it as a flattened table of string and double.
	/// The double value
	/// </summary>

    public sealed class CubeCellStringRenderer
    {
        private const String MEMBER_DELIMITER = "__";

        /// <summary>
        /// Render a multidimensional table (see Cube) as flatted out structure.
        /// Renders each cell's intersecting members of each dimension into a single String. 
        /// </summary>
        /// <param name="cube">the cube to render</param>
        /// <returns>map containing rendered cell/column/row/page dimension members as key and cell values as values</returns>

        public static IDictionary<String, Double> RenderCube(Cube cube)
        {
            IDictionary<String, Double> renderedCube = new LinkedDictionary<String, Double>();

            IList<Cell> measures = cube.Measures;

            for (int ordinal = 0; ordinal < measures.Count; ordinal++)
            {
                IList<DimensionMember> members = cube.GetMembers(ordinal);

                String ordinalString = String.Format("%1$4s", ordinal);
                String renderedCell = "Cell " + ordinalString + " : ";
                String renderedMember = RenderMembers(members);
                String key = renderedCell + renderedMember;

                renderedCube[key] = measures[ordinal].Value;
            }

            return renderedCube;
        }

        private static String RenderMembers(IList<DimensionMember> members)
        {
            StringBuilder buffer = new StringBuilder();
            String memberDelimiter = "";

            foreach( DimensionMember member in members )
            {
                String renderedValues = DimensionMemberRenderHelper.RenderMember(member);
                buffer.Append(memberDelimiter) ;
                buffer.Append(renderedValues);
                memberDelimiter = MEMBER_DELIMITER;
            }

            return buffer.ToString();
        }
    }
}
