package com.espertech.esper.regression.client;

public @interface MyAnnotationValueArray
{
    public abstract long[] value();
    public abstract int[] intArray();
    public abstract double[] doubleArray();
    public abstract String[] stringArray();
    public abstract String[] stringArrayDef() default {"XYZ"};
}
