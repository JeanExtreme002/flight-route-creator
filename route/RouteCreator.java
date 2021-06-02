package route;

import airac.Airac;
import airac.navdata.NavData;
import java.util.HashMap;

public class RouteCreator {
    private HashMap<String, NavData> waypoints;
    private double distanceBetweenWaypoints;
    private int precision;

    public RouteCreator(Airac airac, double distanceBetweenWaypoints, int precision) {
        this.distanceBetweenWaypoints = distanceBetweenWaypoints;
        this.precision = precision;
        this.loadWaypointsFromAirac(airac);
    }

    private Route createRoute(NavData origin, NavData destination) {
        return new Route(origin, destination, this.distanceBetweenWaypoints);
    }

    private void insertWaypointsToMap(HashMap<String, NavData> waypoints) {
        for (NavData waypoint : waypoints.values()) {
            if (!waypoint.getType().toUpperCase().startsWith("ILS")) {
                this.waypoints.put(waypoint.getType() + "_" + waypoint.getIcao(), waypoint);
            }
        }
    }

    private void loadWaypointsFromAirac(Airac airac) {
        this.waypoints = new HashMap<String, NavData>();
        this.insertWaypointsToMap(airac.getIntersectionsData());
        this.insertWaypointsToMap(airac.getAidsData());
    }

    public Route find(NavData origin, NavData destination) {
        Route route = this.createRoute(origin, destination);
        Walker walker = new Walker(origin, destination);

        for (NavData waypoint : this.waypoints.values()) {
            while (route.isWaypointInRouteZone(waypoint) && walker.walk()) {
                if (walker.hasFoundWaypoint(waypoint, this.precision)) {
                    route.addWaypoint(waypoint);
                    break;
                }
            }
            walker.resetCoordinates();
        }
        return route;
    }
}
