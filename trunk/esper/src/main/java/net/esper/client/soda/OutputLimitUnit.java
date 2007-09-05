package net.esper.client.soda;

/**
 * Unit for output rate limiting.
 */
public enum OutputLimitUnit
{
    /**
     * The minutes unit.
     */
    MINUTES ("minutes"),

    /**
     * The seconds unit.
     */
    SECONDS ("seconds"),

    /**
     * The number of events unit.
     */
    EVENTS ("events");

    private String text;

    private OutputLimitUnit(String text)
    {
        this.text = text;
    }

    /**
     * Returns the text for the unit.
     * @return unit text
     */
    public String getText()
    {
        return text;
    }
}
