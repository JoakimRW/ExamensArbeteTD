package com.mygdx.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.managers.WaveTimeManager;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.view.panels.UIPanel;
import com.mygdx.game.view.windows.PauseWindow;


public class UiStage extends Stage {


    // laser tower

    private Table _rootTable;
    private Skin _skin;
    private TextureAtlas _atlas;
    private PauseWindow _pauseWindow;
    private UIPanel uiPanel;

    public UiStage(){
    	 OrthographicCamera _uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
         this.setViewport(new ScreenViewport(_uiCamera));
         // create skin
         _skin = createSkin();
         
         // root table
         _rootTable = createRootTable();


         uiPanel = new UIPanel(_skin);
    
         _rootTable.add(uiPanel)
                 .align(Align.center)
                 .fill(true , true)
                 .minWidth(uiPanel.getLeftSection().getPrefWidth() + uiPanel.getMidSection().getPrefWidth() + uiPanel.getRightSection().getPrefWidth() + 50);
         // align stuff
         _rootTable.align(Align.bottom);
         // add root table to stage
         this.addActor(_rootTable);
         _pauseWindow = new PauseWindow("Pause", _skin);
         _pauseWindow.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
         this.addActor(_pauseWindow);
         this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    private Skin createSkin(){
        Skin skin = new Skin();
        _atlas = new TextureAtlas("interface/ui/atlas-ui.txt");
        skin.addRegions(_atlas);
        skin.add("default-font", Assets.font16);
        skin.add("statPanelFont", Assets.font24);
        skin.add("statPanelFontWaveInfo", Assets.font20);
        skin.add("tooltipFont",Assets.font24);
        skin.load(Gdx.files.internal("interface/ui/uiSkin.json"));
        return skin;
    }

    private Table createRootTable(){
        Table root = new Table(_skin);
        root.setBounds(0 , 0 , Gdx.graphics.getWidth() , 175);
        return root;
    }

    @Override
    public void act(float delta) {
    	super.act(delta);
       // isOverUpgradeButton = _upgradeBtn.isOver();
        if (PlayState.START_GAME) uiPanel.getMidSection().getNextWaveTimeValue().setText(String.format("%d : %.0f",WaveTimeManager.CURRENT_WAVE_TIME ,  WaveTimeManager.CURRENT_WAVE_TIME_MILLIS * 100));
    }
    
    @Override
    public void draw(){
    	super.draw();
    }
   

    public void resize(int width, int height) {
        _rootTable.setWidth(Gdx.graphics.getWidth());
        _pauseWindow.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        this.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }



    public void dispose() {
        _atlas.dispose();
        _skin.dispose();
        this.dispose();
    }


	public PauseWindow getPauseWindow() {
		return _pauseWindow;
	}


	public UIPanel getUiPanel() {
		return uiPanel;
	}
  
}
