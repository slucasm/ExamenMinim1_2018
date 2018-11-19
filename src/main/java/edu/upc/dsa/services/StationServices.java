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
@Path("/")
public class StationServices {

    private MyBike mb;

    public StationServices(){
        this.mb = MyBikeImpl.getInstance();
        this.mb.addStation("Station1","description:: station1", 10, 3, 3);
        this.mb.addStation("Station2","description:: station2", 10, 3, 3);
        this.mb.addUser("user1", "Juan", "Lopex");
        try {
            this.mb.addBike("bike101", "descripton", 25.45, "Station1");
            this.mb.addBike("bike102", "descripton", 70.3, "Station1");
            this.mb.addBike("bike103", "descripton", 10.2, "Station1");
            this.mb.addBike("bike201", "descripton", 1325.45, "Station2");
            this.mb.addBike("bike202", "descripton", 74430.3, "Station2");
            this.mb.addBike("bike203", "descripton", 1320.2, "Station2");
        } catch (StationFullException e) {
            e.printStackTrace();
        } catch (StationNotFoundException e) {
            e.printStackTrace();
        }

    }

    @GET
    @ApiOperation(value = "get all bikes from user", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Bike.class, responseContainer="List of bikes"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/getAllBikesFromUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBikesFromUser(@PathParam("userId") String userId) {
        List<Bike> listaBikes;
        try{
            listaBikes = this.mb.bikesByUser(userId);
            GenericEntity<List<Bike>> entity = new GenericEntity<List<Bike>>(listaBikes){};
            return Response.status(201).entity(entity).build();
        }
        catch (Exception e){
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get first bike from station", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Bike.class, responseContainer="Bike"),
            @ApiResponse(code = 404, message = "Station or User not found")
    })
    @Path("/getBike/{station}/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBike(@PathParam("station") String station,@PathParam("user") String user) {
        try{
            Bike bike = this.mb.getBike(station,user);
             return Response.status(201).entity(bike).build();
        }
        catch (Exception e){
            return Response.status(404).build();
        }
    }

    @GET
    @ApiOperation(value = "get all bikes from station ordered by kms", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Bike.class, responseContainer="List of bikes"),
            @ApiResponse(code = 404, message = "Station not found")
    })
    @Path("/getAllBikesSortedByKms/{station}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBikesSortedByKms(@PathParam("station") String station) {
        try{
            List<Bike> listaBikes = this.mb.bikesByStationOrderByKms(station);
            GenericEntity<List<Bike>> entity = new GenericEntity<List<Bike>>(listaBikes) {};
            return Response.status(201).entity(entity).build();
        }
        catch(Exception e){
            return Response.status(404).build();
        }
    }


    @POST
    @ApiOperation(value = "create a new Station", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Station.class)
    })
    @Path("/addStation/{idStation}/{description}/{max}/{lat}/{lon}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newStation(@PathParam("idStation") String idStation,@PathParam("description") String description,@PathParam("max") int max,@PathParam("lat") double lat,@PathParam("lon") double lon) {
        Station station = new  Station(idStation,description,max,lat,lon);
        this.mb.addStation(idStation,description,max,lat,lon);
        return Response.status(201).entity(station).build();
    }

    @POST
    @ApiOperation(value = "create a new Bike", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Bike.class),
            @ApiResponse(code = 404, message = "Station not found or full")
    })
    @Path("/addbike/{idBike}/{description}/{kms}/{idStation}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newBike(@PathParam("idBike") String idBike,@PathParam("description") String description,@PathParam("kms") double kms,@PathParam("idStation") String idStation) {
        Bike bike = new  Bike(idBike,description,kms,idStation);
        try {
            this.mb.addBike(idBike,description,kms,idStation);
            return Response.status(201).entity(bike).build();
        } catch (StationFullException e) {
            e.printStackTrace();
            return Response.status(404).build();
        } catch (StationNotFoundException e) {
            e.printStackTrace();
            return Response.status(404).build();
        }
    }

    @POST
    @ApiOperation(value = "create a new user", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=User.class)
    })
    @Path("/adduser/{idUser}/{name}/{surname}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(@PathParam("idUser") String idUser,@PathParam("name") String name,@PathParam("surname") String surname) {
        this.mb.addUser(idUser,name,surname);
        return Response.status(201).build();
    }

}
