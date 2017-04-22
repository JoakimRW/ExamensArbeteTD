package com.mygdx.game.utils;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Tile {
	private Vector2 cords;
	private int tileWidth;
	private int tileHeight;
	private Vector2 tileCenter;
	private TileType type;
	private TiledMapTileLayer.Cell cell;

	public Tile(Vector2 cords, int tileWidth, int tileHeight, TileType type, TiledMapTileLayer.Cell cell) {
		this.cords = cords;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.type = type;
		this.cell = cell;
		int tileCenterX = Math.round(cords.x) + tileWidth / 2;
		int tileCenterY = Math.round(cords.y) + tileHeight / 2;
		tileCenter = new Vector2(tileCenterX, tileCenterY);
	}

	public Tile() {

	}


	public Vector2 getCords() {
		return cords;
	}

	public void setType(TileType type) {
		this.type = type;
	}

	public void setCords(Vector2 cords) {
		this.cords = cords;
	}

	public TileType getType() {
		return type;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public void setTileCenter(Vector2 tileCenter) {
		this.tileCenter = tileCenter;
	}

	public Vector2 getTileCenter() {
		return tileCenter;
	}

	public TiledMapTileLayer.Cell getCell() {
		return cell;
	}

	public void setCell(TiledMapTileLayer.Cell cell) {
		this.cell = cell;
	}
}
