package com.mygdx.game.view.sections;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.view.panels.GrayPanel;
import com.mygdx.game.view.panels.StatPanel;

public class RightSection extends Table {
	
	private Table _towerSelectPanel;
	private Label healthLabel;
	private Label _towerSelectName;
	private Label _towerSelectUpgradePrice;
	private Label _towerSelectSellPrice;
	private TextButton _sellBtn;
	private TextButton _upgradeBtn;
	private Label _towerSelectFireRate;
	private Label _towerSelectDamage;
	private Label _towerSelectRange;
	private Label _towerSelectSpecial;
	private boolean isOverUpgradeBtn;

	public RightSection(Skin skin) {
		this.setSkin(skin);
        Table grayPanel = new GrayPanel(skin);
        _towerSelectPanel = new Table(skin);
        healthLabel = new Label("",skin , "font20" , "white");
        Image heart = new Image(skin,"heart");
        // stats panels that show hp , and money
        Table healthStatPanel = new StatPanel(skin);
        healthStatPanel.add(heart).size(21,18).align(Align.left).pad(5).expand();
        healthStatPanel.add(healthLabel).align(Align.right).pad(5,5,5,10);
        this.add(healthStatPanel).size(120,30).align(Align.topLeft).expand().spaceBottom(2).row();

        // table to contain tower operations
        Table leftTowerSelectSection = new Table(skin);
            // tower name
            _towerSelectName = new Label("",skin);
            _towerSelectName.setColor(Assets.greenColor);
        Table name = new StatPanel(skin);

        name.add(_towerSelectName).pad(0 , 10,  0 ,10).align(Align.left).expand().fill();
            // sell label and value
        Label towerSelectUpgradeText = new Label("Upgrade",skin);
        _towerSelectUpgradePrice = new Label("",skin );
        Table upgrade = new StatPanel(skin);
        upgrade.add(_towerSelectUpgradePrice).expand().align(Align.right).pad(10);
        // upgrade label and value
        Label towerSelectSellText = new Label("Sell",skin,"font20","white");
        _towerSelectSellPrice = new Label("",skin );
        Table sell = new StatPanel(skin);
        sell.add(_towerSelectSellPrice).expand().align(Align.right).pad(10);
        // sell button
        _sellBtn = new TextButton("Sell",skin);
        // upgrade button
        _upgradeBtn = new TextButton("Upgrade",skin);
        leftTowerSelectSection.pad(5);
        leftTowerSelectSection.add(name).align(Align.left).expand().fill().colspan(2).pad(0).row();
        leftTowerSelectSection.add(towerSelectSellText).align(Align.left);
        leftTowerSelectSection.add(sell).align(Align.right).width(60).pad(0).row();
        leftTowerSelectSection.add(towerSelectUpgradeText).align(Align.left);
        leftTowerSelectSection.add(upgrade).align(Align.right).width(60).pad(0).row();
        leftTowerSelectSection.add(_sellBtn).height(35).width(60).align(Align.left).spaceRight(5);
        leftTowerSelectSection.add(_upgradeBtn).height(35).width(75).align(Align.left);
        leftTowerSelectSection.align(Align.bottomLeft);
        leftTowerSelectSection.left();
        // right select section	
        Table fireRateTblRow = new StatPanel(skin);
        Table dmgTblRow = new StatPanel(skin);
        Table rangeTblRow = new StatPanel(skin);
        Table specialTblrow = new StatPanel(skin);
        // table to contain tower information
        Table towerInfoSection = new Table(skin);
        // fire rate text then value
        Label fireRate = new Label("Fire rate",skin);
        _towerSelectFireRate = new Label("",skin);
        fireRateTblRow.add(_towerSelectFireRate).align(Align.right).expand().padRight(10);
        // damage text and then value
        Label damage = new Label("Damage",skin);
        _towerSelectDamage = new Label("",skin);
        dmgTblRow.add(_towerSelectDamage).align(Align.right).expand().padRight(10);
        // range text and then value
        Label range = new Label("Range",skin);
        _towerSelectRange = new Label("",skin);
        rangeTblRow.add(_towerSelectRange).align(Align.right).expand().padRight(10);
        // special text the value
        Label special = new Label("Special",skin);
        _towerSelectSpecial = new Label("",skin);
        specialTblrow.add(_towerSelectSpecial).align(Align.right).expand().padRight(10);
        // add widgets to tower info section
        towerInfoSection.add(fireRate).align(Align.left).spaceRight(5);
        towerInfoSection.add(fireRateTblRow).align(Align.right).row();
        towerInfoSection.add(damage).align(Align.left).spaceRight(5);
        towerInfoSection.add(dmgTblRow).align(Align.right).row();
        towerInfoSection.add(range).align(Align.left).spaceRight(5);
        towerInfoSection.add(rangeTblRow).align(Align.right).row();
        towerInfoSection.add(special).align(Align.left).spaceRight(5);
        towerInfoSection.add(specialTblrow).align(Align.right).row();
        // add left and right container for tower select
        _towerSelectPanel.add(leftTowerSelectSection).expand().fill();
        _towerSelectPanel.add(towerInfoSection).expand().fill();
        grayPanel.add(_towerSelectPanel).expand().fill();
        this.add(grayPanel).padTop(2f);
        _towerSelectPanel.setVisible(false);
	}

	public Table getTowerSelectPanel() {
		return _towerSelectPanel;
	}

	public Label getHealthLabel() {
		return healthLabel;
	}

	public Label getTowerSelectName() {
		return _towerSelectName;
	}

	public Label getTowerSelectUpgradePrice() {
		return _towerSelectUpgradePrice;
	}

	public Label getTowerSelectSellPrice() {
		return _towerSelectSellPrice;
	}

	public TextButton getSellBtn() {
		return _sellBtn;
	}

	public TextButton getUpgradeBtn() {
		return _upgradeBtn;
	}

	public Label getTowerSelectFireRate() {
		return _towerSelectFireRate;
	}

	public Label getTowerSelectDamage() {
		return _towerSelectDamage;
	}

	public Label getTowerSelectRange() {
		return _towerSelectRange;
	}

	public Label getTowerSelectSpecial() {
		return _towerSelectSpecial;
	}
	
	public void setIsOverUpgradeButton(boolean isOverUpgradeButton){
		this.isOverUpgradeBtn = isOverUpgradeButton;
	}

	public boolean isOverUpgradeButton() {
		// TODO Auto-generated method stub
		return isOverUpgradeBtn;
	}
}
