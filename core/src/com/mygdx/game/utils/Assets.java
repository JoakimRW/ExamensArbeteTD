package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by MichaelSjogren on 2017-03-04.
 */
public class Assets {
    private static Texture bloodWormSpriteSheet;
    public static TextureRegion[]bloodWormFrames;
    public static Animation<TextureRegion> bloodWormAnimation;
    public static Sprite enemyGreenHealthbarBG;
    public static Sprite enemyRedHealthbarBG;

    public static Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

    public static void load(){
 
        bloodWormSpriteSheet = loadTexture("enemies/mutant-bloodworm-sheet.png");
        bloodWormFrames = getRegions(bloodWormSpriteSheet , bloodWormSpriteSheet.getWidth(),bloodWormSpriteSheet.getHeight(),32 , 32);
        bloodWormAnimation = new Animation<>(0.1f , bloodWormFrames);
        
        enemyGreenHealthbarBG = new Sprite(new Texture("enemies/healthbar-green.png"));
        enemyRedHealthbarBG = new Sprite(new Texture("enemies/healthbar-red.png"));
    }

    /** delar upp ett spritesheet i delar och returnerar en textureregion array för animation **/
    public static TextureRegion[] getRegions(Texture texture , int imgWidth , int imgHeight , int clipWidth , int clipheight){
        final int rowLength = imgWidth / clipWidth;
        final int colLength = imgHeight / clipheight;
        int index = 0;
        TextureRegion[] tmpArray = new TextureRegion[rowLength * colLength];
        for (int row = 0; row < rowLength ; row++){
            for (int col = 0; col < colLength; col++) {
                tmpArray[index++] = new TextureRegion(texture , row * clipWidth , col * clipheight , clipWidth , clipheight);
            }
        }
        return tmpArray;
    }
}


