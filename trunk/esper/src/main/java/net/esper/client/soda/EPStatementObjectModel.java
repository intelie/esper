package net.esper.client.soda;

import net.esper.eql.spec.StreamSpecRaw;
import net.esper.eql.spec.FilterStreamSpecRaw;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

public class EPStatementObjectModel implements Serializable
{
    private InsertInto insertInto;
    private SelectClause selectClause;
    private FromClause fromClause;
    private Expression whereClause;
    private GroupByClause groupByClause;
    private Expression havingClause;
    private OutputLimitClause outputLimitClause;
    private OrderByClause orderByClause;

    public EPStatementObjectModel()
    {
    }

    public EPStatementObjectModel setInsertInto(InsertInto insertInto)
    {
        this.insertInto = insertInto;
        return this;
    }

    public InsertInto getInsertInto()
    {
        return insertInto;
    }

    public EPStatementObjectModel setSelectClause(SelectClause selectClause)
    {
        this.selectClause = selectClause;
        return this;
    }

    public SelectClause getSelectClause()
    {
        return selectClause;
    }

    public EPStatementObjectModel setFromClause(FromClause fromClause)
    {
        this.fromClause = fromClause;
        return this;
    }

    public Expression getWhereClause()
    {
        return whereClause;
    }

    public EPStatementObjectModel setWhereClause(Expression whereClause)
    {
        this.whereClause = whereClause;
        return this;
    }

    public FromClause getFromClause()
    {
        return fromClause;
    }

    public GroupByClause getGroupByClause()
    {
        return groupByClause;
    }

    public void setGroupByClause(GroupByClause groupByClause)
    {
        this.groupByClause = groupByClause;
    }

    public Expression getHavingClause()
    {
        return havingClause;
    }

    public void setHavingClause(Expression havingClause)
    {
        this.havingClause = havingClause;
    }

    public OrderByClause getOrderByClause()
    {
        return orderByClause;
    }

    public void setOrderByClause(OrderByClause orderByClause)
    {
        this.orderByClause = orderByClause;
    }

    public OutputLimitClause getOutputLimitClause()
    {
        return outputLimitClause;
    }

    public void setOutputLimitClause(OutputLimitClause outputLimitClause)
    {
        this.outputLimitClause = outputLimitClause;
    }

    public String toEQL()
    {
        StringWriter writer = new StringWriter();

        if (insertInto != null)
        {
            insertInto.toEQL(writer);
        }
        selectClause.toEQL(writer);
        fromClause.toEQL(writer);
        if (whereClause != null)
        {
            writer.write(" where ");
            whereClause.toEQL(writer);
        }
        if (groupByClause != null)
        {
            writer.write(" group by ");
            groupByClause.toEQL(writer);
        }
        if (havingClause != null)
        {
            writer.write(" having ");
            havingClause.toEQL(writer);
        }
        if (outputLimitClause != null)
        {
            writer.write(" output ");
            outputLimitClause.toEQL(writer);
        }
        if (orderByClause != null)
        {
            writer.write(" order by ");
            orderByClause.toEQL(writer);
        }

        return writer.toString();
    }
}
