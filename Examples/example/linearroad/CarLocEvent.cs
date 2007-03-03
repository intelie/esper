namespace net.esper.example.linearroad
{
    public class CarLocEvent
    {
        private long carId;     /* unique car identifier       */
        private int speed;      /* speed of the car            */
        private int expressway; /* expressway: 0..10           */
        private int lane;       /* lane: 0,1,2,3               */
        private int direction;  /* direction: 0(east), 1(west) */
        private int segment;    /* segment in express way   */

        public CarLocEvent(long carId, int speed, int expressway, int lane, int direction, int segment)
        {
            this.carId = carId;
            this.speed = speed;
            this.expressway = expressway;
            this.lane = lane;
            this.direction = direction;
            this.segment = segment;
        }

        public long CarId
        {
            get { return carId; }
        }

        public int Speed
        {
            get { return speed; }
        }

        public int Expressway
        {
            get { return expressway; }
        }

        public int Lane
        {
            get { return lane; }
        }

        public int Direction
        {
            get { return direction; }
        }

        public int Segment
        {
            get { return segment; }
        }

        public override string ToString()
        {
            return "CarLocEvent carId=" + carId +
                    " speed=" + speed +
                    " expressway=" + expressway +
                    " lane=" + lane +
                    " direction=" + direction +
                    " segment=" + segment;
        }
    }
}