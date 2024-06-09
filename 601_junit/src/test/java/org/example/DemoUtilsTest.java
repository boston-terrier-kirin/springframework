package org.example;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeAll
    static void beforeAll() {
        System.out.println("=== beforeAll");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("=== afterAll");
    }

    @BeforeEach
    void beforeEach() {
        demoUtils = new DemoUtils();
    }

    @AfterEach
    void afterEach() {
        demoUtils = null;
    }

    @Test
    @DisplayName("assertEquals / assertNotEquals")
    void equals_notEquals() {
        assertEquals(6, demoUtils.add(2, 4), "2+4は6");
        assertNotEquals(6, demoUtils.add(2, 3), "2+3は6ではない");

        // Stringも比較できるっぽい
        assertEquals("ABC", "ABC");
    }

    @Test
    @DisplayName("assertNull / assertNotNull")
    void null_notNull() {
        assertNull(demoUtils.checkNull(null));
        assertNotNull(demoUtils.checkNull("ABC"));
    }

    @Test
    void same_notSame() {
        String str = "Luv2Code";

        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate());
        assertNotSame(str, demoUtils.getAcademy());
    }

    @Test
    void true_false() {
        assertTrue(demoUtils.isGreater(10, 9));
        assertFalse(demoUtils.isGreater(10, 19));
    }

    @Test
    void arrayEquals() {
        String[] arr = {"A", "B", "C"};
        assertArrayEquals(arr, demoUtils.getFirstThreeLettersOfAlphabet());
    }

    @Test
    void iterableEquals() {
        List<String> list = List.of("luv", "2", "code");
        assertIterableEquals(list, demoUtils.getAcademyInList());
    }

    @Test
    void linesMatch() {
        List<String> list = List.of("luv", "2", "code");
        assertLinesMatch(list, demoUtils.getAcademyInList());
    }
}
