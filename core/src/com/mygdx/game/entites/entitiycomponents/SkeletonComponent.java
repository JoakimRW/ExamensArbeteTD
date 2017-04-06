package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;
import com.esotericsoftware.spine.Skeleton;

public class SkeletonComponent implements Component {
    public Skeleton skeleton;
    public SkeletonComponent(Skeleton skeleton){
        this.skeleton = skeleton;
    }
}
