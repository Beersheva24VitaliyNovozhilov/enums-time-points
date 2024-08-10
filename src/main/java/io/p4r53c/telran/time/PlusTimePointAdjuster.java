package io.p4r53c.telran.time;

import io.p4r53c.telran.time.enums.TimeUnit;

/**
 * Represents a time point adjuster that adds a specified amount of time to the
 * base time point.
 *
 * @author p4r53c
 */
public class PlusTimePointAdjuster implements TimePointAdjuster {

    private int amount;
    private TimeUnit timeUnit;

    public PlusTimePointAdjuster(int amount, TimeUnit timeUnit) {
        this.amount = amount;
        this.timeUnit = timeUnit;
    }

    /**
     * Adjusts the base time point by adding the specified amount of time.
     *
     * @param timePoint the base time point
     * @return a new time point representing the adjusted time point
     */
    @Override
    public TimePoint adjust(TimePoint timePoint) {
        TimePoint point = timePoint.convert(timeUnit);
        float amountPoint = point.getAmount() + amount;

        TimePoint pointTemp = new TimePoint(amountPoint, timeUnit);

        return pointTemp.convert(timePoint.getTimeUnit());
    }
}