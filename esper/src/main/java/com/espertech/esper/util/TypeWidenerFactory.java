package com.espertech.esper.util;

import com.espertech.esper.epl.expression.ExprValidationException;

/**
 * Factory for type widening.
 */
public class TypeWidenerFactory
{
    private static TypeWidenerStringToCharCoercer stringToCharCoercer = new TypeWidenerStringToCharCoercer();

    /**
     * Returns the widener.
     * @param columnName name of column
     * @param columnType type of column
     * @param writeablePropertyType property type
     * @param writeablePropertyName propery name
     * @return type widender
     * @throws ExprValidationException if type validation fails
     */
    public static TypeWidener getCheckPropertyAssignType(String columnName, Class columnType, Class writeablePropertyType, String writeablePropertyName)
            throws ExprValidationException
    {
        Class columnClassBoxed = JavaClassHelper.getBoxedType(columnType);
        Class targetClass = writeablePropertyType;
        Class targetClassBoxed = JavaClassHelper.getBoxedType(writeablePropertyType);

        if (columnType == null)
        {
            if (targetClass.isPrimitive())
            {
                String message = "Invalid assignment of column '" + columnName +
                        "' of null type to event property '" + writeablePropertyName +
                        "' typed as '" + writeablePropertyType.getName() +
                        "', nullable type mismatch";
                throw new ExprValidationException(message);
            }
        }
        else if (columnClassBoxed != targetClassBoxed)
        {
            if (columnClassBoxed == String.class && targetClassBoxed == Character.class)
            {
                return stringToCharCoercer;
            }
            else if (!JavaClassHelper.isAssignmentCompatible(columnClassBoxed, targetClassBoxed))
            {
                String message = "Invalid assignment of column '" + columnName +
                        "' of type '" + columnType.getName() +
                        "' to event property '" + writeablePropertyName +
                        "' typed as '" + writeablePropertyType.getName() +
                        "', column and parameter types mismatch";
                throw new ExprValidationException(message);
            }

            if (JavaClassHelper.isNumeric(targetClass))
            {
                return new TypeWidenerBoxedNumeric(SimpleNumberCoercerFactory.getCoercer(columnClassBoxed, targetClassBoxed));
            }
        }

        return null;
    }
}
