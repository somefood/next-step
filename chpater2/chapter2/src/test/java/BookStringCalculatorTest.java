import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookStringCalculatorTest {

    private BookStringCalculator cal;

    @BeforeEach
    void setup() {
        cal = new BookStringCalculator();
    }

    @Test
    @DisplayName("null이거나 빈 문자열이면 0을 반환한다.")
    void test1() {
        assertEquals(0, cal.add(null));
        assertEquals(0, cal.add(""));
    }

    @Test
    @DisplayName("숫자하나 추가")
    void test2() {
        assertEquals(1, cal.add("1"));
    }

    @Test
    @DisplayName("쉼표 구분자 추가")
    void test3() {
        assertEquals(3, cal.add("1,2"));
    }

    @Test
    @DisplayName("쉼표 또는 콜론 구분자 추가")
    void test4() {
        assertEquals(6, cal.add("1,2:3"));
    }

    @Test
    @DisplayName("custom 구분자 추가")
    void test5() {
        assertEquals(6, cal.add("//;\n1;2;3"));
    }

    @Test
    @DisplayName("RuntimeException 던지기")
    void test6() {
        assertThrows(RuntimeException.class, () -> cal.add("-1,2,3"));
    }
}