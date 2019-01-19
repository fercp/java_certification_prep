package ocp.functional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLambda {


    public static Runnable print() {
        return Assertions::fail;
    }


    @Test
    public void testLambda() {
        Runnable r = TestLambda::print;
        /*
        Runnable r2=new Runnable() {
            @Override
            public void run() {
                TestLambda.print();
            }
        };*/
        r.run();
        List<String> strings =
                Arrays.asList("Stream", "Operations", "on", "Collections");
        Collections.sort(strings, String::compareTo);

        assertEquals("Collections",strings.get(0));
        assertEquals("[a, a, a, b, b, b, c, c, c]",
                Stream.of("a", "b", "c")
                        .flatMap(s -> Stream.of(s, s, s))
                        .collect(Collectors.toList()).toString()
        );

        OptionalDouble avg =
                IntStream.rangeClosed(1, 10)
                        .parallel()
                        .average();
        assertEquals(5.5,avg.getAsDouble());
    }
}

class Question_19_1 {
    protected static int m1() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            return 1;
        } finally {
            return 2;
        }
    }

    public static void main(String[] args) {
        Instant instant = Instant.now();
        System.out.println(instant.get(ChronoField.NANO_OF_SECOND));
        System.out.println(instant.getNano());
        ZoneOffset offset = ZoneOffset.of("Z");
        System.out.println(
                offset.get(ChronoField.OFFSET_SECONDS)
        );
    }
}