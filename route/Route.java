package route;

import airac.navdata.NavData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {
    private NavData origin;
    private NavData destination;
    private double distanceBetweenWaypoints;
    private List<NavData> waypoints = new ArrayList<>();
    private List<Zone> waypointZones = new ArrayList<>();

    public Route(NavData origin, NavData destination, double distanceBetweenWaypoints) {
        this.origin = origin;
        this.destination = destination;
        this.distanceBetweenWaypoints = distanceBetweenWaypoints;
        this.initializeRoute();
    }

    private void addWaypointToRoute(NavData waypoint) {
        this.waypointZones.add(new Zone(waypoint, this.distanceBetweenWaypoints));
        this.waypoints.add(waypoint);
    }

    private void initializeRoute() {
        this.addWaypointToRoute(this.origin);
        this.addWaypointToRoute(this.destination);
    }

    private boolean isWaypointFarFromWaypointZones(NavData waypoint) {
        for (Zone zone : this.waypointZones) {
            if (zone.hasWaypoint(waypoint)) {
                return false;
            }
        }
        return true;
    }

    private void organizeWaypoints() {
        int heading = this.getHeading();
        boolean orderByLatitude = !((45 < heading && heading < 135) || (225 < heading && heading < 315));

        Collections.sort(this.waypoints, new WaypointComparator(orderByLatitude));

        if (!this.waypoints.get(0).getIcao().equals(this.origin.getIcao())) {
            Collections.reverse(this.waypoints);
        }
    }

    public boolean addWaypoint(NavData waypoint) {
        if (this.isWaypointFarFromWaypointZones(waypoint) && !this.waypoints.contains(waypoint)) {
            this.addWaypointToRoute(waypoint);
            return true;
        }
        return false;
    }

    public List<NavData> get() {
        this.organizeWaypoints();
        return new ArrayList<>(this.waypoints);
    }

    public double getDistance() {
        return this.getDistance(this.destination);
    }

    public double getDistance(NavData waypoint) {
        double latitude1 = this.origin.getLatitude();
        double longitude1 = this.origin.getLongitude();
        double latitude2 = waypoint.getLatitude();
        double longitude2 = waypoint.getLongitude();

        return Calculator.getDistance(latitude1, longitude1, latitude2, longitude2);
    }

    public int getHeading() {
        double latitude1 = this.origin.getLatitude();
        double longitude1 = this.origin.getLongitude();
        double latitude2 = this.destination.getLatitude();
        double longitude2 = this.destination.getLongitude();

        int heading = (int) Math.round(Calculator.getHeading(latitude1, longitude1, latitude2, longitude2));
        return heading == 0 ? 360 : heading;
    }

    public boolean isWaypointInRouteZone(NavData waypoint) {
        return new Zone(this.origin, this.destination).hasWaypoint(waypoint);
    }
}
