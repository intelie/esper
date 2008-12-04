/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.message.fix;

import com.espertech.esper.client.EventBean;

import java.io.StringWriter;
import java.text.DecimalFormat;

/**
 * Marshaller for Fix message.
 */
public class FixMsgMarshaller
{
    private static final String DEFAULT_FIX_VERSION = "FIX4.2";
    private static String fixVersion;
    private static final String soh ;
    private static DecimalFormat checksumFormat = new DecimalFormat("000");

    static
    {
        byte[] sohCh =  new byte[1];
        sohCh[0] = (byte) 1;
        soh = new String(sohCh, 0, 1);
    }

    /**
     * Marshals a fix event.
     * @param event the event to marshal
     * @return marshalled fix message
     */
    public static String marshalFix(EventBean event)
    {
        if (fixVersion == null)
        {
            String fixSystemProperty = System.getProperty("FIX_VERSION");
            if (fixSystemProperty != null)
            {
                fixVersion = fixSystemProperty;
            }
            else
            {
                fixVersion = DEFAULT_FIX_VERSION;
            }
        }

        StringWriter writer = new StringWriter();

        // as a performance enhancement one could use the EventPropertyGetter here
        String delimiter = "";
        for (String property : event.getEventType().getPropertyNames())
        {
            Object value = event.get(property);
            if (value == null)
            {
                continue;
            }
            String valueText = value.toString();
            writer.write(delimiter);
            writer.write(property);
            writer.write('=');
            writer.write(valueText);
            delimiter = soh;
        }
        writer.write(soh);
        String fixMsgText = writer.toString();

        // write fix body
        writer = new StringWriter();

        writer.write("8=");
        writer.write(fixVersion);
        writer.write(soh);

        writer.write("9=");
        writer.write(Integer.toString(fixMsgText.length() + 1));
        writer.write(soh);

        writer.write(fixMsgText);
        String fixMsgBody = writer.toString();

        // write complete fix message text
        writer = new StringWriter();
        writer.write(fixMsgBody);
        writer.write("10=");
        int checksum = checkSum(fixMsgBody);
        writer.write(checksumFormat.format(checksum));
        writer.write(soh);

        return writer.toString();
    }

    /**
     * Compute a checksum of a fix message.
     * @param s fix message
     * @return checksum
     */
    protected static int checkSum(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += s.charAt(i);
        }
        return (sum + 1) % 256;
    }
}
