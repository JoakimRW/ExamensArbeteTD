package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

import java.util.ArrayList;

/**
 * Created by MichaelSjogren on 2017-02-23.
 */
public class LevelManager {
    public static int mapWidthInTiles;
    public static int mapHeightInTiles;
    public static int mapPixelWidth;
    public static int mapPixelHeight;
    public static TiledMap tiledMap;
    public static Integer tileWidth;
    public static Integer tileHeight;
    public static TiledMapTileLayer tileLayer;
    public static Tile[][] tiles;
    private static MapLayer spawnLocLayer;
    public static ArrayList<Vector2> spawnLocations = new ArrayList<>();
    public static ArrayList<Vector2> endLocactions = new ArrayList<>();
    private static  ShapeRenderer shapeRenderer = new ShapeRenderer();
    private static MapLayer endLocLayer;
    public static Tile tileSpawn;
    public static Tile tileEnd;

    public static void loadLevel(String filePath) {
        tiledMap = new TmxMapLoader().load(filePath);
        MapProperties properties = tiledMap.getProperties();
        tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("collision");
        spawnLocLayer =  tiledMap.getLayers().get("spawnLocations");
        endLocLayer = tiledMap.getLayers().get("endLocations");

        for (MapObject object :endLocLayer.getObjects()
                ) {
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);

            endLocactions.add(new Vector2(x, y));
        }

        for (MapObject object :spawnLocLayer.getObjects()
             ) {
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);

            spawnLocations.add(new Vector2(x, y));
        }
        System.out.println( ((int)endLocactions.get(0).x / 32));
        System.out.println( ((int)spawnLocations.get(0).x / 32));


        mapWidthInTiles = properties.get("width", Integer.class);
        mapHeightInTiles = properties.get("height", Integer.class);
        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight = properties.get("tileheight", Integer.class);
        System.out.println(Gdx.graphics.getWidth() + " :: " + Gdx.graphics.getHeight());
        mapPixelWidth = mapWidthInTiles * tileWidth;
        mapPixelHeight = mapHeightInTiles * tileHeight;
        tiles = new Tile[mapWidthInTiles][mapHeightInTiles];
        System.out.println("Map width: " + mapPixelWidth + " :: " + mapPixelHeight);
        System.out.println("map width in tiles:" + mapWidthInTiles + " : map height in tiles: " + mapHeightInTiles);
        createTileList();
        tileEnd = getTile((int)endLocactions.get(0).x /32 ,(int) endLocactions.get(0).y / 32);
        tileSpawn = getTile((int)spawnLocations.get(0).x /32 ,(int) spawnLocations.get(0).y / 32);
    }


    public static boolean checkIfWall(int x, int y) {
        TiledMapTile tile = null;
        try {
            tile = tileLayer.getCell(x, y).getTile();
        }catch (NullPointerException e){

        }
        if(tile != null){
            boolean iswall = tile.getProperties().get("Wall",Boolean.class);
            if (iswall) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public static Tile getTile(int x , int y){
        if(x > tiles.length -1 || y > tiles[0].length -1  || y < 0 || x < 0) return null;
        return tiles[x][y];
    }

    private static void createTileList() {
        int walls = 0;
        int floor = 0;
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                if (checkIfWall(  x  ,  y )) {
                    walls ++;
                    tiles[x][y] = new Tile(new Vector2(x * tileWidth , y * tileHeight), tileWidth , tileHeight , TileType.WALL);
                } else {
                    floor++;
                    tiles[x][y] = new Tile(new Vector2(x * tileWidth , y * tileHeight), tileWidth , tileHeight , TileType.FLOOR);
                }
            }
        }
    }


    /** for debugging **/
    @SuppressWarnings("unused")
    public static void drawTiles(Camera camera){

        Vector2 enemySpawnLoc = endLocactions.get(0);
        Vector2 enemyEndLoc = spawnLocations.get(0);

        tileSpawn = getTile((int)enemySpawnLoc.x / 32 , (int)enemySpawnLoc.y / 32);
        tileEnd = getTile((int)enemyEndLoc.x / 32 , (int)enemyEndLoc.y / 32);

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f , 1f , 0f , 1f);
        shapeRenderer.rect(tileSpawn.getCords().x , tileSpawn.getCords().y , 32 ,32);
        shapeRenderer.setColor(1f , 0f , 1f , 1f);
        shapeRenderer.rect(tileEnd.getCords().x , tileEnd.getCords().y , 32 ,32);
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.end();
        for (int row = 0; row < LevelManager.tiles.length; row++) {
            Tile rowTile = LevelManager.getTile(row , 0);
            for (int col = 0; col < LevelManager.tiles[0].length; col++) {
                Tile tile = LevelManager.getTile(row , col);
                Tile colTile = LevelManager.getTile(0 , col);
                @SuppressWarnings("ConstantConditions")
                TileType type = tile.getType();
                shapeRenderer.begin();
                // ritar tile grid , och fyller med vit färg där fiender inte kan gå

                if(type == TileType.FLOOR){
                    shapeRenderer.setColor(1,1,1,.2f);
                    shapeRenderer.rect(tile.getCords().x , tile.getCords().y , tile.getTileWidth() , tile.getTileHeight());
                }else {
                  //  shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
                  //  shapeRenderer.setColor(.8f,0,0,.2f);
                    shapeRenderer.rect(tile.getCords().x , tile.getCords().y , tile.getTileWidth() , tile.getTileHeight());
                }
                shapeRenderer.end();
            }
        }
    }
}
