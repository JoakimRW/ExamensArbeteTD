package com.mygdx.game.entites.systems;

import java.util.Random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.AngleComponent;
import com.mygdx.game.entites.entitiycomponents.DirectionComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.entites.entitiycomponents.SkeletonComponent;
import com.mygdx.game.entites.entitiycomponents.VelocityComponent;
import com.mygdx.game.utils.Assets;

public class CoinSystem extends IteratingSystem{
	ComponentMapper<SkeletonComponent> skeleton_m;
	ComponentMapper<DirectionComponent> diretion_m;
	ComponentMapper<PositionComponent> position_m;
	ComponentMapper<TimeComponent> time_m;
	ComponentMapper<VelocityComponent> velocity_m;
	ComponentMapper<AngleComponent> angle_m;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private BitmapFont font;
	
	public CoinSystem(SpriteBatch batch , OrthographicCamera camera) {
		super(Family.all(SkeletonComponent.class , DirectionComponent.class , PositionComponent.class , TimeComponent.class , VelocityComponent.class , AngleComponent.class).get());
		skeleton_m = ComponentMapper.getFor(SkeletonComponent.class);
		diretion_m = ComponentMapper.getFor(DirectionComponent.class);
		position_m = ComponentMapper.getFor(PositionComponent.class);
		velocity_m = ComponentMapper.getFor(VelocityComponent.class);
		time_m = ComponentMapper.getFor(TimeComponent.class);
		angle_m = ComponentMapper.getFor(AngleComponent.class);
		this.batch = batch;
		this.camera = camera;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		DirectionComponent dcomp = diretion_m.get(entity);
		PositionComponent pcomp = position_m.get(entity);
		VelocityComponent vcomp = velocity_m.get(entity);
		TimeComponent tcomp = time_m.get(entity);
		
		tcomp.time += deltaTime;
		if (dcomp.direction.len() > 0) {
			dcomp.direction = dcomp.direction.nor();
		}
        Gdx.gl20.glActiveTexture(GL20.GL_TEXTURE0);
		batch.begin();
		Assets.font12.draw(batch, " + 1 ", pcomp.position.x, pcomp.position.y);
		batch.end();
		//Then scale it by the current speed to get the velocity
		vcomp.velocity.x = dcomp.direction.x * vcomp.maxSpeed;
		vcomp.velocity.y = dcomp.direction.y * vcomp.maxSpeed;
		//So now you know your velocity based on your rotation. You can the update your position with that information.


		//This is basic s = vt physics
		pcomp.position.x += vcomp.velocity.x * deltaTime;
		pcomp.position.y += vcomp.velocity.y * deltaTime;
		// despawn coin when it has reach its lifetime
		if (tcomp.time > tcomp.lifeTime) {
			entity.removeAll();
			getEngine().removeEntity(entity);
		}
	}
	
	

}
