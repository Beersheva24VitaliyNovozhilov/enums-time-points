package io.p4r53c.telran.time;

/**
 * Interface for adjusting a {@link TimePoint}.
 *
 * @see TimePoint
 */
public interface TimePointAdjuster {

    TimePoint adjust(TimePoint timePoint);
}
