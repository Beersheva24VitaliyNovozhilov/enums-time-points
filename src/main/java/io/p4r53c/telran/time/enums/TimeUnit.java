package io.p4r53c.telran.time.enums;

import io.p4r53c.telran.time.TimePoint;

public enum TimeUnit {

    NANOSECOND(1),
    MICROSECOND(1_000),
    MILLISECOND(1_000_000),
    SECOND(1_000_000_000),
    MINUTE(60_000_000_000L),
    HOUR(3_600_000_000_000L);

    private long nanos;

    TimeUnit(long l) {
        this.nanos = l;
    }

    /**
     * Returns the number of nanoseconds for the given time unit.
     *
     * @return the number of nanoseconds
     */
    public long getNanos() {
        return nanos;
    }

    /**
     * Calculates the difference between two time points in the current time unit.
     *
     * @param t1 the first time point
     * @param t2 the second time point
     * @return the difference between the two time points in the current time unit
     */
    public float between(TimePoint t1, TimePoint t2) {
        float t1Nanos = t1.getAmount() * t1.getTimeUnit().getNanos();
        float t2Nanos = t2.getAmount() * t2.getTimeUnit().getNanos();
        float result = (t2Nanos - t1Nanos) / this.getNanos();

        if (t2Nanos < t1Nanos) {
            result = -result;
        }

        return result;
    }
}
