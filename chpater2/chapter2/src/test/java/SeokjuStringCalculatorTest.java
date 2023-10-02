import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeokjuStringCalculatorTest {

    private SeokjuStringCalculator calculator;

    @BeforeEach
    void setup() {
        calculator = new SeokjuStringCalculator();
    }

    @Test
    @DisplayName("빈 문자열이면 0을 반환한다.")
    void test1() {
        int result = calculator.cal(" ");
        assertEquals(0, result);
    }

    @Test
    @DisplayName(", 또는 :는 구분자로 취급하여 더한다.")
    void test2() {
        int result1 = calculator.cal("1,2");
        assertEquals(3, result1);

        int result2 = calculator.cal("1,2,3");
        assertEquals(6, result2);

        int result3 = calculator.cal("1,2:3");
        assertEquals(6, result3);
    }


    @Test
    @DisplayName("음수를 전달하는 경우 RuntimeException 예외가 발생한다.")
    void test3() {
        assertThrows(RuntimeException.class, () -> calculator.cal("-3"));
        assertThrows(RuntimeException.class, () -> calculator.cal("1:-2"));
        assertThrows(RuntimeException.class, () -> calculator.cal("1,-4"));
    }

    @Test
    @DisplayName("커스텀 기본자 지정. //와 \n 사이에 있는것이 구분자가 된다.")
    void test4() {
        int result = calculator.cal("//;\n1;2;3");
        assertEquals(6, result);
    }

}