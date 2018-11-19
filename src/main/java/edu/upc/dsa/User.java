package edu.upc.dsa;

import java.util.LinkedList;
import java.util.List;

public class User {

    String idUser,name,surname;
    private List<Bike> listaBikes;

    public User(String idUser,String name, String surname){
        this.idUser = idUser;
        this.name = name;
        this.surname = surname;
        this.listaBikes = new LinkedList<Bike>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Bike> getListaBikes() {
        return listaBikes;
    }

    public void setListaBikes(List<Bike> listaBikes) {
        this.listaBikes = listaBikes;
    }

    public void addBike(Bike bike){
        this.listaBikes.add(bike);
    }
}
