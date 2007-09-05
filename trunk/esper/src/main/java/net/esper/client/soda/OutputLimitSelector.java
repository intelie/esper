package net.esper.client.soda;

/**
 * Selector for use in output rate limiting.
 */
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

    /**
     * Returns the text for the selector.
     * @return text
     */
    public String getText()
    {
        return text;
    }
}
