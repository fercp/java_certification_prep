package ocp.functional;

import org.junit.jupiter.api.Test;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Summary
 *
 * @author Ferat Capar - U005509
 * @version Creation Date : 5.05.2018 00:46
 * @see
 */
public class TestPredicate {
    @Test
    public void testPredicate(){
        Predicate<String> predicate=s->s.isEmpty();
        assertTrue(predicate.test(""));
        predicate=String::isEmpty;
        assertTrue(predicate.test(""));

        BiPredicate<String, String> b1 = String::startsWith;
        BiPredicate<String, String> b2 = (string, prefix) -> string.startsWith(prefix);
        assertTrue(b1.test("chicken", "chick"));
        assertTrue(b2.test("chicken", "chick"));

        Predicate<Integer> isPositive=k->k>=0;
        Predicate<Integer> isEven=k->k%2==0 ;

        int x=4;
        assertTrue(isPositive.and(isEven).test(x));
        assertFalse(isPositive.and(isEven).test(-4));
        assertTrue(isPositive.or(isEven).test(-4));
        assertFalse(isPositive.and(isEven).negate().test(x));
    }
}
