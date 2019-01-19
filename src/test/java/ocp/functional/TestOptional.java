package ocp.functional;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Summary
 *
 * @author Ferat Capar - U005509
 * @version Creation Date : 6.05.2018 16:33
 * @see
 */
public class TestOptional {
    @Test
    public void testOptional(){
        Optional<Integer> optionalInteger=Optional.empty();
        assertEquals(Integer.valueOf(0),optionalInteger.orElse(0));
        assertEquals(Integer.valueOf(0),optionalInteger.orElseGet(()->0));
        assertFalse(optionalInteger.isPresent());
        assertThrows(NoSuchElementException.class,()->optionalInteger.get());
        Optional optionalInteger2=Optional.of(1);
        assertEquals(Integer.valueOf(1),optionalInteger2.get());
        AtomicBoolean present=new AtomicBoolean(false);
        optionalInteger2.ifPresent(k->present.set(true));
        assertTrue(present.get());
        assertEquals("a",optionalInteger2.flatMap(k->Optional.of("a")).get() ) ;
        assertEquals("a",optionalInteger2.map(k->"a").get() ) ;
        Optional<String> optional=Optional.ofNullable(null);
        assertFalse(optional.isPresent());
        assertThrows(NullPointerException.class,()->Optional.of(null));
    }
}
