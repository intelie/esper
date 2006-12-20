package net.esper.example.linearroad;

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

    public long getCarId()
    {
        return carId;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getExpressway()
    {
        return expressway;
    }

    public int getLane()
    {
        return lane;
    }

    public int getDirection()
    {
        return direction;
    }

    public int getSegment()
    {
        return segment;
    }

    public String toString()
    {
        return "CarLocEvent carId=" + carId +
                " speed=" + speed +
                " expressway=" + expressway +
                " lane=" + lane +
                " direction=" + direction +
                " segment=" + segment;
    }
}


