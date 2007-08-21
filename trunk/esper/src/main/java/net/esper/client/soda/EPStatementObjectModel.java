package net.esper.client.soda;

import java.io.Serializable;
import java.util.List;

public class EPStatementObjectModel implements Serializable
{
    private InsertInto insertInto;
    private SelectClause selectClause;
    private List<ProjectedStream> streams;
    private Expression whereClause;

    public EPStatementObjectModel()
    {
    }

    public EPStatementObjectModel addInsertInto(InsertInto insertInto)
    {
        this.insertInto = insertInto;
        return this;
    }

    public InsertInto getInsertInto()
    {
        return insertInto;
    }

    public void setSelectClause(SelectClause selectClause)
    {
        this.selectClause = selectClause;
    }

    public SelectClause getSelectClause()
    {
        return selectClause;
    }

    public EPStatementObjectModel addSelectClause(SelectClause selectClause)
    {
        selectClause.add(selectClause);
        return this;
    }

    public EPStatementObjectModel addStream(ProjectedStream stream)
    {
        streams.add(stream);
        return this;
    }

    public List<ProjectedStream> getStreams()
    {
        return streams;
    }

    public void setStreams(List<ProjectedStream> streams)
    {
        this.streams = streams;
    }

    public Expression getWhereClause()
    {
        return whereClause;
    }

    public void addWhereClause(Expression whereClause)
    {
        this.whereClause = whereClause;
    }
}
