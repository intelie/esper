package net.esper.support.bean;

public class SupportBean_S1
{
    private static int idCounter;

    private int id;
    private String p10;
    private String p11;
    private String p12;
    private String p13;

    public static Object[] makeS1(String propOne, String[] propTwo)
    {
        idCounter++;

        Object[] events = new Object[propTwo.length];
        for (int i = 0; i < propTwo.length; i++)
        {
            events[i] = new SupportBean_S1(idCounter, propOne, propTwo[i]);
        }
        return events;
    }


    public SupportBean_S1(int id)
    {
        this.id = id;
    }

    public SupportBean_S1(int id, String p10)
    {
        this.id = id;
        this.p10 = p10;
    }

    public SupportBean_S1(int id, String p10, String p11)
    {
        this.id = id;
        this.p10 = p10;
        this.p11 = p11;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getP10()
    {
        return p10;
    }

    public void setP10(String p10)
    {
        this.p10 = p10;
    }

    public String getP11()
    {
        return p11;
    }

    public void setP11(String p11)
    {
        this.p11 = p11;
    }

    public String getP12()
    {
        return p12;
    }

    public void setP12(String p12)
    {
        this.p12 = p12;
    }

    public String getP13()
    {
        return p13;
    }

    public void setP13(String p13)
    {
        this.p13 = p13;
    }
}
