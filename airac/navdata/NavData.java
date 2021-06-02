package airac.navdata;

public abstract class NavData {
    private String name;
    private String icao;
    private String type;
    private double latitude;
    private double longitude;

    protected void setName(String name) {
        this.name = name.trim().toUpperCase();
    }

    protected void setIcao(String icao) {
        this.icao = icao.trim().toUpperCase();
    }

    protected void setType(String type) {
        this.type = type.trim().toUpperCase();
    }

    protected void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    protected void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return this.name;
    }

    public String getIcao() {
        return this.icao;
    }

    public String getType() {
        return this.type;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }
}
