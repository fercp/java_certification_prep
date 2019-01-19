package ocp.functional;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Summary
 *
 * @author Ferat Capar - U005509
 * @version Creation Date : 5.05.2018 11:12
 * @see
 */
public class TestFunction {
    public int x=40;
    BooleanSupplier a;
    {
        a=()->x>50;
    }

    interface A{
        int m1(int i);
    }
    @Test
    public void testFunction(){
        IntUnaryOperator u1 = i -> i / 6;
        IntUnaryOperator u2 = i -> i + 12;
        assertEquals(4,
                u1.compose(u2).applyAsInt(12)
        );
        Function<String,Integer> convert2Int=Integer::parseInt;
        assertEquals(Integer.valueOf(2),convert2Int.apply("2"));
        BiFunction<String,String,String> concat=String::concat;
        assertEquals("ab",concat.apply("a","b"));
        assertFalse(a.getAsBoolean());
        x=60;
        assertTrue(a.getAsBoolean());
        assertFalse(Optional.empty().isPresent());
        assertThrows(NullPointerException.class,()->Optional.of(null));
        assertFalse(Optional.ofNullable(null).isPresent());
        Optional<Exception> opt=Optional.empty();
        assertThrows(IllegalArgumentException.class,()->opt.orElseThrow(IllegalArgumentException::new));
        UnaryOperator<String> unaryOperator=s->s;
        m1(unaryOperator);
        Function<String,String> function=s->s;
        m1(function);
    }

    private void m1(Function<String,String> fun){}
    private void m2(UnaryOperator<String> fun){}
}

@FunctionalInterface
interface X{
    boolean equals(Object o);
    void m1();
}