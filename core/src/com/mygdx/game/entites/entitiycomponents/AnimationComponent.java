package com.mygdx.game.entites.entitiycomponents;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class AnimationComponent implements Component{
    public IntMap<Animation> animations = new IntMap<>();
}
