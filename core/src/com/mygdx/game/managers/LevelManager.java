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
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

import java.util.ArrayList;

/**
 * This is a helper class that loads the map and then creates a list of tiles of that map.
 * Created by MichaelSjogren on 2017-02-23.
 */
public abstract class LevelManager {
    public static int mapWidthInTiles;
    public static int mapHeightInTiles;
    public static int mapPixelWidth;
    public static int mapPixelHeight;
    public static TiledMap tiledMap;
    public static Integer tileWidth;
    public static Integer tileHeight;
    public static TiledMapTileLayer collisionLayer;
    public static TiledMapTileLayer floorLayer;
    public static Tile[][] tiles;
    private static ArrayList<Vector2> spawnLocations = new ArrayList<>();
    private static ArrayList<Vector2> endLocactions = new ArrayList<>();
    private static  ShapeRenderer shapeRenderer = new ShapeRenderer();
    public static Tile tileSpawn;
    public static Tile tileEnd;

    /**
     * Loads the level and inits all the public variables for this class
     * @param filePath The path of where the file is , root is in your assets folder
     * **/
    public static void loadLevel(String filePath) {
        tiledMap = new TmxMapLoader().load(filePath);
        MapProperties properties = tiledMap.getProperties();
        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("collision");
        floorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("floor");
        MapLayer spawnLocLayer = tiledMap.getLayers().get("spawnLocations");
        MapLayer endLocLayer = tiledMap.getLayers().get("endLocations");

        for (MapObject object : endLocLayer.getObjects()
                ) {
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);

            endLocactions.add(new Vector2(x, y));
        }

        for (MapObject object : spawnLocLayer.getObjects()
             ) {
            float x = object.getProperties().get("x", Float.class);
            float y = object.getProperties().get("y", Float.class);

            spawnLocations.add(new Vector2(x, y));
        }

        mapWidthInTiles = properties.get("width", Integer.class);
        mapHeightInTiles = properties.get("height", Integer.class);
        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight = properties.get("tileheight", Integer.class);

        mapPixelWidth = mapWidthInTiles * tileWidth;
        mapPixelHeight = mapHeightInTiles * tileHeight;
        tiles = new Tile[mapWidthInTiles][mapHeightInTiles];

        createTileList();
        tileEnd = getTile((int)endLocactions.get(0).x /32 ,(int) endLocactions.get(0).y / 32);
        tileSpawn = getTile((int)spawnLocations.get(0).x /32 ,(int) spawnLocations.get(0).y / 32);
    }


    private static boolean checkIfWall(int x, int y) {
        TiledMapTile tile = null;
        try {
            tile = collisionLayer.getCell(x, y).getTile();
        }catch (NullPointerException e){
            System.out.println(e);
        }


        if(tile != null){
            boolean iswall = tile.getProperties().get("Wall",Boolean.class);
            if (iswall) return true;
        }
        return false;
    }

    /**
     * You need to divide by the x / tile width and y / tile height if you have pixel cords
     * This will only work with Tile coordinates
     * @param x The x position of the Tile
     * @param y The y position of the Tile
     * @return if the coordinates are not out of bounds : returns Tile object , otherwise returns null
     * **/
    public static Tile getTile(int x , int y){
        if(x > tiles.length -1 || y > tiles[0].length -1  || y < 0 || x < 0) return null;
        return tiles[x][y];
    }

    private static void createTileList() {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {


                if (checkIfWall(  x  ,  y )) {
                    TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
                    Tile tile = new Tile();
                    if( cell != null){tile.setCell(cell);}
                    tile.setCords(new Vector2(x * tileWidth , y * tileHeight));
                    tile.setTileCenter(new Vector2((x * tileWidth) + tileWidth / 2 ,( y * tileHeight) + tileHeight / 2));
                    tile.setTileWidth(tileWidth);
                    tile.setTileHeight(tileHeight);
                    tile.setType(TileType.WALL);
                    tiles[x][y] = tile;
                } else {
                    TiledMapTileLayer.Cell cell = floorLayer.getCell(x, y);
                    Tile tile = new Tile();
                    if( cell != null){tile.setCell(cell);}
                    tile.setCords(new Vector2(x * tileWidth , y * tileHeight));
                    tile.setTileCenter(new Vector2((x * tileWidth) + tileWidth / 2 ,( y * tileHeight) + tileHeight / 2));
                    tile.setTileWidth(tileWidth);
                    tile.setTileHeight(tileHeight);
                    tile.setType(TileType.FLOOR);
                    tiles[x][y] = tile;
                }
            }
        }
    }


    /** For debugging pathfinding draws tile grid and fills with white where enemies cannot go
     * @param camera need the camera to set the projection matrix **/
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
                if (tile == null) continue;
                TileType type = tile.getType();
                shapeRenderer.begin();
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

    public static void dispose() {
        tiledMap.dispose();
        tiles = null;
        tileSpawn = null;
        tileEnd = null;
        tileHeight = 0;
        tileWidth = 0;
    }
}
