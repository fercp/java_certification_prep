package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestParallelStreams {
    @Test
    public void testParallelReduce(){
        System.out.println(Stream.iterate("1",a->Character.valueOf((char)(a.charAt(0)+1)).toString()).
                limit(100).
                parallel()
                .reduce("X",String::concat));
        System.out.println(Stream.iterate("1",a->Character.valueOf((char)(a.charAt(0)+1)).toString()).
                limit(50).
                parallel()
                .reduce("",String::concat));
        System.out.println(Stream.iterate("1",a->Character.valueOf((char)(a.charAt(0)+1)).toString()).
                limit(100).
                parallel()
                .reduce("e",(i,j)->i+j,
                        (i,j)->j+i));

        System.out.println(IntStream.rangeClosed(1,100)
                .parallel()
                .reduce(0,(a,b) -> (a-b)));
    }

    @Test
    public void testParallelCollect(){
        Stream<String> ohMy = Stream.of("lions", "tigers", "bears").parallel();
        ConcurrentMap<Integer, String> map = ohMy
                .collect(Collectors.toConcurrentMap(String::length, k -> k,
                        (s1, s2) -> s1 + "," + s2));
        assertEquals("tigers",map.get(6));

        ohMy = Stream.of("lions", "tigers", "bears").parallel();
        ConcurrentMap<Integer, List<String>> map2= ohMy.collect(
                Collectors.groupingByConcurrent(String::length));
        assertEquals(Arrays.asList("tigers"),map2.get(6));

        Stream<String> stream = Stream.iterate("1",a->String.valueOf((char)(a.charAt(0)+1))).unordered().parallel().limit(200);
        List<String> list = stream.collect(ArrayList::new, ArrayList::add,
                ArrayList::addAll);
        // values of list in indeterministic
    }

    @Test
    public void testUnordered(){
        for(int i=0;i<100;i++) {
            Stream<List<Integer>> sDogNames = Stream.generate(() -> Stream.iterate(0,s->s+1).limit(100).collect(Collectors.toList())).unordered();
            sDogNames.parallel().filter(s->true).limit(2).flatMap(s -> s.stream().parallel()).forEach(s -> System.out.print(s + " "));
            System.out.println();
        }

    }
}
