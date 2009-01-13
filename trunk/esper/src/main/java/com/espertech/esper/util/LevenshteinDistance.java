package com.espertech.esper.util;

public class LevenshteinDistance
{
    public final static int ACCEPTABLE_DISTANCE = 3;

    public static int computeLevenshteinDistance(CharSequence str1, CharSequence str2)
    {
        if ((str1 == null) || (str2 == null))
        {
            return Integer.MAX_VALUE;
        }
        if (str1.toString().toLowerCase().equals(str2.toString().toLowerCase()))
        {
            return 0;
        }
        
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++)
        {
            distance[i][0] = i;
        }
        for (int j = 0; j <= str2.length(); j++)
        {
            distance[0][j] = j;
        }

        for (int i = 1; i <= str1.length(); i++)
        {
            for (int j = 1; j <= str2.length(); j++)
            {
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1]
                                + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
                                : 1));
            }
        }

        return distance[str1.length()][str2.length()];
    }

    private static int minimum(int a, int b, int c)
    {
        return Math.min(Math.min(a, b), c);
    }
}
