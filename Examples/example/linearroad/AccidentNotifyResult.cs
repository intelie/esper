using System;

namespace net.esper.example.linearroad
{
    public class AccidentNotifyResult
    {
        private int expressway;
        private int direction;
        private int segment;

        public AccidentNotifyResult(int expressway, int direction, int segment)
        {
            this.expressway = expressway;
            this.direction = direction;
            this.segment = segment;
        }

        public int Expressway
        {
            get { return expressway; }
            set { this.expressway = value ; }
        }

        public int Direction
        {
            get { return direction; }
            set { this.direction = value; }
        }

        public int Segment
        {
            get { return segment; }
        }

        public void setSegment(int segment)
        {
            this.segment = segment;
        }

        public override String ToString()
        {
            return "expressway=" + expressway +
                    " direction=" + direction +
                    " segment=" + segment;
        }

        public override bool Equals(Object other)
        {
            if (!(other is AccidentNotifyResult))
            {
                return false;
            }

            AccidentNotifyResult otherResult = (AccidentNotifyResult)other;

            if ((otherResult.expressway != this.expressway) ||
                (otherResult.direction != this.direction) ||
                (otherResult.segment != this.segment))
            {
                return false;
            }

            return true;
        }
    }
}