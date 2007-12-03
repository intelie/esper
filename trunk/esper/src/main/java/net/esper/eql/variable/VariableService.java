package net.esper.eql.variable;

import net.esper.collection.Pair;
import net.esper.util.JavaClassHelper;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Variable service for reading and writing variables, and for setting a version number for the current thread to
 * consider variables for.
 * <p>
 * Consider a statement as follows: select * from MyEvent as A where A.val > var1 and A.val2 > var1 and A.val3 > var2
 * <p>
 * Upon statement execution we need to guarantee that the same atomic value for all variables is applied for all
 * variable reads (by expressions typically) within the statement. 
 * <p>
 * Designed to support:
 * <ol>
 * <li>lock-less read of the current and prior version, locked reads for older versions
 * <li>atomicity by keeping multiple versions for each variable and a threadlocal that receives the current version each call
 * <li>one write lock for all variables (required to coordinate with single global version number),
 *   however writes are very fast (entry to collection plus increment an int) and therefore blocking should not be an issue
 * </ol>
 * <p>
 * Every new write to a variable creates a new version. Thus when reading variables, readers can ignore newer versions
 * and a read lock is not required in most circumstances.
 * <p>
 * This algorithm works as follows:
 * <p>
 * A thread processing an event into the engine via sendEvent() calls the "setLocalVersion" method once
 * before processing a statement that has variables.
 * This places into a threadlocal variable the current version number, say version 570.
 * <p>
 * A statement that reads a variable has an {@link net.esper.eql.expression.ExprVariableNode} that has a {@link VariableReader} handle
 * obtained during validation (example).
 * <p>
 * The {@link VariableReader} takes the version from the threadlocal (570) and compares the version number with the
 * version numbers held for the variable.
 * If the current version is same or lower (520, as old or older) then the threadlocal version,
 * then use the current value.
 * If the current version is higher (571, newer) then the threadlocal version, then go to the prior value.
 * Use the prior value until a version is found that as old or older then the threadlocal version.
 * <p>
 * If no version can be found that is old enough, output a warning and return the oldest version.
 * This should not happen, unless a thread is executing for very long with a statement such that
 * LOW_WATERMARK_VERSIONS versions passed before the thread asks for variable values. 
 */
public class VariableService
{
    protected final static int ROLLOVER_READER_BOUNDARY = Integer.MAX_VALUE - 2048;
    protected final static int ROLLOVER_WRITER_BOUNDARY = ROLLOVER_READER_BOUNDARY + 500;

    private final static int HIGH_WATERMARK_VERSIONS = 200;
    private final static int LOW_WATERMARK_VERSIONS = 100;

    private transient int currentVersionNumber;
    private int currentVariableNumber;

    private ThreadLocal<Integer> versionThreadLocal = new ThreadLocal<Integer>()
    {
        protected synchronized Integer initialValue()
        {
            return 0;
        }
    };

    // Keep the variable list
    private Map<String, Pair<VariableReader, VariableWriter>> variables;

    // Each variable has a index number, a current version and a list of values
    private ArrayList<VersionedValueList<Object>> variableVersions;

    private ReadWriteLock readWriteLock;

    public VariableService()
    {
        this(0);
    }

    protected VariableService(int startVersion)
    {
        variables = new HashMap<String, Pair<VariableReader, VariableWriter>>();
        variableVersions = new ArrayList<VersionedValueList<Object>>();
        readWriteLock = new ReentrantReadWriteLock();
        currentVersionNumber = startVersion;
    }

    public void setLocalVersion()
    {
        versionThreadLocal.set(currentVersionNumber);
    }

    public synchronized void createNewVariable(String variableName, Class type, Object value)
            throws VariableExistsException, VariableTypeException
    {
        // check type
        Class variableType = JavaClassHelper.getBoxedType(type);

        if (!JavaClassHelper.isJavaBuiltinDataType(variableType))
        {
            throw new VariableTypeException("Invalid variable type for variable '" + variableName
                + "' as type '" + variableType.getName() + "', only Java primitive, boxed or String types are allowed");
        }

        // check coercion
        Object coercedValue = value;

        // allow string assignments to non-string variables 
        if ((coercedValue != null) && (coercedValue instanceof String))
        {
            try
            {
                coercedValue = JavaClassHelper.parse(type, (String) coercedValue);
            }
            catch (Exception ex)
            {
                throw new VariableTypeException("Variable '" + variableName
                    + "' of declared type '" + variableType.getName() +
                        "' cannot be initialized by value '" + coercedValue + "': " + ex.toString());
            }                        
        }

        if ((coercedValue != null) &&
            (variableType != coercedValue.getClass()))
        {
            // if the declared type is not numeric or the init value is not numeric, fail
            if ((!JavaClassHelper.isNumeric(variableType)) ||
                (!(coercedValue instanceof Number)))
            {
                throw new VariableTypeException("Variable '" + variableName
                    + "' of declared type '" + variableType.getName() +
                        "' cannot be initialized by a value of type '" + coercedValue.getClass().getName() + "'");
            }

            if (!(JavaClassHelper.canCoerce(coercedValue.getClass(), variableType)))
            {
                throw new VariableTypeException("Variable '" + variableName
                    + "' of declared type '" + variableType.getName() +
                        "' cannot be initialized by a value of type '" + coercedValue.getClass().getName() + "'");
            }

            // coerce
            coercedValue = JavaClassHelper.coerceBoxed((Number)coercedValue, variableType);
        }

        // check if it exists
        Pair<VariableReader, VariableWriter> readerWriter = variables.get(variableName);
        if (readerWriter != null)
        {
            throw new VariableExistsException("Variable by name '" + variableName + "' has already been created");
        }

        // create new holder for versions
        VersionedValueList<Object> valuePerVersion = new VersionedValueList<Object>(variableName, currentVersionNumber, coercedValue, readWriteLock.readLock(), HIGH_WATERMARK_VERSIONS, LOW_WATERMARK_VERSIONS, false);

        variableVersions.add(valuePerVersion);

        // create reader
        readerWriter = new Pair<VariableReader, VariableWriter>
            (new VariableReader(versionThreadLocal, variableType, variableName, currentVariableNumber, valuePerVersion),
             new VariableWriter(currentVariableNumber, variableType, this));
        variables.put(variableName, readerWriter);

        currentVariableNumber++;
    }

    public VariableReader getReader(String variableName)
    {
        Pair<VariableReader, VariableWriter> pair = variables.get(variableName);
        if (pair == null)
        {
            return null;
        }
        return pair.getFirst();
    }

    public VariableWriter getWriter(String variableName)
    {
        Pair<VariableReader, VariableWriter> pair = variables.get(variableName);
        if (pair == null)
        {
            return null;
        }
        return pair.getSecond();
    }

    protected void write(int variableNumber, Object newValue)
    {
        VersionedValueList<Object> versions = variableVersions.get(variableNumber);

        // We lock all variables here since otherwise the current version number cannot easily be
        // increased in/outside the lock
        readWriteLock.writeLock().lock();
        try
        {
            // get new version for adding the new value
            int newVersion = currentVersionNumber + 1;

            if (currentVersionNumber == ROLLOVER_READER_BOUNDARY)
            {
                // Roll over to new collections;
                // This honors existing threads that will now use the "high" collection in the reader for high version requests
                // and low collection (new and updated) for low version requests
                rollOver();
                versions = variableVersions.get(variableNumber);
                newVersion = 2;
            }

            // add new value as a new version
            versions.addValue(newVersion, newValue);

            // this makes the new value visible to other threads (not this thread unless set-version called again)
            currentVersionNumber = newVersion;
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * Rollover includes creating a new
     */
    private void rollOver()
    {
        for (Map.Entry<String, Pair<VariableReader, VariableWriter>> entry : variables.entrySet())
        {
            int variableNum = entry.getValue().getFirst().getVariableNumber();
            String name = entry.getKey();

            // Construct a new collection, forgetting the history
            VersionedValueList<Object> versionsOld = variableVersions.get(variableNum);
            Object currentValue = versionsOld.getCurrentAndPriorValue().getCurrentVersion().getValue();
            VersionedValueList<Object> versionsNew = new VersionedValueList<Object>(name, 1, currentValue, readWriteLock.readLock(), HIGH_WATERMARK_VERSIONS, LOW_WATERMARK_VERSIONS, false);

            // Tell the reader to use the high collection for old requests
            entry.getValue().getFirst().setVersionsHigh(versionsOld);
            entry.getValue().getFirst().setVersionsLow(versionsNew);

            // Save new collection instead
            variableVersions.set(variableNum, versionsNew);
        }
    }

    public String toString()
    {
        StringWriter writer = new StringWriter();
        for (Map.Entry<String, Pair<VariableReader, VariableWriter>> entry : variables.entrySet())
        {
            int variableNum = entry.getValue().getFirst().getVariableNumber();
            VersionedValueList<Object> list = variableVersions.get(variableNum);
            writer.write("Variable '" + entry.getKey() + "' : " + list.toString() + "\n");
        }
        return writer.toString();
    }
}
