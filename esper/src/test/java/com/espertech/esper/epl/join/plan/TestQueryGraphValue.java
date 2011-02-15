package com.espertech.esper.epl.join.plan;

import junit.framework.TestCase;

public class TestQueryGraphValue extends TestCase {

    public void testRangeRelOp() {

        tryAdd("b", QueryGraphRangeEnum.GREATER_OR_EQUAL, "a",      // read a >= b
               "c", QueryGraphRangeEnum.LESS_OR_EQUAL, "a",         // read a <= c
                new Object[][] {{null, "b", "c", QueryGraphRangeEnum.RANGE_CLOSED, "a"}});

        tryAdd("b", QueryGraphRangeEnum.GREATER, "a",      // read a > b
               "c", QueryGraphRangeEnum.LESS, "a",         // read a < c
                new Object[][] {{null, "b", "c", QueryGraphRangeEnum.RANGE_OPEN, "a"}});

        tryAdd("b", QueryGraphRangeEnum.GREATER_OR_EQUAL, "a",      // read a >= b
               "c", QueryGraphRangeEnum.LESS, "a",                  // read a < c
                new Object[][] {{null, "b", "c", QueryGraphRangeEnum.RANGE_HALF_OPEN, "a"}});

        tryAdd("b", QueryGraphRangeEnum.GREATER, "a",                       // read a > b
               "c", QueryGraphRangeEnum.LESS_OR_EQUAL, "a",                  // read a <= c
                new Object[][] {{null, "b", "c", QueryGraphRangeEnum.RANGE_HALF_CLOSED, "a"}});

        // sanity
        tryAdd("b", QueryGraphRangeEnum.LESS_OR_EQUAL, "a",                     // read a <= b
               "c", QueryGraphRangeEnum.GREATER_OR_EQUAL, "a",                  // read a >= c
                new Object[][] {{null, "c", "b", QueryGraphRangeEnum.RANGE_CLOSED, "a"}});
    }

    private void tryAdd(String propertyKeyOne, QueryGraphRangeEnum opOne, String valueOne,
                        String propertyKeyTwo, QueryGraphRangeEnum opTwo, String valueTwo,
                        Object[][] expected) {

        QueryGraphValue value = new QueryGraphValue();
        value.addRelOp(propertyKeyOne, opOne, valueOne, true);
        value.addRelOp(propertyKeyTwo, opTwo, valueTwo, true);
        assertRanges(expected, value);

        value = new QueryGraphValue();
        value.addRelOp(propertyKeyTwo, opTwo, valueTwo, true);
        value.addRelOp(propertyKeyOne, opOne, valueOne, true);
        assertRanges(expected, value);
    }

    public void testNoDup() {

        QueryGraphValue value = new QueryGraphValue();
        value.addRelOp("b", QueryGraphRangeEnum.LESS_OR_EQUAL, "a", false);
        value.addRelOp("b", QueryGraphRangeEnum.LESS_OR_EQUAL, "a", false);
        assertRanges(new Object[][] {{"b", null, null, QueryGraphRangeEnum.LESS_OR_EQUAL, "a"}}, value);

        value = new QueryGraphValue();
        value.addRange(QueryGraphRangeEnum.RANGE_CLOSED, "b", "c", "a");
        value.addRange(QueryGraphRangeEnum.RANGE_CLOSED, "b", "c", "a");
        assertRanges(new Object[][] {{null, "b", "c", QueryGraphRangeEnum.RANGE_CLOSED, "a"}}, value);
    }

    private void assertRanges(Object[][] ranges, QueryGraphValue value) {
        assertEquals(ranges.length, value.getRangeEntries().size());
        for (int i = 0; i < value.getRangeEntries().size(); i++) {
            QueryGraphValueRange r =  value.getRangeEntries().get(i);

            assertEquals(ranges[i][3], r.getType());
            assertEquals(ranges[i][4], r.getPropertyValue());

            if (r instanceof QueryGraphValueRangeRelOp) {
                QueryGraphValueRangeRelOp relOp = (QueryGraphValueRangeRelOp) r;
                assertEquals(ranges[i][0], relOp.getPropertyKey());
            }
            else {
                QueryGraphValueRangeIn in = (QueryGraphValueRangeIn) r;
                assertEquals(ranges[i][1], in.getPropertyStart());
                assertEquals(ranges[i][2], in.getPropertyEnd());
            }
        }
    }
}
