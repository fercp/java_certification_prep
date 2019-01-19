package ocp.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestAssertions {
    @Test
    public void testAssertions(){
        assert true;
        assert true : "error";
        assert(true);
        assert(true): "error";

        //assert(false,"error"); compile error
        //if -ea test  becames true else test remains false
        boolean test=false;
        assert test=true;
        assertFalse(test);
    }
}
