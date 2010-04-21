package com.espertech.esper.core.deploy;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class TestEPLModuleUtil extends TestCase
{
    private static Log log = LogFactory.getLog(TestEPLModuleUtil.class);

    public void testParse() {

        Object[][] testdata = new Object[][] {
                {"/* Comment One */ select * from A;\n" +
                 "/* Comment Two */  select   *  from  B ;\n",
                 new String[] {"/* Comment One */ select * from A",
                               "/* Comment Two */  select   *  from  B"},
                 new int[] {1, 2}
                },

                {"select /* Comment One\n\r; */ *, ';', \";\" from A order by x;; ;\n\n \n;\n" +
                 "/* Comment Two */  select   *  from  B ;\n",

                 new String[] {"select /* Comment One\n\r; */ *, ';', \";\" from A order by x",
                               "/* Comment Two */  select   *  from  B"},
                 new int[] {1, 6}
                },
        };

        for (int i = 0; i < testdata.length; i++) {
            String input = (String) testdata[i][0];
            String[] expectedBuf = (String[]) testdata[i][1];
            int[] expectedLine = (int[]) testdata[i][2];

            List<Pair<String, Integer>> result = EPLModuleUtil.parse(input);

            String[] receivedBuf = new String[result.size()];
            int[] receivedLine = new int[result.size()];
            for (int j = 0; j < result.size(); j++) {
                receivedBuf[j] = result.get(j).getFirst();
                receivedLine[j] = result.get(j).getSecond();
            }

            ArrayAssertionUtil.assertEqualsExactOrder(receivedBuf, expectedBuf);
            ArrayAssertionUtil.assertEqualsExactOrder(receivedLine, expectedLine);
        }
    }
}
