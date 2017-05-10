package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.managers.WaveTimeManager;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.Assets;


public class UiView implements Screen {

    private Table _rootTable;
    private Tooltip<Table> _tooltip;
    private Image _laserTowerIcon;
    private TextButton _nextWaveBtn;
    private Label _next_enemy_value;
    private Label _next_wave_time_value;
    private Skin _skin;
    private TextureAtlas _atlas;
    private Stage _uiStage;
    private Label healthLabel;
    private Label moneyLabel;

    // tooltip for tower
    private Table _tooltipTable;
    private TooltipManager _manager;
    private Label toolTip_towerName_lbl;
    private Label toolTip_val_fireRate_lbl;
    private Label toolTip_val_damage_lbl;
    private Label toolTip_val_range_lbl;
    private Label toolTip_val_special_lbl;
    private Label toolTip_val_price_lbl;
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
    private Color green;
    private boolean isOverUpgradeButton;

    public UiView(){

    }

    @Override
    public void show() {
        // colors for fonts
        green = new Color(0.1f , 0.8f , 0.1f,1f);
        OrthographicCamera _uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        _uiStage = new Stage(new ScreenViewport(_uiCamera));
        // create skin
        _skin = createSkin();
        // create pause window
        createPauseWindow();
        // create tooltip
        createTooltipTable();

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
        _uiStage.addActor(_rootTable);
        _pauseWindow.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        _uiStage.addActor(_pauseWindow);
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
            _towerSelectName.setColor(green);
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
        grayPanel2.add(_laserTowerIcon).size(32,32).pad(10).align(Align.topLeft).expand();
        towerListContainer.add(grayPanel2).prefHeight(110).expand().fill();
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
        nextWaveTable.add(_next_wave_time_value).align(Align.bottom).prefSize(40 , 31);
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

    private void createTooltipTable(){
        // tooltip stuff
        _tooltipTable = new Table(_skin);
        _tooltipTable.background(_skin.getDrawable("black-rectangle-hover"));
        _laserTowerIcon = new Image(_skin.getDrawable("turret-icon"));
        _laserTowerIcon.setSize(32 , 32);
        _manager = new TooltipManager();
        _manager.instant();
        _manager.offsetX = 0;
        _manager.offsetY = 0;
        _manager.animations = false;
        _tooltip = new Tooltip<>(_tooltipTable , _manager);
        _laserTowerIcon.addListener(_tooltip);

        // labels for tooltip
        toolTip_towerName_lbl = new Label("Laser Tower",_skin , "tooltipFont" , "white");
        toolTip_towerName_lbl.setColor(green);
        Label toolTip_text_fireRate_lbl = new Label("Fire rate:", _skin);
        toolTip_val_fireRate_lbl = new Label("2/s" , _skin);
        Label toolTip_text_damage_lbl = new Label("Damage:", _skin);
        toolTip_val_damage_lbl = new Label("10",_skin);
        Label toolTip_text_range_lbl = new Label("Range:", _skin);
        toolTip_val_range_lbl = new Label("20",_skin);
        Label description = new Label("The Laser Turret fires quickly but with moderate damage.", _skin);
        description.setColor(green);
        description.setWrap(true);
        Label toolTip_text_special_lbl = new Label("Special:", _skin);
        toolTip_val_special_lbl = new Label("Fast",_skin);
        Label toolTip_text_price_lbl = new Label("Price:", _skin);
        toolTip_val_price_lbl = new Label("25",_skin);
        // Add labels to tooltip table
        _tooltipTable.defaults().grow();
        _tooltipTable.add(toolTip_towerName_lbl).align(Align.left);
        _tooltipTable.row();
        _tooltipTable.add(toolTip_text_fireRate_lbl).align(Align.left);
        _tooltipTable.add(toolTip_val_fireRate_lbl).align(Align.right);
        _tooltipTable.row();
        _tooltipTable.add(toolTip_text_damage_lbl).align(Align.left);
        _tooltipTable.add(toolTip_val_damage_lbl).align(Align.right);
        _tooltipTable.row();
        _tooltipTable.add(toolTip_text_range_lbl).align(Align.left);
        _tooltipTable.add(toolTip_val_range_lbl).align(Align.right);
        _tooltipTable.row();
        _tooltipTable.add(toolTip_text_special_lbl).align(Align.left);
        _tooltipTable.add(toolTip_val_special_lbl).align(Align.right);
        _tooltipTable.row();
        _tooltipTable.add(description).align(Align.left).colspan(2);
        _tooltipTable.row();
        _tooltipTable.add(toolTip_text_price_lbl).align(Align.left);
        _tooltipTable.add(toolTip_val_price_lbl).align(Align.right);
        _tooltipTable.pad(8);
        _tooltipTable.align(Align.left);
    }

    @Override
    public void render(float delta) {
        getStage().act(delta);
        isOverUpgradeButton = _upgradeBtn.isOver();
        if (PlayState.START_GAME) _next_wave_time_value.setText(String.format("%d : %d",WaveTimeManager.CURRENT_WAVE_TIME ,  WaveTimeManager.CURRENT_WAVE_TIME_MILLIS));
        getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        _rootTable.setWidth(Gdx.graphics.getWidth());
        _pauseWindow.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        _uiStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        _atlas.dispose();
        _skin.dispose();
        _uiStage.dispose();
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

    public Label gettoolTip_towerName_lbl() {return toolTip_towerName_lbl;}

    public Label gettoolTip_val_fireRate_lbl() {return toolTip_val_fireRate_lbl;}

    public Label getToolTip_val_damage_lbl() {return toolTip_val_damage_lbl;}

    public Label getToolTip_val_range_lbl() {return toolTip_val_range_lbl;}

    public Label getToolTip_val_special_lbl() {return toolTip_val_special_lbl;}

    public Label getToolTip_val_price_lbl() {return toolTip_val_price_lbl;}

    public Stage getStage(){
        return _uiStage;
    }

    public Table getTooltipTable() {
        return _tooltipTable;
    }

    public Tooltip<Table> get_tooltip() {
        return _tooltip;
    }

    public Image get_laserTowerIcon() {
        return _laserTowerIcon;
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

    public Stage get_uiStage() {
        return _uiStage;
    }

    public Table get_tooltipTable() {
        return _tooltipTable;
    }

    public Table getTowerSelectPanel() {
        return _towerSelectPanel;
    }

    public TooltipManager get_manager() {
        return _manager;
    }

    public Label getMoneyLabel() { return moneyLabel; }
    
    public Window get_pauseWindow() {
        return _pauseWindow;
    }

    public Label getHealthLabel() {
        return healthLabel;
    }

    public Color getGreen() {
        return green;
    }

    public boolean isOverUpgradeButton() {
        return isOverUpgradeButton;
    }
}
