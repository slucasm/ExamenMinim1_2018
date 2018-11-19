package edu.upc.dsa;

import java.lang.reflect.Array;
import java.util.*;
import org.apache.log4j.Logger;


public class MyBikeImpl implements  MyBike {

    final static Logger log = Logger.getLogger(MyBikeImpl.class.getName());

    private static MyBikeImpl instance;

    private HashMap<String,User> userMap;
    private Station[] stations;
    private int sizeArray;


    private MyBikeImpl(int S){
        this.userMap = new HashMap<>();
        this.stations = new Station[S];
        this.sizeArray = 0;
    }

    public static MyBike getInstance(){
        if(instance==null) {
            instance = new MyBikeImpl(MyBike.S);
        }
        return instance;
    }

    public void addUser(String idUser, String name, String surname) {
        log.info("User added: "+idUser);
        User usuario = new User(idUser,name,surname);
        userMap.put(idUser,usuario);
        log.info("Number of users: " +userMap.size());
    }
    public int numUsers(){
        log.info("Number of users: " + userMap.size());
        return userMap.size();
    }

    public void addStation(String idStation, String description, int max, double lat, double lon) {
        Station station = new Station(idStation, description, max, lat, lon);
        boolean isfull = true;
        for(int i = 0; i < stations.length;i++){
            if (stations[i] == null){
                log.info("Station added: "+idStation);
                stations[i] = station;
                sizeArray ++;
                log.info("Number of stations: " +sizeArray);
                isfull = false;
                break;
            }
            else{}
        }
        if (isfull == true){
            log.warn("Array of stations is full");
        }
        else{}


    }
    public int numStations(){
        log.info("Number of stations: " +sizeArray);
        return sizeArray;
    }


    public int numBikes(String idStation) throws StationNotFoundException {
        boolean stationNotFound = true;
        int size = 0;
        for(int i = 0; i < stations.length;i++){
            if (idStation.equals(stations[i].getIdStation())){
                log.info("Number of bikes: " +stations[i].getListaBikes().size());
                stationNotFound = false;
                size = stations[i].getListaBikes().size();
                break;
            }
            else{}
        }
        if(stationNotFound == false) {
            return size;
        }
        else{
            throw new StationNotFoundException("The station is not found");
        }

    }

    public void clear() {
        int S = instance.stations.length;
        instance = new MyBikeImpl(S);
    }


    public void addBike(String idBike, String description, double kms, String idStation) throws StationFullException, StationNotFoundException {
        Bike bike = new Bike(idBike, description, kms, idStation);
        boolean stationNotFound = true;
        for (int i = 0; i < sizeArray;i++) {
            String id = this.stations[i].idStation;
            if(idStation.equals(this.stations[i].idStation)){
                stationNotFound = false;
                List<Bike> listaBikesStation = this.stations[i].getListaBikes();
                if (listaBikesStation.size() < this.stations[i].getMax()){
                    this.stations[i].addBike(bike);
                    log.info("Bike added to station: "+idStation);
                    break;
                }
                else {
                    log.info("The station is full");
                    throw new StationFullException("The station is full of bikes");
                }

            }
            else{}
        }
        if (stationNotFound == false){}
        else{
          throw new StationNotFoundException("The station is not found");
        }

    }

    public List<Bike> bikesByStationOrderByKms(String idStation) throws StationNotFoundException {
        List<Bike> listaBikes = new LinkedList<>();
        boolean stationNotFound = true;
        for (int i = 0; i < sizeArray;i++){
            String nombreStation = this.stations[i].getIdStation();
            if (nombreStation.equals(idStation)){
                stationNotFound = false;
                listaBikes = this.stations[i].getListaBikes();
                break;
            }
        }

        if (stationNotFound == false){
            Collections.sort(listaBikes, new Comparator<Bike>() {
                public int compare(Bike o1, Bike o2) {
                    return (int)(o1.getKms()-o2.getKms());
                }
            });
            log.info("The list of bikes from station ordered by Kms is:" +listaBikes);
            return listaBikes;
        }
        else{
            log.warn("The station is not found");
            throw new StationNotFoundException("The station is not found");
        }

    }

    public Bike getBike(String stationId, String userId) throws UserNotFoundException, StationNotFoundException {
        Bike bike;
        Station station = new Station();
        boolean stationIsFound = false;
        for(int i = 0; i < sizeArray; i++){
            if (stationId.equals(stations[i].getIdStation())){
                stationIsFound= true;
                station = stations[i];
            }
        }
        if(stationIsFound == false){
            log.warn("The station is not found");
            throw new StationNotFoundException("The station is not found");
        }
        else{
            User user = userMap.get(userId);
            if (user == null){
                log.warn("The user don't exist");
                throw  new UserNotFoundException("The user don't exist");
            }
            else{
                bike = station.getBike();
                user.addBike(bike);
                log.info("The bike " +bike.getIdBike()+ " is added to user: " +userId);
            }
        }
        return bike;
    }

    public List<Bike> bikesByUser(String userId) throws UserNotFoundException {
        User user = userMap.get(userId);
        List<Bike> listaBikes;// = new LinkedList<>();
        if (user == null){
            log.warn("The user dn't exist");
            throw new UserNotFoundException("The user don't exist");
        }
        else{
            log.info("The user is: " +userId);
            listaBikes = user.getListaBikes();

        }
        return listaBikes;
    }


}
