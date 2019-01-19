package ocp.functional;

import org.junit.jupiter.api.Test;

import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.stream.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Summary
 *
 * @author Ferat Capar
 * @version Creation Date : 6.05.2018 23:33
 */
public class TestPrimitiveStreams {
    @Test
    public void testIntStream(){
        IntStream intStream=IntStream.range(1,4);
        assertEquals(6,intStream.sum());
        intStream=IntStream.rangeClosed(1,4);
        assertEquals(10,intStream.sum());
        intStream=IntStream.rangeClosed(1,4);
        OptionalDouble optionalDouble=intStream.average();
        assertEquals(2.5,optionalDouble.getAsDouble());
        intStream=IntStream.range(1,4);
        assertEquals(1,intStream.min().getAsInt());

        DoubleStream doubleStream=DoubleStream.iterate(1.0,n->n+1);
        assertEquals(6,doubleStream.limit(3).sum());
        doubleStream=DoubleStream.iterate(1.0,n->n+1);
        optionalDouble=doubleStream.limit(4).average();
        assertEquals(2.5,optionalDouble.getAsDouble());


        LongStream longStream=LongStream.iterate(1, n->n+1);
        assertEquals(6,longStream.limit(3).sum());
        longStream=LongStream.iterate(1,n->n+1);
        optionalDouble=longStream.limit(4).average();
        assertEquals(2.5,optionalDouble.getAsDouble());

        intStream=IntStream.range(1,4);
        assertEquals(3,intStream.max().orElse(0));

        intStream=IntStream.rangeClosed(1,4);
        assertEquals(4,intStream.max().orElseGet(()->5));

        intStream=IntStream.rangeClosed(1,4);
        IntSummaryStatistics intSummaryStatistics=intStream.summaryStatistics();
        assertEquals(4,intSummaryStatistics.getMax());
        assertEquals(1,intSummaryStatistics.getMin());
        assertEquals(2.5,intSummaryStatistics.getAverage());
        assertEquals(4,intSummaryStatistics.getCount());
    }

    @Test
    public void testConversions(){
        Stream<String> stringStream=Stream.of("1","2","3");
        IntStream intStream=stringStream.mapToInt(Integer::parseInt);
        stringStream=Stream.of("1","2","3");
        DoubleStream doubleStream=stringStream.mapToDouble(Double::parseDouble);
        stringStream=Stream.of("1","2","3");
        LongStream longStream=stringStream.mapToLong(Long::parseLong);
        assertEquals(6,intStream.sum());
        assertEquals(6.0,doubleStream.sum());
        assertEquals(6L,longStream.sum());
        stringStream=Stream.of("1","2","3");
        intStream=stringStream.mapToInt(Integer::parseInt);
        stringStream=intStream.mapToObj(Integer::toString);
        assertEquals("123",stringStream.collect(Collectors.joining()));
        stringStream=Stream.of("1","2","3");
        intStream=stringStream.mapToInt(Integer::parseInt);
        doubleStream=intStream.mapToDouble(i->(double)i);
        assertEquals(6.0,doubleStream.sum());
        stringStream=Stream.of("1","2","3");
        intStream=stringStream.mapToInt(Integer::parseInt).map(i->i+1);
        assertEquals(9,intStream.sum());
    }
}
