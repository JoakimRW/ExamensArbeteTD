package com.mygdx.game.controllers;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.stages.UiView;
import com.mygdx.game.states.PlayState;

import static com.mygdx.game.Factory.TowerType.*;


public class UIStageController extends ClickListener {

    private UiView uistage;
    private EntityModel model;

    public UIStageController(UiView uistage, EntityModel model) {
        this.uistage = uistage;
        this.model = model;

        initListeners();
        /** button that starts the game if wave manager hasnt started else spawns next wave**/

    }

    private void initListeners() {
        /* Start game button / start next wave**/
        uistage.addNextWaveButtonnListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!PlayState.START_GAME) {
                    PlayState.START_GAME = true;
                    uistage.get_nextWaveBtn().setText("Next Wave");
                } else {
                    uistage.get_nextEnemyText().setText(model.getNextWave());
                    EntityModel.startNextWave();
                }
            }
        });

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

    }

    public Window getPauseWindow() {
        return uistage.get_pauseWindow();
    }
}
