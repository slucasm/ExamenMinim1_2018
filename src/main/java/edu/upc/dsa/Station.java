package edu.upc.dsa;

import java.util.LinkedList;
import java.util.List;

public class Station {

    String  idStation;
    String description;
    int max;
    double lat;
    double lon;
    LinkedList<Bike> listaBikes = new LinkedList<>();

    public Station(String idStation, String description, int max, double lat, double lon){
        this.idStation = idStation;
        this.description = description;
        this.max = max;
        this.lat = lat;
        this.lon = lon;
    }

    public String getIdStation() {
        return idStation;
    }

    public void setIdStation(String idStation) {
        this.idStation = idStation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public LinkedList<Bike> getListaBikes() {
        return listaBikes;
    }
    public void addBike(Bike bike){
        this.listaBikes.add(bike);
    }


    @Override
    public String toString() {
        return "Station [idStation = " + idStation + ", Description = " + description +", Max = " + max +", Latitud = " +lat+ ", Longitud = " +lon+"]";
    }
}
