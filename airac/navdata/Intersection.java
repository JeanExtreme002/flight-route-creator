package airac.navdata;

public class Intersection extends NavData {
    public Intersection(String data) {
        this.setType("FIX");
        this.setName(data.substring(0, 24));
        this.setIcao(data.substring(24, 29));
        this.setLatitude(Double.parseDouble(data.substring(29, 39).trim()));
        this.setLongitude(Double.parseDouble(data.substring(39, 50).trim()));
    }
}
