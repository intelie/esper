package net.esper.client.soda;

public enum OutputLimitUnit
{
    MINUTES ("minutes"),
    SECONDS ("seconds"),
    EVENTS ("events");

    private String text;

    private OutputLimitUnit(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}
