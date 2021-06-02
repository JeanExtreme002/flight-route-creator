package route;

import airac.navdata.NavData;
import java.util.Comparator;

class WaypointComparator implements Comparator<NavData> {
    private boolean orderByLatitude;

    public WaypointComparator(boolean orderByLatitude) {
        this.orderByLatitude = orderByLatitude;
    }

    private int compareValues(double valueA, double valueB) {
        if (valueA == valueB) {
            return 0;
        }
        if (valueA < valueB) {
            return -1;
        }
        return 1;
    }

    public int compare(NavData waypointA, NavData waypointB) {
        if (this.orderByLatitude) {
            return this.compareValues(waypointA.getLatitude(), waypointB.getLatitude());
        }
        return this.compareValues(waypointA.getLongitude(), waypointB.getLongitude());
    }
}
