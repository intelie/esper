/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.db;

import java.sql.Connection;

/**
 * Factory for new database connections.
 */
public interface DatabaseConnectionFactory
{
    /**
     * Creates a new database connection.
     * @return new connection
     * @throws DatabaseConfigException throws to indicate a problem getting a new connection
     */
    public Connection getConnection() throws DatabaseConfigException;
}
