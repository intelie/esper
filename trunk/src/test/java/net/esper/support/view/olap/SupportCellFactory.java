package net.esper.support.view.olap;

import net.esper.view.stat.olap.MultidimCubeCellFactory;

public class SupportCellFactory implements MultidimCubeCellFactory<SupportCell>
{
    public SupportCell newCell()
    {
        return new SupportCell();
    }

    public SupportCell[] newCells(int numElements)
    {
        return new SupportCell[numElements];
    }
}
