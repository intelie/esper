package com.espertech.esperio.message.fix;

import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Parses and performs very basic validation of a fix message.
 * <p>
 * Validations are:
 * <UL>
 * <LI>The message must be parsable as a fix message.</LI>
 * <LI>The tags 8, 9, 35, 10 must exist</LI>
 * </UL>
 */
public class FixMsgParser
{
	private static final String soh ;

    static
    {
        byte[] sohCh =  new byte[1];
        sohCh[0] = (byte) 1;
        soh = new String(sohCh, 0, 1);
    }

	public static Map<String, String> parse(String fixMsg)
            throws FixMsgParserException
    {
		Map<String, String> parsedMessage = internalParse(fixMsg);
        validate(parsedMessage, fixMsg);
        return parsedMessage;
	}

    private static Map<String, String> internalParse(String fixMsg)
            throws FixMsgParserException
    {
        if (fixMsg == null)
        {
            throw new FixMsgUnrecognizableException("Unrecognizable fix message, message is a null string");
        }

        if (fixMsg.length() == 0)
        {
            throw new FixMsgUnrecognizableException("Unrecognizable fix message, message is a empty string");
        }

        Map<String, String> parsedMessage = new HashMap<String, String>();
        StringTokenizer tok = new StringTokenizer(fixMsg, soh);
        if (tok.countTokens() < 4)
        {
            throw new FixMsgUnrecognizableException("Unrecognizable fix message, number of tokens is less then 4", fixMsg);
        }

        while(tok.hasMoreTokens())
        {
            String filed = tok.nextToken();
            StringTokenizer innerTokens = new StringTokenizer(filed, "=");
            if (innerTokens.countTokens() != 2)
            {
                 continue;
            }
            String tag = innerTokens.nextToken();
            String value = innerTokens.nextToken();
            parsedMessage.put(tag, value);
        }

        return parsedMessage;
    }

    public static void validate(Map<String, String> fix, String fixMsg) throws FixMsgInvalidException
    {
        for (String required : new String[] {"8", "9", "35", "10"})
        {
            if (!fix.containsKey(required) || fix.get(required).equals(""))
            {
                throw new FixMsgInvalidException("Failed to find tag " + required + " in fix message");
            }
        }

        String checksum = fix.get("10");
        if (checksum == null)
        {
            throw new FixMsgInvalidException("Checksum validation failed, could not find tag '8'");
        }

        int deliveredChecksum;
        try {
            deliveredChecksum = Integer.parseInt(checksum);
        }
        catch (RuntimeException ex)
        {
            throw new FixMsgInvalidException("Invalid non-numeric checksum");
        }

        String checksumText = fixMsg.substring(fixMsg.indexOf("8="));
        int offset = checksumText.lastIndexOf("\u000110=");
        checksumText = checksumText.substring(0, offset) + soh;
        int computedChecksum = FixMsgMarshaller.checkSum(checksumText);

        if (computedChecksum != deliveredChecksum)
        {
            throw new FixMsgInvalidException("Invalid checksum, failed to validate checksum, found " + deliveredChecksum + " but should be " + computedChecksum);
        }
    }
}
