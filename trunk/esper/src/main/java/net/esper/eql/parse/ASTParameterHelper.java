/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import net.esper.type.*;
import net.esper.eql.generated.EsperEPLParser;

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
     * @return object value
     * @throws ASTWalkException is thrown to indicate a parse error
     */
    public static Object makeParameter(Tree parameterNode) throws ASTWalkException
    {
        return parseConstant(parameterNode);
    }

    private static Object parseConstant(Tree node) throws ASTWalkException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".parseConstant Node type=" + node.getType() + " text=" + node.getText());
        }

        switch(node.getType())
        {
            case EsperEPLParser.NUM_INT:
            case EsperEPLParser.INT_TYPE:
            case EsperEPLParser.LONG_TYPE:
            case EsperEPLParser.BOOL_TYPE:
            case EsperEPLParser.FLOAT_TYPE:
            case EsperEPLParser.DOUBLE_TYPE:
            case EsperEPLParser.STRING_TYPE:               return ASTConstantHelper.parse(node);
            case EsperEPLParser.NUMERIC_PARAM_FREQUENCY:   return makeFrequency(node);
            case EsperEPLParser.NUMERIC_PARAM_RANGE:       return makeRange(node);
            case EsperEPLParser.LAST:
            case EsperEPLParser.LW:
            case EsperEPLParser.WEEKDAY_OPERATOR:
            case EsperEPLParser.LAST_OPERATOR:             return makeCronParameter(node);
            case EsperEPLParser.STAR:                      return new WildcardParameter();
            case EsperEPLParser.NUMERIC_PARAM_LIST:        return makeList(node);
            case EsperEPLParser.ARRAY_PARAM_LIST:          return makeArray(node);
            case EsperEPLParser.TIME_PERIOD:               return makeTimePeriod(node);
            default:
                throw new ASTWalkException("Unexpected constant of type " + node.getType() + " encountered");
        }
    }

    private static TimePeriodParameter makeTimePeriod(Tree node)
    {
        double result = 0;
        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            Number numValue = (Number) parseConstant(child.getChild(0));
            double partValue = numValue.doubleValue();

            switch (child.getType())
            {
                case EsperEPLParser.MILLISECOND_PART :
                    result += partValue / 1000d;
                    break;
                case EsperEPLParser.SECOND_PART :
                    result += partValue;
                    break;
                case EsperEPLParser.MINUTE_PART :
                    result += 60 * partValue;
                    break;
                case EsperEPLParser.HOUR_PART :
                    result += 60 * 60 * partValue;
                    break;
                case EsperEPLParser.DAY_PART :
                    result += 24 * 60 * 60 * partValue;
                    break;
                default:
                    throw new IllegalStateException("Illegal part of interval encountered, type=" + child.getType() + " text=" + child.getText());
            }
        }

        return new TimePeriodParameter(result);
    }

    private static Object makeList(Tree node) throws ASTWalkException
    {
        ListParameter list = new ListParameter();

        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            Object parsedChild = parseConstant(child);

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

    private static Object makeCronParameter(Tree node)
    {
       if (node.getChild(0) == null) {
           return new CronParameter(node.getText(), null);
       } else {
        return new CronParameter(node.getText(), node.getChild(0).getText());
       }
    }

    private static Object makeArray(Tree node) throws ASTWalkException
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
            return makeNonUniform(node);
        }
    }

    private static Object makeNonUniform(Tree node) throws ASTWalkException
    {
        int count = node.getChildCount();
        Object[] result = new Object[count];

        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            result[i] = parseConstant(child);
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
            case EsperEPLParser.INT_TYPE:  return IntValue.parseString(nodeValues);
            case EsperEPLParser.LONG_TYPE:  return LongValue.parseString(nodeValues);
            case EsperEPLParser.BOOL_TYPE:  return BoolValue.parseString(nodeValues);
            case EsperEPLParser.FLOAT_TYPE:  return FloatValue.parseString(nodeValues);
            case EsperEPLParser.DOUBLE_TYPE:  return DoubleValue.parseString(nodeValues);
            case EsperEPLParser.STRING_TYPE:  return StringValue.parseString(nodeValues);
            default:
                throw new IllegalStateException("Unexpected constant of type " + nodeType + " encountered");
        }
    }

    private static final Log log = LogFactory.getLog(ASTParameterHelper.class);
}
