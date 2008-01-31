package com.espertech.esper.eql.spec;

/**
 * Specification for the on-delete statement.
 */
public class OnTriggerWindowDesc extends OnTriggerDesc
{
    private String windowName;
    private String optionalAsName;

    /**
     * Ctor.
     * @param windowName the window name
     * @param optionalAsName the optional alias
     * @param isOnDelete true for on-delete and false for on-select
     */
    public OnTriggerWindowDesc(String windowName, String optionalAsName, boolean isOnDelete)
    {
        super(isOnDelete ? OnTriggerType.ON_DELETE : OnTriggerType.ON_SELECT);
        this.windowName = windowName;
        this.optionalAsName = optionalAsName;
    }

    /**
     * Returns the window name.
     * @return window name
     */
    public String getWindowName()
    {
        return windowName;
    }

    /**
     * Returns the alias, or null if none defined.
     * @return alias
     */
    public String getOptionalAsName()
    {
        return optionalAsName;
    }
}
