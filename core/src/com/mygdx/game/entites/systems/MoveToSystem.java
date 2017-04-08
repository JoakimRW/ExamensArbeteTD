package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.stages.GameStage;


public class MoveToSystem extends IteratingSystem {
    float time = 0;
    public MoveToSystem(){
        super(Family.all(PositionComponent.class , VelocityComponent.class , DirectionComponent.class , PathComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        time += deltaTime;
        PositionComponent posComp = entity.getComponent(PositionComponent.class);
        DirectionComponent dirComp = entity.getComponent(DirectionComponent.class);
        PathComponent pathComp = entity.getComponent(PathComponent.class);
        VelocityComponent velocityComp = entity.getComponent(VelocityComponent.class);
        if (pathComp.path != null){
            if(pathComp.path.size() >= pathComp.index){
                moveTo(posComp , dirComp , deltaTime , pathComp , velocityComp);
            }else {
                entity.removeAll();
                getEngine().removeEntity(entity);
                GameStage.PlAYER_HEALTH = GameStage.PlAYER_HEALTH != 0 ? GameStage.PlAYER_HEALTH - 1 : -1;
            }
        }

    }

    private void moveTo(PositionComponent pos , DirectionComponent dir, float deltaTime , PathComponent pathComp , VelocityComponent velocityComponent){
        final float tolerance = 4f;
        final float speed = velocityComponent.speed;
        // a xy point in the path array that the entity will go to
        final int pointX = MathUtils.round(pathComp.path.get(pathComp.path.size() - pathComp.index ).getCordinates().x) << 5;
        final int pointY = MathUtils.round(pathComp.path.get(pathComp.path.size() - pathComp.index ).getCordinates().y) << 5;
        // position of entity
        final int positionX = MathUtils.round(pos.x);
        final int positionY = MathUtils.round(pos.y);
        // calculate direction
        double difX = pointX - pos.x;
        double difY = pointY - pos.y;
        // set direction
        float rotAng = (float)Math.toDegrees(Math.atan2(difX,-difY));
        dir.angle = MathUtils.lerpAngleDeg(dir.angle,rotAng,.1f);
        // check if entity has the same cords that the point x and y has , if it has go to next point
        if (MathUtils.isEqual(positionX , pointX , tolerance) && MathUtils.isEqual(positionY , pointY , tolerance)){
            pathComp.index++;
        }
        if(positionY < pointY){
            dir.yAxis = 1;
        }
        else if(positionY > pointY){
            dir.yAxis = -1;
        }else {
            dir.yAxis = 0;
        }


        if(positionX  < pointX){
            dir.xAxis = 1;
        }
        else if(positionX  > pointX){
            dir.xAxis = -1;
        }else {
            dir.xAxis = 0;
        }

        pos.y += (dir.yAxis * speed) * deltaTime;
        pos.x +=  (dir.xAxis * speed) * deltaTime;
    }
}
