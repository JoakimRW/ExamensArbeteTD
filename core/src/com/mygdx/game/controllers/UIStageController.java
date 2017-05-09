package com.mygdx.game.controllers;

import static com.mygdx.game.Factory.TowerType.BASIC_LASER_TURRET;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.stages.UiView;
import com.mygdx.game.states.PlayState;

public class UIStageController extends ClickListener {

	private UiView uistage;
	private EntityModel model;
	private GameStateManager _gsm;

	public UIStageController(UiView uistage, EntityModel model, GameStateManager gsm) {
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
		// setting this to be touchable so there's no events passing through to game input listener when click on ui background
		uistage.getUiPanel().setTouchable(Touchable.enabled);
		uistage.getUiPanel().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });
		/* resume button in pause mode */
		uistage.getResumeButton().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hidePauseWindow();
			}
		});
		/* Main menu button in pause mode */
		uistage.getMainMenuButton().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				_gsm.setState(GameStateManager.State.MAINMENU);
			}
		});

		/* upgrade selected tower */
		uistage.getUpgradeBtn().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        /* sell selected tower */
		uistage.getSellBtn().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                model.sellSelectedTower();
            }
        });

		/* laser tower icon */
		uistage.get_laserTowerIcon().addListener(new ClickListener() {
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
	public void hidePauseWindow() {
		PlayState.PAUSE = false;
		uistage.get_pauseWindow().setVisible(false);
	}

	/** hides towerSelection panels **/
	public void hideTowerSelectionPanel() {
		uistage.getTowerSelectPanel().setVisible(false);
	}

	/** shows towerSelection panels **/
	public void showTowerSelectionPanel() {
		uistage.getTowerSelectPanel().setVisible(true);
	}

	/**
	 * When the player has selected a tile with a tower on it, call this method
	 **/
	public void setTowerSelectionInfo(String towerName, double sellPrice, double upgradePrice, double fireRate, double damage,
			double range, String special) {
		uistage.getTowerSelectName().setText(towerName);
		uistage.getTowerSelectSellPrice().setText(String.valueOf(sellPrice));
		uistage.getTowerSelectUpgradePrice().setText(String.valueOf(upgradePrice));
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
