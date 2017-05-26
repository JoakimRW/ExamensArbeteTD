package com.mygdx.game.view.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Factory.TowerType;
import com.mygdx.game.managers.WaveTimeManager;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.Assets;
import com.mygdx.game.view.tooltip.LaserTowerTooltipTable;
import com.mygdx.game.view.tooltip.MissileTowerTooltipTable;
import com.mygdx.game.view.tooltip.PlastmaTowerTooltipTable;
import com.mygdx.game.view.tooltip.TooltipTable;


public class UiStage extends Stage {


    // laser tower

    private Table _rootTable;
    private Image _laserTowerIcon;
    private Image _plastmaTowerIcon;
    private Image _missleTurretIcon;
    
    private TextButton _nextWaveBtn;
    private Label _next_enemy_value;
    private Label _next_wave_time_value;
    private Skin _skin;
    private TextureAtlas _atlas;
    private Label healthLabel;
    private Label moneyLabel;

    // pause window
    private Window _pauseWindow;

    // tower select widgets
    private Table _towerSelectPanel;
    private Label _towerSelectName;
    private Label _towerSelectUpgradePrice;
    private Label _towerSelectSellPrice;
    private TextButton  _sellBtn , _upgradeBtn;
    private Label _towerSelectFireRate;
    private Label _towerSelectDamage;
    private Label _towerSelectRange;
    private Label _towerSelectSpecial;

    private TextButton _resumeButton;
    private TextButton _mainMenuButton;
    private Table uiPanel;
    private boolean isOverUpgradeButton;

	private LaserTowerTooltipTable _laserTowerTooltipTable;
	private PlastmaTowerTooltipTable _plastmaTowerTooltipTable;
	private MissileTowerTooltipTable _missileTowerTooltipTable;
	

    public UiStage(){
    	 OrthographicCamera _uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
         this.setViewport(new ScreenViewport(_uiCamera));
         // create skin
         _skin = createSkin();
         
         // create pause window
         createPauseWindow();
         // create tooltip
         _laserTowerTooltipTable = new LaserTowerTooltipTable(_skin, TowerType.BASIC_LASER_TURRET);
         _plastmaTowerTooltipTable = new PlastmaTowerTooltipTable(_skin, TowerType.PLASTMA_TOWER);
         _missileTowerTooltipTable = new MissileTowerTooltipTable(_skin, TowerType.MISSILE_TURRET);
         // root table
         _rootTable = createRootTable();
         // PANEL that show info about the selected tower
         // add to root
         Table leftSection = createLeftSection();
         Table midSection = createMidSection();
         Table rightSection = createRightSection();
         uiPanel = createUiPanel();
         uiPanel.add(leftSection).size(328,175).expand().align(Align.bottomRight).pad(3);
         uiPanel.add(midSection).fill().align(Align.center).pad(3,3,3,3);
         uiPanel.add(rightSection).size(355,175).expand().align(Align.bottomLeft).pad(3);
         _rootTable.add(uiPanel)
                 .align(Align.center)
                 .fill(true , true)
                 .minWidth(leftSection.getPrefWidth() + midSection.getPrefWidth() + rightSection.getPrefWidth() + 50);
         // align stuff
         _rootTable.align(Align.bottom);
         // add root table to stage
         this.addActor(_rootTable);
         _pauseWindow.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
         this.addActor(_pauseWindow);
         this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }



	private Table createUiPanel() {
        Table uiPanel = new Table(_skin);
        uiPanel.setBackground("ui-bg2");
        return uiPanel;
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

    private Table createRightSection() {
        Table towerTargetContainer = new Table();
        Table grayPanel = createGrayPanel();
        _towerSelectPanel = new Table(_skin);
        healthLabel = new Label("",_skin , "statPanelFont" , "white");
        Image heart = new Image(_skin,"heart");
        // stats panels that show hp , and money
        Table healthStatPanel = createStatPanel();
        healthStatPanel.add(heart).size(21,18).align(Align.left).pad(5).expand();
        healthStatPanel.add(healthLabel).align(Align.right).pad(5,5,5,10);
        towerTargetContainer.add(healthStatPanel).size(120,30).align(Align.topLeft).expand().spaceBottom(2).row();

        // table to contain tower operations
        Table leftTowerSelectSection = new Table(_skin);
            // tower name
            _towerSelectName = new Label("",_skin);
            _towerSelectName.setColor(Assets.greenColor);
        Table name = createStatPanel();

        name.add(_towerSelectName).pad(0 , 10,  0 ,10).align(Align.left).expand().fill();
            // sell label and value
        Label towerSelectUpgradeText = new Label("Upgrade",_skin);
        _towerSelectUpgradePrice = new Label("",_skin );
        Table upgrade = createStatPanel();
        upgrade.add(_towerSelectUpgradePrice).expand().align(Align.right).pad(10);
        // upgrade label and value
        Label towerSelectSellText = new Label("Sell",_skin,"default");
        _towerSelectSellPrice = new Label("",_skin );
        Table sell = createStatPanel();
        sell.add(_towerSelectSellPrice).expand().align(Align.right).pad(10);
        // sell button
        _sellBtn = new TextButton("Sell",_skin);
        // upgrade button
        _upgradeBtn = new TextButton("Upgrade",_skin);
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
        Table fireRateTblRow = createStatPanel();
        Table dmgTblRow = createStatPanel();
        Table rangeTblRow = createStatPanel();
        Table specialTblrow = createStatPanel();
        // table to contain tower information
        Table towerInfoSection = new Table(_skin);
        // fire rate text then value
        Label fireRate = new Label("Fire rate",_skin);
        _towerSelectFireRate = new Label("",_skin);
        fireRateTblRow.add(_towerSelectFireRate).align(Align.right).expand().padRight(10);
        // damage text and then value
        Label damage = new Label("Damage",_skin);
        _towerSelectDamage = new Label("",_skin);
        dmgTblRow.add(_towerSelectDamage).align(Align.right).expand().padRight(10);
        // range text and then value
        Label range = new Label("Range",_skin);
        _towerSelectRange = new Label("",_skin);
        rangeTblRow.add(_towerSelectRange).align(Align.right).expand().padRight(10);
        // special text the value
        Label special = new Label("Special",_skin);
        _towerSelectSpecial = new Label("",_skin);
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
        towerTargetContainer.add(grayPanel);
        _towerSelectPanel.setVisible(false);
        return towerTargetContainer;
    }

    private Table createLeftSection() {
        Table towerListContainer = new Table(_skin);
        Table moneyStatPanel = createStatPanel();
        Image coin = new Image(_skin , "coin");
        moneyLabel = new Label("",_skin , "statPanelFont" , "white");
        moneyStatPanel.add(coin).pad(5).align(Align.left).expand().size(23 , 21);
        moneyStatPanel.add(moneyLabel).align(Align.right).pad(5 , 5 , 5 ,10).expand();
        towerListContainer.add(moneyStatPanel).align(Align.right).size(120 , 30).spaceBottom(2).row();
        Table grayPanel2 = createGrayPanel();
        _laserTowerIcon = new Image(_skin , "laser-tower-icon");
        _plastmaTowerIcon = new Image(_skin , "plastma-tower-icon");
        _missleTurretIcon = new Image(_skin,"missile-tower-icon");
        _laserTowerIcon.addListener(_laserTowerTooltipTable.getTooltip());
        _plastmaTowerIcon.addListener(_plastmaTowerTooltipTable.getTooltip());
        _missleTurretIcon.addListener(_missileTowerTooltipTable.getTooltip());
        grayPanel2.add(_laserTowerIcon).size(32,32).pad(10).align(Align.topLeft);
        grayPanel2.add(_plastmaTowerIcon).size(32,32).pad(10).align(Align.topLeft);
        grayPanel2.add(_missleTurretIcon).size(32, 32).pad(10).align(Align.topLeft).expand(32,32);
        towerListContainer.add(grayPanel2);
        return towerListContainer;
    }

    private Table createRootTable(){
        Table root = new Table(_skin);
        root.setBounds(0 , 0 , Gdx.graphics.getWidth() , 175);
        return root;
    }

    private Table createMidSection(){
        //  add ui components to root table
        Table waveInfoContainer = new Table(_skin);
        Table nextEnemyTable = createStatPanel();
        Table nextWaveTable = createStatPanel();
        Table buttonContainer = new Table(_skin);
        Table MidContainer = new Table(_skin);

        // labels
        Label nextEnemyText = new Label("Next Enemy", _skin, "statPanelFontWaveInfo", "white");
        _next_enemy_value = new Label("",_skin , "statPanelFontWaveInfo","white");

        Label nextWaveTimeText = new Label("In:", _skin, "statPanelFontWaveInfo", "white");
        _next_wave_time_value = new Label("",_skin , "statPanelFontWaveInfo","white");
        // buttons
        _nextWaveBtn = new TextButton("START",_skin);
        // tables
        waveInfoContainer.add(nextEnemyText).align(Align.bottomLeft);
        waveInfoContainer.add(nextWaveTimeText).align(Align.bottomLeft).row();
        nextEnemyTable.add(_next_enemy_value).align(Align.center);
        nextWaveTable.add(_next_wave_time_value).align(Align.bottom).expand().prefWidth(62).align(Align.center);
        waveInfoContainer.add(nextEnemyTable).align(Align.bottomLeft).expand().spaceRight(3f);
        waveInfoContainer.add(nextWaveTable).align(Align.bottomRight).expand().spaceLeft(3f);
        waveInfoContainer.setWidth(150);
        MidContainer.add(waveInfoContainer).expand().align(Align.bottom).row();
        buttonContainer.add(_nextWaveBtn).expand().fill().spaceTop(0);
        MidContainer.add(buttonContainer).expand().fillX().spaceTop(0).align(Align.bottom).padBottom(2);
        //MidContainer.debug();
        return MidContainer;
    }

    private Table createStatPanel(){
    	Table table = new Table(_skin);
    	table.setBackground("statPanel");
    	return table;
    }

    private Table createGrayPanel(){
    	Table table = new Table(_skin);
    	table.setBackground("gray-panel");
    	return table;
    }

    private void createPauseWindow() {
        _pauseWindow = new Window("Pause",_skin);
        _pauseWindow.setVisible(false);
        Table root = new Table(_skin);
        _resumeButton = new TextButton("Resume",_skin);
        _mainMenuButton = new TextButton("Main Menu",_skin);
        root.add(_resumeButton).padBottom(10).row();
        root.add(_mainMenuButton);
        _pauseWindow.add(root);
    }

    

    @Override
    public void act(float delta) {
    	super.act(delta);
        isOverUpgradeButton = _upgradeBtn.isOver();
        if (PlayState.START_GAME) _next_wave_time_value.setText(String.format("%d : %.0f",WaveTimeManager.CURRENT_WAVE_TIME ,  WaveTimeManager.CURRENT_WAVE_TIME_MILLIS * 100));
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

    /** getters **/
    public Table getRoot(){
        return _rootTable;
    }
    public Table getUiPanel() {
        return uiPanel;
    }

    public TextButton getResumeButton() {
        return _resumeButton;
    }

    public TextButton getMainMenuButton() {
        return _mainMenuButton;
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

    public Label get_nextWaveTimeValue() {return _next_wave_time_value;}


    public Stage getStage(){
        return this;
    }

    public TooltipTable getLaserTowerTooltip() {
        return _laserTowerTooltipTable;
    }
    
    public PlastmaTowerTooltipTable getPlastmaTowerTooltipTable() {
		return _plastmaTowerTooltipTable;
	}


	public MissileTowerTooltipTable getMissileTowerTooltipTable() {
		return _missileTowerTooltipTable;
	}


    public Image get_laserTowerIcon() {
        return _laserTowerIcon;
    }
    
    public Image getPlastmaTowerIcon(){
    	return _plastmaTowerIcon;
    }

    public TextButton get_nextWaveBtn() {
        return _nextWaveBtn;
    }

    public TextButton getSellBtn() {
        return _sellBtn;
    }

    public TextButton getUpgradeBtn() {
        return _upgradeBtn;
    }

    public Label get_next_enemy_value() {
        return _next_enemy_value;
    }

    public Skin getSkin() {
        return _skin;
    }

    public TextureAtlas getAtlas() {
        return _atlas;
    }


    public Table getTowerSelectPanel() {
        return _towerSelectPanel;
    }

    public Label getMoneyLabel() { return moneyLabel; }
    
    public Window get_pauseWindow() {
        return _pauseWindow;
    }

    public Label getHealthLabel() {
        return healthLabel;
    }

    public boolean isOverUpgradeButton() {
        return isOverUpgradeButton;
    }

	public Image getMissleTowerIcon() {
		// TODO Auto-generated method stub
		return _missleTurretIcon;
	}
}
