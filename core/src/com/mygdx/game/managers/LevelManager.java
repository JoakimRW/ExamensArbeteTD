package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

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

    public static void loadLevel(String filePath) {
        tiledMap = new TmxMapLoader().load(filePath);
        MapProperties properties = tiledMap.getProperties();
        tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("collision");
        mapWidthInTiles = properties.get("width", Integer.class);
        mapHeightInTiles = properties.get("height", Integer.class);
        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight = properties.get("tileheight", Integer.class);

        tiles = new Tile[tileWidth][tileHeight];
        mapPixelWidth = mapWidthInTiles * tileWidth;
        mapPixelHeight = mapHeightInTiles * tileHeight;
        System.out.println("Map width: " + mapPixelWidth + " :: " + mapPixelHeight);
        System.out.println("map width in tiles:" + mapWidthInTiles + " : map height in tiles: " + mapHeightInTiles);
        createNodeList();
    }


    public static boolean checkIfWall(int x, int y) {
        if(tileLayer.getCell(x, y).getTile().getProperties().get("Wall" ) == null) return true;
        boolean iswall = Boolean.valueOf(tileLayer.getCell(x, y).getTile().getProperties().get("Wall" , String.class));
        if (iswall) {
            return true;
        } else {
            return false;
        }
    }

    public static Tile getTile(int x , int y){
        if(x > tiles.length -1 || y > tiles[0].length -1 || y < 0 || x < 0) return null;
        return tiles[x][y];
    }

    private static void createNodeList() {
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
        System.out.println("Floor tiles: " + floor);
        System.out.println("Wall tiles: " + walls);
    }
}
