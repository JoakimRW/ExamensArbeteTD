package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.entites.entitiycomponents.CollisionComponent;
import com.mygdx.game.entites.entitiycomponents.DimensionComponent;
import com.mygdx.game.entites.entitiycomponents.DirectionComponent;
import com.mygdx.game.entites.entitiycomponents.PositionComponent;
import com.mygdx.game.managers.LevelManager;
import com.mygdx.game.utils.Tile;
import com.mygdx.game.utils.TileType;

/**
 * Created by MichaelSjogren on 2017-03-05.
 */
public class CollisionSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<DimensionComponent> dm = ComponentMapper.getFor(DimensionComponent.class);
    private ComponentMapper<CollisionComponent> col = ComponentMapper.getFor(CollisionComponent.class);
    private ComponentMapper<DirectionComponent> dir = ComponentMapper.getFor(DirectionComponent.class);
    private Rectangle collsiionRectangle;
    private Rectangle wallRect;

    public CollisionSystem(){
        collsiionRectangle = new Rectangle();
        wallRect = new Rectangle();
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class , DimensionComponent.class , CollisionComponent.class , DirectionComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity: entities) {
            PositionComponent pos = pm.get(entity);
            DimensionComponent dim = dm.get(entity);
            CollisionComponent coli = col.get(entity);
            DirectionComponent direction = dir.get(entity);
            int tileX = (int)pos.position.x >> 5;
            int tileY = (int)pos.position.y >> 5;
            Tile tile;
            if(direction.xAxis < 0){
                // top left
                tile = LevelManager.getTile(tileX-1,tileY-1);
                if (tile != null){
                    coli.isCollidingX = tile.getType() == TileType.WALL;
                }
                // middle left
                tile = LevelManager.getTile(tileX-1,tileY);
                if (tile != null){
                    coli.isCollidingX = tile.getType() == TileType.WALL;
                }
                // bottom left
                tile = LevelManager.getTile(tileX-1,tileY + 1);
                if (tile != null){
                    coli.isCollidingX = tile.getType() == TileType.WALL;
                }
            }else if(direction.xAxis > 0){

                // top right
                tile = LevelManager.getTile(tileX+1,tileY-1);
                if (tile != null){
                    coli.isCollidingX = tile.getType() == TileType.WALL;
                }
                // middle right
                tile = LevelManager.getTile(tileX+1,tileY);
                if (tile != null){
                    coli.isCollidingX = tile.getType() == TileType.WALL;
                }
                // bottom right
                tile = LevelManager.getTile(tileX+1,tileY + 1);
                if (tile != null){
                    coli.isCollidingX = tile.getType() == TileType.WALL;
                }
            }

            if(direction.yAxis < 0){
                // bottom left
                tile = LevelManager.getTile(tileX-1,tileY+1);
                if (tile != null){
                    coli.isCollidingY = tile.getType() == TileType.WALL;
                }
                // bottom middle
                tile = LevelManager.getTile(tileX,tileY+1);
                if (tile != null){
                    coli.isCollidingY = tile.getType() == TileType.WALL;
                }
                // bottom right
                tile = LevelManager.getTile(tileX+1,tileY + 1);
                if (tile != null){
                    coli.isCollidingY = tile.getType() == TileType.WALL;
                }

            }else if(direction.yAxis > 0){
                // top left
                tile = LevelManager.getTile(tileX-1,tileY - 1);
                if (tile != null){
                    coli.isCollidingY = tile.getType() == TileType.WALL;
                }
                // top middle
                tile = LevelManager.getTile(tileX,tileY-1);
                if (tile != null){
                    coli.isCollidingY = tile.getType() == TileType.WALL;
                }
                // top right
                tile = LevelManager.getTile(tileX+1,tileY-1);
                if (tile != null){
                    coli.isCollidingY = tile.getType() == TileType.WALL;
                }
            }
        }
    }
}
