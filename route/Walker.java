package route;

import airac.navdata.NavData;

class Walker {
    private NavData origin;
    private NavData destination;
    private double latitude;
    private double longitude;
    private double[] ratio;

    public Walker(NavData origin, NavData destination) {
        this.origin = origin;
        this.destination = destination;
        this.ratio = this.calculateRatio(origin, destination);
        this.resetCoordinates();
    }

    private double[] calculateRatio(NavData origin, NavData destination) {
        double latitudeRatio = (destination.getLatitude() - origin.getLatitude()) / 1000;
        double longitudeRatio = (destination.getLongitude() - origin.getLongitude()) / 1000;
        return new double[] {latitudeRatio, longitudeRatio};
    }

    private boolean hasNext() {
        double destLatitude = this.destination.getLatitude();
        double destLongitude = this.destination.getLongitude();

        boolean latitudeOver = this.isGoingLeft() ? this.latitude <= destLatitude : this.latitude >= destLatitude;
        boolean longitudeOver = this.isGoingDown() ? this.longitude <= destLongitude : this.longitude >= destLongitude;
        return !(latitudeOver || longitudeOver);
    }

    private boolean isGoingDown() {
        return this.ratio[1] < 0;
    }

    private boolean isGoingLeft() {
        return this.ratio[0] < 0;
    }

    private double setPrecision(double value, int precision) {
        double factor = Math.pow(10, precision);    // Removes the decimals after the limit.
        return Math.floor(value * factor) / factor; // Example (precision = 2): 23.4756 => 2347.56 => 2347 => (23.47)
    }

    public boolean hasFoundWaypoint(NavData waypoint, int precision) {
        double waypointLatitude = this.setPrecision(waypoint.getLatitude(), precision);
        double waypointLongitude = this.setPrecision(waypoint.getLongitude(), precision);
        double walkerLatitude = this.setPrecision(this.latitude, precision);
        double walkerLongitude = this.setPrecision(this.longitude, precision);
        return (walkerLatitude == waypointLatitude && walkerLongitude == waypointLongitude);
    }

    public void resetCoordinates() {
        this.latitude = origin.getLatitude();
        this.longitude = origin.getLongitude();
    }

    public boolean walk() {
        this.latitude += this.ratio[0];
        this.longitude += this.ratio[1];
        return this.hasNext();
    }
}
