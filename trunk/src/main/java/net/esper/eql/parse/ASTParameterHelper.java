package net.esper.eql.parse;

import net.esper.type.*;
import net.esper.eql.generated.EqlEvalTokenTypes;
import antlr.collections.AST;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Parse AST parameter nodes including constants, arrays, lists.
 * Distinguishes between uniform and non-uniform arrays.
 */
public class ASTParameterHelper implements EqlEvalTokenTypes
{

    /**
     * Returns the parse Object for the parameter/constant AST node whose text to parse.
     * @param parameterNode - AST node to parse
     * @return object value
     * @throws ASTWalkException is thrown to indicate a parse error
     */
    public static Object makeParameter(AST parameterNode) throws ASTWalkException
    {
        return parseConstant(parameterNode);
    }

    private static Object parseConstant(AST node) throws ASTWalkException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".parseConstant Node type=" + node.getType() + " text=" + node.getText());
        }

        switch(node.getType())
        {
            case NUM_INT:
            case INT_TYPE:
            case LONG_TYPE:
            case BOOL_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:               return ASTConstantHelper.parse(node);
            case NUMERIC_PARAM_FREQUENCY:   return makeFrequency(node);
            case NUMERIC_PARAM_RANGE:       return makeRange(node);
            case STAR:                      return new WildcardParameter();
            case NUMERIC_PARAM_LIST:        return makeList(node);
            case ARRAY_PARAM_LIST:          return makeArray(node);
            default:
                throw new ASTWalkException("Unexpected constant of type " + node.getType() + " encountered");
        }
    }

    private static Object makeList(AST node) throws ASTWalkException
    {
        ListParameter list = new ListParameter();

        AST child = node.getFirstChild();
        while(child != null)
        {
            Object parsedChild = parseConstant(child);

            if (parsedChild instanceof Integer)
            {
                list.add(new IntParameter((Integer) parsedChild));
            }
            else
            {
                list.add((NumberSetParameter) parsedChild);
            }
            child = child.getNextSibling();
        }

        return list;
    }

    private static Object makeFrequency(AST node)
    {
        int frequency = IntValue.parseString(node.getFirstChild().getText());
        return new FrequencyParameter(frequency);
    }

    private static Object makeRange(AST node)
    {
        int low = IntValue.parseString(node.getFirstChild().getText());
        int high = IntValue.parseString(node.getFirstChild().getNextSibling().getText());
        return new RangeParameter(low, high);
    }

    private static Object makeArray(AST node) throws ASTWalkException
    {
        // Determine the distinct node types in the AST
        Set<Integer> nodeTypes = new HashSet<Integer>();
        AST child = node.getFirstChild();

        while(child != null)
        {
            nodeTypes.add(child.getType());
            child = child.getNextSibling();
        }

        if (nodeTypes.size() == 0)
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

    private static Object makeNonUniform(AST node) throws ASTWalkException
    {
        int count = node.getNumberOfChildren();
        Object[] result = new Object[count];

        AST child = node.getFirstChild();
        int index = 0;
        while(child != null)
        {
            result[index++] = parseConstant(child);
            child = child.getNextSibling();
        }

        return result;
    }

    private static Object makeUniform(AST node) throws ASTWalkException
    {
        int count = node.getNumberOfChildren();
        String[] values = new String[count];

        AST child = node.getFirstChild();
        int index = 0;
        while(child != null)
        {
            values[index++] = child.getText();
            child = child.getNextSibling();
        }

        Object result = parseStringArray(node.getFirstChild().getType(), values);
        return result;
    }

    private static Object parseStringArray(int nodeType, String[] nodeValues) throws ASTWalkException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".parseStringArray Node type=" + nodeType + " values=" + Arrays.toString(nodeValues));
        }

        switch(nodeType)
        {
            case INT_TYPE:  return IntValue.parseString(nodeValues);
            case LONG_TYPE:  return LongValue.parseString(nodeValues);
            case BOOL_TYPE:  return BoolValue.parseString(nodeValues);
            case FLOAT_TYPE:  return FloatValue.parseString(nodeValues);
            case DOUBLE_TYPE:  return DoubleValue.parseString(nodeValues);
            case STRING_TYPE:  return StringValue.parseString(nodeValues);
            default:
                throw new IllegalStateException("Unexpected constant of type " + nodeType + " encountered");
        }
    }

    private static final Log log = LogFactory.getLog(ASTParameterHelper.class);
}
