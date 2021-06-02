package airac;

import airac.navdata.NavData;
import airac.navdata.Aid;
import airac.navdata.Airport;
import airac.navdata.Intersection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Airac {
    private List<String> airacInformation;
    private HashMap<String, NavData> aidsData = new HashMap<>();
    private HashMap<String, NavData> airportsData = new HashMap<>();
    private HashMap<String, NavData> intersectionsData = new HashMap<>();
    private String airacInformationFilename = "cycle_info.txt";
    private String aidsDataFilename = "wpNavAID.txt";
    private String airportsDataFilename = "wpNavAPT.txt";
    private String intersectionsDataFilename = "wpNavFIX.txt";

    public Airac(String navigationDataPath) throws IOException {
        this.loadAiracInformation(navigationDataPath + "/" + this.airacInformationFilename);
        this.loadAidsData(navigationDataPath + "/" + this.aidsDataFilename);
        this.loadAirportsData(navigationDataPath + "/" + this.airportsDataFilename);
        this.loadIntersectionsData(navigationDataPath + "/" + this.intersectionsDataFilename);
    }

    private void loadAiracInformation(String filename) throws IOException {
        this.airacInformation = AiracFile.getNavigationDataFrom(filename, false);
    }

    private void loadAirportsData(String filename) throws IOException {
        List<String> navigationData = AiracFile.getNavigationDataFrom(filename, true);

        for (String airportData : navigationData) {
            Airport airport = new Airport(airportData);
            this.airportsData.put(airport.getIcao(), airport);
        }
    }

    private void loadAidsData(String filename) throws IOException {
        List<String> navigationData = AiracFile.getNavigationDataFrom(filename, true);

        for (String aidData : navigationData) {
            Aid aid = new Aid(aidData);
            this.aidsData.put(aid.getIcao(), aid);
        }
    }

    private void loadIntersectionsData(String filename) throws IOException {
        List<String> navigationData = AiracFile.getNavigationDataFrom(filename, true);

        for (String intersectionData : navigationData) {
            Intersection intersection = new Intersection(intersectionData);
            this.intersectionsData.put(intersection.getIcao(), intersection);
        }
    }

    public List<String> getInfo() {
        return new ArrayList<String>(this.airacInformation);
    }

    public HashMap<String, NavData> getAidsData() {
        return new HashMap<String, NavData>(this.aidsData);
    }

    public HashMap<String, NavData> getAirportsData() {
        return new HashMap<String, NavData>(this.airportsData);
    }

    public HashMap<String, NavData> getIntersectionsData() {
        return new HashMap<String, NavData>(this.intersectionsData);
    }
}
