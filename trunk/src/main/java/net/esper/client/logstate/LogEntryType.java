package net.esper.client.logstate;

/**
 * Type of engine state logged.
 */
public enum LogEntryType
{
    ENGINE,
    STATEMENT,
    SUBSCRIPTION,
    GROUP_KEY_AGG_STATE,
    GROUP_AGG_STATE
}
