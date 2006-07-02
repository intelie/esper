package net.esper.view.ext;

import java.util.Comparator;

/**
 * For use by the SortWindowView class to have its sorted map be sorted in descending order.
 */
final class SortWindowDescending implements Comparator<Object>
{
    public final int compare(Object o, Object o1)
    {
        Comparable<Object> compO = (Comparable<Object>) o;
        Comparable<Object> comp1 = (Comparable<Object>) o1;

        return compO.compareTo(comp1) * -1;
    }
}
