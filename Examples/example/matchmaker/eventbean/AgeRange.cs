using System;

namespace net.esper.example.matchmaker.eventbean
{
    public class AgeRange
    {
        public static readonly AgeRange AGE_1 = new AgeRange(18, 25);
        public static readonly AgeRange AGE_2 = new AgeRange(26, 35);
        public static readonly AgeRange AGE_3 = new AgeRange(36, 45);
        public static readonly AgeRange AGE_4 = new AgeRange(46, 55);
        public static readonly AgeRange AGE_5 = new AgeRange(55, 65);
        public static readonly AgeRange AGE_6 = new AgeRange(65, Int32.MaxValue);

        private int low;
        private int high;

        public AgeRange(int low, int high)
        {
            this.low = low;
            this.high = high;
        }

        public int Low
        {
            get { return low; }
        }

        public int High
        {
            get { return high; }
        }
    }
}
