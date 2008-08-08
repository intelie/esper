/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.parse;

import com.espertech.esper.epl.spec.*;
import com.espertech.esper.epl.generated.EsperEPL2GrammarParser;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.type.TimePeriodParameter;
import com.espertech.esper.type.IntParameter;
import com.espertech.esper.type.IntValue;
import com.espertech.esper.schedule.ScheduleSpec;
import com.espertech.esper.collection.Pair;
import org.antlr.runtime.tree.Tree;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Builds an output limit spec from an output limit AST node.
 */
public class ASTOutputLimitHelper
{
    /**
     * Build an output limit spec from the AST node supplied.
     *
     * @param node - parse node
     * @param astExprNodeMap is the map of current AST tree nodes to their respective expression root node
     * @param engineTime is current engine time
     * @return output limit spec
     */
    public static OutputLimitSpec buildOutputLimitSpec(Tree node, long engineTime, Map<Tree, ExprNode> astExprNodeMap)
    {
        int count = 0;
        Tree child = node.getChild(count);

        // parse type
        OutputLimitLimitType displayLimit = OutputLimitLimitType.DEFAULT;
        if (child.getType() == EsperEPL2GrammarParser.FIRST)
        {
            displayLimit = OutputLimitLimitType.FIRST;
            child = node.getChild(++count);
        }
        else if (child.getType() == EsperEPL2GrammarParser.LAST)
        {
            displayLimit = OutputLimitLimitType.LAST;
            child = node.getChild(++count);
        }
        else if (child.getType() == EsperEPL2GrammarParser.SNAPSHOT)
        {
            displayLimit = OutputLimitLimitType.SNAPSHOT;
            child = node.getChild(++count);
        }
        else if (child.getType() == EsperEPL2GrammarParser.ALL)
        {
            displayLimit = OutputLimitLimitType.ALL;
            child = node.getChild(++count);
        }

        // next is a variable, or time period, or number
        String variableName = null;
        double rate = -1;
        ExprNode whenExpression = null;
        Object[] crontabScheduleSpec = null;
        List<OnTriggerSetAssignment> thenExpressions = null;

        if (node.getType() == EsperEPL2GrammarParser.WHEN_LIMIT_EXPR)
        {
            Tree expressionNode = node.getChild(0);
            whenExpression = astExprNodeMap.remove(expressionNode);
            if (node.getChildCount() > 1)
            {
                thenExpressions = EPLTreeWalker.getOnTriggerSetAssignments(node.getChild(1), astExprNodeMap);
            }
        }
        else if (node.getType() == EsperEPL2GrammarParser.CRONTAB_LIMIT_EXPR)
        {
            Tree parent = node.getChild(0);
            if (parent.getType() != EsperEPL2GrammarParser.CRONTAB_LIMIT_EXPR_PARAM)
            {
                parent = node.getChild(1);
            }

            List<Object> parameters = new ArrayList<Object>(parent.getChildCount());
            for (int i = 0; i < parent.getChildCount(); i++)
            {
                Tree childNode = parent.getChild(i);
                Object object = ASTParameterHelper.makeParameter(childNode, engineTime);
                parameters.add(object);
            }
            crontabScheduleSpec = parameters.toArray();
        }
        else
        {
            if (child.getType() == EsperEPL2GrammarParser.IDENT)
            {
                variableName = child.getText();
            }
            else if (child.getType() == EsperEPL2GrammarParser.TIME_PERIOD)
            {
                TimePeriodParameter param = ASTParameterHelper.makeTimePeriod(child, 0L);
                rate = param.getNumSeconds();
            }
            else
            {
                rate = Double.parseDouble(child.getText());
            }
        }

        switch (node.getType())
        {
            case EsperEPL2GrammarParser.EVENT_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.EVENTS, displayLimit, null, null, null);
            case EsperEPL2GrammarParser.SEC_LIMIT_EXPR:
            case EsperEPL2GrammarParser.TIMEPERIOD_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.TIME_SEC, displayLimit, null, null, null);
            case EsperEPL2GrammarParser.MIN_LIMIT_EXPR:
                return new OutputLimitSpec(rate, variableName, OutputLimitRateType.TIME_MIN, displayLimit, null, null, null);
            case EsperEPL2GrammarParser.CRONTAB_LIMIT_EXPR:
                return new OutputLimitSpec(null, null, OutputLimitRateType.CRONTAB, displayLimit, null, null, crontabScheduleSpec);
            case EsperEPL2GrammarParser.WHEN_LIMIT_EXPR:
                return new OutputLimitSpec(null, null, OutputLimitRateType.WHEN_EXPRESSION, displayLimit, whenExpression, thenExpressions, null);
            default:
                throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized output limit type");
		 }
	}

    /**
     * Builds a row limit specification.
     * @param node to interrogate
     * @return row limit spec
     */
    public static RowLimitSpec buildRowLimitSpec(Tree node)
    {
        Object numRows;
        Object offset;

        if (node.getChildCount() == 1)
        {
            numRows = parseNumOrVariableIdent(node.getChild(0));
            offset = null;
        }
        else
        {
            if (node.getChild(node.getChildCount()- 1).getType() == EsperEPL2GrammarParser.COMMA)
            {
                offset = parseNumOrVariableIdent(node.getChild(0));
                numRows = parseNumOrVariableIdent(node.getChild(1));
            }
            else
            {
                numRows = parseNumOrVariableIdent(node.getChild(0));
                offset = parseNumOrVariableIdent(node.getChild(1));
            }
        }

        Integer numRowsInt = null;
        String numRowsVariable = null;
        if (numRows instanceof String)
        {
            numRowsVariable = (String) numRows;
        }
        else
        {
            numRowsInt = (Integer) numRows;
        }

        Integer offsetInt = null;
        String offsetVariable = null;
        if (offset instanceof String)
        {
            offsetVariable = (String) offset;
        }
        else
        {
            offsetInt = (Integer) offset;
        }

        return new RowLimitSpec(numRowsInt, offsetInt, numRowsVariable, offsetVariable);        
    }

    private static Object parseNumOrVariableIdent(Tree child)
    {
        if (child.getType() == EsperEPL2GrammarParser.IDENT)
        {
            return child.getText();
        }
        else
        {
            return IntValue.parseString(child.getText());
        }
    }
}
