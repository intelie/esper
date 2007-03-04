package net.esper.adapter;

import net.esper.client.*;

/**
 * Created for ESPER.
 */
public interface InputAdapter extends Adapter
{
  Object read() throws EPException;
}
