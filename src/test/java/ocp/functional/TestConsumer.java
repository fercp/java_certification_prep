package ocp.functional;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Summary
 *
 * @author Ferat Capar - U005509
 * @version Creation Date : 5.05.2018 00:35
 * @see
 */
public class TestConsumer {
    @Test
    public void testConsumer(){
        LongAdder longAdder=new LongAdder();
        Consumer<Integer> consumer=s->longAdder.add(s);
        consumer.accept(5);
        assertEquals(5,longAdder.intValue());

        consumer=longAdder::add;
        consumer.accept(5);
        assertEquals(10,longAdder.intValue());

        BiConsumer<Integer,Integer> biConsumer=(a,b)-> longAdder.add(a+b);
        biConsumer.accept(2,3);
        assertEquals(15,longAdder.intValue());
    }
}
