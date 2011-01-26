package com.espertech.esper.regression.client;

public class MySingleRowFunction
{
    public static int computePower3(int i)
    {
        return i * i * i;
    }

    public static String surroundx(String target)
    {
        return "X" + target + "X";
    }

    public static InnerSingleRow getChainTop() {
        return new InnerSingleRow();
    }

    public static class InnerSingleRow {
        public int chainValue(int i, int j) {
            return i*j;
        }
    }
}
