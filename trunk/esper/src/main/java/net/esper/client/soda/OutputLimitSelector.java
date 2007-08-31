package net.esper.client.soda;

public enum OutputLimitSelector
{
    /**
     * Output first event.
     */
    FIRST ("first"),

    /**
     * Output last event.
     */
    LAST ("last"),

    /**
     * Output all events.
     */
    ALL ("all");

    private String text;

    private OutputLimitSelector(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}
