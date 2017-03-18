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
        post("/fire/:row/:col/:diff", (req, res) -> fireAt(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to scan
        post("/scan/:row/:col/:diff", (req, res) -> scan(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));
        //This will listen to POST requests and expects to recieve a game model, as well as if the game is ready to start
        post("/startGame/:diff", (req, res) -> startGame(req));
    }

    //This function returns a new model
    private static String newModel() {
        BattleshipModel bm = new BattleshipModel();
        Gson gson = new Gson();
        return gson.toJson(bm);
    }

    //This function accepts an HTTP request and deseralizes it into an actual Java object.
    private static HardModel getHardModelFromReq(Request req) {
        Gson gson = new Gson();
        String result = "";
        try {
            result = java.net.URLDecoder.decode(req.body(),"US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HardModel modelFromReq = null;

        try {
            modelFromReq = gson.fromJson(result, HardModel.class);
        }

        catch (Exception e){
            e.printStackTrace();
        }
        return modelFromReq;
    }


    //This function accepts an HTTP request and deseralizes it into an actual Java object.
    private static EasyModel getEasyModelFromReq(Request req){
        Gson gson = new Gson();
        String result = "";
        try {
            result = java.net.URLDecoder.decode(req.body(),"US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        EasyModel modelFromReq = null;

        try {
            modelFromReq = gson.fromJson(result, EasyModel.class);
        }

        catch (Exception e){
            e.printStackTrace();
        }
        return modelFromReq;
    }

    //This controller
    private static String placeShip(Request req) {
        String id = req.params("id");
        String row = req.params("row");
        String col = req.params("col");
        String orientation = req.params("orientation");

        BattleshipModel model = getEasyModelFromReq(req);
        model.createShipArrays();
        model = model.placeShip(id,row,col,orientation);
        model.deleteShipArrays();
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    private static String fireAt(Request req) {
        String diff = req.params("diff");
        String row = req.params("row");
        String col = req.params("col");
        int rowInt = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);

        EasyModel easy;
        HardModel hard;

        if(diff.equals("easy")) {
            easy = getEasyModelFromReq(req);
            easy.createShipArrays();
            if(!easy.shootAtComputer(rowInt,colInt))
                easy.shootAtPlayer();
            easy.deleteShipArrays();
            Gson gson = new Gson();
            return gson.toJson(easy);
        }

        else {
            hard = getHardModelFromReq(req);
            hard.createShipArrays();
            if(!hard.shootAtComputer(rowInt,colInt))
                hard.shootAtPlayer();
            hard.deleteShipArrays();
            Gson gson = new Gson();
            return gson.toJson(hard);
        }
    }


    private static String scan(Request req) {
        String diff = req.params("diff");
        String row = req.params("row");
        String col = req.params("col");
        int rowInt = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);

        EasyModel easy;
        HardModel hard;

        if(diff.equals("easy")) {
            easy = getEasyModelFromReq(req);
            easy.createShipArrays();
            easy.scan(rowInt,colInt);
            easy.shootAtPlayer();
            easy.deleteShipArrays();
            Gson gson = new Gson();
            return gson.toJson(easy);
        }

        else {
            hard = getHardModelFromReq(req);
            hard.createShipArrays();
            hard.scan(rowInt,colInt);
            hard.shootAtPlayer();
            hard.deleteShipArrays();
            Gson gson = new Gson();
            return gson.toJson(hard);
        }
    }

    private static String startGame(Request req) {
        String diff = req.params("diff");

        EasyModel easy;
        HardModel hard;

        if(diff.equals("easy")) {
            easy = getEasyModelFromReq(req);
            easy.createShipArrays();
            easy.startGame();
            easy.deleteShipArrays();
            Gson gson = new Gson();
            return gson.toJson(easy);
        }

        else {
            hard = getHardModelFromReq(req);
            hard.createShipArrays();
            hard.startGame();
            hard.deleteShipArrays();
            Gson gson = new Gson();
            return gson.toJson(hard);
        }
    }
}
