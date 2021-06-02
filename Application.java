import airac.Airac;
import airac.navdata.NavData;
import route.Route;
import route.RouteCreator;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static Scanner inputScanner = new Scanner(System.in);
    public static double distanceBetweenWaypoints = 0.1;

    public static void main(String[] args) throws Exception {
        System.out.println("Loading data from airac... Please wait some seconds.");

        Airac airac = new Airac(args.length > 0 ? args[0] : "NAVDATA");
        HashMap<String, NavData> airportsData = airac.getAirportsData();

        System.out.print("Show airac information (Y/N)? ");
        if (Application.inputScanner.nextLine().toUpperCase().equals("Y")) {
            Application.showAiracInformation(airac);
        }

        System.out.println("\n--------------------------------------------------------\n");

        System.out.print("Origin airport ICAO (with runway heading): ");
        NavData origin = airportsData.get(Application.inputScanner.nextLine().toUpperCase());

        System.out.print("Destination airport ICAO (with runway heading): ");
        NavData destination = airportsData.get(Application.inputScanner.nextLine().toUpperCase());

        System.out.print("Set route precision (integer value): ");
        int precision = Integer.parseInt(Application.inputScanner.nextLine().toUpperCase());
        precision = (int) Math.sqrt(Math.pow(precision, 2));

        System.out.println("Calculating route ...");
        RouteCreator routeCreator = new RouteCreator(airac, Application.distanceBetweenWaypoints, precision);
        Route route = routeCreator.find(origin, destination);

        File file = new File(String.format("Route [%s to %s].txt", origin.getIcao(), destination.getIcao()));

        try (PrintStream printStream = new PrintStream(file)) {
            Application.writeRouteDataTo(printStream, route);
            Desktop.getDesktop().open(file);
        } catch (IOException exception) {
            exception.printStackTrace();
            Application.inputScanner.nextLine();
        }
    }

    public static void showAiracInformation(Airac airac) {
        for (String line : airac.getInfo()) {
            System.out.println(line);
        }
    }

    public static void writeRouteDataTo(PrintStream file, Route route) {
        String format = "| %-8s | %-5s | %-10s | %-14s | %-10s |";

        file.println(" -------------------------------------------------------------");
        file.println(String.format(format, "Waypoint", "Type", "Latitude", "Longitude", "Distance"));
        file.println("|-------------------------------------------------------------|");

        for (NavData waypoint : route.get()) {
            String icao = waypoint.getIcao();
            String type = waypoint.getType();
            double latitude = waypoint.getLatitude();
            double longitude = waypoint.getLongitude();
            String distance = String.format("%.1f NM", route.getDistance(waypoint));
            file.println(String.format(format, icao, type, latitude, longitude, distance));
        }

        file.println(" -------------------------------------------------------------");
        file.println();
        file.println(String.format("Heading: %s°", route.getHeading()));
        file.println(String.format("Distance: %.1f NM", route.getDistance()));
    }
}
