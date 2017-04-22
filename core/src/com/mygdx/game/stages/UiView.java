package com.mygdx.game.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.managers.WaveTimeManager;
import com.mygdx.game.states.PlayState;

public class UiView implements Screen {

	private Table _rootTable, _nxtEnemyPanel, _grayPanel1, _grayPanel2;//, _towerListPanel;
	private Tooltip<Table> _tooltip;
	private Image _laserTowerIcon;
	// private Dialog _towerHoverTooptip;
	private TextButton _nextWaveBtn, _sellBtn, _upgradeBtn;
	// private Label _towersLbl;
	private Label _nextEnemyLbl;
	private Label sellPriceLbl;
	private Label upgradePriceLbl;
	private Label _nextEnemyText;
	// private Label _nextWaveTimeLbl;
	private Label _nextWaveTimeValue;
	private Skin _skin;
	private TextureAtlas _atlas;
	private Stage _uiStage;
	private BitmapFont font16;
	private BitmapFont font10;
	private Table _tooltipTable;
	private TooltipManager _manager;

	// tooltip components
	private Label toolTip_tower_lbl;
	private Label toolTip_stat_firerate_lbl;
	private Label toolTip_stat_damage_lbl;
	private Label toolTip_stat_range_lbl;
	private Label toolTip_stat_special_lbl;
	private Label toolTip_val_firerate_lbl;
	private Label toolTip_val_damage_lbl;
	private Label toolTip_val_range_lbl;
	private Label toolTip_val_special_lbl;
	private Label toolTip_stat_price_lbl;
	private Label toolTip_val_price_lbl;

	public UiView() {
	}

	@Override
	public void show() {
		font16 = createFont(16);
		font10 = createFont(10);
		OrthographicCamera _uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		_uiStage = new Stage(new ScreenViewport(_uiCamera));
		_skin = new Skin();
		_atlas = new TextureAtlas("interface/ui/atlas-ui.txt");
		_skin.addRegions(_atlas);
		_skin.add("default-font", font16);
		_skin.add("tooltipFont", font10);
		_skin.load(Gdx.files.internal("interface/ui/uiSkin.json"));

		Table waveInfoContainer = new Table(_skin);
		Table towerTargetContainer = new Table(_skin);
		Table towerListContainer = new Table(_skin);
		// tooltip stuff
		_tooltipTable = new Table(_skin);
		_tooltipTable.background(_skin.getDrawable("black-rectangle-hover"));
		_laserTowerIcon = new Image(_skin.getDrawable("turret-icon"));
		_laserTowerIcon.setSize(32, 32);
		_manager = new TooltipManager();
		_manager.instant();
		_manager.offsetX = 0;
		_manager.offsetY = 0;
		_manager.animations = false;
		_tooltip = new Tooltip<>(_tooltipTable, _manager);
		_laserTowerIcon.addListener(_tooltip);
		_laserTowerIcon.addListener(new InputListener() {
		 	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
		 		Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
		 		return false;
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		 		Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
		 	}
		 });

		// labels for tooltip
		toolTip_tower_lbl = new Label("Laser Tower", _skin);
		toolTip_stat_firerate_lbl = new Label("Fire rate:", _skin);
		toolTip_val_firerate_lbl = new Label("134", _skin);
		toolTip_stat_damage_lbl = new Label("Damage:", _skin);
		toolTip_val_damage_lbl = new Label("140", _skin);
		toolTip_stat_range_lbl = new Label("Range:", _skin);
		toolTip_val_range_lbl = new Label("20", _skin);
		toolTip_stat_special_lbl = new Label("Special:", _skin);
		toolTip_val_special_lbl = new Label("None", _skin);
		toolTip_stat_price_lbl = new Label("Price:", _skin);
		toolTip_val_price_lbl = new Label("250", _skin);
		// tooltip label styling
		// add labels to tooltip table
		_tooltipTable.defaults().grow();
		_tooltipTable.add(toolTip_tower_lbl).align(Align.left);
		_tooltipTable.row();
		_tooltipTable.add(toolTip_stat_firerate_lbl).align(Align.left);
		_tooltipTable.add(toolTip_val_firerate_lbl).align(Align.right);
		_tooltipTable.row();
		_tooltipTable.add(toolTip_stat_damage_lbl).align(Align.left);
		_tooltipTable.add(toolTip_val_damage_lbl).align(Align.right);
		_tooltipTable.row();
		_tooltipTable.add(toolTip_stat_range_lbl).align(Align.left);
		_tooltipTable.add(toolTip_val_range_lbl).align(Align.right);
		_tooltipTable.row();
		_tooltipTable.add(toolTip_stat_special_lbl).align(Align.left);
		_tooltipTable.add(toolTip_val_special_lbl).align(Align.right);
		_tooltipTable.row();
		_tooltipTable.row();
		_tooltipTable.add(toolTip_stat_price_lbl).align(Align.left);
		_tooltipTable.add(toolTip_val_price_lbl).align(Align.right);
		_tooltipTable.pad(5);
		_tooltipTable.align(Align.left);
		// labels
		_nextEnemyLbl = new Label("Next Enemy in:", _skin);
		_nextEnemyText = new Label("FLYING", _skin, "default");
		_nextWaveTimeValue = new Label("--", _skin);
		// buttons
		_nextWaveBtn = new TextButton("START", _skin);
		// tables
		_nxtEnemyPanel = new Table(_skin);
		_nxtEnemyPanel.setBackground(_skin.getDrawable("gray-panel"));
		_nxtEnemyPanel.add(_nextEnemyText);

		// root table
		_rootTable = new Table(_skin);
		_rootTable.setBackground("uibg");
		// PANEL that show info about the selected tower
		_grayPanel1 = new Table(_skin);
		_grayPanel1.setBackground(_skin.getDrawable("gray-panel"));
		_grayPanel2 = new Table(_skin);
		_grayPanel2.setBackground(_skin.getDrawable("gray-panel"));
		_grayPanel2.add(_laserTowerIcon).size(32, 32).pad(2).align(Align.topLeft).expand();
		// PANELS
		_rootTable.setBounds(0, 0, Gdx.graphics.getWidth(), 120);

		towerListContainer.add(_grayPanel2).prefHeight(110).expand().fill();
		// add ui components to root table
		Table nextEnemyTable = new Table(_skin);
		nextEnemyTable.add(_nextEnemyLbl).align(Align.left).expand();
		nextEnemyTable.add(_nextWaveTimeValue).align(Align.left).expand();
		waveInfoContainer.add(nextEnemyTable).expand().fill();
		waveInfoContainer.row();
		waveInfoContainer.add(_nxtEnemyPanel).height(45).align(Align.left).expand();
		waveInfoContainer.row();
		waveInfoContainer.add(_nextWaveBtn).height(40).align(Align.left).expand();
		towerTargetContainer.add(_grayPanel1).prefHeight(110).expand().fill().padRight(2);
		waveInfoContainer.debug();
		// add to root
		_rootTable.add(towerListContainer).expand().fill();
		_rootTable.add(towerTargetContainer).expand().fill();
		_rootTable.add(waveInfoContainer).expand().fill();
		// align stuff
		_rootTable.align(Align.bottomRight).pad(5, 5, 5, 5);
		_rootTable.getCell(towerTargetContainer).getActor().getCell(_grayPanel1).expand();
		// add root table to stage
		_uiStage.addActor(_rootTable);
		_rootTable.debug();
	}

	private static BitmapFont createFont(int fontSize) {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/HEMIHEAD.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = fontSize;
		parameter.genMipMaps = true;
		parameter.minFilter = Texture.TextureFilter.Linear;
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.gamma = 1f;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}

	@Override
	public void render(float delta) {
		getStage().act(delta);
		if (PlayState.START_GAME)
			_nextWaveTimeValue.setText(String.valueOf(WaveTimeManager.CURRENT_WAVE_TIME));
		getStage().draw();
	}

	@Override
	public void resize(int width, int height) {
		_rootTable.setWidth(Gdx.graphics.getWidth());
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
	public Label get_nextWaveTimeValue() {
		return _nextWaveTimeValue;
	}

	public Label getToolTip_tower_lbl() {
		return toolTip_tower_lbl;
	}

	public Label getToolTip_val_firerate_lbl() {
		return toolTip_val_firerate_lbl;
	}

	public Label getToolTip_val_damage_lbl() {
		return toolTip_val_damage_lbl;
	}

	public Label getToolTip_val_range_lbl() {
		return toolTip_val_range_lbl;
	}

	public Label getToolTip_val_special_lbl() {
		return toolTip_val_special_lbl;
	}

	public Label getToolTip_val_price_lbl() {
		return toolTip_val_price_lbl;
	}

	public Skin getSkin() {
		return _skin;
	}

	public Stage getStage() {
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

	public Label get_nextEnemyLbl() {
		return _nextEnemyLbl;
	}

	public Label getSellPriceLbl() {
		return sellPriceLbl;
	}

	public Label getUpgradePriceLbl() {
		return upgradePriceLbl;
	}

	public Label get_nextEnemyText() {
		return _nextEnemyText;
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

	public BitmapFont getFont16() {
		return font16;
	}

	public Table get_tooltipTable() {
		return _tooltipTable;
	}

	public TooltipManager get_manager() {
		return _manager;
	}

	/** action listeners ***/
	public void addNextWaveButtonnListener(ClickListener clickListener) {
		_nextWaveBtn.addListener(clickListener);
	}
}
