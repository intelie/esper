package net.esper.filter;

import net.esper.eql.expression.ExprNode;

import java.util.*;

public class FilterParamExprMap
{
    private Map<ExprNode, FilterSpecParam> exprNodes;
    private Map<FilterSpecParam, ExprNode> specParams;

    public FilterParamExprMap()
    {
        exprNodes = new HashMap<ExprNode, FilterSpecParam>();
        specParams = new HashMap<FilterSpecParam, ExprNode>();
    }

    public void put(ExprNode exprNode, FilterSpecParam param)
    {
        exprNodes.put(exprNode, param);
        if (param != null)
        {
            specParams.put(param, exprNode);
        }        
    }

    public List<ExprNode> getUnassignedExpressions()
    {
        List<ExprNode> unassigned = new ArrayList<ExprNode>();
        for (ExprNode exprNode : exprNodes.keySet())
        {
            if (exprNodes.get(exprNode) == null)
            {
                unassigned.add(exprNode);
            }
        }
        return unassigned;
    }

    public Collection<FilterSpecParam> getFilterParams()
    {
        return specParams.keySet();
    }

    public void removeEntry(FilterSpecParam param)
    {
        ExprNode exprNode = specParams.get(param);
        if (exprNode == null)
        {
            throw new IllegalStateException("Not found in collection param: " + param);
        }

        specParams.remove(param);
        exprNodes.remove(exprNode);
    }

    public void removeValue(FilterSpecParam param)
    {
        ExprNode exprNode = specParams.get(param);
        if (exprNode == null)
        {
            throw new IllegalStateException("Not found in collection param: " + param);
        }

        specParams.remove(param);
        exprNodes.put(exprNode, null);
    }
}
