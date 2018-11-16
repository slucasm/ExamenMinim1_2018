package edu.upc.dsa;

import java.lang.reflect.Array;
import java.util.*;
import org.apache.log4j.Logger;


public class MyBikeImpl implements  MyBike {

    final static Logger log = Logger.getLogger(MyBikeImpl.class.getName());

    private static MyBike instance;

    private HashMap<String,User> userMap;
    private ArrayList<Station> stations;


    private MyBikeImpl(){
        this.userMap = new HashMap<>();
        this.stations = new ArrayList<>();
    }

    public static MyBike getInstance(){
        if(instance==null) instance = new MyBikeImpl();
        return instance;
    }

    public void addUser(String idUser, String name, String surname) {
        log.info("User added: "+idUser);
        User usuario = new User(idUser,name,surname);
        userMap.put(idUser,usuario);
        log.info("Number of user: " +userMap.size());
    }
    public int numUsers(){
        log.info("Number of users: " + userMap.size());
        return userMap.size();
    }

    public void addStation(String idStation, String description, int max, double lat, double lon) {
        log.info("Station added: "+idStation);
        Station station = new Station(idStation, description, max, lat, lon);
        stations.add(station);
        log.info("Number of stations: " +stations.size());
    }
    public int numStations(){
        log.info("Number of stations: " +stations.size());
        return stations.size();
    }


    public int numBikes(String idStation) throws StationNotFoundException {
        int size = 0;
        boolean stationNotFound = true;
        for (int i = 0; i < stations.size();i++) {
            String nombreStation = this.stations.get(i).getIdStation();
            if (nombreStation.equals(idStation)) {
                stationNotFound = false;
                List<Bike> listabikes = this.stations.get(i).getListaBikes();
                if (listabikes != null){
                    size = listabikes.size();
                }
                else {}

                break;
            }
        }
        if(stationNotFound == false) {}
        else{
            throw new StationNotFoundException();
        }

        log.info("Number of bikes: " +size);
        return size;
    }

    public void clear() {
        instance = new MyBikeImpl();
    }

    public int size() {
        return stations.size();
    }

    @Override
    public List<Station> allStations() {
        return stations;
    }


    public HashMap<String, User> allUsers() {
        return userMap;
    }

    public void addBike(String idBike, String description, double kms, String idStation) throws StationFullException, StationNotFoundException {
        Bike bike = new Bike(idBike, description, kms, idStation);
        boolean stationNotFound = true;
        for (int i = 0; i < stations.size();i++) {
            String id = this.stations.get(i).idStation;
            if(id.equals(this.stations.get(i).idStation)){
                stationNotFound = false;
                List<Bike> listaBikesStation = this.stations.get(i).getListaBikes();
                if (listaBikesStation == null){
                    this.stations.get(i).addBike(bike);
                    log.info("Bike added to station: "+idStation);
                }
                else {
                    if (listaBikesStation.size() < this.stations.get(i).getMax())
                    {
                        this.stations.get(i).addBike(bike);
                        log.info("Bike added to station: "+idStation);
                    }
                    else{
                        log.info("The station is full");
                        throw new StationFullException();
                    }
                }

            }
        }
        if (stationNotFound == false){}
        else{
          throw new StationNotFoundException();
        }

    }

    public List<Bike> bikesByStationOrderByKms(String idStation) throws StationNotFoundException {
        List<Bike> listaBikes = new LinkedList<>();
        boolean stationNotFound = true;
        for (int i = 0; i < stations.size();i++){
            String nombreStation = this.stations.get(i).getIdStation();
            if (nombreStation.equals(idStation)){
                stationNotFound = false;
                listaBikes = this.stations.get(i).getListaBikes();
                break;
            }
        }
        Collections.sort(listaBikes, new Comparator<Bike>() {
            public int compare(Bike o1, Bike o2) {
                return (int)(o1.getKms()-o2.getKms());
            }
        });
        if (stationNotFound == false){}
        else{
            throw new StationNotFoundException();
        }
        log.info("The list of bikes from station ordered by Kms is:" +listaBikes);
        return listaBikes;
    }

    public Bike getBike(String stationId, String userId) throws UserNotFoundException, StationNotFoundException {
        Bike bike = new Bike();

        User user = userMap.get(userId);
        if (user!= null){
            log.info("The user is: " +userId);
            boolean stationNotFound = true;
            for (int i = 0; i < stations.size();i++){
                String nombreStation = this.stations.get(i).getIdStation();
                if (nombreStation.equals(stationId)){
                    stationNotFound = false;
                    bike = this.stations.get(i).listaBikes.pop();
                    break;
                }
            }
            if (stationNotFound == false){}
            else {
                throw new StationNotFoundException();
            }
            log.info("The bike added to user is: " +bike.getIdBike());
            user.addBike(bike);
        }
        else{
            log.info("The user don't exist");
            throw  new UserNotFoundException();
        }
        return bike;
    }

    public List<Bike> bikesByUser(String userId) throws UserNotFoundException {
        User user = userMap.get(userId);
        List<Bike> listaBikes;// = new LinkedList<>();
        if (user!= null){
            log.info("The user is: " +userId);
            listaBikes = user.getListaBikes();
        }
        else{
            log.info("The user dn't exist");
            throw new UserNotFoundException();
        }
        return listaBikes;
    }


}
