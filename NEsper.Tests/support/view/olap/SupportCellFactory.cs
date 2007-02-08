using System;

using net.esper.view.stat.olap;

namespace net.esper.support.view.olap
{
    public class SupportCellFactory : MultidimCubeCellFactory<SupportCell>
	{
		public virtual SupportCell newCell()
		{
			return new SupportCell();
		}
		
		public virtual SupportCell[] newCells(int numElements)
		{
			return new SupportCell[numElements];
		}
	}
}
