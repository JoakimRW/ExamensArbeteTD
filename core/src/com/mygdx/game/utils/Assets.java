package com.mygdx.game.utils;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
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
    public static Cursor slickArrow;
    public static Cursor slickArrowDelta;
    private static Cursor slickArrowTriangularBottomLeft;
    private static Cursor slickArrowTriangularBottomRight;
    private static Cursor slickArrowTriangularTopLeft;
    private static Cursor slickArrowTriangularTopRight;
	private static Texture birdSpriteSheet;
	private static TextureRegion[] birdFrames;
	public static Animation<TextureRegion> birdAnimation;

    public static Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

    public static void load(){
 
        bloodWormSpriteSheet = loadTexture("enemies/mutant-bloodworm-sheet.png");
        bloodWormFrames = getRegions(bloodWormSpriteSheet , bloodWormSpriteSheet.getWidth(),bloodWormSpriteSheet.getHeight(),32 , 32);
        bloodWormAnimation = new Animation<TextureRegion>(0.1f , bloodWormFrames);
        
        birdSpriteSheet = loadTexture("enemies/bird_spritesheet.png");
        birdFrames = getRegions(birdSpriteSheet , birdSpriteSheet.getWidth(),birdSpriteSheet.getHeight(),32 , 32);
        birdAnimation = new Animation<TextureRegion>(0.045f , birdFrames);
        
        enemyGreenHealthbarBG = new Sprite(new Texture("enemies/healthbar-green.png"));
        enemyRedHealthbarBG = new Sprite(new Texture("enemies/healthbar-red.png"));

        slickArrow = Gdx.graphics.newCursor(new Pixmap(Gdx.files.getFileHandle("slick_arrow-arrow.png", Files.FileType.Internal)), 0 ,0);
        slickArrowDelta = Gdx.graphics.newCursor(new Pixmap(Gdx.files.getFileHandle("slick_arrow-delta.png", Files.FileType.Internal)), 0 ,0);
        slickArrowTriangularBottomLeft = Gdx.graphics.newCursor(new Pixmap(Gdx.files.getFileHandle("slick_arrow-triangular-bottomleft.png", Files.FileType.Internal)), 0 ,0);
        slickArrowTriangularBottomRight = Gdx.graphics.newCursor(new Pixmap(Gdx.files.getFileHandle("slick_arrow-triangular-bottomright.png", Files.FileType.Internal)), 0 ,0);
        slickArrowTriangularTopLeft = Gdx.graphics.newCursor(new Pixmap(Gdx.files.getFileHandle("slick_arrow-triangular-topleft.png", Files.FileType.Internal)), 0 ,0);
        slickArrowTriangularTopRight = Gdx.graphics.newCursor(new Pixmap(Gdx.files.getFileHandle("slick_arrow-triangular-topright.png", Files.FileType.Internal)), 0 ,0);
        Gdx.graphics.setCursor(slickArrow);
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


