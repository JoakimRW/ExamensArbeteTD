package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entites.entitiycomponents.AngleComponent;
import com.mygdx.game.entites.entitiycomponents.Families;
import com.mygdx.game.entites.entitiycomponents.Mappers;
import com.mygdx.game.entites.entitiycomponents.OffsetComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.SpriteComponent;

public class SpriteRenderSystem extends IteratingSystem {

	private SpriteBatch _batch;

	public SpriteRenderSystem(SpriteBatch batch) {
		super(Families.SPRITE);
		_batch = batch;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		SpriteComponent spriteComp = Mappers.SPRITE_M.get(entity); 
		AngleComponent angleComp = Mappers.ANGLE_M.get(entity);
		PositionComponent posComp = Mappers.POSITION_M.get(entity);
		OffsetComponent offsetComp = Mappers.OFFSET_M.get(entity);
	    Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		_batch.begin();
		spriteComp.sprite.setPosition(posComp.position.x + offsetComp.offsetX , posComp.position.y + offsetComp.offsetY);
		spriteComp.sprite.setRotation(angleComp.spriteAngle);
		spriteComp.sprite.setAlpha(0.2f);
		spriteComp.sprite.draw(_batch, 1f);
		_batch.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

}
