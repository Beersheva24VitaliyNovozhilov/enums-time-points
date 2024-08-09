package io.p4r53c.telran.time;

import java.util.Objects;

import io.p4r53c.telran.time.enums.TimeUnit;

/**
 * Represents a specific point in time with a specific unit of time.
 * 
 * HW 15
 * 
 * @author p4r53c
 */
public class TimePoint implements Comparable<TimePoint> {

    private float amount;
    private TimeUnit timeUnit;

    public TimePoint(float amount, TimeUnit timeUnit) {
        this.amount = amount;
        this.timeUnit = timeUnit;
    }

    /**
     * Returns the amount of time.
     *
     * @return The amount of time.
     */
    public float getAmount() {
        return amount;
    }

    /**
     * Returns the time unit.
     *
     * @return The time unit.
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /**
     * Compares this TimePoint to another TimePoint.
     *
     * @param timePoint The TimePoint to compare to.
     * @return A negative integer, zero, or a positive integer as this TimePoint is
     *         less than, equal to, or greater
     *         than the specified TimePoint.
     */
    @Override
    public int compareTo(TimePoint timePoint) {
        float thisTime = this.amount * this.timeUnit.getMillis();
        float otherTime = timePoint.amount * timePoint.timeUnit.getMillis();

        return Float.compare(thisTime, otherTime);
    }

    /**
     * Converts this TimePoint to the specified time unit.
     *
     * @param timeUnit The time unit to convert to.
     * @return A new TimePoint representing this point in time in the specified time
     *         unit.
     */
    public TimePoint convert(TimeUnit timeUnit) {
        float amountInTargetUnit = this.amount
                * (this.timeUnit.getMillis() / timeUnit.getMillis());
        return new TimePoint(amountInTargetUnit, timeUnit);
    }

    /**
     * Returns a new TimePoint that is adjusted by the specified TimePoint adjuster.
     *
     * @param adjuster The TimePoint adjuster to use.
     * @return A new TimePoint that is adjusted by the specified TimePoint adjuster.
     */
    public TimePoint with(ITimePointAdjuster adjuster) {
        return adjuster.adjust(this);
    }
    
    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;

        if (this == o) {
            isEqual = true;
        } else if (o != null && getClass() == o.getClass()) {
            TimePoint timePoint = (TimePoint) o;
            isEqual = Float.compare(timePoint.amount, amount) == 0 &&
                    timeUnit == timePoint.timeUnit;
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, timeUnit);
    }
}
