package edu.upc.dsa.services;


import edu.upc.dsa.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Api(value = "/stations", description = "Endpoint to Station Service")
@Path("/stations")
public class StationServices {

    private MyBike mb;

    public StationServices(){
        this.mb = MyBikeImpl.getInstance();
        if (mb.size()==0){

            this.mb.addStation("Station1","description:: station1", 10, 3, 3);
            this.mb.addStation("Station2","description:: station2", 10, 3, 3);
            this.mb.addUser("user1", "Juan", "Lopex");
        }
    }

    @GET
    @ApiOperation(value = "get all stations", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Station.class, responseContainer="List of stations")
    })
    @Path("/station")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStations() {

        List<Station> stations = this.mb.allStations();

        GenericEntity<List<Station>> entity = new GenericEntity<List<Station>>(stations) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get all users", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class, responseContainer="List of bikes")
    })
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {

        HashMap<String,User> userMap = this.mb.allUsers();

        GenericEntity<HashMap<String,User>> entity = new GenericEntity<HashMap<String,User>>(userMap) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get all bikes from user", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Bike.class, responseContainer="List of bikes"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBikesFromUser(@PathParam("user") String user) {

        List<Bike> listaBikes;
        HashMap<String,User> userMap = this.mb.allUsers();
        User usuario = userMap.get(user);
        if (usuario!=null){
            listaBikes = usuario.getListaBikes();
            GenericEntity<List<Bike>> entity = new GenericEntity<List<Bike>>(listaBikes){};
            return Response.status(201).entity(entity).build();
        }
        else{
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get all bikes from station", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Bike.class, responseContainer="List of bikes"),
            @ApiResponse(code = 404, message = "Station not found")
    })
    @Path("/{station}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBikesFromStation(@PathParam("station") String station) {
        boolean retornar = false;
        List<Bike> listaBikes = new LinkedList<>();
        List<Station> listastations = this.mb.allStations();
        for (int i = 0; i < listastations.size();i++) {
            if (station.equals(listastations.get(i).getIdStation())){
                listaBikes = listastations.get(i).getListaBikes();

                retornar = true;

                break;
            }
            else {

            }

        }
        if (retornar == true){
            GenericEntity<List<Bike>> entity = new GenericEntity<List<Bike>>(listaBikes){};
            return Response.status(201).entity(entity).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get all bikes from station ordered by kms", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Bike.class, responseContainer="List of bikes"),
            @ApiResponse(code = 404, message = "Station not found")
    })
    @Path("/{station}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBikesSortedByKms(@PathParam("station") String station) {
        boolean retornar = false;
        List<Bike> listaBikes = new LinkedList<>();
        List<Station> listastations = this.mb.allStations();
        for (int i = 0; i < listastations.size();i++) {
            if (station.equals(listastations.get(i).getIdStation())){
                try {
                    listaBikes = this.mb.bikesByStationOrderByKms(station);
                    retornar = true;
                } catch (StationNotFoundException e) {
                    e.printStackTrace();
                    return Response.status(404).build();
                }
                break;
            }
            else {

            }
        }
        if (retornar == true){
            GenericEntity<List<Bike>> entity = new GenericEntity<List<Bike>>(listaBikes){};
            return Response.status(201).entity(entity).build();
        }
        else {
            return Response.status(404).build();
        }
    }


    








}
