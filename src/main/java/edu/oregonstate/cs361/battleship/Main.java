package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;

import java.io.UnsupportedEncodingException;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) {
        staticFiles.location("/public");

        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> fireAt(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to scan
        post("/scan/:row/:col", (req, res) -> scan(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));
    }

    //This function returns a new model
    private static String newModel() {
        BattleshipModel bm = new BattleshipModel();
        Gson gson = new Gson();
        return gson.toJson(bm);
    }

    //This function accepts an HTTP request and deseralizes it into an actual Java object.
    private static BattleshipModel getModelFromReq(Request req){
        Gson gson = new Gson();
        String result = "";
        try {
            result = java.net.URLDecoder.decode(req.body(),"US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        BattleshipModel modelFromReq = null;

        try {
            modelFromReq = gson.fromJson(result, BattleshipModel.class);
        }

        catch (Exception e){
            e.printStackTrace();
        }
        return modelFromReq;
    }

    //This controller
    private static String placeShip(Request req) {
        BattleshipModel currModel = getModelFromReq(req);
        currModel.createShipArrays();
        String id = req.params("id");
        String row = req.params("row");
        String col = req.params("col");
        String orientation = req.params("orientation");
        currModel = currModel.placeShip(id,row,col,orientation);
        currModel.deleteShipArrays();
        Gson gson = new Gson();
        return gson.toJson(currModel);
    }

    private static String fireAt(Request req) {

        BattleshipModel currModel = getModelFromReq(req);
        currModel.createShipArrays();
        String row = req.params("row");
        String col = req.params("col");
        int rowInt = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);
        if(!currModel.shootAtComputer(rowInt,colInt))
            currModel.shootAtPlayer();
        currModel.deleteShipArrays();
        Gson gson = new Gson();
        return gson.toJson(currModel);
    }


    private static String scan(Request req) {

        BattleshipModel currModel = getModelFromReq(req);
        currModel.createShipArrays();
        String row = req.params("row");
        String col = req.params("col");
        int rowInt = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);
        currModel.scan(rowInt,colInt);
        currModel.shootAtPlayer();
        currModel.deleteShipArrays();
        Gson gson = new Gson();
        return gson.toJson(currModel);
    }



}