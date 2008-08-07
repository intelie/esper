package com.espertech.esper.view;

/**
 * Tag interface for data window views that express a batch expiry policy.
 * <p>
 * Such data windows allow iteration through the currently batched events,
 * and such data windows post insert stream events only when batching conditions have been met and
 * the batch is released.
 */
public interface BatchingDataWindowView extends DataWindowView
{
}
