package net.esper.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.Serializable;

/**
 * Binding from database output column type to Java object.
 */
public interface DatabaseTypeBinding extends Serializable
{
    /**
     * Returns the Java object for the given column.
     * @param resultSet is the result set to read the column from
     * @param columnName is the column name
     * @return Java object
     * @throws SQLException if the mapping cannot be performed
     */
    public Object getValue(ResultSet resultSet, String columnName) throws SQLException;

    /**
     * Returns the Java target type.
     * @return Java type
     */
    public Class getType();
}
