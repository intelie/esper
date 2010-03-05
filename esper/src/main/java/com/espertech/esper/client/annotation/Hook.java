package com.espertech.esper.client.annotation;

/**
 * Use this annotation to install a statement-specific hook or callback at time of statement creation.
 *
 * See {@link HookType} to the types of hooks that may be installed.
 */
public @interface Hook
{
    String hook();
    HookType type();    
}
