package com.mygdx.game.entites.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entites.entitiycomponents.AnimationComponent;
import com.mygdx.game.entites.entitiycomponents.StateComponent;
import com.mygdx.game.entites.entitiycomponents.TextureComponent;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class AnimationSystem extends IteratingSystem {

    private ComponentMapper<TextureComponent> tm;
    private ComponentMapper<AnimationComponent> am;
    private ComponentMapper<StateComponent> sm;

    public AnimationSystem() {
        super(Family.all(TextureComponent.class, AnimationComponent.class, StateComponent.class).get());

        tm = ComponentMapper.getFor(TextureComponent.class);
        am = ComponentMapper.getFor(AnimationComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureComponent tex = tm.get(entity);
        AnimationComponent anim = am.get(entity);
        StateComponent state = sm.get(entity);

        Animation animation = anim.animations.get(state.get());

        if (animation != null) {
            tex.region = (TextureRegion) animation.getKeyFrame(state.time);
        }

        state.time += deltaTime;
    }
}
