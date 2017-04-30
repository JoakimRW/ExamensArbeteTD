package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Factory.EntityFactory;
import com.mygdx.game.entites.entitiycomponents.MousePositionComponent;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

public class TowerPlacementSystem extends IteratingSystem {

	private SpriteBatch _batch;
	private Tile _tile;
	private EntityFactory _entityFactory;

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

			if (_tile.getType() == TileType.WALL) {
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
}
