package net.esper.eql.variable;

import net.esper.schedule.TimeProvider;
import net.esper.util.JavaClassHelper;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Variables service for reading and writing variables, and for setting a version number for the current thread to
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
 * As an alternative to a version-based design, a read-lock for the variable space could also be used, with the following
 * disadvantages: The write lock may just not be granted unless fair locks are used which are more expensive; And
 * a read-lock is more expensive to acquire for multiple CPUs; A thread-local is still need to deal with
 * "set var1=3, var2=var1+1" assignments where the new uncommitted value must be visible in the local evaluation.
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
 * A statement that reads a variable has an {@link net.esper.eql.expression.ExprVariableNode} that has a {@link net.esper.eql.variable.VariableReader} handle
 * obtained during validation (example).
 * <p>
 * The {@link net.esper.eql.variable.VariableReader} takes the version from the threadlocal (570) and compares the version number with the
 * version numbers held for the variable.
 * If the current version is same or lower (520, as old or older) then the threadlocal version,
 * then use the current value.
 * If the current version is higher (571, newer) then the threadlocal version, then go to the prior value.
 * Use the prior value until a version is found that as old or older then the threadlocal version.
 * <p>
 * If no version can be found that is old enough, output a warning and return the newest version.
 * This should not happen, unless a thread is executing for very long within a single statement such that
 * lifetime-old-version time speriod passed before the thread asks for variable values.
 * <p>
 * As version numbers are counted up they may reach a boundary. Any write transaction after the boundary
 * is reached performs a roll-over. In a roll-over, all variables version lists are
 * newly created and any existing threads that read versions go against a (old) high-collection,
 * while new threads reading the reset version go against a new low-collection.
 */
public class VariableServiceImpl implements VariableService
{
    /**
     * Sets the boundary above which a reader considers the high-version list of variable values.
     * For use in roll-over when the current version number overflows the ROLLOVER_WRITER_BOUNDARY.
     */
    protected final static int ROLLOVER_READER_BOUNDARY = Integer.MAX_VALUE - 100000;

    /**
     * Sets the boundary above which a write transaction rolls over all variable's
     * version lists.
     */
    protected final static int ROLLOVER_WRITER_BOUNDARY = ROLLOVER_READER_BOUNDARY + 10000;

    /**
     * Applicable for each variable if more then the number of versions accumulated, check
     * timestamps to determine if a version can be expired.
     */
    protected final static int HIGH_WATERMARK_VERSIONS = 50;

    // Keep the variable list
    private final Map<String, VariableReader> variables;

    // Each variable has an index number, a current version and a list of values
    private final ArrayList<VersionedValueList<Object>> variableVersions;

    // Each variable may have a single callback to invoke when the variable changes
    private final ArrayList<VariableChangeCallback> changeCallbacks;

    // Write lock taken on write of any variable; and on read of older versions
    private final ReadWriteLock readWriteLock;

    // Thread-local for the visible version per thread
    private final VariableVersionThreadLocal versionThreadLocal = new VariableVersionThreadLocal();

    // Number of milliseconds that old versions of a variable are allowed to live
    private final long millisecondLifetimeOldVersions;
    private final TimeProvider timeProvider;

    private transient int currentVersionNumber;
    private int currentVariableNumber;

    /**
     * Ctor.
     * @param millisecondLifetimeOldVersions number of milliseconds a version may hang around before expiry
     * @param timeProvider provides the current time
     */
    public VariableServiceImpl(long millisecondLifetimeOldVersions, TimeProvider timeProvider)
    {
        this(0, millisecondLifetimeOldVersions, timeProvider);
    }

    /**
     * Ctor.
     * @param startVersion the first version number to start from
     * @param millisecondLifetimeOldVersions number of milliseconds a version may hang around before expiry
     * @param timeProvider provides the current time
     */
    protected VariableServiceImpl(int startVersion, long millisecondLifetimeOldVersions, TimeProvider timeProvider)
    {
        this.millisecondLifetimeOldVersions = millisecondLifetimeOldVersions;
        this.timeProvider = timeProvider;
        this.variables = new HashMap<String, VariableReader>();
        this.variableVersions = new ArrayList<VersionedValueList<Object>>();
        this.readWriteLock = new ReentrantReadWriteLock();
        this.changeCallbacks = new ArrayList<VariableChangeCallback>();
        currentVersionNumber = startVersion;
    }

    public void setLocalVersion()
    {
        versionThreadLocal.getCurrentThread().setVersion(currentVersionNumber);
    }

    public void registerCallback(int variableNumber, VariableChangeCallback variableChangeCallback)
    {
        changeCallbacks.set(variableNumber, variableChangeCallback);
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
        VariableReader reader = variables.get(variableName);
        if (reader != null)
        {
            throw new VariableExistsException("Variable by name '" + variableName + "' has already been created");
        }

        long timestamp = timeProvider.getTime();

        // create new holder for versions
        VersionedValueList<Object> valuePerVersion = new VersionedValueList<Object>(variableName, currentVersionNumber, coercedValue, timestamp, millisecondLifetimeOldVersions, readWriteLock.readLock(), HIGH_WATERMARK_VERSIONS, false);

        // add entries matching in index the variable number
        variableVersions.add(valuePerVersion);
        changeCallbacks.add(null);

        // create reader
        reader = new VariableReader(versionThreadLocal, variableType, variableName, currentVariableNumber, valuePerVersion);
        variables.put(variableName, reader);
        
        currentVariableNumber++;
    }

    public VariableReader getReader(String variableName)
    {
        return variables.get(variableName);
    }

    public void write(int variableNumber, Object newValue)
    {
        VariableVersionThreadEntry entry = versionThreadLocal.getCurrentThread();
        if (entry.getUncommitted() == null)
        {
            entry.setUncommitted(new HashMap<Integer, Object>());
        }
        entry.getUncommitted().put(variableNumber, newValue);
    }

    public ReadWriteLock getReadWriteLock()
    {
        return readWriteLock;
    }

    public void commit()
    {
        VariableVersionThreadEntry entry = versionThreadLocal.getCurrentThread();
        if (entry.getUncommitted() == null)
        {
            return;
        }

        // get new version for adding the new values (1 or many new values)
        int newVersion = currentVersionNumber + 1;

        if (currentVersionNumber == ROLLOVER_READER_BOUNDARY)
        {
            // Roll over to new collections;
            // This honors existing threads that will now use the "high" collection in the reader for high version requests
            // and low collection (new and updated) for low version requests
            rollOver();
            newVersion = 2;
        }
        long timestamp = timeProvider.getTime();

        // apply all uncommitted changes
        for (Map.Entry<Integer, Object> uncommittedEntry : entry.getUncommitted().entrySet())
        {
            VersionedValueList<Object> versions = variableVersions.get(uncommittedEntry.getKey());

            // add new value as a new version
            Object oldValue = versions.addValue(newVersion, uncommittedEntry.getValue(), timestamp);

            // make a callback that the value changed
            VariableChangeCallback callback = changeCallbacks.get(uncommittedEntry.getKey());            
            if (callback != null)
            {
                callback.update(uncommittedEntry.getValue(), oldValue);
            }
        }

        // this makes the new values visible to other threads (not this thread unless set-version called again)
        currentVersionNumber = newVersion;
        entry.setUncommitted(null);    // clean out uncommitted variables
    }

    public void rollback()
    {
        VariableVersionThreadEntry entry = versionThreadLocal.getCurrentThread();
        entry.setUncommitted(null);
    }

    /**
     * Rollover includes creating a new
     */
    private void rollOver()
    {
        for (Map.Entry<String, VariableReader> entry : variables.entrySet())
        {
            int variableNum = entry.getValue().getVariableNumber();
            String name = entry.getKey();
            long timestamp = timeProvider.getTime();

            // Construct a new collection, forgetting the history
            VersionedValueList<Object> versionsOld = variableVersions.get(variableNum);
            Object currentValue = versionsOld.getCurrentAndPriorValue().getCurrentVersion().getValue();
            VersionedValueList<Object> versionsNew = new VersionedValueList<Object>(name, 1, currentValue, timestamp, millisecondLifetimeOldVersions, readWriteLock.readLock(), HIGH_WATERMARK_VERSIONS, false);

            // Tell the reader to use the high collection for old requests
            entry.getValue().setVersionsHigh(versionsOld);
            entry.getValue().setVersionsLow(versionsNew);

            // Save new collection instead
            variableVersions.set(variableNum, versionsNew);
        }
    }

    public String toString()
    {
        StringWriter writer = new StringWriter();
        for (Map.Entry<String, VariableReader> entry : variables.entrySet())
        {
            int variableNum = entry.getValue().getVariableNumber();
            VersionedValueList<Object> list = variableVersions.get(variableNum);
            writer.write("Variable '" + entry.getKey() + "' : " + list.toString() + "\n");
        }
        return writer.toString();
    }
}
