/*
 * Created on Apr 22, 2006
 *
 */
package net.esper.example.transaction.sim;

import java.security.SecureRandom;
import java.util.Random;

/** Just so we can swap between Random and SecureRandom.
 * 
 * @author Hans Gilde
 *
 */
public class RandomUtil {
    public static Random getNewInstance() {
        return new SecureRandom();
    }
}
