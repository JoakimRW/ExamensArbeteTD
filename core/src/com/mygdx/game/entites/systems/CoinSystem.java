package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.utils.Assets;

public class CoinSystem extends IteratingSystem {

	private SpriteBatch batch;
    private OrthographicCamera camera;
    private Entity player;

    public CoinSystem(OrthographicCamera camera) {
		super(Family.all(SkeletonComponent.class,OffsetComponent.class , PositionComponent.class , TimeComponent.class , VelocityComponent.class ,  MoneyComponent.class ).get());
        this.camera = camera;
        batch = new SpriteBatch();
	}

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        player = getEngine().getEntitiesFor(Families.PLAYER).first();
        System.out.println();
    }

    @Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent pcomp = Mappers.POSITION_M.get(entity);
		VelocityComponent vcomp = Mappers.VELCOITY_M.get(entity);
		OffsetComponent ocomp = Mappers.OFFSET_M.get(entity);
		TimeComponent tcomp = Mappers.TIME_M.get(entity);
		MoneyComponent mcomp = Mappers.MONEY_M.get(entity);
		
		tcomp.time += deltaTime;
		pcomp.position.y += vcomp.maxSpeed * deltaTime;
        batch.begin();
		batch.setProjectionMatrix(camera.combined);
		Assets.fontVera10.getData().setScale(0.5f);
		Assets.fontVera10.setColor(1,0.9f,0,1);
		Assets.fontVera10.draw(batch , String.format("+ %d" , mcomp.money),pcomp.position.x + 21 , pcomp.position.y + ocomp.offsetY + 1);
		batch.end();
		// despawn coin when it has reach its lifetime
		if (tcomp.time > tcomp.lifeTime) {
            player.getComponent(MoneyComponent.class).money += mcomp.money;
            entity.removeAll();
			getEngine().removeEntity(entity);
		}
	}



    public void dispose() {
        //batch.dispose();
    }
}
