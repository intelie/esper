package com.espertech.esper.view;

/**
 * Views that require initialization after view instantiation and after view hook-up with the parent view
 * can impleeent this interface and get invoked to initialize.
 */
public interface InitializableView
{
    /**
     * Initializes a view.
     */
    public void initialize();
}
