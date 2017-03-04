package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entites.entitiycomponents.DirectionComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.utils.Node;
import com.mygdx.game.utils.Tile;

import java.util.ArrayList;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class MoveToSystem extends EntitySystem {
    private final ArrayList<Node> path;
    private ImmutableArray<Entity> entities;
    private float speed = 50f;
    private Vector2 point;
    private int index = 1;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<DirectionComponent> dm = ComponentMapper.getFor(DirectionComponent.class);
    private boolean yReached = false;
    private boolean xReached = false;

    public MoveToSystem(ArrayList<Node> path){
        this.path = path;
        point = new Vector2(0 ,0);
    }

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class , DirectionComponent.class).get());
    }

    public void update(float deltaTime){
        for (int i = 0; i < entities.size(); i++) {

            Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            // DirectionComponent direction = dm.get(entity);

            if(path != null){

                if(path.size() >= index){
                     
                  moveTo((int)position.x , (int)position.y  , position , deltaTime);
                }
            }
        }
    }

    public void moveTo(int x , int y , PositionComponent position , float deltaTime){
        int pointX = (int)path.get(path.size() - index ).getCordinates().x << 5;
        int pointY = (int)path.get(path.size() - index ).getCordinates().y << 5;
        if ((int)position.x == pointX && pointY == (int)position.y){
            index++;
        }
        if(x < pointX) position.x += speed*deltaTime;
        if(x > pointX) position.x -= speed*deltaTime;
        if(y < pointY) position.y += speed*deltaTime;
        if(y > pointY) position.y -= speed*deltaTime;
    }
}
