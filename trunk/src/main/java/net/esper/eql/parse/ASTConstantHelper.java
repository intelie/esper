package net.esper.eql.parse;

import antlr.collections.AST;
import net.esper.type.*;
import net.esper.eql.generated.EqlEvalTokenTypes;

/**
 * Parses constant strings and returns the constant Object.
 */
public class ASTConstantHelper implements EqlEvalTokenTypes
{
    /**
     * Answers the question of whether a given AST constant node can be converted via parsing into a primitive value
     * of a given type.
     * @param astTypeConstant is the AST constant node's type
     * @param targetType is the primitve value's type which will be used to parse the string
     * @return true if the primitive value can be used to parse the AST node, false if not
     */
    public static boolean canConvert(int astTypeConstant, PrimitiveValueType targetType)
    {
        switch (astTypeConstant)
        {
            case STRING_TYPE:
                return (targetType == PrimitiveValueType.STRING);
            case BOOL_TYPE:
                return (targetType == PrimitiveValueType.BOOL);
            case FLOAT_TYPE:
                return ((targetType == PrimitiveValueType.FLOAT) ||
                        (targetType == PrimitiveValueType.DOUBLE));
            case DOUBLE_TYPE:
                return (targetType == PrimitiveValueType.DOUBLE);
            case LONG_TYPE:
                return ((targetType == PrimitiveValueType.FLOAT) ||
                        (targetType == PrimitiveValueType.DOUBLE) ||
                        (targetType == PrimitiveValueType.LONG));
            case NUM_INT:
            case INT_TYPE:
                return  ((targetType == PrimitiveValueType.BYTE) ||
                         (targetType == PrimitiveValueType.SHORT) ||
                         (targetType == PrimitiveValueType.INTEGER) ||
                         (targetType == PrimitiveValueType.LONG) ||
                         (targetType == PrimitiveValueType.DOUBLE) ||
                         (targetType == PrimitiveValueType.FLOAT));
            default:
                throw new IllegalArgumentException("Unexpected constant of type " + astTypeConstant + " encountered");
        }
    }

    /**
     * Returns the type name for the given AST constant type.
     * @param astTypeConstant is the type of the AST constant node.
     * @return name of the type
     */
    public static String getConstantTypeName(int astTypeConstant)
    {
        switch (astTypeConstant)
        {
            case NUM_INT:       return PrimitiveValueType.INTEGER.toString();
            case INT_TYPE:      return PrimitiveValueType.INTEGER.toString();
            case LONG_TYPE:     return PrimitiveValueType.LONG.toString();
            case FLOAT_TYPE:    return PrimitiveValueType.FLOAT.toString();
            case DOUBLE_TYPE:   return PrimitiveValueType.DOUBLE.toString();
            case STRING_TYPE:   return PrimitiveValueType.STRING.toString();
            case BOOL_TYPE:     return PrimitiveValueType.BOOL.toString();
            case NULL_TYPE:     return "null/unknwown type";
            default:
                throw new IllegalArgumentException("Unexpected constant of type " + astTypeConstant + " encountered");
        }
    }

    /**
     * Parse the AST constant node and return Object value.
     * @param node - parse node for which to parse the string value
     * @return value matching AST node type
     */
    public static Object parse(AST node)
    {
        switch(node.getType())
        {
            case NUM_INT:       return IntValue.parseString(node.getText());
            case INT_TYPE:      return IntValue.parseString(node.getText());
            case LONG_TYPE:     return LongValue.parseString(node.getText());
            case BOOL_TYPE:     return BoolValue.parseString(node.getText());
            case FLOAT_TYPE:    return FloatValue.parseString(node.getText());
            case DOUBLE_TYPE:   return DoubleValue.parseString(node.getText());
            case STRING_TYPE:   return StringValue.parseString(node.getText());
            case NULL_TYPE:     return null;
            default:
                throw new IllegalArgumentException("Unexpected constant of non-primitve type " + node.getType() + " encountered");
        }
    }
}
