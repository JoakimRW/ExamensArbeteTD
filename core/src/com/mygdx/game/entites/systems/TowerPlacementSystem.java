package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.entites.entitiycomponents.MousePositionComponent;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.PathFinder;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

public class TowerPlacementSystem extends IteratingSystem {

	private SpriteBatch _batch;
	private Tile _tile;

	public TowerPlacementSystem(SpriteBatch batch) {
		super(Family.one(MousePositionComponent.class).get());
		_batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		if (_tile != null) {
			_batch.begin();
			TextureRegion textureRegion = _tile.getCell().getTile().getTextureRegion();
			Sprite sprite = new Sprite(textureRegion);

			if (!isLegalPlacement()) {
				sprite.setColor(Color.RED);
				sprite.setAlpha(0.5f);
			} else {
				sprite.setColor(Color.GREEN);
				sprite.setAlpha(0.5f);
			}
			sprite.setPosition(_tile.getCords().x, _tile.getCords().y);
			sprite.draw(_batch);
			_batch.end();
		}
	}

	public void tintTile(Tile tile) {
		_tile = tile;
	}

	public void createTower(EntityFactory entityFactory) {

	}

	public void moveTower() {

	}

	public void placeTower() {

		_tile.setType(TileType.WALL);
	}

	private boolean isLegalPlacement() {

		if (!isTowerBlockingPath(_tile)) {
			return _tile.getType() == TileType.FLOOR;
		}
		return false;
	}

	private static boolean isTowerBlockingPath(Tile tile) {
		if (tile.getType() == TileType.FLOOR) {
			tile.setType(TileType.WALL);
			if (PathFinder.findPath(
					new Vector2(LevelManager.tileSpawn.getCords().x / 32, LevelManager.tileSpawn.getCords().y / 32),
					new Vector2(LevelManager.tileEnd.getCords().x / 32, LevelManager.tileEnd.getCords().y / 32), false,
					false) == null) {
				tile.setType(TileType.FLOOR);
				return true;
			}
			tile.setType(TileType.FLOOR);
		}
		return false;
	}

}
