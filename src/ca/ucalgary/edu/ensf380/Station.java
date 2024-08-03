package ca.ucalgary.edu.ensf380;

public class Station {
    private String line;
    private int stationNumber;
    private String stationCode;
    private String stationName;
    private double x;
    private double y;

    public Station(String line, int stationNumber, String stationCode, String stationName, double x, double y, String commonStations) {
        this.line = line;
        this.stationNumber = stationNumber;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.x = x;
        this.y = y;
    }

    public String getLine() {
        return line;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
