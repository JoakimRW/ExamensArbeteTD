package com.mygdx.game.utils;



import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.spine.*;

public class Assets {
	public static Sprite enemyGreenHealthbarBG;
    public static Sprite enemyRedHealthbarBG;

	public static Skeleton bloodWormSkeleton;
	public static AnimationState bloodWormAnimationState;

    public static Skeleton birdSkeleton;
    public static AnimationState birdAnimationState;

    public static Skeleton laserTowerSkeleton;
    public static AnimationState laserTowerAnimationState;
    
    public static Skeleton plastmaTowerSkeleton;
    public static AnimationState plastmaTowerAnimationState;
    
	public static Skeleton missleTowerSkeleton;
	public static AnimationState missleTowerAnimationState;
	
    public static Skeleton coinSkeleton;
    public static AnimationState coinAnimationState;
    
    public static BitmapFont font10;
   // public static BitmapFont font12;
    public static BitmapFont font16;
    public static BitmapFont fontVera10;
    public static BitmapFont font24;
    public static BitmapFont font20;
    public static Music laserMillenium;
    public static Sound laserTurretFire;
    // laser textures
    public static Texture laserSmall;
	public static Texture plastmaProj;
	public static Texture missile;
	public static Skin mainMenuSkin;
	public static Skin uiSkin;

    

    private static Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

    public static void loadGameStageAssets() {
        // music
        laserMillenium = Gdx.audio.newMusic(Gdx.files.internal("music/Laser_Millenium.ogg"));
        laserMillenium.setVolume(0.1f);
        laserMillenium.setLooping(true);
        laserMillenium.play();
        // sounds
        laserTurretFire = Gdx.audio.newSound(Gdx.files.internal("towers/lasertower/fx/bubaproducer__laser-shot-silenced.wav"));
        //laserTurretFire.play(0.5f);
        
        // laser texture
        laserSmall = new Texture(Gdx.files.internal("projectiles/lasers/laser-small.png"));
        plastmaProj = new Texture(Gdx.files.internal("projectiles/lasers/plastma-projectile.png"));
        missile = new Texture(Gdx.files.internal("projectiles/missile/missile.png"));
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
        laserTowerSkeleton = loadSkeleton("towers/lasertower/skeleton.atlas","towers/lasertower/skeleton.json");
        laserTowerAnimationState = new AnimationState(new AnimationStateData(laserTowerSkeleton.getData()));
        
        plastmaTowerSkeleton = loadSkeleton("towers/plastma-tower/skeleton.atlas", "towers/plastma-tower/skeleton.json");
        plastmaTowerAnimationState = new AnimationState(new AnimationStateData(plastmaTowerSkeleton.getData()));
        
        missleTowerSkeleton = loadSkeleton("towers/missle-tower/skeleton.atlas", "towers/missle-tower/skeleton.json");
        missleTowerAnimationState = new AnimationState(new AnimationStateData(missleTowerSkeleton.getData()));
        // Coin
        coinSkeleton = loadSkeleton("misc/coin/skeleton.atlas", "misc/coin/skeleton.json");
        coinSkeleton.getRootBone().setScale(0.16f);
        coinAnimationState = new AnimationState(new AnimationStateData(coinSkeleton.getData()));
        // fonts
        fontVera10 = createFont(12 , Fonts.VERA);
        font10 = createFont(12 , Fonts.HEMI_HEAD);
        //font12 = createFont(12 , Fonts.HEMI_HEAD);
        font16 = createFont(16 , Fonts.HEMI_HEAD);
        font20 = createFont(20,Fonts.HEMI_HEAD);
        font24 = createFont(24 ,Fonts.HEMI_HEAD);
        // skin 
  
        Cursor slickArrow = Gdx.graphics.newCursor(new Pixmap(Gdx.files.getFileHandle("slick_arrow-arrow.png", Files.FileType.Internal)), 0, 0);
        Gdx.graphics.setCursor(slickArrow);
    }
    
    public static void loadMainMenuAssets(){
    	 mainMenuSkin = new Skin(Gdx.files.internal("MainMenuSkin.json"));
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
    
    private static BitmapFont createFont(int fontSize , Fonts type) {
    	FileHandle fontFile = null;
    	switch(type){
    		case HEMI_HEAD: fontFile = Gdx.files.internal("fonts/HEMIHEAD.TTF"); break;
    		case VERA: fontFile = Gdx.files.internal("fonts/Vera.ttf"); break;
    		case VERA_BD: fontFile = Gdx.files.internal("fonts/VeraBd.ttf"); break;
		default:
			break;
    	}
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }
    
    public enum Fonts{
    	HEMI_HEAD,
    	VERA, 
    	VERA_BD,
    	VERA_BI,
    	VERA_IT
    }

    public static void dispose(){
        enemyGreenHealthbarBG.getTexture().dispose();
        enemyRedHealthbarBG.getTexture().dispose();

        bloodWormSkeleton = null;
        bloodWormAnimationState = null;

        birdSkeleton = null;
        birdAnimationState = null;

        laserTowerSkeleton = null;
        laserTowerAnimationState = null;

        coinSkeleton = null;
        coinAnimationState = null;

        font10.dispose();
        // public static BitmapFont font12;

        font16.dispose();
        fontVera10.dispose();
        font24.dispose();
        font20.dispose();
        laserMillenium.dispose();
        laserTurretFire.dispose();
        // laser textures
        laserSmall.dispose();
    }
}


