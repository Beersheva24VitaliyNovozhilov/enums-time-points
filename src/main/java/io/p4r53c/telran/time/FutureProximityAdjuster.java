package io.p4r53c.telran.time;

import java.util.Arrays;

/**
 * Represents a time point adjuster that finds the nearest future time point.
 * 
 * @author p4r53c
 */
public class FutureProximityAdjuster implements TimePointAdjuster {

    TimePoint[] timePoints;

    /**
     * Creates a new instance of the {@link FutureProximityAdjuster} class.
     * 
     * @param timePoints array of time points to be sorted and stored
     */
    public FutureProximityAdjuster(TimePoint[] timePoints) {
        this.timePoints = Arrays.copyOf(timePoints, timePoints.length);
        Arrays.sort(this.timePoints);
    }

    /**
     * Adjusts the base time point to the nearest future time point.
     * 
     * @param timePoint base time point to be adjusted
     * @return the nearest future time point or {@code null} if there are no future
     *         time points
     */
    @Override
    public TimePoint adjust(TimePoint timePoint) {
        int low = 0;
        int high = timePoints.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (timePoints[mid].compareTo(timePoint) > 0) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return result != -1 ? timePoints[result] : null;
    }
}
