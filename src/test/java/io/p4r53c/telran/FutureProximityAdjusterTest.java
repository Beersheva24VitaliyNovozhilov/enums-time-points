package io.p4r53c.telran;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import io.p4r53c.telran.time.FutureProximityAdjuster;
import io.p4r53c.telran.time.TimePoint;
import io.p4r53c.telran.time.enums.TimeUnit;

class FutureProximityAdjusterTest {

    private TimePoint[] timePoints = {
            new TimePoint(1f, TimeUnit.NANOSECOND),
            new TimePoint(1_000f, TimeUnit.NANOSECOND),
            new TimePoint(1_000f, TimeUnit.MICROSECOND),
            new TimePoint(1_000f, TimeUnit.MILLISECOND),
            new TimePoint(1f, TimeUnit.SECOND),
            new TimePoint(30f, TimeUnit.SECOND),
            new TimePoint(10f, TimeUnit.MINUTE),
            new TimePoint(20f, TimeUnit.MINUTE),
            new TimePoint(30f, TimeUnit.MINUTE),
            new TimePoint(1f, TimeUnit.HOUR),
            new TimePoint(48f, TimeUnit.HOUR)
    };

    TimePoint[] emptyTimePointArray = new TimePoint[0];

    private FutureProximityAdjuster futureProximityAdjuster = new FutureProximityAdjuster(timePoints);
    private FutureProximityAdjuster futureProximityAdjusterEmpty = new FutureProximityAdjuster(emptyTimePointArray);

    @Test
    void testAdjustWithEmptyTimePointsArray() {
        TimePoint result = futureProximityAdjusterEmpty.adjust(new TimePoint(5f, TimeUnit.MINUTE));
        assertNull(result);
    }

    @Test
    void testAdjustWithNanosTimePoint() {
        TimePoint result = futureProximityAdjuster.adjust(new TimePoint(769f, TimeUnit.NANOSECOND));
        assertEquals(1_000f, result.getAmount());
    }

    @Test
    void testAdjustWithMicrosToSecondsTimePoint() {
        TimePoint result = futureProximityAdjuster.adjust(new TimePoint(20_000_000f, TimeUnit.MICROSECOND)); // 20 Sec

        assertEquals(result, new TimePoint(30f, TimeUnit.SECOND));
    }

    @Test
    void testAdjustWithSecondsTimePoint() {
        TimePoint result = futureProximityAdjuster.adjust(new TimePoint(25f, TimeUnit.SECOND));
        assertEquals(30f, result.getAmount());
    }

    @Test
    void testAdjustWithSecondsToMinutesTimePoint() {
        TimePoint result = futureProximityAdjuster.adjust(new TimePoint(599f, TimeUnit.SECOND));
        assertEquals(result, new TimePoint(10f, TimeUnit.MINUTE));
    }

    @Test
    void testAdjustWithMinutesTimePoint() {
        TimePoint result = futureProximityAdjuster.adjust(new TimePoint(5f, TimeUnit.MINUTE));
        assertEquals(10f, result.getAmount());
    }

    @Test
    void testAdjustWithMinutesTimePointNext() {
        TimePoint result = futureProximityAdjuster.adjust(new TimePoint(10f, TimeUnit.MINUTE));
        assertEquals(20f, result.getAmount());
    }

    @Test
    void testAdjustWithMinutesToHoursTimePoint() {
        TimePoint result = futureProximityAdjuster.adjust(new TimePoint(59f, TimeUnit.MINUTE));
        assertEquals(result, new TimePoint(1f, TimeUnit.HOUR));
    }

    @Test
    void testAdjustWithSecondsToHoursTimePoint() {
        TimePoint result = futureProximityAdjuster.adjust(new TimePoint(172799f, TimeUnit.SECOND));
        assertEquals(result, new TimePoint(48f, TimeUnit.HOUR));
    }
}
