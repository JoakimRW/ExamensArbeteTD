package com.mygdx.game.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Factory.TowerType;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.view.stages.UiStage;

public class UIStageController  {

	private UiStage uistage;
	private EntityModel model;
	private GameStateManager _gsm;
	public UIStageController(UiStage uistage, EntityModel model, GameStateManager gsm) {
		this.uistage = uistage;
		this.model = model;
		_gsm = gsm;

		initListeners();
	}

	private void initListeners() {
		/* Start game button / start next wave **/
		uistage.getUiPanel().getMidSection().getNextWaveBtn().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!PlayState.START_GAME) {
					PlayState.START_GAME = true;
					uistage.getUiPanel().getMidSection().getNextWaveBtn().setText("Next Wave");
				} else {
					EntityModel.startNextWave();
				}
			}

		});
		// setting this to be touchable so there's no events passing through to
		// game input listener when click on ui background
		uistage.getUiPanel().setTouchable(Touchable.enabled);
		uistage.getUiPanel().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
			}
		});
		/* resume button in pause mode */
		uistage.getPauseWindow().getResumeButton().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				hidePauseWindow();
			}
		});
		/* Main menu button in pause mode */
		uistage.getPauseWindow().getMainMenuButton().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				_gsm.setState(GameStateManager.State.MAINMENU);
			}
		});

		/* upgrade selected tower */
		
		uistage.getUiPanel().getRightSection().getUpgradeBtn().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				model.upgradeSelectedTower();
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
			}
		});

		/* sell selected tower */
		uistage.getUiPanel().getRightSection().getSellBtn().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				model.sellSelectedTower();
			}
		});

		/* laser tower icon */
		uistage.getUiPanel().getLeftSection().getLaserTowerIcon().addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				uistage.getUiPanel().getLeftSection().getlaserTowerTooltipTable().getTooltip().hide();
				EntityModel.beginTowerPlacing(TowerType.BASIC_LASER_TURRET);
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				System.out.println("enter laser turret icon");
				uistage.getUiPanel().getLeftSection().getlaserTowerTooltipTable().getTooltip().setInstant(true);
			}
		});
		
		/* plastma tower icon */
		uistage.getUiPanel().getLeftSection().getPlastmaTowerIcon().addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				uistage.getUiPanel().getLeftSection().getPlastmaTowerTooltipTable().getTooltip().hide();
				EntityModel.beginTowerPlacing(TowerType.PLASTMA_TOWER);
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				uistage.getUiPanel().getLeftSection().getPlastmaTowerTooltipTable().getManager().instant();
			}
		});
		
		/* missile tower icon */
		uistage.getUiPanel().getLeftSection().getMissleTurretIcon().addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				uistage.getUiPanel().getLeftSection().getMissileTowerTooltipTable().getTooltip().hide();
				EntityModel.beginTowerPlacing(TowerType.MISSILE_TURRET);
			}

			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				uistage.getUiPanel().getLeftSection().getMissileTowerTooltipTable().getManager().instant();
			}
		});
		
	}


	public void showPauseWindow() {
		PlayState.PAUSE = true;
		uistage.getPauseWindow().setVisible(true);
	}

	/** hides pause window and sets pause equals to false* */
	public void hidePauseWindow() {
		PlayState.PAUSE = false;
		uistage.getPauseWindow().setVisible(false);
	}

	/** hides towerSelection panels **/
	public void hideTowerSelectionPanel() {
		uistage.getUiPanel().getRightSection().getTowerSelectPanel().setVisible(false);
	}

	/** shows towerSelection panels **/
	public void showTowerSelectionPanel() {
		uistage.getUiPanel().getRightSection().getTowerSelectPanel().setVisible(true);
	}

	/**
	 * When the player has selected a tile with a tower on it, call this method
	 **/
	public void setTowerSelectionInfo(String towerName, double sellPrice, double upgradePrice, double fireRate,
			double damage, double range, String special) {
		uistage.getUiPanel().getRightSection().getTowerSelectName().setText(towerName);
		uistage.getUiPanel().getRightSection().getTowerSelectSellPrice().setText(String.valueOf((int) sellPrice));
		uistage.getUiPanel().getRightSection().getTowerSelectUpgradePrice().setText(String.valueOf((int) upgradePrice));
		uistage.getUiPanel().getRightSection().getTowerSelectFireRate().setText(String.format("%.1f", fireRate));
		uistage.getUiPanel().getRightSection().getTowerSelectDamage().setText(String.valueOf((int) damage));
		uistage.getUiPanel().getRightSection().getTowerSelectRange().setText(String.valueOf((int) range));
		uistage.getUiPanel().getRightSection().getTowerSelectSpecial().setText(special);
		showTowerSelectionPanel();
	}

	public void updateHealth(int health) {
		uistage.getUiPanel().getRightSection().getHealthLabel().setText(String.valueOf(health));
	}

	public void setPlayerMoney(double money) {
		uistage.getUiPanel().getLeftSection().getMoneyLabel().setText(String.format("%.0f",money));
	}

	public boolean isOverUpgradeBtn() {
		return uistage.getUiPanel().getRightSection().isOverUpgradeButton();
	}
	
	public void updateNextEnemyText(String nextEnemy){
		uistage.getUiPanel().getMidSection().getnextEnemyValue().setText(nextEnemy);
	}

	public void updateUpgradeInfo() {
		if (isOverUpgradeBtn()) {
			if (model.isDamageGreenText())
				uistage.getUiPanel().getRightSection().getTowerSelectDamage().setColor(Assets.greenColor);
			else
				uistage.getUiPanel().getRightSection().getTowerSelectDamage().setColor(Color.WHITE);
			if (model.isIsfireRateGreenText())
				uistage.getUiPanel().getRightSection().getTowerSelectFireRate().setColor(Assets.greenColor);
			else
				uistage.getUiPanel().getRightSection().getTowerSelectFireRate().setColor(Color.WHITE);
			if (model.isRangeGreenText())
				uistage.getUiPanel().getRightSection().getTowerSelectRange().setColor(Assets.greenColor);
			else
				uistage.getUiPanel().getRightSection().getTowerSelectRange().setColor(Color.WHITE);
		} else {
			uistage.getUiPanel().getRightSection().getTowerSelectFireRate().setColor(Color.WHITE);
			uistage.getUiPanel().getRightSection().getTowerSelectRange().setColor(Color.WHITE);
			uistage.getUiPanel().getRightSection().getTowerSelectDamage().setColor(Color.WHITE);
		}
	}
}
