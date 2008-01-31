/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.eql.parse;

import com.espertech.esper.type.*;
import com.espertech.esper.eql.generated.EsperEPL2GrammarParser;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.antlr.runtime.tree.Tree;

/**
 * Parse AST parameter nodes including constants, arrays, lists.
 * Distinguishes between uniform and non-uniform arrays.
 */
public class ASTParameterHelper
{

    /**
     * Returns the parse Object for the parameter/constant AST node whose text to parse.
     * @param parameterNode - AST node to parse
     * @param engineTime the engine current time
     * @return object value
     * @throws ASTWalkException is thrown to indicate a parse error
     */
    public static Object makeParameter(Tree parameterNode, long engineTime) throws ASTWalkException
    {
        return parseConstant(parameterNode, engineTime);
    }

    private static Object parseConstant(Tree node, long engineTime) throws ASTWalkException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".parseConstant Node type=" + node.getType() + " text=" + node.getText());
        }

        switch(node.getType())
        {
            case EsperEPL2GrammarParser.NUM_INT:
            case EsperEPL2GrammarParser.INT_TYPE:
            case EsperEPL2GrammarParser.LONG_TYPE:
            case EsperEPL2GrammarParser.BOOL_TYPE:
            case EsperEPL2GrammarParser.FLOAT_TYPE:
            case EsperEPL2GrammarParser.DOUBLE_TYPE:
            case EsperEPL2GrammarParser.STRING_TYPE:               return ASTConstantHelper.parse(node);
            case EsperEPL2GrammarParser.NUMERIC_PARAM_FREQUENCY:   return makeFrequency(node);
            case EsperEPL2GrammarParser.NUMERIC_PARAM_RANGE:       return makeRange(node);
            case EsperEPL2GrammarParser.LAST:
            case EsperEPL2GrammarParser.LW:
            case EsperEPL2GrammarParser.WEEKDAY_OPERATOR:
            case EsperEPL2GrammarParser.LAST_OPERATOR:             return makeCronParameter(node, engineTime);
            case EsperEPL2GrammarParser.STAR:                      return new WildcardParameter();
            case EsperEPL2GrammarParser.NUMERIC_PARAM_LIST:        return makeList(node, engineTime);
            case EsperEPL2GrammarParser.ARRAY_PARAM_LIST:          return makeArray(node, engineTime);
            case EsperEPL2GrammarParser.TIME_PERIOD:               return makeTimePeriod(node, engineTime);
            default:
                throw new ASTWalkException("Unexpected constant of type " + node.getType() + " encountered");
        }
    }

    private static TimePeriodParameter makeTimePeriod(Tree node, long engineTime)
    {
        double result = 0;
        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            Number numValue = (Number) parseConstant(child.getChild(0), engineTime);
            double partValue = numValue.doubleValue();

            switch (child.getType())
            {
                case EsperEPL2GrammarParser.MILLISECOND_PART :
                    result += partValue / 1000d;
                    break;
                case EsperEPL2GrammarParser.SECOND_PART :
                    result += partValue;
                    break;
                case EsperEPL2GrammarParser.MINUTE_PART :
                    result += 60 * partValue;
                    break;
                case EsperEPL2GrammarParser.HOUR_PART :
                    result += 60 * 60 * partValue;
                    break;
                case EsperEPL2GrammarParser.DAY_PART :
                    result += 24 * 60 * 60 * partValue;
                    break;
                default:
                    throw new IllegalStateException("Illegal part of interval encountered, type=" + child.getType() + " text=" + child.getText());
            }
        }

        return new TimePeriodParameter(result);
    }

    private static Object makeList(Tree node, long engineTime) throws ASTWalkException
    {
        ListParameter list = new ListParameter();

        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            Object parsedChild = parseConstant(child, engineTime);

            if (parsedChild instanceof Integer)
            {
                list.add(new IntParameter((Integer) parsedChild));
            }
            else
            {
                list.add((NumberSetParameter) parsedChild);
            }
        }

        return list;
    }

    private static Object makeFrequency(Tree node)
    {
        int frequency = IntValue.parseString(node.getChild(0).getText());
        return new FrequencyParameter(frequency);
    }

    private static Object makeRange(Tree node)
    {
        int low = IntValue.parseString(node.getChild(0).getText());
        int high = IntValue.parseString(node.getChild(1).getText());
        return new RangeParameter(low, high);
    }

    private static Object makeCronParameter(Tree node, long engineTime)
    {
       if (node.getChild(0) == null) {
          return new CronParameter(node.getType(), null, engineTime);
       }
       else {
          return new CronParameter(node.getType(), node.getChild(0).getText(), engineTime);
       }
    }

    private static Object makeArray(Tree node, long engineTime) throws ASTWalkException
    {
        // Determine the distinct node types in the AST
        Set<Integer> nodeTypes = new HashSet<Integer>();

        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree childNode = node.getChild(i);
            nodeTypes.add(childNode.getType());
        }

        if (nodeTypes.isEmpty())
        {
            return new Object[0];
        }
        else if (nodeTypes.size() == 1)
        {
            return makeUniform(node);
        }
        else
        {
            return makeNonUniform(node, engineTime);
        }
    }

    private static Object makeNonUniform(Tree node, long engineTime) throws ASTWalkException
    {
        int count = node.getChildCount();
        Object[] result = new Object[count];

        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            result[i] = parseConstant(child, engineTime);
        }

        return result;
    }

    private static Object makeUniform(Tree node) throws ASTWalkException
    {
        int count = node.getChildCount();
        String[] values = new String[count];

        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            values[i] = child.getText();
        }

        return parseStringArray(node.getChild(0).getType(), values);
    }

    private static Object parseStringArray(int nodeType, String[] nodeValues) throws ASTWalkException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".parseStringArray Node type=" + nodeType + " values=" + Arrays.toString(nodeValues));
        }

        switch(nodeType)
        {
            case EsperEPL2GrammarParser.INT_TYPE:  return IntValue.parseString(nodeValues);
            case EsperEPL2GrammarParser.LONG_TYPE:  return LongValue.parseString(nodeValues);
            case EsperEPL2GrammarParser.BOOL_TYPE:  return BoolValue.parseString(nodeValues);
            case EsperEPL2GrammarParser.FLOAT_TYPE:  return FloatValue.parseString(nodeValues);
            case EsperEPL2GrammarParser.DOUBLE_TYPE:  return DoubleValue.parseString(nodeValues);
            case EsperEPL2GrammarParser.STRING_TYPE:  return StringValue.parseString(nodeValues);
            default:
                throw new IllegalStateException("Unexpected constant of type " + nodeType + " encountered");
        }
    }

    private static final Log log = LogFactory.getLog(ASTParameterHelper.class);
}
