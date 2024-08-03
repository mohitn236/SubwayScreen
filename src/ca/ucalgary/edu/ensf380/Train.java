package ca.ucalgary.edu.ensf380;

public class Train {
    private String trainNumber;
    private String line;
    private String currentStation;
    private String direction;

    // Constructor
    public Train(String trainNumber, String line, String currentStation, String direction) {
        this.trainNumber = trainNumber;
        this.line = line;
        this.currentStation = currentStation;
        this.direction = direction;
    }

    // Getters and Setters
    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return String.format("Train Number: %s, Line: %s, Current Station: %s, Direction: %s",
                trainNumber, line, currentStation, direction);
    }
}
