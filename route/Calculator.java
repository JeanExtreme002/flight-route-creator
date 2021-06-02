package route;

public class Calculator {
    public static final double radiusOfEarth = 6378.137 * 1000; // Radius in meters.

    public static final double toDegrees(double radians) {
        return radians * (180 / Math.PI);
    }

    /**
    * Returns the distance in nautical miles.
    */
    public static double getDistance(double lat1, double long1, double lat2, double long2) {
        lat1 = Math.toRadians(lat1);
        long1 = Math.toRadians(long1);
        lat2 = Math.toRadians(lat2);
        long2 = Math.toRadians(long2);

        double latDistance = lat2 - lat1;
        double longDistance = long2 - long1;

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
        Math.cos(lat1) * Math.cos(lat2) * Math.sin(longDistance / 2) * Math.sin(longDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Calculator.radiusOfEarth * c / 1852; // Converts to nautical miles.
    }

    /**
    * Returns the heading in degrees.
    */
    public static double getHeading(double lat1, double long1, double lat2, double long2) {
        lat1 = Math.toRadians(lat1);
        long1 = Math.toRadians(long1);
        lat2 = Math.toRadians(lat2);
        long2 = Math.toRadians(long2);

        /* Formula to find the heading:
           β = atan2(X, Y)
           X = cos θb * sin ∆L
           Y = cos θa * sin θb – sin θa * cos θb * cos ∆L
        */
        double longDistance = long2 - long1;
        double x = Math.cos(lat2) * Math.sin(longDistance);
        double y = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(longDistance);

        return (Calculator.toDegrees(Math.atan2(x, y)) + 360) % 360;
    }
}
