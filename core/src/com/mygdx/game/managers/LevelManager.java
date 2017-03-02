package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
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
        System.out.println(Gdx.graphics.getWidth() + " :: " + Gdx.graphics.getHeight());
        mapPixelWidth = mapWidthInTiles * tileWidth;
        mapPixelHeight = mapHeightInTiles * tileHeight;
        tiles = new Tile[mapWidthInTiles][mapHeightInTiles];
        System.out.println("Map width: " + mapPixelWidth + " :: " + mapPixelHeight);
        System.out.println("map width in tiles:" + mapWidthInTiles + " : map height in tiles: " + mapHeightInTiles);
        createNodeList();
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

    /** for debugging **/
    @SuppressWarnings("unused")
    public static void drawTiles(ShapeRenderer shapeRenderer){
        for (int row = 0; row < LevelManager.tiles.length; row++) {
            for (int col = 0; col < LevelManager.tiles[0].length; col++) {

                Tile tile = LevelManager.getTile(row , col);
                @SuppressWarnings("ConstantConditions")
                TileType type = tile.getType();
                shapeRenderer.begin();
                // ritar tile grid , och fyller med vit färg där fiender inte kan gå
                if(type == TileType.FLOOR){
                    shapeRenderer.setColor(1,1,1,.2f);
                    shapeRenderer.rect(tile.getCords().x , tile.getCords().y , tile.getTileWidth() , tile.getTileHeight());
                }else {
                    shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(.8f,0,0,.2f);
                    shapeRenderer.rect(tile.getCords().x , tile.getCords().y , tile.getTileWidth() , tile.getTileHeight());
                }
                shapeRenderer.end();
            }
        }
    }
}
