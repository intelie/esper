package com.espertech.esper.support.epl;

public class SupportChainFuncLib {

    public static SupportChainInner getInner(int one, int two) {
        return new SupportChainInner(one, two);
    }

    public static class SupportChainInner
    {
        private int sum;

        public SupportChainInner(int one, int two) {
            sum = one + two;
        }

        public SupportChainInner add(int one, int two) {
            return new SupportChainInner(sum, one + two);
        }

        public int getTotal() {
            return sum;
        }
    }
}
