package com.espertech.esper.support.epl;

import javax.sql.DataSource;
import java.util.Properties;

public class SupportDataSourceFactory
{
    public static DataSource createDataSource(Properties properties)
    {
        return new SupportDriverManagerDataSource(properties);
    }
}
