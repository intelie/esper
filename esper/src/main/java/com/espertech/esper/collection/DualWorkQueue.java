package com.espertech.esper.collection;

public class DualWorkQueue<Object> {

    private ArrayDequeJDK6Backport<Object> frontQueue;
    private ArrayDequeJDK6Backport<Object> backQueue;

    public DualWorkQueue() {
        frontQueue = new ArrayDequeJDK6Backport<Object>();
        backQueue = new ArrayDequeJDK6Backport<Object>();
    }

    public ArrayDequeJDK6Backport<Object> getFrontQueue() {
        return frontQueue;
    }

    public ArrayDequeJDK6Backport<Object> getBackQueue() {
        return backQueue;
    }
}
