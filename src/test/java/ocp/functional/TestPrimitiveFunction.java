package ocp.functional;

import org.junit.jupiter.api.Test;

import java.util.function.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPrimitiveFunction {
    @Test
    public void testFunction() {
        IntFunction<String> intFunction = String::valueOf;
        assertEquals("5", intFunction.apply(5));
        ToIntFunction<String> toIntFunction = Integer::parseInt;
        assertEquals(5, toIntFunction.applyAsInt("5"));
        IntToDoubleFunction intToDoubleFunction = Double::valueOf;
        assertEquals(5.0, intToDoubleFunction.applyAsDouble(5));
        IntUnaryOperator intUnaryOperator = i -> i + 1;
        assertEquals(6, intUnaryOperator.applyAsInt(5));
    }

    @Test
    public void testSupplier() {
        BooleanSupplier booleanSupplier=()->true;
        assertTrue(booleanSupplier.getAsBoolean());
        IntSupplier intSupplier=()->1;
        assertEquals(1,intSupplier.getAsInt());
    }

    @Test
    public void testConsumer() {
        IntConsumer intConsumer=i->assertEquals(1,i);
        intConsumer.accept(1);
        DoubleConsumer doubleConsumer=i->assertEquals(1.0,i);
        doubleConsumer.accept(1.0);
    }

    @Test
    public void testPredicate() {
        IntPredicate intPredicate=i->i==1;
        assertTrue(intPredicate.test(1));
    }

    @Test
    public void testOperators() {
        IntUnaryOperator intUnaryOperator=i->i+1;
        assertEquals(2,intUnaryOperator.applyAsInt(1));
        IntBinaryOperator intBinaryOperator=(i,j)->i+j;
        assertEquals(3,intBinaryOperator.applyAsInt(1,2));
    }

    @Test
    public void testToFunction(){
        ToIntFunction<String> toIntFunction=Integer::parseInt;
        assertEquals(5,toIntFunction.applyAsInt("5"));
    }

    @Test
    public void testToBiFunction(){
        ToIntBiFunction<String,String> toIntFunction=(s,t)->Integer.parseInt(s)+Integer.parseInt(t);
        assertEquals(11,toIntFunction.applyAsInt("5","6"));
    }

    @Test
    public void testFromToFunction(){
        DoubleToIntFunction doubleToIntFunction=k->Double.valueOf(k).intValue();
        assertEquals(5,doubleToIntFunction.applyAsInt(5.0));
    }

    @Test
    public void testObjIntConsumer(){
        ObjIntConsumer<String> objIntConsumer=(s,i)->assertEquals(i,Integer.parseInt(s));
        objIntConsumer.accept("5",5);
    }
}
