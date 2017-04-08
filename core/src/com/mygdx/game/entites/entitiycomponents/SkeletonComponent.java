package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;

public class SkeletonComponent implements Component {
    public Skeleton skeleton;
    public AnimationState animationState = new AnimationState();

    public SkeletonComponent(Skeleton skeleton){
        this.skeleton = skeleton;
    }
}
