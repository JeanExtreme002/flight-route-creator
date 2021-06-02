package airac.navdata;

public class Aid extends NavData {
    public Aid(String data) {
        this.setName(data.substring(0, 24));
        this.setIcao(data.substring(24, 29));
        this.setType(data.substring(29, 33));
        this.setLatitude(Double.parseDouble(data.substring(33, 43).trim()));
        this.setLongitude(Double.parseDouble(data.substring(43, 57).trim()));
    }
}
