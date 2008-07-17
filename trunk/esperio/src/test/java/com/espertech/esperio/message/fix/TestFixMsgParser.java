package com.espertech.esperio.message.fix;

import junit.framework.TestCase;

import java.util.Map;

public class TestFixMsgParser extends TestCase
{
    private final String SAMPLE = "8=FIX4.2<SOH>9=222<SOH>35=S<SOH>49=Broker<SOH>56=Institution\n" +
            "<SOH>34=251<SOH>52=2000072814:06:22<SOH>117=XXX<SOH>131=YYY\n" +
            "<SOH>55=AA<SOH>200=199901<SOH>202=25.00<SOH>201=1<SOH>132=5.00\n" +
            "<SOH>133=5.25<SOH>134=10<SOH>135=10<SOH>10=123<SOH>";

    public void testParse() throws Exception
    {
        Map<String, String> msg = FixMsgParser.parse(replaceSOH(SAMPLE));
        assertEquals("FIX4.2", msg.get("8"));
        assertEquals("222", msg.get("9"));
        assertEquals("S", msg.get("35"));
        assertEquals("Broker", msg.get("49"));
        assertEquals("123", msg.get("10"));
    }

    public void testInvalidParse() throws Exception
    {
        tryInvalid(null, "Unrecognizable fix message, message is a null string");
        tryInvalid("", "Unrecognizable fix message, message is a empty string");
        tryInvalid("abc", "Unrecognizable fix message, number of tokens is less then 4 for message text 'abc'");
        tryInvalid(replaceSOH("8=FIX4.2<SOH>9"), "Unrecognizable fix message, number of tokens is less then 4 for message text '8=FIX4.2\u00019'");
    }

    protected static String replaceSOH(String fixMsg)
    {
        byte[] separator = new byte[1];
        separator[0] = 1;
        String separatorStr = new String(separator, 0, 1);
        return fixMsg.replace("<SOH>", separatorStr);
    }

    private void tryInvalid(String fix, String errorMessage)
    {
        try
        {
            FixMsgParser.parse(fix);
            fail();
        }
        catch (FixMsgParserException e)
        {
            assertEquals(errorMessage, e.getMessage());
        }
    }
}
