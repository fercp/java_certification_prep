package ocp.functional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Summary
 *
 * @author Ferat Capar - U005509
 * @version Creation Date : 6.05.2018 17:00
 * @see
 */
public class TestStream {
    @Test
    public void testStream() {
        Stream<String> stringStream = Stream.empty();
        stringStream = Stream.<String>of("a", "b", "c");
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> fromList = list.stream();
        assertFalse(fromList.isParallel());
        Stream<String> fromListParallel = list.parallelStream();
        assertTrue(fromListParallel.isParallel());
        Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);
        assertEquals(Integer.valueOf(1), oddNumbers.findFirst().get());
        assertThrows(IllegalStateException.class, () -> oddNumbers.limit(3).max(Comparator.naturalOrder()));
        Stream<Integer> oddNumbers2 = Stream.iterate(1, n -> n + 2);
        assertEquals(Integer.valueOf(5), oddNumbers2.limit(3).max(Comparator.naturalOrder()).get());
    }

    @Test
    public void testMatch() {
        Stream<String> anyMatch = Stream.of("a", "b", "c");
        assertTrue(anyMatch.anyMatch(k -> k.equals("a")));
        assertThrows(IllegalStateException.class, () -> anyMatch.allMatch(k -> k.equals("a")));
        Stream<String> allMatch = Stream.of("a", "b", "c");
        assertFalse(allMatch.allMatch(k -> k.equals("a")));
        Stream<String> noneMatch = Stream.of("a", "b", "c");
        assertFalse(noneMatch.noneMatch(k -> k.equals("a")));
    }

    @Test
    public void testCount() {
        Stream<String> stringStream = Stream.of("a", "b", "c");
        assertEquals(3, stringStream.count());
        stringStream = Stream.empty();
        assertEquals(0, stringStream.count());
    }

    @Test
    public void testMinMax() {
        Stream<String> stringStream = Stream.of("a", "b", "c");
        Optional<String> max = stringStream.max(Comparator.naturalOrder());
        assertEquals("c", max.get());

        stringStream = Stream.of("a", "b", "c");
        Optional<String> min = stringStream.min(Comparator.naturalOrder());
        assertEquals("a", min.get());

        stringStream = Stream.of("a", "b", "c");
        Optional<String> reverse = stringStream.min(Comparator.reverseOrder());
        assertEquals("c", reverse.get());

    }

    @Test
    public void testFind() {
        Stream<String> stringStream = Stream.of("a", "b", "c");
        Stream<String> infinite = Stream.generate(() -> "d");
        Optional<String> any = stringStream.findAny();
        assertEquals("a", any.get());
        assertEquals("d", infinite.findFirst().get());
    }

    @Test
    public void testForEach() {
        Stream<String> stringStream = Stream.of("a", "b", "c");
        stringStream.forEach(Assertions::assertNotNull);
    }

    @Test
    public void testReduce() {
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        BinaryOperator<String> accumulator = (s, c) -> s + c;
        String word = stream.reduce("-", accumulator);
        assertEquals("-wolf", word);

        stream = Stream.of("w", "o", "l", "f");
        Optional optional = stream.reduce(accumulator);
        assertEquals("wolf", optional.get());

        stream = Stream.of("1", "2", "3", "4");
        BiFunction<Integer, String, Integer> accumulatorMedium = (i, s) -> i + s.charAt(0) - '1' + 1;
        BinaryOperator<Integer> combiner = (i, j) -> i + j;
        Integer alpha = stream.reduce(1, accumulatorMedium, combiner);
        assertEquals(Integer.valueOf(11), alpha);
    }

    @Test
    public void testCollect() {
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        Supplier<StringBuilder> supplier = StringBuilder::new;
        BiConsumer<StringBuilder, String> biConsumer = StringBuilder::append;
        BiConsumer<StringBuilder, StringBuilder> combiner = StringBuilder::append;
        StringBuilder word = stream.collect(supplier, biConsumer, combiner);
        assertEquals("wolf", word.toString());
        Stream<Integer> streamInt = Stream.of(119, 111, 108, 102);
        Set<String> set = streamInt.collect(TreeSet::new, (t, s) -> t.add(Character.toString((char) s.intValue())), TreeSet::addAll);
        assertEquals("[f, l, o, w]", set.toString());

        stream = Stream.of("w", "o", "l", "f");
        set = stream.collect(Collectors.toCollection(TreeSet::new));
        assertEquals("[f, l, o, w]", set.toString());
    }

    static class UnSortable {

    }

    @Test
    public void testIntermediate() {
        List<String> zero = Collections.emptyList();
        List<String> one = Collections.singletonList("Bonobo");
        List<String> two = Arrays.asList("Mama Gorilla", "Baby Gorilla");
        Stream<List<String>> animals = Stream.of(zero, one, two);
        assertEquals(Arrays.asList("Bonobo", "Mama Gorilla", "Baby Gorilla"), animals.flatMap(List::stream).collect(Collectors.toList()));
        assertEquals("Baby Gorilla", two.stream().sorted().findFirst().get());
        List<UnSortable> unSortableList = Arrays.asList(new UnSortable(), new UnSortable());
        assertThrows(ClassCastException.class, () -> unSortableList.stream().sorted().findFirst().isPresent());
    }

    @Test
    public void testMax(){
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        assertEquals("w",stream.max(Comparator.naturalOrder()).get());
    }


    @Test
    public void testJoining() {
        Stream<String> words = Stream.of("eeny", "meeny", "miny", "mo");
        assertEquals("eenymeenyminymo", words.collect(Collectors.joining()));
        words = Stream.of("eeny", "meeny", "miny", "mo");
        assertEquals("eeny,meeny,miny,mo", words.collect(Collectors.joining(",")));
        words = Stream.of("eeny", "meeny", "miny", "mo");
        assertEquals("[eeny, meeny, miny, mo]", words.collect(Collectors.joining(", ", "[", "]")));
    }

    @Test
    public void testParallelReduce(){
        List<String> lst=Arrays.asList("SE","EE");
        for(int i=0;i<100;i++)
        System.out.println(lst.parallelStream().unordered().reduce("JAVA ",(a,b)->a+b));

    }

}
