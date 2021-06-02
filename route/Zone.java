package route;

import airac.navdata.NavData;

class Zone {
    private double[] bounds;

    private Zone(double y1, double y2, double x1, double x2) {
        this.bounds = new double[] {y1, y2, x1, x2};
    }

    public Zone(NavData origin, NavData destination) {
        this(origin.getLatitude(), destination.getLatitude(), origin.getLongitude(), destination.getLongitude());
    }

    public Zone(NavData waypoint, double distance) {
        this(
            waypoint.getLatitude() + distance, waypoint.getLatitude() - distance,
            waypoint.getLongitude() - distance, waypoint.getLongitude() + distance
        );
    }

    public boolean hasWaypoint(NavData waypoint) {
        // When y1 and x1 are smaller (down, left) than y2 and x2 ...
        boolean latitude = (this.bounds[0] < waypoint.getLatitude() && waypoint.getLatitude() < this.bounds[1]);
        boolean longitude = (this.bounds[2] < waypoint.getLongitude() && waypoint.getLongitude() < this.bounds[3]);

        // When y1 and x1 are bigger (up, right) than y2 and x2 ...
        if (!latitude) {
            latitude = (this.bounds[0] > waypoint.getLatitude() && waypoint.getLatitude() > this.bounds[1]);
        }
        if (!longitude) {
            longitude = (this.bounds[2] > waypoint.getLongitude() && waypoint.getLongitude() > this.bounds[3]);
        }

        // Returns true if the waypoint is between y1 and y2 AND waypoint is between x1 and x2.
        return (latitude && longitude);
    }
}
