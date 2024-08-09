package io.p4r53c.telran;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

import io.p4r53c.telran.time.enums.DayOfWeek;

class DayOfWeekTest {

    private static final String ISRAEL_WEEKEND = "Israel weekend";
    private static final String NON_ISRAEL_WEEKEND = "Non Israel weekend";
    private static final String WEEKEND = "Weekend";
    private static final String WORKING_DAY = "Working day";

    String getComment(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case FRIDAY -> ISRAEL_WEEKEND;
            case SATURDAY -> WEEKEND;
            case SUNDAY -> NON_ISRAEL_WEEKEND;
            default -> WORKING_DAY;
        };
    }

    @Test
    void testGetComment() {
        assertEquals(ISRAEL_WEEKEND, getComment(DayOfWeek.FRIDAY));
        assertEquals(WEEKEND, getComment(DayOfWeek.SATURDAY));
        assertEquals(NON_ISRAEL_WEEKEND, getComment(DayOfWeek.SUNDAY));
        assertEquals(WORKING_DAY, getComment(DayOfWeek.MONDAY));
        assertEquals(WORKING_DAY, getComment(DayOfWeek.TUESDAY));
        assertEquals(WORKING_DAY, getComment(DayOfWeek.WEDNESDAY));
        assertEquals(WORKING_DAY, getComment(DayOfWeek.THURSDAY));
    }

    @Test
    void testEnumExistingMethodTest() {
        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        assertEquals(7, daysOfWeek.length);
        assertArrayEquals(daysOfWeek, DayOfWeek.values());
        assertEquals("MONDAY", DayOfWeek.MONDAY.name());
        assertEquals("MONDAY", DayOfWeek.MONDAY.toString());
        assertEquals(DayOfWeek.MONDAY, DayOfWeek.valueOf("MONDAY"));
        assertThrowsExactly(IllegalArgumentException.class, () -> DayOfWeek.valueOf("SUN"));
    }
}
