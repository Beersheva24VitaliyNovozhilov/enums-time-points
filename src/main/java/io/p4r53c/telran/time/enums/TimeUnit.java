package io.p4r53c.telran.time.enums;

import io.p4r53c.telran.time.TimePoint;

public enum TimeUnit {
    MILLISECOND(1),
    SECOND(1_000),
    MINUTE(60_000),
    HOUR(3_600_000);

    private float millis;

    TimeUnit(float f) {
        this.millis = f;
    }

    /**
     * Returns the number of milliseconds for the current time unit.
     *
     * @return the number of milliseconds
     */
    public float getMillis() {
        return millis;
    }

    /**
     * Calculates the difference between two TimePoints in the current time unit.
     *
     * @param t1 the first TimePoint
     * @param t2 the second TimePoint
     * @return the difference between t1 and t2 in the current time unit
     */
    public float between(TimePoint t1, TimePoint t2) {
        float t1Millis = t1.getAmount() * t1.getTimeUnit().getMillis();
        float t2Millis = t2.getAmount() * t2.getTimeUnit().getMillis();
        float result = (t2Millis - t1Millis) / this.getMillis();

        if (t2Millis < t1Millis) {
            result = -result;
        }

        return result;
    }

}
