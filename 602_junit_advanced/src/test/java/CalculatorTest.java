import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Order(1)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CalculatorTest {

    @Order(1)
    @DisplayName("Test 4 / 2 = 2")
    @Test
    public void testIntegerDivision() {
        Calculator calc = new Calculator();

        int result = calc.integerDivision(4, 2);

        assertEquals(2, result, "4 /2 != 2");
    }

    @Order(2)
    @DisplayName("10 / 0 throws ArithmeticException")
    @Test
    public void testIntegerDivision_dividedByZero() {
        Calculator calc = new Calculator();

        ArithmeticException arithmeticException = assertThrows(ArithmeticException.class, ()-> {
            calc.integerDivision(10, 0);
        });

        System.out.println(arithmeticException);
    }

    @Order(3)
    @DisplayName("Test MethodSource")
    @ParameterizedTest
    @MethodSource("integerSubtractionInputParameters")
    public void testIntegerSubtraction(int minuend, int subtrahend, int expectedResult) {
        Calculator calc = new Calculator();

        int actualResult = calc.integerSubtraction(minuend, subtrahend);

        assertEquals(expectedResult, actualResult,
                        () -> minuend + " - " + subtrahend + " did not produce " + expectedResult);
    }

    private static Stream<Arguments> integerSubtractionInputParameters() {
        return Stream.of(
                Arguments.of(33, 1, 32),
                Arguments.of(24, 10, 14),
                Arguments.of(22, 11, 11)
        );
    }

    @Order(4)
    @DisplayName("Test CsvSource")
    @ParameterizedTest
    @CsvSource({"33, 1, 32", "24, 10, 14", "22, 11, 11"})
    public void testIntegerSubtraction2(int minuend, int subtrahend, int expectedResult) {
        Calculator calc = new Calculator();

        int actualResult = calc.integerSubtraction(minuend, subtrahend);

        assertEquals(expectedResult, actualResult,
                () -> minuend + " - " + subtrahend + " did not produce " + expectedResult);
    }

    @Order(5)
    @DisplayName("Test CsvFileSource")
    @ParameterizedTest
    @CsvFileSource(resources = "testIntegerSubtraction3.csv")
    public void testIntegerSubtraction3(int minuend, int subtrahend, int expectedResult) {
        Calculator calc = new Calculator();

        int actualResult = calc.integerSubtraction(minuend, subtrahend);

        assertEquals(expectedResult, actualResult,
                () -> minuend + " - " + subtrahend + " did not produce " + expectedResult);
    }

    @Order(6)
    @DisplayName("Test ValueSource")
    @ParameterizedTest
    @ValueSource(strings = {"Steph", "LeBron", "Kevin", "Devin"})
    public void valueSource(String name) {
        System.out.println(name);
        assertNotNull(name);
    }
}
