package ocp.functional;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestCollectors {
    @Test
    public void testAveragingInt() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        assertEquals(Double.valueOf(2.0), integerStream.collect(Collectors.averagingInt(k -> k)));
    }

    @Test
    public void testCounting() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        assertEquals(Long.valueOf(3), integerStream.collect(Collectors.counting()));
    }

    @Test
    public void testGroupingBy() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);
        Map<Integer, List<Integer>> collect = integerStream.collect(Collectors.groupingBy(k -> k % 2));
        assertEquals(Arrays.asList(2, 4), collect.get(0));
        assertEquals(Arrays.asList(1, 3, 5), collect.get(1));

        integerStream = Stream.of(1, 2, 3, 4, 5);
        Map<Integer, Set<Integer>> set = integerStream.collect(Collectors.groupingBy(k -> k % 2, Collectors.toSet()));
        assertEquals(new HashSet<>(Arrays.asList(2, 4)), set.get(0));
        assertEquals(new HashSet<>(Arrays.asList(1, 3, 5)), set.get(1));

        integerStream = Stream.of(1, 2, 3, 4, 5);
        TreeMap<Integer, Set<Integer>> collect1 = integerStream.collect(Collectors.groupingBy(k -> k % 2, TreeMap::new, Collectors.toSet()));
        assertEquals(new HashSet<>(Arrays.asList(2, 4)), collect1.get(0));
        assertEquals(new HashSet<>(Arrays.asList(1, 3, 5)), collect1.get(1));

        integerStream = Stream.of(1, 2, 3, 4, 5);
        Map<Integer, Integer> sums = integerStream.collect(Collectors.groupingBy(k -> k % 2, TreeMap::new, Collectors.summingInt(k -> k)));
        assertEquals(Integer.valueOf(6), sums.get(0));
        assertEquals(Integer.valueOf(9), sums.get(1));

        integerStream = Stream.of(1, 2, 3, 4, 5);
        Map<Integer, HashSet<Integer>> manualCollect = integerStream.collect(Collectors.groupingBy(k -> k % 2,
                Collector.of(
                        HashSet::new,
                        HashSet::add,
                        (a, b) -> {
                            a.addAll(b);
                            return a;
                        }
                )));
        assertEquals(new HashSet<>(Arrays.asList(2, 4)), manualCollect.get(0));
        assertEquals(new HashSet<>(Arrays.asList(1, 3, 5)), manualCollect.get(1));
    }

    @Test
    public void testJoining(){
        Stream<String> stringStream=Stream.of("a","b","c");
        assertEquals("abc",stringStream.collect(Collectors.joining()));
        stringStream=Stream.of("a","b","c");
        assertEquals("a-b-c",stringStream.collect(Collectors.joining("-")));
        assertEquals("~a-b-c!",stringStream.collect(Collectors.joining("-","~","!")));
    }

    @Test
    public void testMaxBy(){
        Stream<Integer> integerStream=Stream.of(1,2,3,4,5);
        Optional<Integer> optional = integerStream.collect(Collectors.maxBy(Comparator.naturalOrder()));
        assertEquals(Integer.valueOf(5),optional.get());
    }

    @Test
    public void testMapping(){
        Stream<Integer> integerStream=Stream.of(1,2,3,4,5);
        assertEquals(Integer.valueOf(30),integerStream.collect(Collectors.mapping(k->k*2,Collectors.summingInt(i->i))));
    }

    @Test
    public void testPartitioningBy(){
        Stream<Integer> integerStream=Stream.of(1,2,3,4,5);
        Map<Boolean, List<Integer>> collect = integerStream.collect(Collectors.partitioningBy(k -> k % 2 == 0));
        assertEquals(Arrays.asList(1,3,5),collect.get(false));

        integerStream=Stream.of(1,2,3,4,5);
        Map<Boolean, Integer> collect1 = integerStream.collect(Collectors.partitioningBy(k -> k % 2 == 0, Collectors.summingInt(k -> k)));
        assertEquals(Integer.valueOf(6),collect1.get(true));
        assertEquals(Integer.valueOf(9),collect1.get(false));
    }

    @Test
    public void testToMap(){
        Stream<Integer> integerStream=Stream.of(1,2,3,4,5);
        Map<Integer, Integer> collect = integerStream.collect(Collectors.toMap(k -> k, k -> k));
        assertEquals(Integer.valueOf(1),collect.get(1));

        integerStream=Stream.of(1,2,3,4,5,5);
        collect = integerStream.collect(Collectors.toMap(k -> k, k -> k,(a,b)->a+b));
        assertEquals(Integer.valueOf(1),collect.get(1));
        assertEquals(Integer.valueOf(10),collect.get(5));

        integerStream=Stream.of(1,2,3,4,5,5);
        collect = integerStream.collect(Collectors.toMap(k -> k, k -> k,(a,b)->a+b,TreeMap::new));
        assertEquals(Integer.valueOf(1),collect.get(1));
        assertEquals(Integer.valueOf(10),collect.get(5));
    }

    @Test
    public void testIsEmpty(){
        Stream<String> s=Stream.generate(()->"meow");
        assertFalse(s.allMatch(String::isEmpty));
    }
}
