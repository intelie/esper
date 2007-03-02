using System;

using net.esper.view.stat.olap;

namespace net.esper.support.view.olap
{
    public class SupportCellFactory : MultidimCubeCellFactory<SupportCell>
	{
		public virtual SupportCell NewCell()
		{
			return new SupportCell();
		}
		
		public virtual SupportCell[] NewCells(int numElements)
		{
			return new SupportCell[numElements];
		}
	}
}
