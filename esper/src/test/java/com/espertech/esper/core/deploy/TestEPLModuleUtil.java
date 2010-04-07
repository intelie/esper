package com.espertech.esper.core.deploy;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.core.deploy.EPLModuleUtil;

public class TestEPLModuleUtil extends TestCase
{
    private static Log log = LogFactory.getLog(TestEPLModuleUtil.class);

    public void testParse() {

        Object[][] testdata = new Object[][] {
                {"/* Comment One */ select * from A;\n" +
                 "/* Comment Two */  select   *  from  B ;\n",
                 new String[] {"/* Comment One */ select * from A",
                               "/* Comment Two */  select   *  from  B"}
                },

                {"select /* Comment One\n\r; */ *, ';', \";\" from A order by x;; ;\n\n \n;\n" +
                 "/* Comment Two */  select   *  from  B ;\n",

                 new String[] {"select /* Comment One\n\r; */ *, ';', \";\" from A order by x",
                               "/* Comment Two */  select   *  from  B"}
                },
        };

        for (int i = 0; i < testdata.length; i++) {
            String input = (String) testdata[i][0];
            String[] expected = (String[]) testdata[i][1];

            List<String> result = EPLModuleUtil.parse(input);
            String[] received = result.toArray(new String[result.size()]);
            ArrayAssertionUtil.assertEqualsExactOrder(received, expected);
        }
    }
}
