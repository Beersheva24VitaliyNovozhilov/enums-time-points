package io.p4r53c.telran;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.p4r53c.telran.time.PlusTimePointAdjuster;
import io.p4r53c.telran.time.TimePoint;
import io.p4r53c.telran.time.ITimePointAdjuster;
import io.p4r53c.telran.time.enums.TimeUnit;

class TimePointTest {

    private static final float DELTA = 0.1f;

    // --- Adjusters ---

    @Test
    void testWithValidPlusAdjuster() {
        TimePoint timePoint = new TimePoint(10f, TimeUnit.HOUR);
        PlusTimePointAdjuster adjuster = new PlusTimePointAdjuster(5, TimeUnit.HOUR);
        TimePoint result = timePoint.with(adjuster);

        assertNotNull(result);
        assertEquals(15f, result.getAmount());
        assertEquals(TimeUnit.HOUR, result.getTimeUnit());
    }

    @Test
    void testWithAdjusterThatReturnsNull() {
        TimePoint timePoint = new TimePoint(10f, TimeUnit.HOUR);
        ITimePointAdjuster adjuster = new ITimePointAdjuster() {
            @Override
            public TimePoint adjust(TimePoint timePoint) {
                return null;
            }
        };

        TimePoint result = timePoint.with(adjuster);
        assertNull(result);
    }

    // --- compare ---

    @Test
    void testCompareToSameTimePoints() {
        TimePoint t1 = new TimePoint(10f, TimeUnit.MINUTE);
        TimePoint t2 = new TimePoint(10f, TimeUnit.MINUTE);

        assertEquals(0, t1.compareTo(t2));
    }

    @Test
    void testCompareToDifferentTimePoints() {
        TimePoint t1 = new TimePoint(10f, TimeUnit.MINUTE);
        TimePoint t2 = new TimePoint(20f, TimeUnit.MINUTE);

        assertTrue(t1.compareTo(t2) < 0);
        assertTrue(t2.compareTo(t1) > 0);
    }

    @Test
    void testCompareToDifferentTimeUnits() {
        TimePoint t1 = new TimePoint(1f, TimeUnit.MICROSECOND);
        TimePoint t2 = new TimePoint(1_000f, TimeUnit.NANOSECOND);

        assertEquals(0, t1.compareTo(t2));
    }

    // --- convert ---

    @Test
    void testConvertFromSecondsToMinutes() {
        TimePoint timePoint = new TimePoint(60f, TimeUnit.SECOND);
        TimePoint convertedTimePoint = timePoint.convert(TimeUnit.MINUTE);

        assertEquals(1f, convertedTimePoint.getAmount(), DELTA);
        assertEquals(TimeUnit.MINUTE, convertedTimePoint.getTimeUnit());
    }

    @Test
    void testConvertFromMinutesToHours() {
        TimePoint timePoint = new TimePoint(60f, TimeUnit.MINUTE);
        TimePoint convertedTimePoint = timePoint.convert(TimeUnit.HOUR);

        assertEquals(1f, convertedTimePoint.getAmount());
        assertEquals(TimeUnit.HOUR, convertedTimePoint.getTimeUnit());
    }

    @Test
    void testConvertFromSecondsToHours() {
        TimePoint timePoint = new TimePoint(3600f, TimeUnit.SECOND);
        TimePoint convertedTimePoint = timePoint.convert(TimeUnit.HOUR);

        assertEquals(1f, convertedTimePoint.getAmount());
        assertEquals(TimeUnit.HOUR, convertedTimePoint.getTimeUnit());
    }

    @Test
    void testConvertFromNanosToMilliseconds() {
        TimePoint timePoint = new TimePoint(1_000_000f, TimeUnit.NANOSECOND);
        TimePoint convertedTimePoint = timePoint.convert(TimeUnit.MILLISECOND);

        assertEquals(1f, convertedTimePoint.getAmount());
        assertEquals(TimeUnit.MILLISECOND, convertedTimePoint.getTimeUnit());
    }

    // --- between ---

    @Test
    void testBetween() {
        TimePoint t1 = new TimePoint(1f, TimeUnit.HOUR);
        TimePoint t2 = new TimePoint(61f, TimeUnit.MINUTE);

        TimePoint t3 = new TimePoint(1f, TimeUnit.MICROSECOND);
        TimePoint t4 = new TimePoint(1f, TimeUnit.MILLISECOND);

        assertEquals(60f, TimeUnit.SECOND.between(t1, t2), DELTA);
        assertEquals(1f, TimeUnit.MINUTE.between(t1, t2), DELTA);
        assertEquals(1f / 60f, TimeUnit.HOUR.between(t1, t2), DELTA);
        
        assertEquals(999000f, TimeUnit.NANOSECOND.between(t3, t4));
        assertEquals(999f, TimeUnit.MICROSECOND.between(t3, t4));
    }

}
