package com.mygdx.game.utils;



import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;

public class Assets {
    public static Sprite enemyGreenHealthbarBG;
    public static Sprite enemyRedHealthbarBG;

	public static Skeleton bloodWormSkeleton;
	public static AnimationState bloodWormAnimationState;

    public static Skeleton birdSkeleton;
    public static AnimationState birdAnimationState;

    public static Skeleton lasetTowerSleleton;
    public static AnimationState lasetTowerAnimationState;

    private static Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

    public static void loadGameStageAssets(){
        // bloodworm
        bloodWormSkeleton = loadSkeleton("enemies/bloodworm/skeleton.atlas","enemies/bloodworm/skeleton.json");
        bloodWormAnimationState = new AnimationState(new AnimationStateData(bloodWormSkeleton.getData()));
       // bird
        birdSkeleton = loadSkeleton("enemies/bird/skeleton.atlas","enemies/bird/skeleton.json");
        birdAnimationState = new AnimationState(new AnimationStateData(birdSkeleton.getData()));
        // healtbar
        enemyGreenHealthbarBG = new Sprite(loadTexture("enemies/healthbar-green.png"));
        enemyRedHealthbarBG = new Sprite(loadTexture("enemies/healthbar-red.png"));
        // Tower
        lasetTowerSleleton = loadSkeleton("towers/lvl1/skeleton.atlas","towers/lvl1/skeleton.json");
        lasetTowerAnimationState = new AnimationState(new AnimationStateData(lasetTowerSleleton.getData()));

        Cursor slickArrow = Gdx.graphics.newCursor(new Pixmap(Gdx.files.getFileHandle("slick_arrow-arrow.png", Files.FileType.Internal)), 0, 0);
        Gdx.graphics.setCursor(slickArrow);
    }

    /* delar upp ett spritesheet och returnerar en textureregion array för animation **/
    /*
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
    **/

    private static Skeleton loadSkeleton(String atlasPath , String jsonPath){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        SkeletonJson json = new SkeletonJson(atlas);
        SkeletonData data = json.readSkeletonData(Gdx.files.internal(jsonPath));
        return new Skeleton(data);
    }
}


