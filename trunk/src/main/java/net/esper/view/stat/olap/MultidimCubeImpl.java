package net.esper.view.stat.olap;

import java.util.*;

import net.esper.collection.MultiKeyUntyped;

/**
 * Implementation for a multidimensional cube that can be configured with the type of cell to store.
 *
 * This cube implementation grows in each dimension as new dimension members are made known to the cube.
 * If cells are added for dimension members that have not been encountered before,
 * then the new dimension members are added to the known dimension members and the cube is redimensioned, ie. grows.
 *
 * In design of this class, performance is important in the following areas:
 * - Access to cells should be very fast in the normal case.
 *   Normal case is that the cell for all dimension members (the particular coordinate) already exists.
 *   This class uses a map of dimension member set (coordinates) to fact array ordinal which gets great performance.
 */
public final class MultidimCubeImpl<V> implements MultidimCube<V>
{
    private final int numDimensions;

    // Fact array, grows when new dimension members are encountered
    private V cells[];

    private final MultidimCubeCellFactory<V> multidimCubeCellFactory;

    // Store for each coordinate the ordinal index into the fact array
    private final Map<MultiKeyUntyped, Integer> ordinals = new HashMap<MultiKeyUntyped, Integer>();

    // Store for each dimension the list of members
    private final Map<Integer, List<Object>> dimensionMembers = new HashMap<Integer, List<Object>>();
    private final String[] dimensionNames;

    /**
     * Constructor.
     * @param dimensionNames is the name of each dimension - and dictates the number of dimensions
     * @param multidimCubeCellFactory is the factory for cube cells
     */
    public MultidimCubeImpl(String[] dimensionNames, MultidimCubeCellFactory<V> multidimCubeCellFactory)
    {
        if (dimensionNames.length < 2)
        {
            throw new IllegalArgumentException("Cannot create a cube with less then 1 dimensions");
        }

        this.numDimensions = dimensionNames.length - 1;

        this.dimensionNames = dimensionNames;
        this.multidimCubeCellFactory = multidimCubeCellFactory;
        this.cells = multidimCubeCellFactory.newCells(0);

        for (int i = 0; i < numDimensions; i++)
        {
            this.dimensionMembers.put(i, new LinkedList<Object>());
        }
    }

    public String[] getDimensionNames()
    {
        return dimensionNames;
    }

    public final int getNumDimensions()
    {
        return numDimensions;
    }

    public final void setMembers(int dimension, Class enumType)
    {
        Object[] enumConstants = enumType.getEnumConstants();
        if (enumConstants != null)
        {
            setMembers(dimension, Arrays.asList(enumConstants));
        }
    }

    public final void setMembers(int dimension, List<Object> members)
    {
        List<Object> memberKeys = dimensionMembers.get(dimension);

        // Check dimension
        if ((dimension < 0) || (dimension > (numDimensions - 1)))
        {
            throw new IllegalArgumentException("Cannot add dimension members for given dimension - given dimension is out of range");
        }

        // If members already existed, disallow setting members
        if (memberKeys.size() != 0)
        {
            throw new IllegalArgumentException("Cannot add dimension members - dimension members already existed, merge not supported");
        }

        // If ordinals (cells) already existed, disallow setting members
        if (ordinals.size() != 0)
        {
            throw new IllegalArgumentException("Cannot add dimension members - cells already existed, merge not supported");
        }

        // Add members
        for (Object member : members)
        {
            memberKeys.add(member);
        }

        // Compute the new array size
        int currentSize = cells.length;
        int newSize = currentSize * members.size();
        if (currentSize == 0)
        {
            newSize = members.size();
        }

        // Initialize new array
        V newFacts[] = multidimCubeCellFactory.newCells(newSize);
        if (currentSize > 0)
        {
            System.arraycopy(this.cells, 0, newFacts, 0, currentSize);
        }
        for (int i = currentSize; i < newFacts.length; i++)
        {
            newFacts[i] = multidimCubeCellFactory.newCell();
        }

        this.cells = newFacts;
    }

    public final List<Object> getMembers(int dimension)
    {
        return dimensionMembers.get(dimension);
    }

    public V getCell(MultiKeyUntyped coordinates)
    {
        if (coordinates.size() != numDimensions)
        {
            throw new IllegalArgumentException("Cannot return cell as number of dimension members does not match cube dimensions");
        }

        Integer ordinal = ordinals.get(coordinates);

        if (ordinal == null)
        {
            return null;
        }
        return (V) cells[ordinal];
    }

    public V getCellAddMembers(MultiKeyUntyped coordinates)
    {
        if (coordinates.size() != numDimensions)
        {
            throw new IllegalArgumentException("Cannot return cell as number of dimension members does not match cube dimensions");
        }

        Integer ordinal = ordinals.get(coordinates);

        // The dimension members are all known and a value has previously been added for these coordinates
        if (ordinal != null)
        {
            return cells[ordinal];
        }

        // Check the coordinates against existing dimension members, compile array of indizes
        int[] indizes = new int[numDimensions];
        boolean allMembersFound = calcIndizes(coordinates, indizes);

        // All coordinates map to dimension members
        if (allMembersFound)
        {
            // Compile array containing the size of each dimension
            int[] dimensionSizes = getDimensionSizes();

            // From the individual dimension indizes [x,y,z] values calculate the ordinal into the array
            ordinal = CubeDimensionHelper.getOrdinal(indizes, dimensionSizes);

            // Add ordinal and fact
            ordinals.put(coordinates, ordinal);

            return cells[ordinal];
        }

        // If this is the very first cell, all dimensions started out with zero members and now became size one
        if (cells.length == 0)
        {
            for (int i = 0; i < coordinates.size(); i++)
            {
                Object member = coordinates.get(i);
                dimensionMembers.get(i).add(member);
            }

            // Add ordinal and fact
            cells = multidimCubeCellFactory.newCells(1);
            cells[0] = multidimCubeCellFactory.newCell();
            ordinals.put(coordinates, 0);

            return cells[0];
        }

        // One or more of the dimension members have never occured before, add member will redimensionize
        for (int i = 0; i < indizes.length; i++)
        {
            // Add each new member
            if (indizes[i] == -1)
            {
                addMember(coordinates.get(i), i, multidimCubeCellFactory);
            }
        }

        // Again check the coordinates against existing dimension members, they must exist now
        indizes = new int[numDimensions];
        allMembersFound = calcIndizes(coordinates, indizes);

        if (!allMembersFound)
        {
            throw new IllegalStateException("Internal error - member for dimension could not be added");
        }

        // Determine ordinal
        int[] dimensionSizes = getDimensionSizes();
        ordinal = CubeDimensionHelper.getOrdinal(indizes, dimensionSizes);

        // Add ordinal and fact
        ordinals.put(coordinates, ordinal);
        return cells[ordinal];
    }

    public final V[] getCells()
    {
        return cells;
    }

    private void addMember(Object member, int dimension, MultidimCubeCellFactory<V> multidimCubeCellFactory)
    {
        // Add member, capture old and new dimension sizes
        int[] oldDimensionSizes = getDimensionSizes();
        dimensionMembers.get(dimension).add(member);
        int[] newDimensionSizes = getDimensionSizes();

        // Allocate new array
        int newSize = CubeDimensionHelper.getTotalCells(newDimensionSizes);
        V newFacts[] = multidimCubeCellFactory.newCells(newSize);

        // Adding to the last dimension will just grow the array
        if (dimension == (numDimensions - 1))
        {
            System.arraycopy(this.cells, 0, newFacts, 0, cells.length);
            for (int i = cells.length; i < newFacts.length; i++)
            {
                newFacts[i] = multidimCubeCellFactory.newCell();
            }
            this.cells = newFacts;
            return;
        }

        int[] indizes = new int[numDimensions];
        int[] newOrdinals = new int[cells.length];

        // Determine new ordinals for the old cells and copy over cells,
        for (int i = 0; i < cells.length; i++)
        {
            int newOrdinal = CubeDimensionHelper.getOrdinal(indizes, newDimensionSizes);
            newOrdinals[i] = newOrdinal;

            newFacts[newOrdinal] = cells[i];

            if (i + 1 < cells.length)
            {
                CubeDimensionHelper.nextIndize(oldDimensionSizes, indizes);
            }
        }

        // Initialize the new fact array where null
        for (int i = 0; i < newFacts.length; i++)
        {
            if (newFacts[i] == null)
            {
                newFacts[i] = multidimCubeCellFactory.newCell();
            }
        }

        // Change references to old ordinals
        for (Map.Entry<MultiKeyUntyped, Integer> entry : ordinals.entrySet())
        {
            int oldOrdinal = entry.getValue();
            int newOrdinal = newOrdinals[oldOrdinal];
            ordinals.put(entry.getKey(), newOrdinal);
        }

        this.cells = newFacts;
    }

    private boolean calcIndizes(MultiKeyUntyped coordinates, int[] indizes)
    {
        boolean allFound = true;

        for (int i = 0; i < coordinates.size(); i++)
        {
            Object memberKey = coordinates.get(i);
            List<Object> existingMembers = dimensionMembers.get(i);

            int index = existingMembers.indexOf(memberKey);

            if (index == -1)
            {
                allFound = false;
                indizes[i] =  -1;
            }
            else
            {
                indizes[i] =  index;
            }
        }

        return allFound;
    }

    private int[] getDimensionSizes()
    {
        int[] dimensionSizes = new int[this.numDimensions];
        for (int i = 0; i < this.numDimensions; i++)
        {
            dimensionSizes[i] = dimensionMembers.get(i).size();
        }
        return dimensionSizes;
    }
}
