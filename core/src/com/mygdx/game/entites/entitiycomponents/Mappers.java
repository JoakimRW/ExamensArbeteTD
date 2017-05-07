package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.ComponentMapper;

/** call tease mappers to avoid writing them in the systems
 *      to get component of entity do this:
 *     PositionComponent pcomp = ComponentMappers.POSITION_M.get(entity)
 * **/
public class Mappers {

    public static final ComponentMapper<PositionComponent> POSITION_M = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<DirectionComponent> DIRECTION_M = ComponentMapper.getFor(DirectionComponent.class);
    public static final ComponentMapper<HealthComponent> HEALTH_M = ComponentMapper.getFor(HealthComponent.class);
    public static final ComponentMapper<PathComponent> PATH_M = ComponentMapper.getFor(PathComponent.class);
    public static final ComponentMapper<RenderableComponent> RENDER_M = ComponentMapper.getFor(RenderableComponent.class);
    public static final ComponentMapper<SkeletonComponent> SKELETON_M = ComponentMapper.getFor(SkeletonComponent.class);
    public static final ComponentMapper<TextureComponent> TEXTURE_M = ComponentMapper.getFor(TextureComponent.class);
    public static final ComponentMapper<VelocityComponent> VELCOITY_M = ComponentMapper.getFor(VelocityComponent.class);
    public static final ComponentMapper<TimeComponent> TIME_M = ComponentMapper.getFor(TimeComponent.class);
    public static final ComponentMapper<AngleComponent> ANGLE_M = ComponentMapper.getFor(AngleComponent.class);
    public static final ComponentMapper<OffsetComponent> OFFSET_M = ComponentMapper.getFor(OffsetComponent.class);
    public static final ComponentMapper<MoneyComponent> MONEY_M = ComponentMapper.getFor(MoneyComponent.class);
    public static final ComponentMapper<MousePositionComponent> MOUSE_POS_M = ComponentMapper.getFor(MousePositionComponent.class);
    public static final ComponentMapper<DestinationComponent> DESTINATION_M = ComponentMapper.getFor(DestinationComponent.class);
    public static final ComponentMapper<EnemyComponent> ENEMY_M = ComponentMapper.getFor(EnemyComponent.class);
    public static final ComponentMapper<TowerStatComponent> TOWER_STATS_M = ComponentMapper.getFor(TowerStatComponent.class);
}
