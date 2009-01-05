package com.espertech.esper.event.util;

public class XMLOutputString implements JSONOutput
{
    public void render(Object object, StringBuilder buf)
    {
        if (object == null)
        {
            buf.append("null");
            return;
        }

        xmlEncode(object.toString(), buf);
    }

    public static void xmlEncode(String s, StringBuilder sb)
    {
        if (s == null || s.length() == 0)
        {
            return;
        }

        char c;
        int i;
        int len = s.length();
        String t;

        for (i = 0; i < len; i += 1)
        {
            c = s.charAt(i);
            // replace literal values with entities

            if (c == '&')
            {
                sb.append("&amp;");
            }
            else if (c == '<')
            {
                sb.append("&lt;");
            }
            else if (c == '>')
            {
                sb.append("&gt;");
            }
            else if (c == '\'')
            {
                sb.append("&apos;");
            }
            else if (c == '\"')
            {
                sb.append("&quot;");
            }
            else
            {
                if (c < ' ')
                {
                    t = "000" + Integer.toHexString(c);
                    sb.append("\\u" + t.substring(t.length() - 4));
                }
                else
                {
                    sb.append(c);
                }
            }
        }
    }
}
