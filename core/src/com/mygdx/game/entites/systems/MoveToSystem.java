package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.CollisionComponent;
import com.mygdx.game.entites.entitiycomponents.DirectionComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.utils.Node;

import java.util.ArrayList;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class MoveToSystem extends EntitySystem {
    private final ArrayList<Node> path;
    private ImmutableArray<Entity> entities;
    private float speed = 60f;
    private int index = 1;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<DirectionComponent> dm = ComponentMapper.getFor(DirectionComponent.class);
    private ComponentMapper<CollisionComponent> col = ComponentMapper.getFor(CollisionComponent.class);
    private Vector2 target;

    public MoveToSystem(ArrayList<Node> path){
        this.path = path;
        target = new Vector2(0 ,0);
    }

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class , DirectionComponent.class , CollisionComponent.class).get());
    }

    public void update(float deltaTime){
        for (int i = 0; i < entities.size(); i++) {

            Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            DirectionComponent direction = dm.get(entity);
            CollisionComponent coli = col.get(entity);
            if(path != null){

                if(path.size() >= index){
                     
                  moveTo(position , direction, coli , deltaTime);
                }
            }
        }
    }

    public void moveTo(PositionComponent pos , DirectionComponent dir, CollisionComponent coli, float deltaTime){
        final float oldPosX = pos.position.x;
        final float oldPosY = pos.position.y;
        pos.oldPosition.set(oldPosX, oldPosY);
        int pointX = MathUtils.round(path.get(path.size() - index ).getCordinates().x) << 5;
        int pointY = MathUtils.round(path.get(path.size() - index ).getCordinates().y) << 5;
        double difX = pointX - pos.position.x;
        double difY = pointY - pos.position.y;
        float rotAng = (float)Math.toDegrees(Math.atan2(difX,-difY));
        dir.angle = MathUtils.lerpAngleDeg(dir.angle,rotAng,.1f);
        if ( MathUtils.isEqual(pos.position.x , pointX,speed /80f) && MathUtils.isEqual(pointY , pos.position.y, speed /80f)){
                index++;
        }
        if (coli.isCollidingX){
                pos.position.x = oldPosX;
                dir.xAxis = 0;
        }
        if(MathUtils.round(pos.position.x) < pointX){
                dir.xAxis = 1;
        }
        else if(MathUtils.round(pos.position.x) > pointX){
                dir.xAxis = -1;
        }
        pos.position.x += (speed*dir.xAxis)*deltaTime;

        if (coli.isCollidingY){
            pos.position.y = oldPosY;
            dir.yAxis = 0;
        }

        if(MathUtils.round(pos.position.y) < pointY){
                dir.yAxis = 1;
        }
        else if(MathUtils.round(pos.position.y) > pointY){
                dir.yAxis = -1;
        }

        pos.position.y += (speed*dir.yAxis)*deltaTime;
    }
}
