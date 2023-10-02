import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

//    private static Calculator cal;
//
//    @BeforeAll
//    static void setup() {
//        System.out.println("초기화 한 번");
//        cal = new Calculator();
//    }

    private Calculator cal;

    @BeforeEach
    void setup() {
        System.out.println("초기화");
        cal = new Calculator();
    }

    @AfterEach
    void teardown() {
        System.out.println("tear down");
    }

    @Test
    void add() {
        assertEquals(9, cal.add(6, 3));
    }

    @Test
    void subtract() {
        assertEquals(3, cal.subtract(6, 3));
    }

    @Test
    void multiply() {
        assertEquals(18, cal.multiply(6, 3));
    }

    @Test
    void divide() {
        assertEquals(2, cal.divide(6, 3));
    }
}