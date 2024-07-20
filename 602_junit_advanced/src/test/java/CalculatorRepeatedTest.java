import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Order(2)
public class CalculatorRepeatedTest {

    @DisplayName("Test 4 / 2 = 2")
    @RepeatedTest(3)
    public void testIntegerDivision(RepetitionInfo repetitionInfo) {
        System.out.println(repetitionInfo.getCurrentRepetition() + " of " + repetitionInfo.getTotalRepetitions());

        Calculator calc = new Calculator();

        int result = calc.integerDivision(4, 2);

        assertEquals(2, result, "4 /2 != 2");
    }
}
