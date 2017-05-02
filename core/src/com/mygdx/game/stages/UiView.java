package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.managers.WaveTimeManager;
import com.mygdx.game.states.PlayState;
import com.mygdx.game.utils.Assets;


public class UiView implements Screen {

    private Table _rootTable , _nxtEnemyPanel , _grayPanel1 , _grayPanel2 ;
    private Tooltip<Table> _tooltip;
    private Image _laserTowerIcon;
    private TextButton _nextWaveBtn , _sellBtn , _upgradeBtn;
    private Label _next_enemy_text;
    private Label _next_enemy_value;
    private Label sellPriceLbl;
    private Label upgradePriceLbl;
    private Label _next_wave_time_text;
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
    private Label toolTip_text_fireRate_lbl;
    private Label toolTip_text_damage_lbl;
    private Label toolTip_text_range_lbl;
    private Label toolTip_text_special_lbl;
    private Label toolTip_text_price_lbl;
    // pause window
    private Window _pauseWindow;

    private GameStateManager _gsm;
    private Table uiPanel;


    public UiView(GameStateManager gsm){
        _gsm = gsm;
    }

    @Override
    public void show() {
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
        _grayPanel1 = createGrayPanel();

        // add to root
        Table leftSection = createLeftSection();
        Table midSection = createMidSection();
        Table rightSection = createRightSection();
        uiPanel = createUiPanel();
        uiPanel.add(leftSection).size(328,165).expand().align(Align.bottomRight).pad(3,3,3,3);
        uiPanel.add(midSection).fill().align(Align.center).pad(3,3,3,3);
        uiPanel.add(rightSection).size(328,165).expand().align(Align.bottomLeft).pad(3,3,3,3);
        uiPanel.debug();
        _rootTable.add(uiPanel)
                .prefHeight(165)
                .align(Align.center)
                .fill(true , true)
                .prefWidth(leftSection.getPrefWidth() + midSection.getPrefWidth() + rightSection.getPrefWidth());
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
        skin.add("tooltipFont",Assets.font24);
        skin.load(Gdx.files.internal("interface/ui/uiSkin.json"));
        return skin;
    }

    private Table createRightSection() {
        Table towerTargetContainer = new Table(_skin);
        healthLabel = new Label("",_skin , "statPanelFont" , "white");
        Image heart = new Image(_skin,"heart");
        // stats panels that show hp , and money
        Table healthStatPanel = createStatPanel();
        healthStatPanel.add(heart).size(21,18).align(Align.left).pad(5).expand();
        healthStatPanel.add(healthLabel).align(Align.right).pad(5,5,5,10);
        towerTargetContainer.add(healthStatPanel).size(90,30).align(Align.left).expand().row();
        towerTargetContainer.add(_grayPanel1).prefHeight(110).expand().fill();
        return towerTargetContainer;
    }

    private Table createLeftSection() {
        Table towerListContainer = new Table(_skin);
        Table moneyStatPanel = createStatPanel();
        Image coin = new Image(_skin , "coin");
        moneyLabel = new Label("",_skin , "statPanelFont" , "white");
        moneyStatPanel.add(coin).pad(5).align(Align.left).expand().size(23 , 21);
        moneyStatPanel.add(moneyLabel).align(Align.right).pad(5 , 5 , 5 ,10).expand();
        towerListContainer.add(moneyStatPanel).align(Align.right).size(90 , 30).spaceBottom(2).row();

        _grayPanel2 = createGrayPanel();
        _grayPanel2.add(_laserTowerIcon).size(32,32).pad(2).align(Align.topLeft).expand();
        towerListContainer.add(_grayPanel2).prefHeight(110).expand().fill();
        return towerListContainer;
    }

    private Table createRootTable(){
        Table root = new Table(_skin);
        root.setBounds(0 , 0 , Gdx.graphics.getWidth() , 165);
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
        _next_enemy_text = new Label("Next Enemy",_skin);
        _next_enemy_value = new Label("",_skin);

        _next_wave_time_text = new Label("In:",_skin);
        _next_wave_time_value = new Label("",_skin);
        // buttons
        _nextWaveBtn = new TextButton("START",_skin);
        // tables
        _nxtEnemyPanel = new Table(_skin );
        waveInfoContainer.add(_next_enemy_text).align(Align.left);
        waveInfoContainer.add(_next_wave_time_text).align(Align.left).row();
        nextEnemyTable.add(_next_enemy_value).align(Align.center).prefSize(103, 31).pad(5);
        nextWaveTable.add(_next_wave_time_value).align(Align.center).prefSize(103 , 31).pad(5);
        waveInfoContainer.add(nextEnemyTable).align(Align.left).expand().spaceRight(3f);
        waveInfoContainer.add(nextWaveTable).align(Align.right).expand().spaceLeft(3f);
        waveInfoContainer.row();
        waveInfoContainer.add(_nxtEnemyPanel).height(45).align(Align.left).expand();
        waveInfoContainer.row();
        waveInfoContainer.setWidth(204);
        MidContainer.add(waveInfoContainer).expand().row();
        buttonContainer.add(_nextWaveBtn).expandX();
        MidContainer.add(buttonContainer).expand();
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
        TextButton resumeButton = new TextButton("Resume",_skin);
        TextButton mainMenuButton = new TextButton("Main Menu",_skin);
        root.add(resumeButton).padBottom(10).row();
        root.add(mainMenuButton);
        _pauseWindow.add(root);
        // TODO put tease in controller
        resumeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                _pauseWindow.setVisible(false);
                PlayState.PAUSE = false;
            }
        });

        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                _pauseWindow.setVisible(false);
                _gsm.dispose();
                _gsm.setState(GameStateManager.State.MAINMENU);
            }
        });
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
        toolTip_text_fireRate_lbl = new Label("Fire rate:",_skin);
        toolTip_val_fireRate_lbl = new Label("134" , _skin);
        toolTip_text_damage_lbl = new Label("Damage:",_skin);
        toolTip_val_damage_lbl = new Label("140",_skin);
        toolTip_text_range_lbl = new Label("Range:" , _skin);
        toolTip_val_range_lbl = new Label("20",_skin);
        toolTip_text_special_lbl = new Label("Special:" , _skin);
        toolTip_val_special_lbl = new Label("None",_skin);
        toolTip_text_price_lbl = new Label("Price:",_skin);
        toolTip_val_price_lbl = new Label("250",_skin);
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
        _tooltipTable.row();
        _tooltipTable.add(toolTip_text_price_lbl).align(Align.left);
        _tooltipTable.add(toolTip_val_price_lbl).align(Align.right);
        _tooltipTable.pad(5);
        _tooltipTable.align(Align.left);
    }

    @Override
    public void render(float delta) {
        getStage().act(delta);
        if (PlayState.START_GAME) _next_wave_time_value.setText(String.valueOf(WaveTimeManager.CURRENT_WAVE_TIME));
        getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println(Gdx.graphics.getWidth() + " :: " + Gdx.graphics.getHeight());
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
    public Label get_nextWaveTimeValue() {return _next_wave_time_value;}

    public Label gettoolTip_towerName_lbl() {return toolTip_towerName_lbl;}

    public Label gettoolTip_val_fireRate_lbl() {return toolTip_val_fireRate_lbl;}

    public Label getToolTip_val_damage_lbl() {return toolTip_val_damage_lbl;}

    public Label getToolTip_val_range_lbl() {return toolTip_val_range_lbl;}

    public Label getToolTip_val_special_lbl() {return toolTip_val_special_lbl;}

    public Label getToolTip_val_price_lbl() {return toolTip_val_price_lbl;}

    public Skin getSkin() {
        return _skin;
    }

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

    public TextButton get_sellBtn() {
        return _sellBtn;
    }

    public TextButton get_upgradeBtn() {
        return _upgradeBtn;
    }

    public Label get_next_enemy_value() {
        return _next_enemy_value;
    }

    public Label getSellPriceLbl() {
        return sellPriceLbl;
    }

    public Label getUpgradePriceLbl() {
        return upgradePriceLbl;
    }


    public Skin get_skin() {
        return _skin;
    }

    public TextureAtlas get_atlas() {
        return _atlas;
    }

    public Stage get_uiStage() {
        return _uiStage;
    }

    public Table get_tooltipTable() {
        return _tooltipTable;
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
    /** action listeners ***/
    public void addNextWaveButtonnListener(ClickListener clickListener) {
        _nextWaveBtn.addListener(clickListener);
    }

}
