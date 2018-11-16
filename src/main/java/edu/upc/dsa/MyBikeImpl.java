package edu.upc.dsa;

import java.lang.reflect.Array;
import java.util.*;
import org.apache.log4j.Logger;


public class MyBikeImpl implements  MyBike {

    final static Logger log = Logger.getLogger(ProductManagerImpl.class.getName());

    private static MyBike instance;

    private HashMap<String,User> userMap;
    private ArrayList<Station> stations;


    private MyBikeImpl(){
        this.userMap = new HashMap<>();
        this.stations = new Array<>();
    }

    public static MyBike getInstance(){
        if(instance==null) instance = new MyBikeImpl();
        return instance;
    }

    public void addUser(String idUser, String name, String surname) {
        User usuario = new User(idUser,name,surname);
        userMap.put(idUser,usuario);
    }
    public int numUsers(){
        return userMap.size();
    }

    public void addStation(String idStation, String description, int max, double lat, double lon) {
        Station station = new Station(idStation, description, max, lat, lon);
        stations.add(station);
    }
    public int numStations(){
        return stations.size();
    }

    public void addBike(String idBike, String description, double kms, String idStation) throws StationFullException, StationNotFoundException {
        Bike bike = new Bike(idBike, description, kms, idStation);
        for (int i = 0; i < stations.size();i++){
            String nombreStation = this.stations.get(i).getIdStation();
            if (nombreStation.equals(idStation)){
                this.stations.get(i).addBike(bike);
                break;
            }
        }
    }

    public List<Bike> bikesByStationOrderByKms(String idStation) throws StationNotFoundException {
        List<Bike> listaBikes = new LinkedList<>();
        for (int i = 0; i < stations.size();i++){
            String nombreStation = this.stations.get(i).getIdStation();
            if (nombreStation.equals(idStation)){
                listaBikes = this.stations.get(i).getListaBikes();
                break;
            }
        }
        Collections.sort(listaBikes, new Comparator<Bike>() {
            public int compare(Bike o1, Bike o2) {
                return (int)(o1.getKms()-o2.getKms());
            }
        });
        return listaBikes;
    }

    public Bike getBike(String stationId, String userId) throws UserNotFoundException, StationNotFoundException {
        Bike bike = new Bike();
        for (int i = 0; i < stations.size();i++){
            String nombreStation = this.stations.get(i).getIdStation();
            if (nombreStation.equals(stationId)){
                bike = this.stations.get(i).listaBikes.pop();
                break;
            }
        }
        User user = userMap.get(userId);
        if (user!= null){
            user.addBike(bike);
        }
        return bike;
    }


}
