package com.espertech.esper.util;

public class AnnotationUtil {
    public static boolean isListed(String list, String lookedForValue) {
        if (list == null)
        {
            return false;
        }

        lookedForValue = lookedForValue.trim().toUpperCase();
        list = list.trim().toUpperCase();

        if (list.toUpperCase().equals(lookedForValue))
        {
            return true;
        }

        if (list.indexOf('=') != -1)
        {
            String hintName = list.substring(0, list.indexOf('='));
            if (hintName.trim().toUpperCase().equals(lookedForValue))
            {
                return true;
            }
        }

        String[] items = list.split(",");
        for (String item : items)
        {
            String listItem = item.trim().toUpperCase();
            if (listItem.equals(lookedForValue))
            {
                return true;
            }

            if (listItem.indexOf('=') != -1)
            {
                String listItemName = listItem.substring(0, listItem.indexOf('='));
                if (listItemName.trim().toUpperCase().equals(lookedForValue))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getAssignedValue(String value, String enumValue) {

        String valMixed = value.trim();
        String val = valMixed.toUpperCase();

        if (val.indexOf(",") == -1)
        {
            if (val.indexOf('=') == -1)
            {
                return null;
            }

            String hintName = val.substring(0, val.indexOf('='));
            if (!hintName.equals(enumValue))
            {
                return null;
            }
            return valMixed.substring(val.indexOf('=') + 1, val.length());
        }

        String[] hints = valMixed.split(",");
        for (String hint : hints)
        {
            int indexOfEquals = hint.indexOf('=');
            if (indexOfEquals == -1)
            {
                continue;
            }

            val = hint.substring(0, indexOfEquals).trim().toUpperCase();
            if (!val.equals(enumValue))
            {
                continue;
            }

            String strValue = hint.substring(indexOfEquals + 1).trim();
            if (strValue.length() == 0)
            {
                return null;
            }
            return strValue;
        }
        return null;
    }
}
