/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import net.esper.eql.spec.SubstitutionParameterExpression;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;

/**
 * Object model of an EQL statement.
 * <p>
 * Applications can create an object model by instantiating this class and then setting the various clauses.
 * When done, use {@link net.esper.client.EPAdministrator} to create a statement from the model.
 * <p>
 * Alternativly, a given textual EQL can be compiled into an object model representation via the compile method on
 * {@link net.esper.client.EPAdministrator}.
 * <p>
 * Use the toEQL method to generate a textual EQL from an object model. 
 * <p>
 * Minimally, and EQL statement consists of the select-clause and the where-clause. These are represented by {@link SelectClause}
 * and {@link FromClause} respectively.
 * <p>
 * Here is a short example that create a simple EQL statement such as "select page, responseTime from PageLoad" :
 * <pre>
 * EPStatementObjectModel model = new EPStatementObjectModel();
 * model.setSelectClause(SelectClause.create("page", "responseTime"));
 * model.setFromClause(FromClause.create(FilterStream.create("PageLoad")));
 * </pre>
 * <p>
 * The select-clause and from-clause must be set for the statement object model to be useable by the
 * administrative API. All other clauses a optional.
 * <p>
 * Please see the documentation set for further examples.
 */
public class EPStatementObjectModel implements Serializable
{
    private static final long serialVersionUID = 0L;

    private InsertIntoClause insertInto;
    private SelectClause selectClause;
    private FromClause fromClause;
    private Expression whereClause;
    private GroupByClause groupByClause;
    private Expression havingClause;
    private OutputLimitClause outputLimitClause;
    private OrderByClause orderByClause;

    private List<SubstitutionParameterExpression> substitutions = new ArrayList<SubstitutionParameterExpression>();

    /**
     * Ctor.
     */
    public EPStatementObjectModel()
    {
    }

    /**
     * Specify an insert-into-clause.
     * @param insertInto specifies the insert-into-clause, or null to indicate that the clause is absent
     * @return model
     */
    public EPStatementObjectModel setInsertInto(InsertIntoClause insertInto)
    {
        this.insertInto = insertInto;
        return this;
    }

    /**
     * Return the insert-into-clause, or null to indicate that the clause is absent.
     * @return specification of the insert-into-clause, or null if none present
     */
    public InsertIntoClause getInsertInto()
    {
        return insertInto;
    }

    /**
     * Specify a select-clause.
     * @param selectClause specifies the select-clause, the select-clause cannot be null and must be set
     * @return model
     */
    public EPStatementObjectModel setSelectClause(SelectClause selectClause)
    {
        this.selectClause = selectClause;
        return this;
    }

    /**
     * Return the select-clause.
     * @return specification of the select-clause
     */
    public SelectClause getSelectClause()
    {
        return selectClause;
    }

    /**
     * Specify a from-clause.
     * @param fromClause specifies the from-clause, the from-clause cannot be null and must be set
     * @return model
     */
    public EPStatementObjectModel setFromClause(FromClause fromClause)
    {
        this.fromClause = fromClause;
        return this;
    }

    /**
     * Return the where-clause, or null to indicate that the clause is absent.
     * @return specification of the where-clause, or null if none present
     */
    public Expression getWhereClause()
    {
        return whereClause;
    }

    /**
     * Specify a where-clause.
     * @param whereClause specifies the where-clause, which is optional and can be null
     * @return model
     */
    public EPStatementObjectModel setWhereClause(Expression whereClause)
    {
        this.whereClause = whereClause;
        return this;
    }

    /**
     * Return the from-clause.
     * @return specification of the from-clause
     */
    public FromClause getFromClause()
    {
        return fromClause;
    }

    /**
     * Return the group-by-clause, or null to indicate that the clause is absent.
     * @return specification of the group-by-clause, or null if none present
     */
    public GroupByClause getGroupByClause()
    {
        return groupByClause;
    }

    /**
     * Specify a group-by-clause.
     * @param groupByClause specifies the group-by-clause, which is optional and can be null
     * @return model
     */
    public EPStatementObjectModel setGroupByClause(GroupByClause groupByClause)
    {
        this.groupByClause = groupByClause;
        return this;
    }

    /**
     * Return the having-clause, or null to indicate that the clause is absent.
     * @return specification of the having-clause, or null if none present
     */
    public Expression getHavingClause()
    {
        return havingClause;
    }

    /**
     * Specify a having-clause.
     * @param havingClause specifies the having-clause, which is optional and can be null
     * @return model
     */
    public EPStatementObjectModel setHavingClause(Expression havingClause)
    {
        this.havingClause = havingClause;
        return this;
    }

    /**
     * Return the order-by-clause, or null to indicate that the clause is absent.
     * @return specification of the order-by-clause, or null if none present
     */
    public OrderByClause getOrderByClause()
    {
        return orderByClause;
    }

    /**
     * Specify an order-by-clause.
     * @param orderByClause specifies the order-by-clause, which is optional and can be null
     * @return model
     */
    public EPStatementObjectModel setOrderByClause(OrderByClause orderByClause)
    {
        this.orderByClause = orderByClause;
        return this;
    }

    /**
     * Return the output-rate-limiting-clause, or null to indicate that the clause is absent.
     * @return specification of the output-rate-limiting-clause, or null if none present
     */
    public OutputLimitClause getOutputLimitClause()
    {
        return outputLimitClause;
    }

    /**
     * Specify an output-rate-limiting-clause.
     * @param outputLimitClause specifies the output-rate-limiting-clause, which is optional and can be null
     * @return model
     */
    public EPStatementObjectModel setOutputLimitClause(OutputLimitClause outputLimitClause)
    {
        this.outputLimitClause = outputLimitClause;
        return this;
    }

    /**
     * Renders the object model in it's EQL syntax textual representation.
     * @return EQL representing the statement object model
     * @throws IllegalStateException if required clauses do not exist
     */
    public String toEQL()
    {
        StringWriter writer = new StringWriter();

        if (selectClause == null)
        {
            throw new IllegalStateException("Select-clause has not been defined");
        }
        if (fromClause == null)
        {
            throw new IllegalStateException("From-clause has not been defined");
        }

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
