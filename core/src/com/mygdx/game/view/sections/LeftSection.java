package com.mygdx.game.view.sections;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Factory.TowerType;
import com.mygdx.game.view.panels.GrayPanel;
import com.mygdx.game.view.panels.StatPanel;
import com.mygdx.game.view.tooltip.LaserTowerTooltipTable;
import com.mygdx.game.view.tooltip.MissileTowerTooltipTable;
import com.mygdx.game.view.tooltip.PlastmaTowerTooltipTable;

public class LeftSection extends Table {
	private Label moneyLabel;
	private Image _laserTowerIcon;
	private Image _plastmaTowerIcon;
	private Image _missleTurretIcon;
	private LaserTowerTooltipTable _laserTowerTooltipTable;
	private PlastmaTowerTooltipTable _plastmaTowerTooltipTable;
	private MissileTowerTooltipTable _missileTowerTooltipTable;
	
	
	public LeftSection(Skin skin) {
		this.setSkin(skin);
		
        Table moneyStatPanel = new StatPanel(skin);
        Image coin = new Image(skin , "coin");
        moneyLabel = new Label("",skin , "font20" , "white");
        moneyStatPanel.add(coin).pad(5).align(Align.left).expand().size(23 , 21);
        moneyStatPanel.add(moneyLabel).align(Align.right).pad(5 , 5 , 5 ,10).expand();
        this.add(moneyStatPanel).align(Align.right).size(120 , 30).spaceBottom(2).row();
        Table grayPanel = new GrayPanel(skin);
        _laserTowerIcon = new Image(skin , "laser-tower-icon");
        _plastmaTowerIcon = new Image(skin , "plastma-tower-icon");
        _missleTurretIcon = new Image(skin,"missile-tower-icon");
        
        _laserTowerTooltipTable = new LaserTowerTooltipTable(skin, TowerType.BASIC_LASER_TURRET);
        _plastmaTowerTooltipTable = new PlastmaTowerTooltipTable(skin, TowerType.PLASTMA_TOWER);
        _missileTowerTooltipTable = new MissileTowerTooltipTable(skin, TowerType.MISSILE_TURRET);
        
        _laserTowerIcon.addListener(_laserTowerTooltipTable.getTooltip());
        _plastmaTowerIcon.addListener(_plastmaTowerTooltipTable.getTooltip());
        _missleTurretIcon.addListener(_missileTowerTooltipTable.getTooltip());
        grayPanel.add(_laserTowerIcon).size(32,32).pad(10).align(Align.topLeft);
        grayPanel.add(_plastmaTowerIcon).size(32,32).pad(10).align(Align.topLeft);
        grayPanel.add(_missleTurretIcon).size(32, 32).pad(10).align(Align.topLeft).expand(32,32);
        this.add(grayPanel);
	}


	public Label getMoneyLabel() {
		return moneyLabel;
	}


	public Image getLaserTowerIcon() {
		return _laserTowerIcon;
	}


	public Image getPlastmaTowerIcon() {
		return _plastmaTowerIcon;
	}


	public Image getMissleTurretIcon() {
		return _missleTurretIcon;
	}


	public LaserTowerTooltipTable getlaserTowerTooltipTable() {
		return _laserTowerTooltipTable;
	}


	public PlastmaTowerTooltipTable getPlastmaTowerTooltipTable() {
		return _plastmaTowerTooltipTable;
	}


	public MissileTowerTooltipTable getMissileTowerTooltipTable() {
		return _missileTowerTooltipTable;
	}
}
