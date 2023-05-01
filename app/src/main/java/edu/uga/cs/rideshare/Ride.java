package edu.uga.cs.rideshare;

public class Ride {

    private String time;
    private String driver;
    private String rider;
    private String driverId;
    private String riderId;
    private String destination;
    private String date;

    private String id;
    private boolean driverConfirm;
    private boolean finished;
    private boolean secured;
    private boolean riderConfirm;
    private String type;
    private String driverName;

    private String riderName;
    private int points;
    public Ride() {

    }

    public Ride(String time, String driver, String rider, String driverId, String riderId, String destination, String date, int points) {
        this.time = time;
        this.driver = driver;
        this.rider = rider;
        this.driverId = driverId;
        this.riderId = riderId;
        this.destination = destination;
        this.date = date;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getRider() {
        return rider;
    }

    public void setRider(String rider) {
        this.rider = rider;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    // getter and setter for driverConfirm
    public boolean isDriverConfirm() {
        return driverConfirm;
    }

    public void setDriverConfirm(boolean driverConfirm) {
        this.driverConfirm = driverConfirm;
    }

    // getter and setter for riderConfirm
    public boolean isRiderConfirm() {
        return riderConfirm;
    }

    public void setRiderConfirm(boolean riderConfirm) {
        this.riderConfirm = riderConfirm;
    }

    // getter and setter for finished
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    // getter and setter for type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // getter and setter for secured
    public boolean isSecured() {
        return secured;
    }

    public void setSecured(boolean secured) {
        this.secured = secured;
    }

    public String toString() {
        return "Ride{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", driver='" + driver + '\'' +
                ", rider='" + rider + '\'' +
                ", driverId='" + driverId + '\'' +
                ", riderId='" + riderId + '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", points=" + points +
                ", driverConfirm=" + driverConfirm +
                ", finished=" + finished +
                ", secured=" + secured +
                ", riderConfirm=" + riderConfirm +
                ", type='" + type + '\'' +
                ", driverName='" + driverName + '\'' +
                '}';
    }


}
