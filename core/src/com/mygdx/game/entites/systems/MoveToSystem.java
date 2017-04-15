package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.*;
import com.mygdx.game.states.PlayState;



public class MoveToSystem extends IteratingSystem {

    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<VelocityComponent> vm;
    private ComponentMapper<DirectionComponent> dm;
    private ComponentMapper<AngleComponent> am;
    private ComponentMapper<PathComponent> pam;

    public MoveToSystem(){
        super(Family.all(PositionComponent.class , VelocityComponent.class , DirectionComponent.class , AngleComponent.class , PathComponent.class).get());
        pm = ComponentMapper.getFor(PositionComponent.class);
        vm = ComponentMapper.getFor(VelocityComponent.class);
        dm = ComponentMapper.getFor(DirectionComponent.class);
        am = ComponentMapper.getFor(AngleComponent.class);
        pam = ComponentMapper.getFor(PathComponent.class);
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent posComp = pm.get(entity);
        DirectionComponent dirComp = dm.get(entity);
        PathComponent pathComp = pam.get(entity);
        AngleComponent angleComp = am.get(entity);
        VelocityComponent velocityComp = vm.get(entity);
        if (pathComp.path != null){
            if(pathComp.path.size() >= pathComp.index){
                moveTo(posComp , dirComp , angleComp , deltaTime , pathComp , velocityComp);
            }else {
                entity.removeAll();
                getEngine().removeEntity(entity);
                PlayState.PLAYER_HEALTH = PlayState.PLAYER_HEALTH != 0 ? PlayState.PLAYER_HEALTH - 1 : -1;
            }
        }

    }

    private void moveTo(PositionComponent pos , DirectionComponent dir, AngleComponent angleComp , float deltaTime , PathComponent pathComp , VelocityComponent vel){
        // a xy point in the path array that the entity will go to
        float pointX = pathComp.path.get(pathComp.path.size() - pathComp.index ).getCordinates().x * 32;
        float pointY = pathComp.path.get(pathComp.path.size() - pathComp.index ).getCordinates().y * 32;
        double difX = pointX - pos.position.x;
        double difY = pointY - pos.position.y;
        float distance = Vector2.dst(pos.position.x , pos.position.y , pointX , pointY);
        // set direction
        float sprAng = (float)Math.toDegrees(Math.atan2(difX,-difY));
        float rotAng = (float)Math.toDegrees(Math.atan2(difY,difX));
        angleComp.spriteAngle = sprAng;
        angleComp.angle = rotAng;
        vel.velocity.x = approach(vel.maxSpeed,vel.velocity.x,deltaTime );
        vel.velocity.y = approach(vel.maxSpeed,vel.velocity.y,deltaTime);
        // check if entity has the same cords that the point x and y has , if it has go to next point
        if (distance < 2)
            pathComp.index++;
        float angleGoalX = (float)Math.cos(Math.toRadians(angleComp.angle));
        float angleGoalY = (float)Math.sin(Math.toRadians(angleComp.angle));

        dir.direction.x = angleGoalX;
        dir.direction.y = angleGoalY;

        if (dir.direction.len() > 0)
            dir.direction = dir.direction.nor();
        vel.velocity.x = dir.direction.x * vel.maxSpeed;
        vel.velocity.y = dir.direction.y * vel.maxSpeed;

        pos.position.x += vel.velocity.x * deltaTime;
        pos.position.y += vel.velocity.y * deltaTime;

    }

    private float approach(float goal , float current , float deltaTime){
        float diffrence = goal - current;
        if(diffrence > deltaTime)
            return current + deltaTime;
        if(diffrence < deltaTime)
            return current - deltaTime;
        return goal;
    }

}
