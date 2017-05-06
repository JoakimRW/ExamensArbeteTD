package com.mygdx.game.controllers;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.stages.UiView;
import com.mygdx.game.states.PlayState;

import static com.mygdx.game.Factory.TowerType.*;


public class UIStageController extends ClickListener {

    private UiView uistage;
    private EntityModel model;
    private GameStateManager _gsm;

    public UIStageController(UiView uistage, EntityModel model , GameStateManager gsm) {
        this.uistage = uistage;
        this.model = model;
        _gsm = gsm;

        initListeners();
    }

    private void initListeners() {
        /* Start game button / start next wave **/
        uistage.get_nextWaveBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!PlayState.START_GAME) {
                    PlayState.START_GAME = true;
                    uistage.get_nextWaveBtn().setText("Next Wave");
                    updateNextEnemyText();
                } else {
                    updateNextEnemyText();
                    EntityModel.startNextWave();
                }
            }

        });

        /* resume button in pause mode*/
        uistage.getResumeButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hidePauseWindow();
            }
        });
        /* Main menu button in pause mode */
        uistage.getMainMenuButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                _gsm.setState(GameStateManager.State.MAINMENU);
            }
        });

        /* laser tower icon */
        uistage.get_laserTowerIcon().addListener(new ClickListener(){
        	
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        	    uistage.get_tooltip().hide();
                EntityModel.beginTowerPlacing(BASIC_LASER_TURRET);
                System.out.println("TURRET PLACEMENT STARTED");
        	}
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                uistage.get_tooltip().setInstant(true);
            }
        });
    }

    public void showPauseWindow() {
        PlayState.PAUSE = true;
        uistage.get_pauseWindow().setVisible(true);
    }
    /** hides pause window and sets pause equals to false* */
    public void hidePauseWindow(){
        PlayState.PAUSE = false;
        uistage.get_pauseWindow().setVisible(false);
    }
    /** hides towerSelection panels**/
    public void hideTowerSelectionPanel(){
        uistage.getTowerSelectPanel().setVisible(false);
    }
    /** shows towerSelection panels**/
    public void showTowerSelectionPanel(){
        uistage.getTowerSelectPanel().setVisible(true);
    }
    /** When the player has selected a tile with a tower on it, call this method **/
    public void setTowerSelectionInfo(  String towerName
                                        , int sellPrice
                                        , int upgradePrice
                                        , float fireRate
                                        , float damage
                                        , float range
                                        , String special  ){
        uistage.getTowerSelectName().setText(towerName);
        uistage.getTowerSelectSellPrice().setText(String.valueOf(sellPrice));
        uistage.getTowerSelectDamage().setText(String.valueOf(upgradePrice));
        uistage.getTowerSelectFireRate().setText(String.valueOf(fireRate));
        uistage.getTowerSelectDamage().setText(String.valueOf(damage));
        uistage.getTowerSelectRange().setText(String.valueOf(range));
        uistage.getTowerSelectSpecial().setText(special);
        showTowerSelectionPanel();
    }


    public void updateHealth(int health) {
        uistage.getHealthLabel().setText(String.valueOf(health));
    }

    public void setPlayerMoney(int money) {
        uistage.getMoneyLabel().setText(String.valueOf(money));
    }
    
	public void updateNextEnemyText() {
		uistage.get_next_enemy_value().setText(model.getNextWave());
	}
}
