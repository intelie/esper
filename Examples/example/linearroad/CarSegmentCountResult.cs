using System;

namespace net.esper.example.linearroad
{
    public class CarSegmentCountResult
    {
        private int expressway;
        private int direction;
        private int segment;
        private double avgSpeed;
        private long size;

        public CarSegmentCountResult(int expressway, int direction, int segment, double avgSpeed, long size)
        {
            this.expressway = expressway;
            this.direction = direction;
            this.segment = segment;
            this.avgSpeed = avgSpeed;
            this.size = size;
        }

        public int Expressway
        {
            get { return expressway; }
            set { this.expressway = value; }
        }

        public int Direction
        {
            get { return direction; }
            set { this.direction = value; }
        }

        public int Segment
        {
            get { return segment; }
            set { this.segment = value; }
        }

        public double AvgSpeed
        {
            get { return avgSpeed; }
            set { this.avgSpeed = value; }
        }

        public long Size
        {
            get { return size; }
            set { this.size = value; }
        }

        public override String ToString()
        {
            return "expressway=" + expressway +
                    " direction=" + direction +
                    " segment=" + segment +
                    " avgSpeed=" + avgSpeed +
                    " size=" + size;
        }

        public override bool Equals(Object other)
        {
            CarSegmentCountResult otherResult = other as CarSegmentCountResult;
            if (otherResult == null)
            {
                return false;
            }

            if ((otherResult.expressway != this.expressway) ||
                (otherResult.direction != this.direction) ||
                (otherResult.segment != this.segment) ||
                (otherResult.size != this.size))
            {
                return false;
            }

            if (Double.IsNaN(otherResult.avgSpeed) && Double.IsNaN(this.avgSpeed))
            {
                return true;
            }
            if (otherResult.avgSpeed != this.avgSpeed)
            {
                return false;
            }

            return true;
        }
    }
}