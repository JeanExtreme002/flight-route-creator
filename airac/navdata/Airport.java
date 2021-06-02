package airac.navdata;

public class Airport extends NavData {
    public Airport(String data) {
        this.setType("APT");
        this.setName(data.substring(0, 24));
        this.setIcao(data.substring(24, 31));
        this.setLatitude(Double.parseDouble(data.substring(39, 49).trim()));
        this.setLongitude(Double.parseDouble(data.substring(49, 63).trim()));
    }
}
