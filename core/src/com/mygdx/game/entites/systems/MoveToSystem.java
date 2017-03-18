package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.entites.entitiycomponents.*;




/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class MoveToSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;

    public MoveToSystem(){
    }

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class , DirectionComponent.class , PathComponent.class).get());
    }

    public void update(float deltaTime){
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            PositionComponent posComp = entity.getComponent(PositionComponent.class);
            DirectionComponent dirComp = entity.getComponent(DirectionComponent.class);
            PathComponent pathComp = entity.getComponent(PathComponent.class);

                if(pathComp.path.size() >= pathComp.index){
                  moveTo(posComp , dirComp , deltaTime , pathComp);
                }
        }
    }

    public void moveTo(PositionComponent pos , DirectionComponent dir, float deltaTime , PathComponent pathComp){
        final float tolerance = 80f;
        final float speed = 60f;
        int pointX = MathUtils.round(pathComp.path.get(pathComp.path.size() - pathComp.index ).getCordinates().x) << 5;
        int pointY = MathUtils.round(pathComp.path.get(pathComp.path.size() - pathComp.index ).getCordinates().y) << 5;
        double difX = pointX - pos.x;
        double difY = pointY - pos.y;
        float rotAng = (float)Math.toDegrees(Math.atan2(difX,-difY));
        dir.angle = MathUtils.lerpAngleDeg(dir.angle,rotAng,.1f);
        if ( MathUtils.isEqual(pos.x , pointX,speed / tolerance) && MathUtils.isEqual(pointY , pos.y, speed / tolerance)){
            pathComp.index++;
        }
        pos.x += (speed*dir.xAxis)*deltaTime;
        pos.y += (speed*dir.yAxis)*deltaTime;

        if(MathUtils.round(pos.y) < pointY){
                dir.yAxis = 1;
        }
        else if(MathUtils.round(pos.y) > pointY){
                dir.yAxis = -1;
        }else {
            dir.yAxis = 0;
        }

        if(MathUtils.round(pos.x) < pointX){
            dir.xAxis = 1;
        }
        else if(MathUtils.round(pos.x) > pointX){
            dir.xAxis = -1;
        }else {
            dir.xAxis = 0;
        }

    }
}
